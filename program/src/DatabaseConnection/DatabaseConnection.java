package DatabaseConnection;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class DatabaseConnection {
    private static JFrame frame = new JFrame();
    static Connection dbConnection;


    public DatabaseConnection(String url, String username, String password) {

        Connection connection;
        try {
            connection = DriverManager.getConnection(url, username, password);

        } catch (Exception e) {

            connection = null;

            JOptionPane.showMessageDialog(frame, e.getMessage(), "Adatbázis csatlakozási hiba!", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        this.setDbConnection(connection);
    }

    public static void Connect(){
        try{
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
        } catch (Exception e){
            dbConnection = null;
        }
    }

    public static ResultSet FetchData(){

        try{
            return dbConnection.createStatement().executeQuery("select * from users");
        } catch (Exception e){
            System.out.println(e);
        }

        return null;
    }

    public static String SelectUsername(String Username){
        ResultSet result;
        result = FetchData();
        String row;

        try{
            while (result.next() ){
                row = result.getString(1);
                if (row.equals(Username)){
                    return row;
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static String SelectPassword(String Password){
        ResultSet result;
        result = FetchData();
        String row;

        try{
            while (result.next() ){
                row = result.getString(2);
                if (row.equals(Password)){
                    return row;
                }
            }
        } catch (Exception e){
            System.out.println(e);
        }
        return null;
    }
    public Connection getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

}