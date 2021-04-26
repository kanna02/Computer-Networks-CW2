import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;

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
            // PROTOCOL? //
            System.out.print("Enter version: ");
            int version = System.in.read();

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

        // GET? //
        if ("get".equals(request)){
            // take the SHA-256 sum
            // search through database for hash
            // if not found:
            System.out.println("SORRY");
            // if found:
            System.out.println("Found");
            // print message
        }


        // BYE? //
        if ("bye".equals(request)){
            // close socket
        }

    }

}
