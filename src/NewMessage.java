import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class NewMessage {
    private JTextField entry_from;
    private JTextField entry_to;
    private JTextField entry_topic;
    private JTextField entry_subject;
    private JTextArea entry_body;

    private JPanel newMessagePanel;
    private JButton saveButton;
    private JButton cancelButton;

    private JLabel title;




    public NewMessage(){

        //TODO: only creates empty row in database
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String from = entry_from.getText();
                String to = entry_to.getText();
                String topic = entry_topic.getText();
                String subject = entry_subject.getText();
                String body;
                int contents = 0;  //contents counter

                while (true) {
                    body = entry_body.getText();
                    // count lines
                    String[] line = body.split("\n"); //look for new line
                    contents += line.length;
                    break;
                }


                try {
                    Message message = new Message();
                    message.createMessage(/*from, to, topic, subject, contents ,body*/);
                    message.writeToDatabase();

                } catch (IOException | NoSuchAlgorithmException ioException) {
                    ioException.printStackTrace();
                }


            }
        });
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Terminal.nextPanel("start",getNewMessagePanel());
            }
        });
    }

    public JPanel getNewMessagePanel() {
        return newMessagePanel;
    }

    public void setNewMessagePanel(JPanel newMessagePanel) {
        this.newMessagePanel = newMessagePanel;
    }
}
