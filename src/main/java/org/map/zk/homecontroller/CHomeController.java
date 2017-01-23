package org.map.zk.homecontroller;

import java.io.File;

import java.util.LinkedList;


//
import org.map.zk.systemconstans.SystemConstants;
import org.map.zk.utilities.SystemUtilities;
import org.map.zk.database.datamodel.TBLUser;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;


import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;
import commonlibs.utils.ZKUtilities;

public class CHomeController extends SelectorComposer<Component> {
    
    private static final long serialVersionUID = -6992273830457634170L;
    
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
        
        final String strLoggerName = SystemConstants._Home_Controller_Logger_Name;
        final String strLoggerFileName = SystemConstants._Home_Controller_File_Log;
        
        controllerLogger = CExtendedLogger.getLogger( strLoggerName + " " + strUser + " " + strLoginDateTime );
        
        if ( controllerLogger.getSetupSet() == false ) {
            
            if ( strLogPath == null )
                
                strLogPath = RunningPath + "/" + SystemConstants._Logs_Dir;
            
            if ( extendedConfigLogger != null )
                
                controllerLogger.setupLogger( strUser + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, extendedConfigLogger.getClassNameMethodName(), extendedConfigLogger.getExactMatch(), extendedConfigLogger.getLevel(), extendedConfigLogger.getLogIP(), extendedConfigLogger.getLogPort(), extendedConfigLogger.getHTTPLogURL(), extendedConfigLogger.getHTTPLogUser(), extendedConfigLogger.getHTTPLogPassword(), extendedConfigLogger.getProxyIP(), extendedConfigLogger.getProxyPort(), extendedConfigLogger.getProxyUser(), extendedConfigLogger.getProxyPassword() );
            
            else
                
                controllerLogger.setupLogger( strUser + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, SystemConstants._Log_Class_Method, SystemConstants._Log_Exact_Match, SystemConstants._Log_Level, "", -1, "", "", "", "", -1, "", "" );
            
           
                controllerLanguage = CLanguage.getLanguage( controllerLogger, RunningPath + SystemConstants._Langs_Dir + strLoggerName + "." + SystemConstants._Lang_Ext );
            
            //Protección para cuando dos usuarios accedan exactamente al mismo tiempo a la página web
            synchronized ( currentSession ) {
                
                //Guardamos en la sesisón los logger que se van creando para luego ser destruidos.
                @SuppressWarnings( "unchecked" )
                LinkedList<String> loggedSessionLoggers = ( LinkedList<String> ) currentSession.getAttribute( SystemConstants._Logged_Session_Loggers );
                
                if ( loggedSessionLoggers != null ) {
                    
                    synchronized ( loggedSessionLoggers ) {
                        
                        //Lo agregamos a la lista
                        loggedSessionLoggers.add( strLoggerName + " " + strUser + " " + strLoginDateTime );
                        
                    }
                    
                    //Lo retornamos la sesión de este operador
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
            
            initView();
            
        }
        catch ( Exception ex ) {
            
            if ( controllerLogger != null )
                controllerLogger.logException( "-1021", ex.getMessage(), ex );
            
        }
        
    }
    
    
public void initView() {
        
        TBLUser tblUser = (TBLUser) Sessions.getCurrent().getAttribute( SystemConstants._Operator_Credential_Session_Key ); 
        
        if ( tblUser != null ) {
            
            if ( labelHeader != null ) {
                
                labelHeader.setValue( tblUser.getRole() );
                
            }
            
        }

  
        Component[] components = Executions.getCurrent().createComponents( "/views/tabs/home/tabhome.zul", null );
       
        Tab tab = (Tab) ZKUtilities.getComponent( components, "Tab" );  
                     
        if ( tab != null ) {
            
           
            tabboxMainContent.getTabs().appendChild( tab );
            
            
            Tabpanel tabPanel = (Tabpanel) ZKUtilities.getComponent( components, "Tabpanel" );      
            
          
            
            
            if ( tabPanel != null )
                tabboxMainContent.getTabpanels().appendChild( tabPanel );
        
        }
        
     	components = Executions.getCurrent().createComponents( "/views/tabs/map/map.zul", null ); 
     	
     	tab = (Tab) ZKUtilities.getComponent( components, "Tab" );//amen
     	
        if ( tab != null ) {
            
            
            tabboxMainContent.getTabs().appendChild( tab );
            
            
            Tabpanel tabPanel = (Tabpanel) ZKUtilities.getComponent( components, "Tabpanel" );      
            
          
            
            
            if ( tabPanel != null )
                tabboxMainContent.getTabpanels().appendChild( tabPanel );
        
        }
        
        
        
        if ( tblUser.getRole().equalsIgnoreCase( "admin" ) ) {
            
            
            components = Executions.getCurrent().createComponents( "/views/tabs/admin/tabadmin.zul", null );
            
            
            tab = (Tab) ZKUtilities.getComponent( components, "Tab" );      
            
            if ( tab != null ) {
                
             
                tabboxMainContent.getTabs().appendChild( tab );
                
                //Buscamos el componente de tipo tabpanel
                Tabpanel tabPanel = (Tabpanel) ZKUtilities.getComponent( components, "Tabpanel" );      
                
               
                if ( tabPanel != null )
                    tabboxMainContent.getTabpanels().appendChild( tabPanel );
            
            }
            
        }
     
        else if ( tblUser.getRole().equalsIgnoreCase( "user.type1" ) ) {
            
            
        }
        else if ( tblUser.getRole().equalsIgnoreCase( "user.type2" ) ) {
            
            
        }
        
}
    
    @Listen( "onClick = #includeNorthContent #buttonChangePassword" )  
    public void onClickbuttonChangePassword( Event event ) {

        if ( controllerLogger != null )
            controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Button change password clicked" ) );
        
    }    

    
    
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    @Listen( "onClick = #includeNorthContent #buttonLogout" )  
    public void onClickbuttonLogout( Event event ) {
        
        if ( controllerLogger != null )
            controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Button change password clicked" ) );
        
        Messagebox.show( "You are sure do you want logout from system?", "Logout", Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
            
         
        	
        	public void onEvent(Event evt) throws InterruptedException {
                
                if ( evt.getName().equals( "onOK" ) ) {
                    
                    if ( controllerLogger != null )
                        controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Logout confirm accepted" ) );
                    
                    Sessions.getCurrent().invalidate(); //limpiar la sessión 
                    
                    Executions.sendRedirect( "/index.zul"); //Enviamos al login
                    
                }
                else {
                    
                    if ( controllerLogger != null )
                        controllerLogger.logMessage( "1" , CLanguage.translateIf( controllerLanguage, "Logout confirm canceled" ) );
                    
                }
                
            }
            
        });         
        
       }
    
    
      
      
    	
}
