/**
 * Code Availability: https://www.bogotobogo.com/Java/tutorials/tcp_socket_server_client.php
 *
 */

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
    private static JFrame frame;
    private static JPanel view;
    /**
     * Initialise a new client. To run the client, call run().
     */
    public TCPClient() {}

    /***
     * Runs the client.
     */
    public void run() {
        try {

            /*** get server IP address ***/
            // InetAddress host = InetAddress.getLocalHost();
            // InetAddress host = InetAddress.getByName("localhost");

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

            /*** to read data from the keyboard ***/
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

            // to see if program runs  //
            boolean var = true;
            while (var) {
            System.out.println("""
                    Press 1 to create a message
                    Press 2 to chat with server
                    Press 3 to make a request
                    Press 4 to view all messages in the GUI""");
            String entry = keyboardReader.readLine();

                /** write and save a message **/
                if (entry.equals("1")) {

                    Message message = new Message();
                    message.createMessage();
                    message.writeToFile();
                    message.writeToDatabase();

                    System.out.println("Your message has been created");
                }
                /** chat to server **/
                else if (entry.equals("2")) {
                    System.out.println("Write STOP to stop");
                    Chat.chat(clientSocket);
                }
                /** make requests **/
                else if (entry.equals("3")) {

                    System.out.println("Possible requests: TIME?, BYE!, GET?, LIST?, DELETE");

                    System.out.print("Enter your request: ");
                    String requestEntry = scanner.next();

                    DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());

                    switch (requestEntry) {
                        case "TIME?" -> sendData.writeBytes(Requests.time(clientSocket)); // send reply to other peer

                        case "GET?" -> {
                            String hash = scanner.next();
                            sendData.writeBytes(Requests.get(hash, clientSocket));

                        }
                        case "LIST?" -> {
                            long since = scanner.nextLong();
                            int headers = scanner.nextInt(); // to count the headers

                            sendData.writeBytes(Requests.list(since, headers, clientSocket));
                        }
                        case "DELETE" -> {
                            String hash = scanner.next();
                            Message.delete(hash);

                        }
                        case "BYE!" -> {
                            sendData.writeBytes(requestEntry);
                            keyboardReader.close();
                            var = false;
                            Requests.bye(clientSocket); //closes client socket



                        }
                        default -> System.out.println("The request you have entered is not valid");
                    }
                    sendData.close();



                } else if (entry.equals("4")) {

                    // set look and feel
                    try {
                        UIManager.setLookAndFeel(
                                new com.jtattoo.plaf.mint.MintLookAndFeel());
                    } catch (UnsupportedLookAndFeelException e) {
                    }

                    frame = new JFrame("Polite Messaging");
                    Terminal.setFrame(frame);
                    view = new ViewMessages().getViewMessagesPanel();

                    frame.setContentPane(view);
                    frame.pack();
                    frame.setSize(1200, 600);
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }
            }

            } catch(IOException | NoSuchAlgorithmException | SQLException ex){
                ex.printStackTrace();
            }
    }





    /*** to run the program ***/
        public static void main(String[] args) {
            TCPClient client = new TCPClient();
            client.run();



    }

}