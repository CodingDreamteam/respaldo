package org.map.zk.logincontroller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.map.zk.systemconstans.SystemConstants;
import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.CDatabaseConnectionConfig;
import org.map.zk.database.dao.UserDAO;
import org.map.zk.database.datamodel.TBLUser;
import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;
import commonlibs.utils.ZKUtilities;

public class CLoginController extends SelectorComposer<Component> {
    
    private static final long serialVersionUID = 3211193732865097784L;
    
    protected Execution execution = Executions.getCurrent();
    
    protected CDatabaseConnection ConnectionDatabase = null;
    
    protected CExtendedLogger controllogger = null;
    
    protected CLanguage controllanguaje = null;
    
    @Wire
    Textbox textboxOperator;
    
    @Wire
    Textbox textboxPassword;
    
    @Wire
    Label labelMessage;
    
    @Override
    public void doAfterCompose( Component comp ) {
        
        try {
            
            super.doAfterCompose( comp );
            
            controllogger = ( CExtendedLogger ) Sessions.getCurrent().getWebApp().getAttribute( SystemConstants._Webapp_Logger_App_Attribute_Key );
            
        }
        catch ( Exception e ) {
            
            if ( controllogger != null ) {
                
                controllogger.logException( "-1021", e.getMessage(), e );
                
            }
        }
        
    }
    
    //onChangin es una accion que nos permite que cuando seleccionemos un textbox lo limpie si tiene algo dentro
    @Listen( "onChanging=#textboxOperator ;onChanging=#textboxPassword" )
    public void onChangeTextBox( Event event ) {
        
        labelMessage.setValue( "" );
        
    }
    
    @Listen( "onClick=#buttonLogin" )
    public void onClickBlogin( Event event ) {
        
        try {
        	
            //aqui nos conectamos a la DB y verificamos que el user exista y la password sea correcta
            
            final String strUsername = ZKUtilities.getTextBoxValue( textboxOperator, controllogger );
            
            final String strPassword = ZKUtilities.getTextBoxValue( textboxPassword, controllogger );
            
            Session session = Sessions.getCurrent();
            
            if ( strUsername.isEmpty() == false && strPassword.isEmpty() == false ) {
                
                ConnectionDatabase = new CDatabaseConnection();
                
                CDatabaseConnectionConfig config = new CDatabaseConnectionConfig();
                
                String strPatch = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._Web_Inf_Dir );
                
                if ( config.LoadConfig( strPatch + File.separator + SystemConstants._Config_Dir + SystemConstants._Database_Config_File, controllogger, controllanguaje ) ) {
                    
                    if ( ConnectionDatabase.makeConnectionToDatabase( config, controllogger, controllanguaje ) ) {//Si logra conectarse  
                        
                        TBLUser tblUser = UserDAO.checkData( ConnectionDatabase, strUsername, strPassword, controllogger, controllanguaje );
                        
                        if ( tblUser != null ) {
                            
                            Session currentSession = Sessions.getCurrent();
                            
                            labelMessage.setSclass( "" );
                            
                            labelMessage.setValue( "Welcome " + tblUser.getName() + "!" );
                            
                            session.setAttribute( SystemConstants._DB_Connection_Session_Key, ConnectionDatabase );
                            
                            session.setAttribute( SystemConstants._Operator_Credential_Session_Key, tblUser );
                            
                            controllogger.logMessage( "1", CLanguage.translateIf( controllanguaje, "Saved user credential in session for user [%s]", tblUser.getName() ) );
                            
                            //Obtenemos la fecha y la hora en el formato 
                            String strDateTime = Utilities.getDateInFormat( ConstantsCommonClasses._Global_Date_Time_Format_File_System_24, null );
                            
                            //Creamos la variable del logpath
                            String strLogPath = strPatch + File.separator + SystemConstants._Logs_Dir + strUsername + File.separator + strDateTime + File.separator;
                            
                            //Guardamos en la sesion
                            currentSession.setAttribute( SystemConstants._Log_Path_Session_Key, strLogPath );
                            
                            controllogger.logMessage( "1", CLanguage.translateIf( controllanguaje, "Saved user log path [%s] in session for user [%s]", strLogPath, strUsername ) );
                            
                            //Guardamos la fecha y la hora del inicio de sesión
                            currentSession.setAttribute( SystemConstants._Login_Date_Time_Session_Key, strDateTime );
                            
                            controllogger.logMessage( "1", CLanguage.translateIf( controllanguaje, "Saved user login date time [%s] in session for user [%s]", strDateTime, strUsername ) );
                            
                            //Creamos la lista de logger de esta sesion
                            List<String> loggedSessionLoggers = new LinkedList<String>();
                            
                            //Guardamos la lista vacia en la sesion
                            currentSession.setAttribute( SystemConstants._Logged_Session_Loggers, loggedSessionLoggers );
                            
                            //Actualizamos en bd el último inicio de sesión
                            UserDAO.updateLogin( ConnectionDatabase, tblUser.getID(), controllogger, controllanguaje );
                            
                            //Redirecionamos hacia el home.zul
                            Executions.sendRedirect( "/views/home/home.zul" );
                            
                        }
                        else {
                            
                            labelMessage.setValue( "error wrong user or password" );
                            
                        }
                    }
                    else {
                        
                        Messagebox.show( "Connection Failed." );
                        
                    }
                    
                }
                else {
                    
                    Messagebox.show( "Error When reading the configuration file" );
                    
                }
                
            }
        }
        catch ( Exception e ) {
            
            if ( controllogger != null ) {
                
                controllogger.logException( "-1021", e.getMessage(), e );
                
            }
        }
        
    }
    
    @Listen( "onTimer=#TimerSession" )
    public void onTimer( Event event ) {
        
        Clients.showNotification( "automatic session successful", "info", null, "before_center", 2000 );
        
    }
    
}
