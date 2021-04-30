import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;

public class RequestForm {
    private JPanel requestFormPanel;
    private JLabel title;
    private JButton BYEButton;
    private JButton TIMEButton;
    private JTextField entryDelete;
    private JButton deleteButton;
    private JTextField entryGet;
    private JButton getButton;
    private JButton listButton;
    private JTextField entryListSince;
    private JTextField entryListHeadersCount;
    private JTextArea entryListHeaders;
    private JButton backButton;

    public RequestForm() {

        // tool tip texts
        TIMEButton.setToolTipText("Click to make a TIME? request");
        BYEButton.setToolTipText("Click to make a BYE! request");
        deleteButton.setToolTipText("Click to delete the message");
        getButton.setToolTipText("Click to get the message");
        listButton.setToolTipText("Click to list messages");
        backButton.setToolTipText("Click to go back to the StartMenu");
        entryDelete.setToolTipText("Enter a message-id");
        entryGet.setToolTipText("Enter a message-id");
        entryListHeaders.setToolTipText("Enter message headers");
        entryListSince.setToolTipText("Enter since (unix time)");
        entryListHeadersCount.setToolTipText("Enter amount of headers");

        TIMEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: make TIME? request
            }
        });
        BYEButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: make BYE! request
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: delete message
            }
        });
        getButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: make GET? request
            }
        });
        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: make LIST? request
            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Terminal.nextPanel("start",getRequestFormPanel());
            }
        });
    }

    public JPanel getRequestFormPanel() {
        return requestFormPanel;
    }

    public void setRequestFormPanel(JPanel requestFormPanel) {
        this.requestFormPanel = requestFormPanel;
    }
}
