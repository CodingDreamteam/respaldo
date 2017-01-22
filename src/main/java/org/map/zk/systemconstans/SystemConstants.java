package org.map.zk.systemconstans;

import java.io.File;

public class SystemConstants {
	
	public static final String _Libs_Dir = "libs" + File.separator;
	public static final String _Lins_Ext = ".jar";
	
	public static final String _Langs_Dir  = "langs" + File.separator;
	public static final String _Lang_Ext= "init.lang";
	public static final String _Common_Lang_File = "common.init.lang";
	
	public static final String _Logs_Dir = "logs" + File.separator;
	
	public static final String _Temp_Dir = "temp" + File.separator;
	
	public static final String _Security_Dir = "security" + File.separator;
	
	public static final String _System_Dir = "system" + File.separator;
	
	public static final String _Web_Inf_Dir = "WEB-INF";
	
	public static final String _Config_Dir = "config" + File.separator;
	
	public static final String _Database_Config_File = "database.config.xml";
	
	public static final String _Database_Config_Production_File = "database.production.config.xml";
	
	public static final String _Logger_Config_Name = "logger.config.xml";
	
	public static final String _Logger_Config_Production= "logger.production.config.xml";
	
	public static final String _Operator_Unknown = "unknown@unknown.com";
	
	
    public static final String _DB_Connection_Session_Key = "DatabaseConnection";
    public static final String _Operator_Credential_Session_Key = "operatorCredential";
    public static final String _Login_Date_Time_Session_Key = "loginDateTime";
    public static final String _Log_Path_Session_Key = "logPath";
    public static final String _Config_Logger_Session_Key = "configLogger";
  
    public static final String _Webapp_Logger_App_Attribute_Key = "webAppLogger";
    public static final String _Webapp_Logger_Name = "webapplogger";
    public static final String _Webapp_Logger_File_Log = "webapplogger.log";
	
    
    public static final String _Check_Logged_Logger_Name = "checklogged";
    public static final String _Check_Logged_File_log = "checklogged.log";
    
    public static final String  _Log_Class_Method = "*.*";
    public static final boolean _Log_Exact_Match = false;
    public static final boolean _Log_Missing_Traslation = false;
    public static final String  _Log_Name_Missing_Traslation = "MissingTranslations";
    public static final String  _Missing_Traslation_File = _Log_Name_Missing_Traslation + ".log";
    public static final String  _Log_Level = "ALL";
    
    public static final String _Home_Controller_Logger_Name = "home_controller";
    public static final String _Home_Controller_File_Log = "home_controller.log";

    public static final String _Logged_Session_Loggers = "loggedSessionLoggers";
    
    public static final String _Person_Manager_Controller_Logger_Name = "person_manager_controller";
    public static final String _Person_Manager_Controller_File_Log = "person_manager_controller.log";

    public static final String _Person_Editor_Controller_Logger_Name = "person_editor_controller";
    public static final String _Person_Editor_Controller_File_Log = "person_editor_controller.log";
    
    public static final String _Tab_Home_Controller_Logger_Name = "tab_home_controller";
    public static final String _Tab_Home_Controller_File_Log = "tab_home_controller.log";

    public static final String _Tab_Admin_Controller_Logger_Name = "tab_home_controller";
    public static final String _Tab_Admin_Controller_File_Log = "tab_home_controller.log";
    
}
