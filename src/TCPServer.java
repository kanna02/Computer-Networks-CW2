/***
 * code availability: https://www.geeksforgeeks.org/establishing-the-two-way-communication-between-server-and-client-in-java/
 */


import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.Math;
import java.security.NoSuchAlgorithmException;


/***
 * A class to create a TCP Server.
 */
public class TCPServer {

    /**
     * Initialise a new server. To run the server, call run().
     */
    public TCPServer() {}

    /**
     * Runs the server.
     * @throws IOException
     */
    public void run() throws IOException {

        /*** Set up to accept incoming TCP connections ***/

        int port = 20111;

        /*** Opens the server socket ***/
        System.out.println("Opening the server socket on port " + port);
        ServerSocket serverSocket = new ServerSocket(port);


        /*** Receives client connection ***/

        // Waits until a client connects
        System.out.println("Server waiting for client...");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected!");

        /*** define protocol ***/
//        System.out.print("PROTOCOL? ");
//        Requests.protocol(clientSocket);


        /*** to send data to the client ***/
//        PrintStream ps = new PrintStream(clientSocket.getOutputStream());
        DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());


        /*** to read data coming from the client ***/
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        /*** to read data from the keyboard ***/
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));


        /*** output what client says ***/
        while (true) {
            String messageClient, messageServer;

            /*** read from client ***/
            messageClient = clientReader.readLine();

            System.out.println("Client says: " + messageClient);
            messageServer = keyboardReader.readLine();

            if (messageClient == null) {
                break;
            }
            else if (messageClient.equals("BYE!")) {
//                clientSocket.close();
                serverSocket.close();
                clientReader.close();
                keyboardReader.close();
                break;
            }





            /*** send to client ***/
//            ps.println(messageServer);
            sendData.writeBytes(messageServer + "\n");

//        /** output what client says **/
//        while (true) {
//            String messageClient, messageServer;
//            messageServer = keyboardReader.readLine();
//
//            if (messageServer == null) {
//                break;
//            }
//
//            /*** send to the client ***/
//            sendData.writeBytes(messageServer + "\n");
//
//            /*** receive from the client ***/
//            messageClient = clientReader.readLine();
//
//            //System.out.println(messageClient);
//            System.out.println("Client says: " + messageClient);
//        }

        }


    }

    /*** run the program ***/
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        TCPServer server = new TCPServer();
        server.run();


    }


}