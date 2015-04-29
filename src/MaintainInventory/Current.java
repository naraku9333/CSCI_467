/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintaininventory;

/**
 *
 * @author Matt
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Current extends JFrame{

    public Current(String title) throws Exception {
        super(title);
        addCenter();
    }

    // GUI components
    JTable table;
    

    private void addCenter() throws Exception {
        
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);

        // run DB query to get result set
        ResultSet rs = doQuery();
        ResultSetMetaData meta = rs.getMetaData();

        // add columns to table model
        int colCount = meta.getColumnCount();
        for (int i = 1; i <= colCount; i++) {
            model.addColumn(meta.getColumnName(i));
        }
        // row data to table model
        while (rs.next()) {
            Object[] row = new Object[colCount];
            for (int i = 0; i < colCount; i++) {
                row[i] = rs.getString(i+1);
                }
            
            model.addRow(row);
            /* Insert if not exist Logic */
            
            Properties props = new Properties();
            props.put("user", "naraku9333");
            props.put("password", "Sean9333");

            Connection connection = DriverManager.getConnection("jdbc:mysql://73.9.90.158:3306/csci467", props);
            Statement statement;
            statement= connection.createStatement();
            
            int j = 0;
            String item = row[j].toString();
            statement.executeUpdate("INSERT Ignore INTO Inventory(product_num, on_hand) VALUES('"+ item +"', '00')");
            }   
        
        
               
        JPanel pan = new JPanel();
        final JButton newQuer = new JButton("Receive Parts");
        
        
        
        newQuer.addActionListener(
            new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e){
                ReceiveShipment frame;
                    try {
                        frame = new ReceiveShipment("Update Inventory");
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.pack();
                        frame.setVisible(true);
                    } catch (Exception ex) {
                        Logger.getLogger(Current.class.getName()).log(Level.SEVERE, null, ex);
                    }
                
    
                }
            }
            
        );
        
                   
                 System.out.println("loaded mySQL JDBC driver");
                //Create and set up the window.
                  
        
        
                    
        
        pan.add(newQuer);
        
        
        
        add(pan, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    ResultSet doQuery() throws Exception {

        /* set user id and password on server */
        Properties props = new Properties();
        props.put("user", "student");
        props.put("password", "student");

        Connection connection = DriverManager.getConnection("jdbc:mysql://blitz.cs.niu.edu/csci467", props);

        /* create SQL query */
        Statement stmt = connection.createStatement();
        String queryString = "SELECT* from parts;";
        System.out.println("SQL Query: " + queryString);

        /* execute SQL query and process result */
        return stmt.executeQuery(queryString);
    }

}

