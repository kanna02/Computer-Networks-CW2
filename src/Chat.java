import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Chat {

    public static void chat(Socket clientSocket) throws IOException {

        /*** to send data to the server ***/
        DataOutputStream sendData = new DataOutputStream(clientSocket.getOutputStream());

        /*** to read data coming from the server ****/
        BufferedReader serverReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        /*** to read data from the keyboard ***/
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

        /** output what server says **/
        String messageClient, messageServer;
        while (true) {
            messageClient = keyboardReader.readLine();

            if (messageClient.equals("STOP")) {
                break;
            }

            /*** send to the server ***/
            sendData.writeBytes(messageClient + "\n");

            /*** receive from the server ***/
            messageServer = serverReader.readLine();

            //System.out.println(messageClient);
            System.out.println("Server says: " + messageServer);
        }

        /*** close connections ***/
//        sendData.close();
//        serverReader.close();
//        keyboardReader.close();
    }
}
