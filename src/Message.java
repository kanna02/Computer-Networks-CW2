//TODO: comment correctly
//TODO: change methods to voids?
/***
 * Code availability: https://www.dev2qa.com/how-to-write-console-output-to-text-file-in-java/
 * Code availability for SHA-256: https://www.geeksforgeeks.org/sha-256-hash-in-java/
 */

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
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

    /*** Generates the SHA-256 hash for the message ***/

    public static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 32)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }



    /*** Gets the UNIX epoch time ***/
    public long Time(){
        long unixTime = Instant.now().getEpochSecond();
        printWriter.println("Time: "+ unixTime);
        return unixTime;
    }

    /*** Method to read in the origin of the message ***/
    public String Origin() throws IOException {
        System.out.print("Enter origin: ");
        String from = keyboardReader.readLine();
        printWriter.println("From: "+ from);

        return from;
    }

    //TODO ignore if input is null
    public String To() throws IOException {
        System.out.print("Enter recipient: ");
        String to = keyboardReader.readLine();
        printWriter.println("To: " + to);

        return to;
    }

    //TODO ignore if input is null
    /*** Method to read in the topic of a message. ***/
    public String Topic() throws IOException {
        System.out.print("Enter topic: ");
        String topic = keyboardReader.readLine();

        if (topic != null) {
            printWriter.println("Topic: " + topic);
        }
        return topic;
    }

    //TODO ignore if input is null
    /*** Method to read in the subject of the message. ***/
    public String Subject() throws IOException {
        System.out.print("Subject: ");
        String subject = keyboardReader.readLine();
        printWriter.println("Subject: " + subject);

        return subject;
    }


    /** Method to read in an input of 0...* lines. ***/
    public String Body() throws IOException {
        System.out.println("Enter message. Press TAB and then ENTER to finish");
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\t");

        int count = 0;  //contents counter

        String body;
        while (true) {
            body = scanner.next();
            // count lines
            String[] line = body.split("\n"); //look for new line
            count += line.length;
            break;
        }
        // Print the user input to text file
        printWriter.println("Contents: "+ count + "\n" + body);
        printWriter.close();

        return body;
    }




    /*** run the program ***/
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Message message = new Message();
        //headers
        /*** Message ID ***/
        String s1 = "GeeksForGeeks";
        //String sum[] = {"unixTime","body"};
        System.out.println( s1 + " : " + toHexString(getSHA(s1)));

//        message.Time();
//        message.Origin();
//        message.Topic();
//        message.To();
//        message.Subject();
//        //body
//        message.Body();
    }

}
