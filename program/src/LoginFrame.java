import DatabaseConnection.DatabaseConnection;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import org.apache.log4j.Logger;



public class LoginFrame extends JFrame {
    public class Logged{
        public static int logged_user_id;
    }
    public static Logger logger = Logger.getLogger(LoginFrame.class);
    private JButton loginButton, registerButton;
    private JTextField usernameField;
    private JPasswordField passwordField;


    private List<String> user_idRecords = new ArrayList<>();
    private List<String> usernameRecords = new ArrayList<>();
    private List<String> passwordRecords = new ArrayList<>();

    public LoginFrame() {

        setTitle("Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                try{


                    DatabaseConnection con = new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
                    String sql = "select * from users where username='"+username+"' and password='"+password+"'";
                    String sql2 = "select id from users where username='"+username+"'";
                    ResultSet usern = con.getDbConnection().createStatement().executeQuery(sql2);;
                    System.out.println(usern);
                    Statement stm = con.getDbConnection().createStatement();
                    ResultSet rs = stm.executeQuery(sql);

                    if(usern.next()){
                        int temp = usern.getInt(1);
                        Logged.logged_user_id = temp;
                    }

                    if (username.isEmpty()){

                    }
                    if(rs.next()){
                        logger.info("sikeres belépés");
                        System.out.println("Sikeres");
                        dispose();
                        new MainForm();
                    }
                    else{
                        logger.info("sikertelen belépés");
                        System.out.println("sikertelen");
                    }
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                }
            }
        });

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new RegisterFrame();
            }
        });

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        add(panel);
        setVisible(true);
    }



    public static void main(String[] args) {
        try {

            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception e) {
            System.out.println("Look and Feel not set");
        }
        SwingUtilities.invokeLater(() -> {
            new LoginFrame();
        });
    }
}
