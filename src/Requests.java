import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;

/**
 * A class for defining requests.
 */
public class Requests {
    private static long unixTime;

    public static void request(String request) throws IOException {

        /*** to read data from the keyboard ***/
        BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

        // PROTOCOL? //
        if ("protocol".equals(request)) {

            System.out.print("Enter version: ");
            int version = keyboardReader.read();

            System.out.println("Enter identifier: ");
            String identifier = keyboardReader.readLine();

            System.out.println("PROTOCOL? " + version + " " + identifier);
        }

        // TIME? this is a time reply
        if ("time".equals(request)){
            unixTime = Instant.now().getEpochSecond();
            System.out.println("NOW " + unixTime);
        }
        // LIST? //
        if ("list".equals(request)){
            long since = keyboardReader.read();
            int headers = keyboardReader.read();
            // take since (time)
            // take headers (int)
            // if headers > 0
                // take header
            // look in database for messages with that time header
            int count = 0;
            // increase count for every message found
            System.out.println("COUNT " + count);
            // output hash of every found message
        }


        // BYE? //
        if ("bye".equals(request)){
            System.out.println("You will now be disconnected from the server, BYE!");
            // close socket

        }

    }

    /*** GET? request ***/
    public static void get(String hash){
        // PREPARE QUERY //
        String query = "SELECT * FROM PoliteMessaging WHERE MessageID = \"" + hash + "\";";

        // EXECUTE //
        int count = 0;
        ArrayList<ArrayList<String>> resultSet = Database.read(query, Database.connect());
        for (ArrayList<String> result : resultSet) {
            count += 1;

            // print message
            if (count != 0) {
                System.out.println("FOUND");
                System.out.println("Message-id: SHA-256 " + result.get(0));
                System.out.println("Time-sent: " + result.get(1));
                System.out.println("From: " + result.get(2));
                if (!result.get(3).isEmpty()) {
                    System.out.println("To: " + result.get(3));
                }
                if (!result.get(4).isEmpty()) {
                    System.out.println("Topic: " + result.get(4));
                }
                if (!result.get(5).isEmpty()) {
                    System.out.println("Subject: " + result.get(5));
                }
                System.out.println("Contents: " + result.get(6));
                System.out.println(result.get(7));
                System.out.println('\n');
            }
        }
        if (count == 0) {
            System.out.println("SORRY");
        }


    }

}
