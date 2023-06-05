import DatabaseConnection.InsertProductCommand;

import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;
import javax.xml.transform.Result;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import DatabaseConnection.*;
import java.sql.ResultSet;
import java.util.Objects;
import org.apache.log4j.Logger;


import DatabaseConnection.ShowProductCommand;
import org.apache.log4j.chainsaw.Main;


public class MainForm extends JFrame implements ActionListener {

    public static Logger logger = Logger.getLogger(MainForm.class);
    private DatabaseConnection con;
    private JButton addButton, logoutButton, selectButton;
    private JPanel panel;

    private List<String> databaseRecords;

    private List<String> idRecords = new ArrayList<>();
    private List<String> user_idRecords = new ArrayList<>();
    private List<String> titleRecords = new ArrayList<>();
    private List<String> publisherRecords = new ArrayList<>();
    private List<String> priceRecords = new ArrayList<>();

    Products p;

    public MainForm() {
        this.con = con;
        setTitle("Main Form");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(100, 150);
        setLocationRelativeTo(null);

        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        selectButton = new JButton("Borrow");
        selectButton.addActionListener(this);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);


        buttonPanel.add(addButton);
        buttonPanel.add(selectButton);
        buttonPanel.add(logoutButton);


        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.CENTER);

        add(panel);
        setVisible(true);

        retrieveRecords();
    }

    /*
    public MainForm(DatabaseConnection con) {
        this.con = con;
        setTitle("Main Form");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(100, 150);
        setLocationRelativeTo(null);
       JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add");
        addButton.addActionListener(this);
        selectButton = new JButton("Select");
        selectButton.addActionListener(this);
        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(this);
        buttonPanel.add(addButton);
        buttonPanel.add(selectButton);
        buttonPanel.add(logoutButton);
       panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.CENTER);
       add(panel);
        setVisible(true);
       retrieveRecords();
    }*/


    public String Test(String test) {
        return test;
    }

    public ArrayList<Products> retrieveRecords(){
        ArrayList<Products> products = new ArrayList<Products>();


        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select * from products");

            while(myRs.next()){

                p = new Products(myRs.getInt("id"),
                        myRs.getInt("user_id"),
                        myRs.getString("title"),
                        myRs.getString("publisher"),
                        myRs.getInt("price")
                )  ;
                products.add(p);


                //titleRecords.add(myRs.getString(3));
                //publisherRecords.add(myRs.getString(4));
                //priceRecords.add(myRs.getString(5));
                //user_idRecords.add(myRs.getString(2));

            }
            logger.info("Sikeres RS feldolgozás");
        }
        catch (Exception exp){
            exp.printStackTrace();
            logger.warn("Sikertelen adatbázis kapcsolat");
        }
        return products;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            this.dispose();
            AddForm form = new AddForm(new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", ""));
            form.setVisible(true);
            logger.info("Add Gomb lenyomva");
        }
        else if(e.getSource() == selectButton){
            this.dispose();
            MainUI m = new MainUI();
            m.setVisible(true);
            logger.info("Select Gomb lenyomva");
        } else if (e.getSource() == logoutButton) {
            dispose();
            new LoginFrame();
            logger.info("Logged out ");
        }
    }

    public static void main(String[] args) {

        try {

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e) {
            System.out.println("Look and Feel not set");
        }

        SwingUtilities.invokeLater(() -> {
            new MainForm();
        });
    }
}