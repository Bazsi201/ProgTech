import DatabaseConnection.DatabaseConnection;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainUI extends JFrame{
    public static Logger logger = Logger.getLogger(MainUI.class);
    private JPanel rootPanel;
    private JTable table1;
    private JButton Vissza;
    private JLabel Label;
    private JTextArea updateIdField;
    private JButton updateButton;
    private JButton Update;

    private List<String> idRecords = new ArrayList<>();
    private List<String> user_idRecords = new ArrayList<>();
    private List<String> titleRecords = new ArrayList<>();
    private List<String> publisherRecords = new ArrayList<>();
    private List<String> priceRecords = new ArrayList<>();

    Products p;

    public MainUI(){
        this.setContentPane(this.rootPanel);
        this.setVisible(true);
        this.setSize(450,450);
        createTable();
        Vissza.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainForm();
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseConnection con = new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
                    String sql = "update products set kikolcsonozve = 1, kolcsonzo_user_id = '"+ LoginFrame.Logged.logged_user_id+"' where id = '"+updateIdField.getText()+"' ";
                    Statement stm = con.getDbConnection().createStatement();
                    stm.executeUpdate(sql);
                    dispose();
                    new MainUI();
                    logger.info("Sikeres kölcsönzés");
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    logger.warn("Sikertelen kölcsönzés");
                }
            }
        });
    }

    public JPanel getRootPanel(){
        return rootPanel;
    }

    private void createTable(){

        ArrayList<Products> prod = new ArrayList<Products>();
        try {
            Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
            Statement myStmt = myConn.createStatement();
            ResultSet myRs = myStmt.executeQuery("select id, user_id, title, publisher, price from products where kikolcsonozve = 0");
            ResultSetMetaData rsmd = myRs.getMetaData();
            DefaultTableModel model=(DefaultTableModel) table1.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[cols];
            for (int i = 0; i < cols; i++){
                colName[i]=rsmd.getColumnName(i+1);
            }
            model.setColumnIdentifiers(colName);
            int id;
            int user_id;
            String title;
            String publisher;
            int price;

            while(myRs.next()){
                id=myRs.getInt(1);
                user_id=myRs.getInt(2);
                title=myRs.getString(3);
                publisher=myRs.getString(4);
                price = myRs.getInt(5);

                Object[] x = new Object[]{id, user_id, title, publisher, price};

                model.addRow(x);
            }
            myStmt.close();
            myConn.close();


        }
        catch (Exception exp){
            exp.printStackTrace();
        }

    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
