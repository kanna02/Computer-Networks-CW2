import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Scanner;
//TODO: test if GET? works when some headers are null
//TODO: add LIST? request
/**
 * A class for defining requests.
 */
public class Requests {


    /*** PROTOCOL? request ***/
    public static void protocol () throws IOException {

        /*** to read data from the keyboard ***/
        Scanner scanner = new Scanner(System.in);

        int version = scanner.nextInt();
        String identifier = scanner.next();

        System.out.println("PROTOCOL? " + version + " " + identifier);
    }

    /*** TIME? request ***/
    public static void time () {
        long unixTime = Instant.now().getEpochSecond();
        System.out.println("NOW " + unixTime);
    }

    /*** BYE! request ***/
    public static void bye (Socket socket) throws IOException {
        System.out.println("BYE!");
        socket.close();
    }

    /*** GET? request ***/
    public static void get (String hash){

        // PREPARE QUERY //
        String query = "SELECT * FROM PoliteMessaging WHERE MessageID = \"" + hash + "\";";

        // EXECUTE //
        int count = 0;
        ArrayList<ArrayList<String>> resultSet = Database.read(query, Database.connect());
        for (ArrayList<String> result : resultSet) {
            count += 1;

            // reply if message found -> print message
            if (count != 0) {
                System.out.println("FOUND");
                System.out.println("Message-id: SHA-256 " + result.get(0));
                System.out.println("Time-sent: " + result.get(1));
                System.out.println("From: " + result.get(2));
                if (!result.get(3).isEmpty()) { // 0...1
                    System.out.println("To: " + result.get(3));
                }
                if (!result.get(4).isEmpty()) { // 0...1
                    System.out.println("Topic: " + result.get(4));
                }
                if (!result.get(5).isEmpty()) { // 0...1
                    System.out.println("Subject: " + result.get(5));
                }
                System.out.println("Contents: " + result.get(6));
                System.out.println(result.get(7));
                System.out.println('\n');
            }
        }
        //reply if no message found
        if (count == 0) {
            System.out.println("SORRY");
        }


    }

}


