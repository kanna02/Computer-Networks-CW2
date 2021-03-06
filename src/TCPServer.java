import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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


        /*** Opens the server socket ***/
        int port = 20111;
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

        /*** readers for convenience ***/
        // to send data to the client
        DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());
        // to read data coming from the client
        BufferedReader clientReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        // to read data from the keyboard
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

        /** Example 2 - Server: LEFT, Client: RIGHT **/

        while (true) {

            // 1) Server enters request
            System.out.println("Enter your request: ");
            StringBuilder request = new StringBuilder(keyboardReader.readLine());

            String[] requestArray = request.toString().split(" ");
            String requestType = requestArray[0]; // only requestType (e.g. LIST?)

            if (requestType.equals("LIST?")) {
                int headers = Integer.parseInt(requestArray[2]);

                while (headers > 0) {

                    String entry = keyboardReader.readLine();
                    request.append("/").append(entry); // ad "/" to request to divide (LIST? since headers) from (header: content)

                    headers -= 1;
                }
            }

            // 2) Server sends request to client
            sendData.writeBytes(request + "\n");

            // 7) Server reads reply
            String replyClient = clientReader.readLine();

            // read in multiple lines if successful LIST?/GET? reply
            if (replyClient.contains("MESSAGES")) {
                String[] header = replyClient.split(" ");
                int count = Integer.parseInt(header[1]);
                while (count > 0) {
                    replyClient = replyClient + "\n" + clientReader.readLine();
                    count--;
                }
            } else if (replyClient.contains("FOUND")) {
                String[] header = replyClient.split(" ");
                int count = Integer.parseInt(header[0]);
                replyClient = "FOUND"; // take away the lineCounter
                while (count > 0) {
                    replyClient = replyClient + "\n" + clientReader.readLine();
                    count--;
                }
            }
            // close connections
            else if (replyClient.equals("BYE!")) {
                break;
            }

            // 8) Server outputs reply
            System.out.println("Client says: " + replyClient);

        }

        // close connections
        clientReader.close();
        keyboardReader.close();
        serverSocket.close();
    }

    /***
     * Runs the server.
     * @param args command-line arguments
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        TCPServer server = new TCPServer();
        server.run();

    }


}