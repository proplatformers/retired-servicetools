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

import java.sql.*;
import org.apache.log4j.*;

/**
 * @author chrismylonas
 * 
 */
public abstract class Database {
	private static Logger dblogger = Logger.getLogger(Database.class);
	String connectionUrlString = "";
	String username = "";
	String password = "";
	Connection connection;

	/**
	 * 
	 */
	public Database() {

	}

	// constructor params
	// "jdbc:mysql://192.168.1.212/chris?user=chris&password=chris","chris","chris"
	/**
	 * @param aConnectionUrlString
	 * @param aUsername
	 * @param aPassword
	 */
	public Database(String aConnectionUrlString, String aUsername,
			String aPassword) {
		dblogger.debug("Database Instantiated");
		connectionUrlString = aConnectionUrlString;
		username = aUsername;
		password = aPassword;
	}

	/**
	 * @param anSQLString
	 * @return
	 * @throws SQLException
	 */
	public Statement executeSQL(String anSQLString) throws SQLException {
		dblogger.info(anSQLString);
		Statement update = connection().createStatement();
		update.execute(anSQLString);
		return update;
	}

	/**
	 * @param anSQLString
	 * @throws SQLException
	 */
	public void executeSQLnoreturn(String anSQLString) throws SQLException {
		dblogger.info(anSQLString);
		Statement update = connection().createStatement();
		update.execute(anSQLString);
		update.close();
		update = null;
	}

	/**
	 * @param anSQLString
	 * @return
	 * @throws SQLException
	 */
	public ResultSet executeSelectSQL(String anSQLString) throws SQLException {
		Statement update = connection().createStatement();
		return update.executeQuery(anSQLString);
	}

	/**
	 * @param anSQLString
	 * @return
	 * @throws SQLException
	 */
	public PreparedStatement readyPreparedStatement(String anSQLString)
			throws SQLException {
		PreparedStatement update;
		update = connection().prepareStatement(anSQLString);
		return update;
	}

	/**
	 * @return
	 */
	protected Connection connection() {
		if (connection == null) {
			try {
				dblogger.info("Connecting to " + connectionUrlString);
				connection = DriverManager.getConnection(connectionUrlString);
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

	/**
	 * 
	 */
	public void close() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
				dblogger.info("Connection Closed: " + connectionUrlString);
			} catch (SQLException dbNotClosingSQLException) {
				System.err.println("Failed to close JDBC connection "
						+ dbNotClosingSQLException);
			}
		}
	}

}
