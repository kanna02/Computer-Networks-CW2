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

    /*** LIST? request ***/
    public static void list(long since, int headers) {

        // PREPARE ORIGINAL QUERY //
        String query = "SELECT MessageID FROM PoliteMessaging WHERE TimeSent>=\'" + since + "\'";

        // check if since is in future/past
        long currentTime = Instant.now().getEpochSecond();
        if (since <= currentTime) {

            int count = 0; // counts number of found messages

            while (headers > 0) {

                // get headerType and content from user input
                Scanner scanner = new Scanner(System.in);
                String entry = scanner.nextLine();
                String[] header = entry.split("\\:+\\s"); // split entry by ": "

                String headerType = header[0];
                String content = header[1];

                // PREPARE REST OF QUERY //

                if (headerType.equals("Topic")) {
                    query = query + " AND Topic=" + "\'" + content + "\'";

                } else if (headerType.equals("Origin")) {
                    query = query + " AND Origin=" + "\'" + content + "\'";

                }
                else if (headerType.equals("Subject")) {
                    query = query + " AND Subject=" + "\'" + content + "\'";

                }
                else if (headerType.equals("Recipient")) {
                    query = query + " AND Recipient=" + "\'" + content + "\'";

                } else { query = null; }

                // add end (;) to query
                if (headers == 1) {
                    query = query + ";";
                }
                headers -= 1;
            }

            // EXECUTE //
            ArrayList<ArrayList<String>> resultSet = Database.read(query, Database.connect());
            for (ArrayList<String> result : resultSet) {
                count += 1;
            }

            if (count == 0) {
                System.out.println("No messages found");
            } else { // print ids of found messages

                System.out.println("MESSAGES " + count);
                for(int i=0; i < resultSet.size(); i++){
                    String idArray = resultSet.get(i).toString(); //gets the element from the arraylist
                    String oneBracket = idArray.replace("[",""); //removes one square bracket
                    String id = oneBracket.replace("]",""); //removes other square bracket
                    System.out.println(id);
                }
            }
        }
        else { System.out.println("You have entered a time in the future"); }
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


