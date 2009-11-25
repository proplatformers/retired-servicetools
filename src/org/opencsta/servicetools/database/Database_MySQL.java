package org.opencsta.servicetools.database;

//import java.sql.*;
import java.util.Properties;
import org.apache.log4j.*;

public class Database_MySQL extends Database{
	private static Logger dblogger = Logger.getLogger(Database_MySQL.class);
	
	public Database_MySQL(String aConnectionUrlString,String aUsername, String aPassword) {
		super(aConnectionUrlString,aUsername,aPassword);
//        loadDatabaseDriver() ;
	}

    public Database_MySQL(Properties props){
        String connectionURL = props.getProperty("MYSQL_CONNECTION_URL") ;
        String username = props.getProperty("MYSQL_USERNAME") ;
        String password = props.getProperty("MYSQL_PASSWORD") ;
    }

//    private void loadDatabaseDriver() throws Exception{
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
//        } catch (Exception e) {
//            throw new Exception("Unable to load database drivers\n"+e);
//        }
//    }
}
