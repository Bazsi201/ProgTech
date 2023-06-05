import DatabaseConnection.DatabaseConnection;
import com.mysql.cj.protocol.Resultset;
import org.apache.log4j.Logger;
import static DatabaseConnection.DatabaseConnection.*;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Array;
import java.sql.ResultSet;
import javax.swing.*;
import javax.xml.transform.Result;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.ArrayList;

import DatabaseConnection.*;



public class AddForm extends JFrame{
    private DatabaseConnection con;
    public static Logger logger = Logger.getLogger(MainForm.class);
    private JComboBox TypeBox;
    private JComboBox TitleBox;
    private JComboBox PublisherBox;
    private JComboBox PriceBox;
    private JLabel Type;
    private JLabel Title;
    private JLabel Publisher;
    private JLabel Price;
    private JButton AddButton;
    private JButton CancelButton;

    public AddForm(DatabaseConnection con) {
        DatabaseConnection conn = new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
        conn.Connect();
        //String[] typeArray = new String[2];
        ArrayList<String> typeArray = new ArrayList<>();
        try {
            String sql = "select type from strategy";
            Statement stm = con.getDbConnection().createStatement();
            ResultSet rs = stm.executeQuery(sql);
            logger.info("Sikeres lekérdezés");

            int i = 0;
            while (rs.next()){
                typeArray.add(rs.getString(1));
                i++;
            }
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
            logger.warn("Sikertelen lekérdezés");
        }
        String[] options = new String[typeArray.size()];
        for (int i =0; i < typeArray.size(); i++){
            options[i] = typeArray.get(i);
        }


        setTitle("Add Form");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(null);

        TypeBox = new JComboBox<>(options);
        TypeBox.setBounds(150, 30, 200, 25);
        add(TypeBox);

        TypeBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                TitleBox.enableInputMethods(false);
                String selected = String.valueOf(TypeBox.getSelectedItem());
                IselectionStrategy selection = null;
                if(selected.equals("Game")){
                    //System.out.println("asdasd");
                    selection = new GameSelectionStrategy();
                }
                else if (selected.equals("Book")) {
                    selection = new BookSelectionStrategy();
                }
                else {
                    selection = new NullSelectSrategy();
                }

                TitleBox.removeAllItems();
                TitleBox.setModel(new DefaultComboBoxModel(selection.getSelectionArray()));
                selection = new NullSelectSrategy();
                PriceBox.setModel(new DefaultComboBoxModel(selection.getSelectionArray()));
            }

        });

        TitleBox = new JComboBox<>();
        TitleBox.setBounds(150, 60, 200, 25);
        add(TitleBox);

        TitleBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                String selected = String.valueOf(TitleBox.getSelectedItem());
                IselectionStrategy selection = null;

                if(selected.equals("GTA") || selected.equals("WOW") || selected.equals("Fifa")){
                    selection = new PublisherGameSelectionStrategy();
                }
                else if(selected.equals("Harry Potter") || selected.equals("Avatar") || selected.equals("Sikoly")){
                    selection = new PublisherBookSelectionStrategy();
                }
                else {
                    selection = new NullSelectSrategy();
                }
                PublisherBox.removeAllItems();
                PublisherBox.setModel(new DefaultComboBoxModel(selection.getSelectionArray()));
                selection = new NullSelectSrategy();
                PriceBox.setModel(new DefaultComboBoxModel(selection.getSelectionArray()));
            }
        });

        PublisherBox = new JComboBox<>();
        PublisherBox.setBounds(150, 90, 200, 25);
        add(PublisherBox);


        PublisherBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                String selected = String.valueOf(PublisherBox.getSelectedItem());
                IselectionStrategy selection = null;

                if(selected.equals("EA") || selected.equals("Rockstar")){
                    selection = new PriceGameSelectionStrategy();
                }
                else if(selected.equals("Líra")
                        || selected.equals("Libri")){
                    selection = new PriceBookSelectionStrategy();
                }
                else {
                    selection = new NullSelectSrategy();
                }
                PriceBox.removeAllItems();
                PriceBox.setModel(new DefaultComboBoxModel(selection.getSelectionArray()));

            }
        });

        PriceBox = new JComboBox<>();
        PriceBox.setBounds(150, 120, 200, 25);
        add(PriceBox);

        Type = new JLabel("Type");
        Type.setBounds(20, 30, 100, 25);
        add(Type);

        Title = new JLabel("Title");
        Title.setBounds(20, 60, 100, 25);
        add(Title);

        Publisher = new JLabel("Publisher");
        Publisher.setBounds(20, 90, 100, 25);
        add(Publisher);

        Price = new JLabel("Price");
        Price.setBounds(20, 120, 100, 25);
        add(Price);

        AddButton = new JButton("Add");
        AddButton.setBounds(20, 150, 100, 25);
        AddButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(AddButton);


        AddButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try{
                    DatabaseConnection con = new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", "");
                    InsertProductCommand id = new InsertProductCommand(con, (String) TitleBox.getSelectedItem(), (String) PublisherBox.getSelectedItem(), (String)PriceBox.getSelectedItem());
                    id.execute();
                    logger.info("Adat beszúrva az adatbázisba");
                    JOptionPane.showMessageDialog(null, "Sikeres beszúrás", "Alert", JOptionPane.INFORMATION_MESSAGE);

                }
                catch (Exception ex){
                    logger.warn("Hiba adatbáziskapcsolat létrehozásakor");
                    System.out.println(ex.getMessage());
                }
                logger.info("Add button megnyomva");

            }
        });

        CancelButton = new JButton("Cancel");
        CancelButton.setBounds(250, 150, 100, 25);
        add(CancelButton);




        CancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainForm();
                logger.info("Cancel gomb megnyomva");
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddForm(new DatabaseConnection("jdbc:mysql://localhost:3306/progtech", "root", "")));
    }
}