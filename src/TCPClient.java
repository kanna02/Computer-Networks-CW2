/**
 * Code Availability: https://www.bogotobogo.com/Java/tutorials/tcp_socket_server_client.php
 *
 */

import java.io.*;
import java.net.*;

/***
 * A class to create a TCP Client.
 */
public class TCPClient {
    /**
     * Initialise a new client. To run the client, call run().
     */
    public TCPClient() {}

    /***
     * Runs the server.
     * @throws IOException
     */
    public void run() throws IOException {
        try {




            /*** get server IP address ***/
            InetAddress host = InetAddress.getLocalHost();
           // InetAddress host = InetAddress.getByName("localhost");

            /*** get IP address from user ***/
//            System.out.println("Enter IP address:");
//
//            byte part1, part2, part3, part4;
//
//            byte[] ipAddr = new byte[]{part1, 0, 0, 1};
//
//
//            InetAddress host = InetAddress.getByAddress(ipAddr);




            int port = 20111;


            /*** create client socket and connect on server port ***/
            System.out.println("Connecting to server on port " + port);
            Socket clientSocket = new Socket(host,port);
            //Socket socket = new Socket("127.0.0.1", serverPort);
            System.out.println("Just connected to " + clientSocket.getRemoteSocketAddress());


            /*** to send data to the server ***/
            DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());

            /*** to read data coming from the server ****/
            BufferedReader readData = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            /*** to read data from the keyboard ***/
            BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));




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
        }
    }
        /*** run the program ***/
        public static void main(String[] args) throws IOException {
            TCPClient client = new TCPClient();
            client.run();
    }

}