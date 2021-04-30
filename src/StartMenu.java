import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartMenu {
    private JPanel startMenuPanel;
    private JLabel title;
    private JButton createNewMessageButton;
    private JButton viewMessagesButton;
    private JButton chatToPeerButton;
    private JButton makeARequestButton;

    public StartMenu(){

        createNewMessageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: go to panel newMessage

            }
        });
        viewMessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: go to panel viewMessages

            }
        });
        chatToPeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: go to panel chat

            }
        });
        makeARequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //TODO: go to panel makeRequest

            }
        });
    }

    public JPanel getStartMenuPanel() {
        return startMenuPanel;
    }

    public void setStartMenuPanel(JPanel startMenuPanel) {
        this.startMenuPanel = startMenuPanel;
    }
}
