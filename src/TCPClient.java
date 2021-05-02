import javax.swing.*;
import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Scanner;

/***
 * A class to create a TCP Client.
 */
public class TCPClient {
    /**
     * Initialise a new client. To run the client, call run().
     */
    public TCPClient() {}

    /***
     * Runs the client.
     */
    public void run() {
        try {

            /*** get IP address from user input ***/
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter IP address: ");

            //adds numbers from users IP entry to string array splits
            String input = scanner.next();
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
            System.out.print("Enter port number: ");
            int port = scanner.nextInt();

            /*** create client socket and connect to server on chosen port ***/
            System.out.println("Connecting to server on port " + port);
            Socket clientSocket = new Socket(addr, port);
            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());

//            /*** define protocol ***/
//            System.out.print("PROTOCOL? ");
//            Requests.protocol(clientSocket);

            /*** readers for convenience ***/
            // read from keyboard
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
            // read request from server
            BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            // send reply to server
            DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());

            // selection
            while (true) {
                System.out.println("""
                        Press 1 to create a message
                        Press 2 to reply to the requests
                        Press 3 to view all messages in the GUI""");
                String entry = keyboardReader.readLine();

                switch (entry) {
                    /** write and save a message **/
                    case "1" -> {
                        Message message = new Message();
                        message.createMessage();
                        message.writeToFile();
                        message.writeToDatabase();
                        System.out.println("Your message has been created");
                    }
                    /** make requests **/
                    case "2" -> {
                        System.out.println("server must write STOP to stop");

                        /** Example 2 - Server: LEFT, Client: RIGHT **/
                        String reply, request, requestType; // reply- reply sent back to server
                        while (true) {

                            // 3) Client reads request from server
                            request = serverReader.readLine(); // full request (e.g. LIST? 1 1)

                            String[] requestArray = request.split(" ");
                            requestType = requestArray[0]; // only requestType (e.g. LIST?)

                            // 4) Client outputs servers request
                            System.out.println("Server says: " + request);

                            // 5) Client executes request
                            switch (requestType) {
                                case "TIME?" -> {
                                    System.out.println(Requests.time());
                                    reply = Requests.time();
                                }
                                case "BYE!" -> {
                                    reply = request;
                                    sendData.writeBytes(reply); // write back "BYE!" to signal the closing of sockets
                                    Database.closeConnection(Database.connect());
                                    Requests.bye(clientSocket); //closes client socket
                                }
                                case "LIST?" -> {
                                    long since = Long.parseLong(requestArray[1]);
                                    String[] listEntry = request.split("/"); // split list entry into array
                                    reply = Requests.list(since, listEntry);
                                }
                                case "DELETE" -> {
                                    String id = requestArray[1];
                                    Message.delete(id);
                                    reply = "Message deleted";
                                }
                                case "GET?" -> {
                                    if (requestArray.length > 1) {
                                        String id = requestArray[1];
                                        reply = Requests.get(id);
                                    } else { reply = "You have forgotten to enter a message-id"; }
                                }
                                case "STOP" -> reply = "You have entered STOP. The client must now press 3 again to reply to your requests";

                                default -> reply = "You have entered an invalid request";
                            }

                            // 6) Client sends reply to server
                            sendData.writeBytes(reply + "\n");

                            // back to selection
                            if (requestType.equals("STOP")) {
                                break;
                            }
                        }
                    }
                    /*** view messages in the GUI ***/
                    case "3" -> {

                        // set look and feel
                        try {
                            UIManager.setLookAndFeel(new com.jtattoo.plaf.mint.MintLookAndFeel());
                        } catch (UnsupportedLookAndFeelException e) {
                        }

                        // create GUI frame and set panel
                        JFrame frame = new JFrame("Polite Messaging");
                        Terminal.setFrame(frame);
                        JPanel view = new ViewMessages().getViewMessagesPanel();
                        frame.setContentPane(view);
                        frame.pack();
                        frame.setSize(1200, 600);
                        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(true);
                    }
                }
            }

        } catch (IOException | NoSuchAlgorithmException | SQLException ex) {
            ex.printStackTrace();
        }
    }


    /***
     * Runs the client.
     * @param args command-line arguments
     */
        public static void main(String[] args) {
            TCPClient client = new TCPClient();
            client.run();
    }
}