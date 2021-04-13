//TODO: comment correctly
/***
 * Code availability: https://www.dev2qa.com/how-to-write-console-output-to-text-file-in-java/
 */

import java.io.*;
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
    public Message(/***String id, Timestamp time, String origin, int contents***/) throws IOException {
//        this.id = id;
//        this.time = time;
//        this.origin = origin;
//        this.contents = contents;
    }

    /*** to read data from the keyboard ***/
    BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

    /*** to save message to a text file ***/
    FileWriter fileWriter = new FileWriter("messages.txt");
    PrintWriter printWriter = new PrintWriter(fileWriter);


    /*** Method to read in the origin of the message ***/
    public String Origin() throws IOException {
        System.out.print("Enter origin: ");
        String from = keyboardReader.readLine();

        // Print the user input to text file
        printWriter.println("From: "+ from);

        return from;
    }

    /*** Method to read in the topic of a message. ***/
    public String Topic() throws IOException {
        System.out.print("Enter topic: ");
        String topic = keyboardReader.readLine();

        // Print the user input to text file
        printWriter.println("Topic: " + topic);

        return topic;
    }


    /*** Method to read in the subject of the message. ***/
    public String Subject() throws IOException {
        System.out.print("Subject: ");
        String subject = keyboardReader.readLine();

        // Print the user input to text file
        printWriter.println("Subject: " + subject);

        return subject;
    }

    /** Method to read in an input of 0...* lines. ***/
    public String Body() throws IOException {
        System.out.println("Enter message. Press TAB and then ENTER to finish");
        Scanner scanner = new Scanner(System.in);
        String body;
        scanner.useDelimiter("\\t");
        while (true) {
            body = scanner.next();
            break;
        }

        // Print the user input to text file
        printWriter.println("Message: " + "\n" + body);
        printWriter.close();

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
