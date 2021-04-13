//TODO: comment correctly
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/***
 * A class for writing messages.
 */
public class Message {
    private String id;
    private Timestamp time;
    private String origin;
    private int contents;


    /**
     * Creates a message with the following necessary parameters:
     */
    public Message(/***String id, Timestamp time, String origin, int contents***/) {
//        this.id = id;
//        this.time = time;
//        this.origin = origin;
//        this.contents = contents;
    }

    /*** to read data from the keyboard ***/
    BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

    /*** Method to read in the origin of the message ***/
    public String Origin() throws IOException {
        System.out.print("Enter origin: ");
        String from = keyboardReader.readLine();
        System.out.println("Message sent from: " + from);
        return from;
    }

    /*** Method to read in the topic of a message. ***/
    public String Topic() throws IOException {
        System.out.print("Enter topic: ");
        String topic = keyboardReader.readLine();
        System.out.println("Message topic is: " + topic);
        return topic;
    }

    /*** Method to read in the subject of the message. ***/
    public String Subject() throws IOException {
        System.out.print("Enter subject: ");
        String subject = keyboardReader.readLine();
        System.out.println("Message subject is: " + subject);
        return subject;
    }

    /** Method to read in an input of 0...* lines. ***/
    public String Body() throws IOException {
        System.out.println("Enter message. Press tab to finish");
        Scanner scanner = new Scanner(System.in);
        String body;
        scanner.useDelimiter("\\t");
        while (true) {
            body = scanner.next();
            break;
        }
        System.out.println("Your message is: " + "\n" + body);
        return body;
    }








    /*** run the program ***/
    public static void main(String[] args) throws IOException {
        Message message = new Message();
        message.Origin();
        message.Topic();
        message.Subject();
        message.Body();
    }

}
