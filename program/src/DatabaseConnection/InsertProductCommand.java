package DatabaseConnection;

import javax.swing.*;

public class InsertProductCommand implements Command {

    private static JFrame frame = new JFrame();
    private String username;
    private DatabaseConnection databaseConnection;
    private String titleValue;
    private String publisherValue;
    private String priceValue;

    public InsertProductCommand(DatabaseConnection databaseConnection, String titleValue, String publisherValue, String priceValue) {
        this.databaseConnection = databaseConnection;
        this.titleValue = titleValue;
        this.publisherValue = publisherValue;
        this.priceValue = priceValue;

    }

    @Override
    public void execute() {
        try {
            this.databaseConnection.getDbConnection().createStatement().executeUpdate("insert into products (user_id, title, publisher, price, kikolcsonozve, kolcsonzo_user_id) values (1, '"+titleValue+"', '"+publisherValue+"', "+priceValue+", 0, 0)");

        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Adatbázisbeli hiba lépett fel a mező beszúrásakor!", JOptionPane.ERROR_MESSAGE);
        }

    }
}