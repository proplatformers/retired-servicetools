/*
This file is part of Open CSTA.

    Open CSTA is free software: you can redistribute it and/or modify
    it under the terms of the GNU Lesser General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    Open CSTA is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Lesser General Public License for more details.

    You should have received a copy of the GNU Lesser General Public License
    along with Open CSTA.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencsta.servicetools.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * 
 * @author chrismylonas
 */
public class Database_Derby extends Database {

	/**
	 * 
	 */
	private static Logger dblogger = Logger.getLogger(Database.class);

	/**
	 * 
	 */
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";

	/**
	 * 
	 */
	private String protocol = "jdbc:derby:";

	/**
	 * 
	 */
	private String dbName = "change";

	/**
	 * @param aConnectionUrlString
	 * @param aUsername
	 * @param aPassword
	 */
	public Database_Derby(String aConnectionUrlString, String aUsername,
			String aPassword) {
		super(aConnectionUrlString, aUsername, aPassword);
	}

	/**
	 * @return
	 */
	private Properties createProperties() {
		Properties props = new Properties(); // connection properties
		// providing a user name and password is optional in the embedded
		// and derbyclient frameworks
		props.put("user", "user1");
		props.put("password", "user1");
		return props;
	}

	/**
     * 
     */
	private void loadDriver() {
		/*
		 * The JDBC driver is loaded by loading its class. If you are using JDBC
		 * 4.0 (Java SE 6) or newer, JDBC drivers may be automatically loaded,
		 * making this code optional.
		 * 
		 * In an embedded environment, this will also start up the Derby engine
		 * (though not any databases), since it is not already running. In a
		 * client environment, the Derby engine is being run by the network
		 * server framework.
		 * 
		 * In an embedded environment, any static Derby system properties must
		 * be set before loading the driver to take effect.
		 */
		try {
			Class.forName(driver).newInstance();
			System.out.println("Loaded the appropriate driver");
		} catch (ClassNotFoundException cnfe) {
			System.err.println("\nUnable to load the JDBC driver " + driver);
			System.err.println("Please check your CLASSPATH.");
			cnfe.printStackTrace(System.err);
		} catch (InstantiationException ie) {
			System.err.println("\nUnable to instantiate the JDBC driver "
					+ driver);
			ie.printStackTrace(System.err);
		} catch (IllegalAccessException iae) {
			System.err.println("\nNot allowed to access the JDBC driver "
					+ driver);
			iae.printStackTrace(System.err);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.servicetools.database.Database#connection()
	 */
	protected Connection connection() {
		if (connection == null) {
			try {
				dblogger.info("Connecting to " + connectionUrlString);
				connection = DriverManager.getConnection(protocol + dbName
						+ ";create=true", createProperties());
			} catch (SQLException exception) {
				dblogger.fatal("Failed to connect to database using "
						+ connectionUrlString);
				throw new IllegalStateException(
						"Failed to connect to the database using "
								+ connectionUrlString
								+ ". Please contact your DBA.");
			}
		}
		return connection;
	}
}
