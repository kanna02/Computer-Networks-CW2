import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;


public class StartConnection {
    private JPanel startConnectionPanel;
    private JLabel title;
    private JButton connectButton;
    private JTextField entry_IP;
    private JTextField entry_Port;
    private JLabel label_IP;
    private JLabel label_Port;
    private JLabel message;

    public StartConnection() throws IOException {

        // set tool tip text
        entry_IP.setToolTipText("Enter IP address here");
        entry_Port.setToolTipText("Enter port here");

        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    /*** get IP address from user input ***/
                    String input = entry_IP.getText();

                    //adds numbers from users IP entry to string array splits
                    String[] splits = input.split("\\.");

                    //converts the string elements to bytes and adds to byte array ipAddr
                    byte x1, x2, x3, x4;
                    x1 = Byte.parseByte(splits[0]);
                    x2 = Byte.parseByte(splits[1]);
                    x3 = Byte.parseByte(splits[2]);
                    x4 = Byte.parseByte(splits[3]);
                    byte[] ipAddr = new byte[]{x1, x2, x3, x4};

                    //creates IP address out of byte array
                    InetAddress addr = InetAddress.getByAddress("Localhost", ipAddr);

                    /** get port from user input**/
                    String port = entry_Port.getText();


                    /*** create client socket and connect to server on chosen port ***/
                    message.setText("Connecting to server on port " + port);
                    Socket clientSocket = new Socket(addr, Integer.parseInt(port));
                    message.setText("Just connected to " + clientSocket.getRemoteSocketAddress());

                    Terminal.nextPanel("start", startConnectionPanel);
                    //TODO: PROTOCOL? request

                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

            }
        });
    }

    /**
     * Getter for panel
     * @return panel
     */
    public JPanel getStartConnectionPanel() {
        return startConnectionPanel;
    }

    /**
     * Setter for panel
     * @param startConnectionPanel panel
     */
    public void setStartConnectionPanel(JPanel startConnectionPanel) {
        this.startConnectionPanel = startConnectionPanel;
    }
}
