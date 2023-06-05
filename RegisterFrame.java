import DatabaseConnection.DatabaseConnection;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class RegisterFrame extends JFrame implements ActionListener {
    private JButton registerButton, cancelButton;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public static Logger logger = Logger.getLogger(RegisterFrame.class);
    public RegisterFrame() {
        setTitle("Register Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        JLabel usernameLabel = new JLabel("Username:");
        usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

        registerButton = new JButton("Register");
        registerButton.addActionListener(this);

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(this);

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(cancelButton);

        add(panel);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = passwordField.getText();
            dispose();
            new LoginFrame();
            ArrayList<String> userList = new ArrayList<>();
            try {
                Connection myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
                Statement myStmt = myConn.createStatement();
                ResultSet myRs = myStmt.executeQuery("select username from users");
                while(myRs.next()){
                    userList.add(myRs.getString(1));
                }
            }
            catch (Exception exp){
                exp.printStackTrace();
                logger.warn("Sikertelen adatbázis kapcsolat");
            }

            if (userList.contains(username)){
                JOptionPane.showMessageDialog(null, "Felhasználónév már foglalt", "Alert", JOptionPane.INFORMATION_MESSAGE);
                logger.warn("Felhasználónév duplikálás hiba");
            } else if (username.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Felhasználónév nem lehet üres", "Alert", JOptionPane.INFORMATION_MESSAGE);
                logger.warn("Üres felhasználó mező");
            }
            else{
                try{
                    DatabaseConnection con = new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
                    con.getDbConnection().createStatement().executeUpdate("insert into users (username, password) values ('"+username+"', '"+password+"')");
                    logger.info("Sikeres regisztrálás");
                }
                catch (Exception ex){
                    System.out.println(ex.getMessage());
                    logger.warn("Meghiúsult regisztráció");
                }
            }

            /*for (int i =0; i < userList.size(); i++){
                if(username.equals(userList.get(i))){
                    JOptionPane.showMessageDialog(null, "Felhasználónév már foglalt", "Alert", JOptionPane.INFORMATION_MESSAGE);
                    logger.warn("Felhasználónév duplikálás hiba");
                }
            }*/



        } else if (e.getSource() == cancelButton) {
            dispose();
            new LoginFrame();
            logger.info("Vissza gomb megnyomva");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RegisterFrame();
        });
    }
}
