//TODO: comment correctly
/**
 * Code Availability: https://www.bogotobogo.com/Java/tutorials/tcp_socket_server_client.php
 *
 */

import java.io.*;
import java.net.*;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
    public void run()   {
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

//            System.out.println("String array: " + Arrays.toString(splits));

            //converts the string elements to bytes and adds to byte array ipAddr
            byte x1,x2,x3,x4;
            x1 = Byte.parseByte(splits[0]);
            x2 = Byte.parseByte(splits[1]);
            x3 = Byte.parseByte(splits[2]);
            x4 = Byte.parseByte(splits[3]);
            byte[] ipAddr = new byte[] { x1,x2,x3,x4 };

            //creates IP address out of byte array
            InetAddress addr = InetAddress.getByAddress("Localhost",ipAddr);

//            System.out.println("Byte array: " + Arrays.toString(addr.getAddress()));

            /** get port from user input**/
            System.out.print("Enter port number: ");
            int port = scanner.nextInt();

            /*** create client socket and connect to server on chosen port ***/
            System.out.println("Connecting to server on port " + port);
            Socket clientSocket = new Socket(addr,port);
            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());

//            /*** define protocol ***/
//            Requests.request("protocol");

            /*** to send data to the server ***/
            DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());

            /*** to read data coming from the server ****/
            BufferedReader readData = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            /*** to read data from the keyboard ***/
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

            // to see if program runs  //

            System.out.println("Press 1 to write a message" + "\n" + "Press 2 to write to server" + "\n" + "Press 3 to make a request");
            String entry = keyboardReader.readLine();

            /** write and save a message **/
            if (entry.equals("1")  ){

                Message message = new Message();
                message.createMessage();
                message.writeToFile();
                message.writeToDatabase();

                System.out.println("Your message has been created and saved to the file messages.txt");
            }
            /** chat to server **/
            else if (entry.equals("2") ) {

                /** output what server says **/
                String messageClient, messageServer;
                while (true) {
                    messageClient = keyboardReader.readLine();

                    if (messageClient == null) {
                        break;
                    }

                    /*** send to the server ***/
                    sendData.writeBytes(messageClient + "\n");

                    /*** receive from the server ***/
                    messageServer = readData.readLine();

                    System.out.println(messageClient);
                    System.out.println("Server says: " + messageServer);

                }
            }
            /** make requests **/
            else if (entry.equals("3")) {

                System.out.println("Possible requests: TIME?, BYE!, GET?");

                System.out.print("Enter your request: ");
                String requestEntry = scanner.next();

                if (requestEntry.equals("TIME?")) {
                    Requests.request("time");
                }
                else if (requestEntry.equals("BYE!")) {
                    Requests.request("bye");
                }
                else if (requestEntry.equals("GET?")) {
                    String hash = scanner.next();
                    Requests.get(hash);
                }

            }

            /*** close connections ***/
            sendData.close();
            readData.close();
            keyboardReader.close();
            clientSocket.close();

        }
        catch(UnknownHostException ex) {
            ex.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }



    /*** to run the program ***/
        public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
            TCPClient client = new TCPClient();
            client.run();


    }

}