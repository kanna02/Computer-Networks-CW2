import java.io.IOException;

public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        TCPServer server = new TCPServer();
        server.run();

        TCPClient client = new TCPClient();
        client.run();

    }
}
