package org.map.zk.controllers.editor;

import java.time.LocalDate;
import java.util.LinkedList;

import org.map.zk.database.dao.*;
import org.map.zk.database.CDatabaseConnection;
import org.map.zk.database.datamodel.*;
import org.map.zk.systemconstans.SystemConstants;
import org.map.zk.utilities.SystemUtilities;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Selectbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import commonlibs.commonclasses.CLanguage;
import commonlibs.commonclasses.ConstantsCommonClasses;
import commonlibs.extendedlogger.CExtendedConfigLogger;
import commonlibs.extendedlogger.CExtendedLogger;
import commonlibs.utils.Utilities;

public class CEditorController extends SelectorComposer<Component> {
    
    private static final long serialVersionUID = -4893774424235516302L;
    
    @Wire
    Window windowPerson;
    
    @Wire
    Textbox textboxCi;
    
    @Wire
    Textbox textboxFirstName;
    
    @Wire
    Textbox textboxLastName;
    
    @Wire
    Datebox dateboxBirthdate;
    
    @Wire
    Selectbox selectboxGender;
    
    @Wire
    Textbox textboxComment;
    
    protected CExtendedLogger controllerLogger = null;
    
    protected CLanguage controllerLanguage = null;
    
    protected ListModelList<String> datamodel = new ListModelList<String>();
    
    protected Listbox listboxPersons = null;
    
    protected final Execution execution = Executions.getCurrent();
    
    TBLPerson personToModify = null;
    
    protected CDatabaseConnection dbConnection = null;
    
    protected String PersonaCi = null;
    
    public void initcontrollerLoggerAndcontrollerLanguage( String strRunningPath, Session currentSession ) {
        
        CExtendedConfigLogger extendedConfigLogger = SystemUtilities.initLoggerConfig( strRunningPath, currentSession );
        
        TBLUser operatorCredential = ( TBLUser ) currentSession.getAttribute( SystemConstants._Operator_Credential_Session_Key );
        
        String strOperator = SystemConstants._Operator_Unknown;
        String strLoginDateTime = ( String ) currentSession.getAttribute( SystemConstants._Login_Date_Time_Session_Key );
        String strLogPath = ( String ) currentSession.getAttribute( SystemConstants._Log_Path_Session_Key );
        
        if ( operatorCredential != null )
            strOperator = operatorCredential.getName();
        
        if ( strLoginDateTime == null )
            strLoginDateTime = Utilities.getDateInFormat( ConstantsCommonClasses._Global_Date_Time_Format_File_System_24, null );
        
        final String strLoggerName = SystemConstants._Person_Editor_Controller_Logger_Name;
        final String strLoggerFileName = SystemConstants._Person_Editor_Controller_File_Log;
        
        controllerLogger = CExtendedLogger.getLogger( strLoggerName + " " + strOperator + " " + strLoginDateTime );
        
        if ( controllerLogger.getSetupSet() == false ) {
            
            if ( strLogPath == null )
                strLogPath = strRunningPath + "/" + SystemConstants._Logs_Dir;
            
            if ( extendedConfigLogger != null )
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, extendedConfigLogger.getClassNameMethodName(), extendedConfigLogger.getExactMatch(), extendedConfigLogger.getLevel(), extendedConfigLogger.getLogIP(), extendedConfigLogger.getLogPort(), extendedConfigLogger.getHTTPLogURL(), extendedConfigLogger.getHTTPLogUser(), extendedConfigLogger.getHTTPLogPassword(), extendedConfigLogger.getProxyIP(), extendedConfigLogger.getProxyPort(), extendedConfigLogger.getProxyUser(), extendedConfigLogger.getProxyPassword() );
            else
                controllerLogger.setupLogger( strOperator + " " + strLoginDateTime, false, strLogPath, strLoggerFileName, SystemConstants._Log_Class_Method, SystemConstants._Log_Exact_Match, SystemConstants._Log_Level, "", -1, "", "", "", "", -1, "", "" );
            
            controllerLanguage = CLanguage.getLanguage( controllerLogger, strRunningPath + SystemConstants._Langs_Dir + strLoggerName + "." + SystemConstants._Lang_Ext );
            
            synchronized ( currentSession ) {
                
                @SuppressWarnings( "unchecked" )
                LinkedList<String> loggedSessionLoggers = ( LinkedList<String> ) currentSession.getAttribute( SystemConstants._Logged_Session_Loggers );
                
                if ( loggedSessionLoggers != null ) {
                    
                    synchronized ( loggedSessionLoggers ) {
                        
                        loggedSessionLoggers.add( strLoggerName + " " + strOperator + " " + strLoginDateTime );
                        
                    }
                    
                    currentSession.setAttribute( SystemConstants._Logged_Session_Loggers, loggedSessionLoggers );
                    
                }
                
            }
            
        }
        
    }//
    
    public void doAfterCompose( Component comp ) {
        
        try {
            super.doAfterCompose( comp );
            final String strRunningpath = Sessions.getCurrent().getWebApp().getRealPath( SystemConstants._Web_Inf_Dir );
            initcontrollerLoggerAndcontrollerLanguage( strRunningpath, Sessions.getCurrent() );
            controllerLogger = ( CExtendedLogger ) Sessions.getCurrent().getWebApp().getAttribute( ConstantsCommonClasses._Webapp_Logger_App_Attribute_Key );
            dateboxBirthdate.setFormat( "dd-MM-yyyy" );
            datamodel.add( "Female" );
            datamodel.add( "Male" );
            selectboxGender.setModel( datamodel );
            selectboxGender.setSelectedIndex( 0 );
            datamodel.addToSelection( "Female" );
            
            listboxPersons = ( Listbox ) execution.getArg().get( "listboxPersons" );
            
            PersonaCi = ( String ) execution.getArg().get( "PersonaCi" );
            
            Session currentSession = Sessions.getCurrent();
            
            if ( currentSession.getAttribute( SystemConstants._DB_Connection_Session_Key ) instanceof CDatabaseConnection ) {
                
                dbConnection = ( CDatabaseConnection ) currentSession.getAttribute( SystemConstants._DB_Connection_Session_Key );
                
                personToModify = new TBLPerson();
                
                if ( execution.getArg().get( "PersonaCi" ) instanceof String ) {
                    
                    personToModify = PersonDAO.loadData( dbConnection, PersonaCi, controllerLogger, controllerLanguage );
                    
                }
            }
            
            if ( PersonaCi != null ) {
                
                textboxCi.setValue( personToModify.getID() );
                textboxFirstName.setValue( personToModify.getFirstName() );
                textboxLastName.setValue( personToModify.getLastName() );
                textboxComment.setValue( personToModify.getComment() );
                if ( personToModify.getGender() == 0 ) {
                    
                    datamodel.addToSelection( "Female" );
                    
                }
                else {
                    
                    datamodel.addToSelection( "Male" );
                    
                }
                
                dateboxBirthdate.setValue( java.sql.Date.valueOf( personToModify.getBirthdate() ) );
                
            }
            
        }
        
        catch ( Exception e ) {
            
            if ( controllerLogger != null ) {
                
                controllerLogger.logException( "-1021", e.getMessage(), e );
                
            }
        }
    }
    
    @Listen( "onClick=#buttonSave" )
    public void onClickButtonGuardar( Event event ) {
        
        if ( dateboxBirthdate.getValue() != null ) {
            LocalDate localDate = new java.sql.Date( dateboxBirthdate.getValue().getTime() ).toLocalDate();
            personToModify.setID( textboxCi.getValue() );
            personToModify.setFirstName( textboxFirstName.getValue() );
            personToModify.setLastName( textboxLastName.getValue() );
            personToModify.setGender( selectboxGender.getSelectedIndex() );
            personToModify.setBirthdate( localDate );
            personToModify.setComment( textboxComment.getValue() );
            
            if ( ( !personToModify.getID().equals( "" ) ) && ( !personToModify.getFirstName().equals( "" ) ) && ( !personToModify.getLastName().equals( "" ) ) && ( personToModify.getGender() >= 0 ) && ( personToModify.getBirthdate() != null ) && ( !personToModify.getComment().equals( "" ) ) ) {
                
                if ( PersonaCi == null ) {
                    
                    PersonDAO.insertData( dbConnection, personToModify, controllerLogger, controllerLanguage );
                    
                    Events.echoEvent( new Event( "onKek", listboxPersons, personToModify ) );
                    
                    windowPerson.detach();
                    
                }
                else {
                    
                    PersonDAO.updateData( dbConnection, personToModify, controllerLogger, controllerLanguage );
                    
                    Events.echoEvent( new Event( "onKek", listboxPersons, personToModify ) );
                    
                    windowPerson.detach();
                    
                }
                
            }
            else {
                
                Messagebox.show( "Error, all fields must be filled", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
                
            }
            
        }
        
        else {
            
            Messagebox.show( "Error, the field date is empty", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
            
        }
    }
    
    @Listen( "onClick=#buttonCancel" )
    public void onClickButtonCancelar( Event event ) {
        
        Messagebox.show( "No change was made", "Aceptar", Messagebox.OK, Messagebox.EXCLAMATION );
        
        windowPerson.detach();
        
    }
    
}
