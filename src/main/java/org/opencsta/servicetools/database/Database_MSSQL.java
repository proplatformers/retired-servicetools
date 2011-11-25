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
public class Database_MSSQL extends Database {

	/**
	 * 
	 */
	private static Logger dblogger = Logger.getLogger(Database.class);

	/**
	 * @param aConnectionUrlString
	 * @param aUsername
	 * @param aPassword
	 */
	public Database_MSSQL(String aConnectionUrlString, String aUsername,
			String aPassword) {
		super(aConnectionUrlString, aUsername, aPassword);
	}

	// it's a little bit different for mssql, thus over riding.
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.opencsta.servicetools.database.Database#connection()
	 */
	protected Connection connection() {
		if (connection == null) {
			try {
				dblogger.info("Connecting to " + connectionUrlString);
				connection = DriverManager.getConnection(connectionUrlString,
						username, password);
			} catch (SQLException exception) {
				dblogger.fatal("Failed to connect to database using "
						+ connectionUrlString + " User: " + username
						+ " Password: " + password);
				throw new IllegalStateException(
						"Failed to connect to the database using "
								+ connectionUrlString
								+ ". Please contact your DBA.");
			}
		}
		return connection;
	}
}
