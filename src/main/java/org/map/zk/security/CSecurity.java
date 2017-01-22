package org.map.zk.security;

import java.io.File;
import java.util.Map;

import org.map.zk.database.datamodel.TBLUser;
import org.map.zk.systemconstans.SystemConstants;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.util.Initiator;

import commonlibs.commonclasses.CLanguage;
import commonlibs.extendedlogger.CExtendedLogger;

public class CSecurity implements Initiator {
    
    public void doInit( Page page, Map<String, Object> args ) throws Exception {
        
        detectAuthenticatedAndRedirect( page, args );
        
    }
    
    public static void detectAuthenticatedAndRedirect( Page page, Map<String, Object> args ) {
        
        CExtendedLogger extendedLogger = CExtendedLogger.getLogger( SystemConstants._Check_Logged_Logger_Name );
        
        String strRunningPath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._Web_Inf_Dir ) + File.separator;
        
        if ( extendedLogger != null && extendedLogger.getSetupSet() == false ) {
            
            String strLogPath = strRunningPath + SystemConstants._Logs_Dir + SystemConstants._Security_Dir;
            
            extendedLogger.setupLogger( SystemConstants._Check_Logged_Logger_Name, false, strLogPath, SystemConstants._Check_Logged_Logger_Name, SystemConstants._Log_Class_Method, SystemConstants._Log_Exact_Match, SystemConstants._Log_Level, "", -1, "", "", "", "", -1, "", "" );
            
        }
        
        CLanguage languaje = CLanguage.getLanguage( extendedLogger, strRunningPath + SystemConstants._Langs_Dir + SystemConstants._Security_Dir + SystemConstants._Check_Logged_Logger_Name + "." + SystemConstants._Lang_Ext );
        
        Session currentSession = Sessions.getCurrent();
        
        TBLUser tblUser = ( TBLUser ) currentSession.getAttribute( SystemConstants._Operator_Credential_Session_Key );
        
        String strRequestPath = Executions.getCurrent().getDesktop().getRequestPath();
        
        if ( tblUser != null ) {
            
            String strLoginDateTime = ( String ) currentSession.getAttribute( SystemConstants._Login_Date_Time_Session_Key );
            
            if ( strLoginDateTime == null )
                strLoginDateTime = "";
            
            if ( extendedLogger != null )
                extendedLogger.logMessage( "1", languaje.translate( "Requesting [%s] from user [%s] [%s]", strRequestPath, tblUser.getName(), strLoginDateTime ) );
            
        }
        else {
            
            if ( extendedLogger != null )
                extendedLogger.logMessage( "1", languaje.translate( "Requesting [%s]", strRequestPath ) );
            
        }
        if ( tblUser == null ) {
            
            String strRedirectPath = "/index.zul";
            
            if ( extendedLogger != null )
                extendedLogger.logMessage( "1", languaje.translate( "Redirecting to [%s]", strRedirectPath ) );
            
            if ( strRequestPath.isEmpty() == false && strRedirectPath.equalsIgnoreCase( strRequestPath ) == false ) {
                
                Executions.sendRedirect( strRedirectPath );
                
            }
            
        }
        else if ( strRequestPath.isEmpty() || strRequestPath.contains( "/home.zul" ) == false ) {
            
            String strRedirectPath = "/views/home/home.zul";
           
            if ( extendedLogger != null )
                extendedLogger.logMessage( "1", languaje.translate( "Redirecting to [%s]", strRedirectPath ) );
            
            Executions.sendRedirect( strRedirectPath );
            
        }
        
    }
}
