/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package packandshiporder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 *
 * @author Sean
 */
public class SQL {
    //private static Connection legacy;
    //private static Connection internal;
    private static Connection getSystemConnection() throws SQLException {
        /* set user id and password on server */
        Properties props = new Properties();
        props.put("user", "naraku9333");
        props.put("password", "Sean9333");
        
        //if(internal.isClosed()) {
        return DriverManager.getConnection("jdbc:mysql://73.9.90.158/csci467", props);
    }
    
    public static ResultSet executeSystemSQLQuery(String query) throws SQLException {
        
        Connection internal = getSystemConnection();   
        Statement stmt = internal.createStatement();

        return stmt.executeQuery(query);  
    }
    
    public static void executeSystemSQLUpdate(String query) throws SQLException {
        
        Connection internal = getSystemConnection();
        Statement stmt = internal.createStatement();
        //System.out.println("SQL Query: " + query);//DBG

        stmt.executeUpdate(query);  
    }
    
    public static ResultSet executeLegacySQLQuery(String query) throws SQLException {
        /* set user id and password on server */
        Properties props = new Properties();
        props.put("user", "student");
        props.put("password", "student");
        
        Connection legacy = DriverManager.getConnection("jdbc:mysql://blitz.cs.niu.edu/csci467", props);
        
        Statement stmt = legacy.createStatement();
        //System.out.println("SQL Query: " + query);//DBG

        return stmt.executeQuery(query);  
    }
    
}
