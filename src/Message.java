//TODO: comment correctly
//TODO: ignore empty headers that are not necessary
/***
 * Code availability: https://www.dev2qa.com/how-to-write-console-output-to-text-file-in-java/
 * Code availability for SHA-256: https://www.geeksforgeeks.org/sha-256-hash-in-java/
 */

import org.w3c.dom.ls.LSOutput;

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
     * Constructor
     */
    public Message() throws IOException {
    }

    /*** to read data from the keyboard ***/
    BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));

    /*** to save message to a text file ***/
    FileWriter fileWriter = new FileWriter("messages.txt");
    PrintWriter printWriter = new PrintWriter(fileWriter);


    public void createMessage() throws IOException, NoSuchAlgorithmException {

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

        /*** Generate SHA-256 sum of the message ***/
        //TODO: put the id generation here?
    }

    /*** generates the message id and writes the message to a text file ***/
    public void writeToFile() throws NoSuchAlgorithmException {
        /*** Generate SHA-256 sum of the message ***/
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");

        BigInteger time_big = new BigInteger(String.valueOf(unixTime)); // time to big int
        BigInteger contents_big = new BigInteger(String.valueOf(contents)); // contens to big int

        // string headers
        messageDigest.update(from.getBytes(StandardCharsets.UTF_8));
        messageDigest.update(topic.getBytes(StandardCharsets.UTF_8));
        messageDigest.update(to.getBytes(StandardCharsets.UTF_8));
        messageDigest.update(subject.getBytes(StandardCharsets.UTF_8));
        messageDigest.update(body.getBytes(StandardCharsets.UTF_8));
        messageDigest.update(time_big.toByteArray()); // add time header as byte array
        messageDigest.update(contents_big.toByteArray()); // add contents header as byte array

        messageDigest.digest();

        /*** write to file ***/
        printWriter.println("Message-ID: SHA-256 " + toHexString(messageDigest.digest()) );
        printWriter.println("Time-sent: " + unixTime);
        printWriter.println("From: " + from);
        printWriter.println("Topic: " + topic); // 0..1
        printWriter.println("To: " + to); // 0..1
        printWriter.println("Subject: " + subject); // 0..1
        printWriter.println("Contents: " + contents);
        printWriter.println(body);
        printWriter.close();
    }

    /*** to visualize the hash ***/
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

    /*** to run the program ***/
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        Message message = new Message();

        message.createMessage();
        message.writeToFile();

    }
}
