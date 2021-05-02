import java.io.IOException;
import java.net.Socket;
import java.time.Instant;
import java.util.ArrayList;

/***
 * A class for defining requests with their replies.
 */
public class Requests {

    /***
     * PROTOCOL? request.
     * @param version
     * @param identifier
     * @return reply
     * @throws IOException
     */
    public static String protocol(int version, String identifier) throws IOException {
        return "PROTOCOL? " + version + " " + identifier;
    }

    /***
     * TIME? request.
     * @return reply
     * @throws IOException
     */
    public static String time() throws IOException {
        long unixTime = Instant.now().getEpochSecond();
        return "NOW " + unixTime;
    }

    /***
     * BYE! request.
     * @param socket client socket to be closed
     * @throws IOException
     */
    public static void bye (Socket socket) throws IOException {
        socket.close();
    }

    /***
     * LIST? request.
     * @param since time since when message shall be selected
     * @param entry header and content (e.g. Topic: #announcements)
     * @return reply
     * @throws IOException
     */
    public static String list(long since, String[] entry) throws IOException {

        //output sent to other peer
        StringBuilder reply;

        // PREPARE ORIGINAL QUERY //
        String query = "SELECT MessageID FROM PoliteMessaging WHERE TimeSent>=\'" + since + "\'";

        // check if since is in future/past
        long currentTime = Instant.now().getEpochSecond();
        if (since <= currentTime) {

            int count = 0; // counts number of found messages
            int index = entry.length; // counts entry length
            while (index >= 1) {

                // add end (;) to query to finish
                if (index == 1) {
                    query = query + ";";
                    break;
                }

                // split entry by ": "
                String[] header = entry[index-1].split("\\:+\\s");

                String headerType = header[0];
                String content = header[1];

                // PREPARE REST OF QUERY //
                query = switch (headerType) {
                    case "Topic" -> query + " AND Topic=" + "\'" + content + "\'";
                    case "From" -> query + " AND Origin=" + "\'" + content + "\'";
                    case "Subject" -> query + " AND Subject=" + "\'" + content + "\'";
                    case "To" -> query + " AND Recipient=" + "\'" + content + "\'";
                    default -> null;
                };
                index -= 1;
            }

            // EXECUTE //
            ArrayList<ArrayList<String>> resultSet = Database.read(query, Database.connect());

            // count messages found
            for (ArrayList<String> result : resultSet) {
                count += 1;
            }
            if (count == 0) {
                reply = new StringBuilder("No messages found");
            }
            // print reply (number of messages found, ids of found messages)
            else {
                reply = new StringBuilder("MESSAGES " + count + " ");
                for (ArrayList<String> strings : resultSet) {
                    String idArray = strings.toString(); //gets the element from the arraylist
                    String oneBracket = idArray.replace("[", ""); //removes one square bracket
                    String id = oneBracket.replace("]", ""); //removes other square bracket
                    reply.append("\n").append(id); // add found id's to reply
                }
            }

        }
        // if time (since) entered is in the future
        else {
            reply = new StringBuilder("You have entered a time in the future");
        }
        return reply.toString();
    }

    /***
     * GET? request.
     * @param hash message ID
     * @return reply
     * @throws IOException
     */
    public static String get(String hash) throws IOException {

        // counts lines of reply
        int lineCounter = 5; // =1 because reply will always have a line in the beginning (FOUND?SORRY), and necessary headers (id, time, from, contents)

        // output sent to other peer
        StringBuilder reply = new StringBuilder("SORRY");

        // PREPARE QUERY //
        String query = "SELECT * FROM PoliteMessaging WHERE MessageID = \"" + hash + "\";";

        // EXECUTE //
        ArrayList<ArrayList<String>> resultSet = Database.read(query, Database.connect());

        // count found messages
        int count = 0;
        assert resultSet != null;
        for (ArrayList<String> result : resultSet) {

            count += 1;

            // reply if message found -> print message
            if (count != 0) {
                // increase line counter based on message length
                if (!result.get(3).isEmpty()) { // 0...1
                    lineCounter +=1;
                }
                if (!result.get(4).isEmpty()) { // 0...1
                    lineCounter +=1;
                }
                if (!result.get(5).isEmpty()) { // 0...1
                    lineCounter +=1;
                }
                int contents = Integer.parseInt(result.get(6));
                lineCounter += contents; // increase line count for the body

                // create first line of reply
                reply = new StringBuilder( lineCounter + " FOUND" +"\n");

                // add rest of message to reply
                reply.append("Message-id: SHA-256 ").append(result.get(0)).append("\n");
                reply.append("Time-sent: ").append(result.get(1)).append("\n");
                reply.append("From: ").append(result.get(2)).append("\n");
                if (!result.get(3).isEmpty()) { // 0...1
                    reply.append("To: ").append(result.get(3)).append("\n");
                }
                if (!result.get(4).isEmpty()) { // 0...1
                    reply.append("Topic: ").append(result.get(4)).append("\n");
                }
                if (!result.get(5).isEmpty()) { // 0...1
                    reply.append("Subject: ").append(result.get(5)).append("\n");
                }
                reply.append("Contents: ").append(result.get(6)).append("\n");
                reply.append(result.get(7)).append("\n");
            }
        }
        return reply.toString();
    }
}


