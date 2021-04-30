import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ViewMessages {
    private JPanel viewMessagesPanel;
    private JLabel title;
    private JTable messageTable;
    private JButton refreshButton;
    String[] header = {"Message-id", "Time-sent", "From", "To", "Topic", "Subject", "Contents", "Body"};

    public ViewMessages(){

        refreshButton.setToolTipText("Press here to refresh the table");

        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               Terminal.nextPanel("view",getViewMessagesPanel());
            }
        });
    }

    private void createUIComponents() {
        DefaultTableModel model = new DefaultTableModel(0,8); // create table layout
        model.setColumnIdentifiers(header); //set header

        messageTable = new JTable(model); //create table object

        messageTable.getColumnModel().getColumn(0).setPreferredWidth(500);
        messageTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        messageTable.getColumnModel().getColumn(6).setPreferredWidth(60);
        messageTable.getColumnModel().getColumn(7).setPreferredWidth(200);


        String query = "SELECT * FROM PoliteMessaging;";
        ArrayList<ArrayList<String>> resultSet = Database.read(query, Database.connect());

        for (ArrayList<String> result : resultSet ){
            Object[] row = {result.get(0), result.get(1), result.get(2), result.get(3), result.get(4), result.get(5), result.get(6), result.get(7)};
            model.addRow(row);
        }

    }

    public JPanel getViewMessagesPanel() {
        return viewMessagesPanel;
    }

    public void setViewMessagesPanel(JPanel viewMessagesPanel) {
        this.viewMessagesPanel = viewMessagesPanel;
    }


}
