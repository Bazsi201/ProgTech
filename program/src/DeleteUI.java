import DatabaseConnection.DatabaseConnection;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

public class DeleteUI extends JFrame{
    private JTextArea updateIdField;
    private JButton Update;
    private JButton Vissza;
    private JPanel rootPanel;


    public DeleteUI() {
        this.setContentPane(this.rootPanel);
        this.setVisible(true);
        this.setSize(450,450);
        Vissza.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new MainForm();
        }
    });
        Update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    DatabaseConnection con = new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
                    String sql = "update products set kikolcsonozve = 1 where id = '"+updateIdField.getText()+"' ";
                    Statement stm = con.getDbConnection().createStatement();
                    stm.executeUpdate(sql);
                    System.out.println("lefut");

                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });
    }
}


