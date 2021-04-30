import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

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
                Terminal.nextPanel("new",getStartMenuPanel());

            }
        });
        viewMessagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Terminal.nextPanel("view", getStartMenuPanel());

            }
        });
        chatToPeerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Terminal.nextPanel("chat",getStartMenuPanel());

            }
        });
        makeARequestButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Terminal.nextPanel("request",getStartMenuPanel());

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
