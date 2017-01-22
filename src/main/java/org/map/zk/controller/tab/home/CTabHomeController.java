package org.map.zk.controller.tab.home;


import java.io.File;

import java.util.LinkedList;


import org.map.zk.database.datamodel.TBLUser;
import org.map.zk.systemconstans.SystemConstants;
import org.map.zk.utilities.SystemUtilities;
import org.zkoss.zk.ui.Component;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;

import org.zkoss.zk.ui.select.SelectorComposer;

import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Tabbox;


import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;

public class CTabHomeController extends SelectorComposer<Component> {


	private static final long serialVersionUID = 4539285024131689394L;
   
	 protected CExtendedLogger controllerLogger = null;
	    
	    protected CLanguage controllerLanguage = null;

	    
	    @Wire( "#includeNorthContent #labelHeader" )
	    Label labelHeader;
	       
	    @Wire
	    Tabbox tabboxMainContent;
	    
	    public void initControllerLoggerAndControllerLanguage( String RunningPath, Session currentSession ) {
	        
	        CExtendedConfigLogger extendedConfigLogger = SystemUtilities.initLoggerConfig( RunningPath, currentSession );
	        
	        TBLUser tblUser = ( TBLUser ) currentSession.getAttribute( SystemConstants._Operator_Credential_Session_Key );
	        
	        String strUser = SystemConstants._Operator_Unknown; 
	        String strLoginDateTime = ( String ) currentSession.getAttribute( SystemConstants._Login_Date_Time_Session_Key ); 
	        String strLogPath = ( String ) currentSession.getAttribute( SystemConstants._Log_Path_Session_Key );
	        if ( tblUser != null )
	            strUser = tblUser.getName(); 
	            
	        if ( strLoginDateTime == null ) 
	            strLoginDateTime = Utilities.getDateInFormat( ConstantsCommonClasses._Global_Date_Time_Format_File_System_24, null );
	        
	        final String strLoggerName = SystemConstants._Tab_Home_Controller_Logger_Name;
	        final String strLoggerFileName = SystemConstants._Tab_Home_Controller_File_Log;
	        
	        controllerLogger = CExtendedLogger.getLogger( strLoggerName + " " + strUser + " " + strLoginDateTime );
	        
	        if ( controllerLogger.getSetupSet() == false ) {
	            
	            if ( strLogPath == null )
	                
	                strLogPath = RunningPath + "/" + SystemConstants._Logs_Dir;
	            
	            if ( extendedConfigLogger != null )
	                
	                controllerLogger.setupLogger( strUser + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, extendedConfigLogger.getClassNameMethodName(), extendedConfigLogger.getExactMatch(), extendedConfigLogger.getLevel(), extendedConfigLogger.getLogIP(), extendedConfigLogger.getLogPort(), extendedConfigLogger.getHTTPLogURL(), extendedConfigLogger.getHTTPLogUser(), extendedConfigLogger.getHTTPLogPassword(), extendedConfigLogger.getProxyIP(), extendedConfigLogger.getProxyPort(), extendedConfigLogger.getProxyUser(), extendedConfigLogger.getProxyPassword() );
	            
	            else
	                
	                controllerLogger.setupLogger( strUser + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, SystemConstants._Log_Class_Method, SystemConstants._Log_Exact_Match, SystemConstants._Log_Level, "", -1, "", "", "", "", -1, "", "" );
	            
	           
	                controllerLanguage = CLanguage.getLanguage( controllerLogger, RunningPath + SystemConstants._Langs_Dir + strLoggerName + "." + SystemConstants._Lang_Ext );
	            
	          
	            synchronized ( currentSession ) {
	                
	                
	                @SuppressWarnings( "unchecked" )
	                LinkedList<String> loggedSessionLoggers = ( LinkedList<String> ) currentSession.getAttribute( SystemConstants._Logged_Session_Loggers );
	                
	                if ( loggedSessionLoggers != null ) {
	                    
	                    synchronized ( loggedSessionLoggers ) {
	                        
	                       
	                        loggedSessionLoggers.add( strLoggerName + " " + strUser + " " + strLoginDateTime );
	                        
	                    }
	                    
	                   
	                    currentSession.setAttribute( SystemConstants._Logged_Session_Loggers, loggedSessionLoggers );
	                    
	                }
	                
	            }
	            
	        }
	        
	    }
	    
	    @Override
	    public void doAfterCompose( Component comp ) {
	        
	        try {
	            
	            super.doAfterCompose( comp );
	            
	            final String strRunningPath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._Web_Inf_Dir ) + File.separator;
	            
	            initControllerLoggerAndControllerLanguage( strRunningPath, Sessions.getCurrent() );
	            
	        }
	        catch ( Exception ex ) {
	            
	            if ( controllerLogger != null )
	                controllerLogger.logException( "-1021", ex.getMessage(), ex );
	            
	        }
	        
	    }
	    
	   
	   


}
