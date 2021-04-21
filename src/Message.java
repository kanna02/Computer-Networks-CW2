//TODO: comment correctly
//TODO: hash ID
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
    private long unixTime;
    private String from;
    private String to;
    private String subject;
    private String topic;
    private String body;
    private int contents;


    /**
     * Creates a message with the following necessary parameters:
     */
    public Message() throws IOException {

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


    public void createMessage() throws IOException {

        /*** time ***/
        unixTime = Instant.now().getEpochSecond();

        /*** from ***/
        System.out.print("Enter origin: ");
        from = keyboardReader.readLine();

        /*** to (recipient) ***/
        System.out.print("Enter recipient: ");
        to = keyboardReader.readLine();

        /*** subject ***/
        System.out.print("Subject: ");
        subject = keyboardReader.readLine();

        /*** topic ***/
        System.out.print("Enter topic: ");
        topic = keyboardReader.readLine();

        /*** body (includes contents) ***/
        System.out.println("Enter message. Press TAB and then ENTER to finish");
        Scanner scanner = new Scanner(System.in);
        scanner.useDelimiter("\\t");

        contents = 0;  //contents counter

        while (true) {
            body = scanner.next();
            // count lines
            String[] line = body.split("\n"); //look for new line
            contents += line.length;
            break;
        }

        //TODO: place id generation here


    }
    /*** writes the message to a text file ***/
    public void writeToFile() {

        //write id to file
        printWriter.println("Time-sent: " + unixTime);
        printWriter.println("From: " + from);
        printWriter.println("Topic: " + topic); // 0..1
        printWriter.println("To: " + to); // 0..1
        printWriter.println("Subject: " + subject); // 0..1
        printWriter.println("Contents: " + contents);
        printWriter.println(body);
        printWriter.close();
    }


    /*** run the program ***/
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Message message = new Message();

        /*** Message ID ***/
//        String s1 = "GeeksForGeeks";
//       // String sum[] = {"unixTime","body"};
//        System.out.println( s1 + " : " + toHexString(getSHA(s1)));

        message.createMessage();
        message.writeToFile();

    }

}
