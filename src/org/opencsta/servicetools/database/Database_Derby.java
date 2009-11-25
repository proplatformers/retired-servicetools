/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.opencsta.servicetools.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 *
 * @author cm
 */
public class Database_Derby extends Database{
    private static Logger dblogger = Logger.getLogger(Database.class);
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver" ;
    private String protocol = "jdbc:derby:";
    private String dbName = "change" ;
        
    public Database_Derby(String aConnectionUrlString,String aUsername, String aPassword){
        super(aConnectionUrlString,aUsername, aPassword) ;
    }
        
    private Properties createProperties(){
        Properties props = new Properties(); // connection properties
        // providing a user name and password is optional in the embedded
        // and derbyclient frameworks
        props.put("user", "user1");
        props.put("password", "user1");
        return props;
    }
        
    private void loadDriver() {
        /*
         *  The JDBC driver is loaded by loading its class.
         *  If you are using JDBC 4.0 (Java SE 6) or newer, JDBC drivers may
         *  be automatically loaded, making this code optional.
         *
         *  In an embedded environment, this will also start up the Derby
         *  engine (though not any databases), since it is not already
         *  running. In a client environment, the Derby engine is being run
         *  by the network server framework.
         *
         *  In an embedded environment, any static Derby system properties
         *  must be set before loading the driver to take effect.
         */
        try {
            Class.forName(driver).newInstance();
            System.out.println("Loaded the appropriate driver");
        } catch (ClassNotFoundException cnfe) {
            System.err.println("\nUnable to load the JDBC driver " + driver);
            System.err.println("Please check your CLASSPATH.");
            cnfe.printStackTrace(System.err);
        } catch (InstantiationException ie) {
            System.err.println(
                        "\nUnable to instantiate the JDBC driver " + driver);
            ie.printStackTrace(System.err);
        } catch (IllegalAccessException iae) {
            System.err.println(
                        "\nNot allowed to access the JDBC driver " + driver);
            iae.printStackTrace(System.err);
        }
    }
        
    protected Connection connection() {
        if (connection == null) {
                try {
                        dblogger.info("Connecting to " + connectionUrlString );
                        connection = DriverManager.getConnection(protocol + dbName + ";create=true",createProperties());
                } catch (SQLException exception) {
                        dblogger.fatal("Failed to connect to database using " + connectionUrlString);
                        throw new IllegalStateException("Failed to connect to the database using "+ connectionUrlString +". Please contact your DBA.");
                }
        }
        return connection;
    }
}
