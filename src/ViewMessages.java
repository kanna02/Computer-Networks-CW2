import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * A class for designing the frame of the viewMessages GUI form.
 */
public class ViewMessages {
    private JPanel viewMessagesPanel;
    private JLabel title;
    private JTable messageTable;
    private JButton refreshButton;
    String[] header = {"Message-id", "Time-sent", "From", "To", "Topic", "Subject", "Contents", "Body"}; // header of the table

    /**
     * Class constructor.
     */
    public ViewMessages(){

        // adds info box when hovering over refresh button
        refreshButton.setToolTipText("Press here to refresh the table");

        // refreshes the table
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Terminal.nextPanel("view",getViewMessagesPanel());
            }
        });
    }

    /*** to create table ***/
    private void createUIComponents() {
        // create design of the table //
        DefaultTableModel model = new DefaultTableModel(0,8); // create table layout
        model.setColumnIdentifiers(header); //set header

        messageTable = new JTable(model); //create table object

        messageTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        messageTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        messageTable.getColumnModel().getColumn(6).setPreferredWidth(60);
        messageTable.getColumnModel().getColumn(7).setPreferredWidth(200);

        // add contents from the database to the table  //
        String query = "SELECT * FROM PoliteMessaging;";
        ArrayList<ArrayList<String>> resultSet = Database.read(query, Database.connect());

        for (ArrayList<String> result : resultSet ){
            Object[] row = {result.get(0), result.get(1), result.get(2), result.get(3), result.get(4), result.get(5), result.get(6), result.get(7)};
            model.addRow(row);
        }
    }


    /**
     * Getter for panel.
     * @return panel
     */
    public JPanel getViewMessagesPanel() {
        return viewMessagesPanel;
    }

}
