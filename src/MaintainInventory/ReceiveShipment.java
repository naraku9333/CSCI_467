/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maintaininventory;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Matt
 */
public class ReceiveShipment extends JFrame {
    
    public ReceiveShipment(String title) throws Exception {
        super(title);
        addNorth();
        addCenter();
        addSouth();
    }

    // GUI components
    JTable table;
    JPopupMenu popup;
    JTextField text1,text2;
    JButton button;
    JTextArea area;
    
    private void addNorth() {
        JPanel panel = new JPanel();
        text1 = new JTextField(30);
        text2 = new JTextField(30);
        JLabel title = new JLabel("Enter Part Number and Quantity Recieved");
        panel.add(title);
        panel.add(text1);
        panel.add(text2);
        button = new JButton("Update");
        panel.add(button);
        add(panel, BorderLayout.NORTH);

        ActionListener listener;
        listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                //if field one has something
                if (!text1.getText().equals("")) {
                    //if field 1&2 have something
                    if (!text2.getText().equals("")) {
                        fillModel("update csci467.Inventory set on_hand = on_hand + " + text2.getText() + " where product_num = '" + text1.getText()+ " '");
                    }
                    //if field 1 has something and field 2 does not
                    if (text2.getText().equals("")) {
                        JOptionPane.showMessageDialog(null,
                            "Please Enter a Quantity.",
                             "Missing Quantity",
                            JOptionPane.WARNING_MESSAGE);
                        }}
                //if filed 1 empty
                if (text1.getText().equals("")) {
                    fillModel("update csci467.Inventory set on_hand = on_hand where product_num = '01'");
                }

            }
            
            
            
            
        };
        button.addActionListener(listener);
        text1.addActionListener(listener);
        text2.addActionListener(listener);
       }
    
    private void addCenter() throws Exception {
        DefaultTableModel model = new DefaultTableModel();
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
    }
    
    private void addSouth() {
        area = new JTextArea(4, 30);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        add(new JScrollPane(area), BorderLayout.SOUTH);
    }
    
    ResultSet doQuery(String queryString) throws SQLException {

        /* set user id and password on server */
        Properties props = new Properties();
        props.put("user", "*************");
        props.put("password", "*************");

        Connection connection = DriverManager.getConnection("jdbc:mysql://*****************/csci467", props);
        /* create SQL query */
        PreparedStatement stmt = connection.prepareStatement(queryString);
        System.out.println("SQL Query: " + queryString);
        Statement state = connection.createStatement();

        /* execute SQL query and process result */
        stmt.executeUpdate();
        String rString = "Select * from csci467.Inventory";
        return state.executeQuery(rString);
    }
    
    
    void fillModel(String query) {
        try {
            DefaultTableModel model = new DefaultTableModel();
            
            // run DB query to get result set
            ResultSet rs = doQuery(query);
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
                    row[i] = rs.getString(i + 1);
                }
                model.addRow(row);
            }
            table.setModel(model);
        } catch (SQLException ex) {
            area.append(ex.getMessage() + "\n");
        }
    }
}


    



