package org.opencsta.servicetools.database;

import java.sql.*;
import org.apache.log4j.*;

public class Database_MSSQL extends Database{
	private static Logger dblogger = Logger.getLogger(Database.class);
	
	public Database_MSSQL(String aConnectionUrlString,String aUsername, String aPassword){
		super(aConnectionUrlString,aUsername,aPassword);
	}
	
        //it's a little bit different for mssql, thus over riding.
	protected Connection connection() {
		if (connection == null) {
			try {
				dblogger.info("Connecting to " + connectionUrlString );
				connection = DriverManager.getConnection(connectionUrlString,username,password);
			} catch (SQLException exception) {
				dblogger.fatal("Failed to connect to database using " + connectionUrlString + " User: " + username + " Password: " + password);
				throw new IllegalStateException("Failed to connect to the database using "+ connectionUrlString +". Please contact your DBA.");
			}
		}
		return connection;
	}
}
