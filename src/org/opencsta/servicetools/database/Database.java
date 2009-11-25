package org.opencsta.servicetools.database;

import java.sql.*;
import org.apache.log4j.*;

public abstract class Database {
	private static Logger dblogger = Logger.getLogger(Database.class);
	String connectionUrlString = "";
	String username = "";
	String password = "";
	Connection connection;

	public Database(){
		
	}
	
    //constructor params
    //"jdbc:mysql://192.168.1.212/chris?user=chris&password=chris","chris","chris"
	public Database(String aConnectionUrlString,String aUsername, String aPassword){
		dblogger.debug("Database Instantiated");
		connectionUrlString = aConnectionUrlString ;
		username = aUsername;
		password = aPassword;
	}
		
	public Statement executeSQL(String anSQLString) throws SQLException {
		dblogger.info(anSQLString);
		Statement update = connection().createStatement();
		update.execute(anSQLString);
		return update;
	}

    public void executeSQLnoreturn(String anSQLString) throws SQLException {
		dblogger.info(anSQLString);
		Statement update = connection().createStatement();
		update.execute(anSQLString);
        update.close() ;
        update = null ;
	}

	public ResultSet executeSelectSQL(String anSQLString) throws SQLException {
		Statement update = connection().createStatement();
		return update.executeQuery(anSQLString);
	}
	
	public PreparedStatement readyPreparedStatement(String anSQLString) throws SQLException {
		PreparedStatement update ;
		update = connection().prepareStatement(anSQLString);
		return update;
	}
	
	protected Connection connection() {
		if (connection == null) {
			try {
				dblogger.info("Connecting to " + connectionUrlString );
				connection = DriverManager.getConnection(connectionUrlString);
			} catch (SQLException exception) {
				dblogger.fatal("Failed to connect to database using " + connectionUrlString);
				throw new IllegalStateException("Failed to connect to the database using "+ connectionUrlString +". Please contact your DBA.");
			}
		}
		return connection;
	}
	

	public void close() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				dblogger.info("Connection Closed: " + connectionUrlString);
			} catch (SQLException dbNotClosingSQLException) {
				System.err.println("Failed to close JDBC connection " + dbNotClosingSQLException);
			}
		}
	}

}
