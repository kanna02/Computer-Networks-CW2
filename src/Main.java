import javax.swing.*;
import java.io.IOException;

public class Main {

    private static JFrame frame;
    private static JPanel startConnection;
    private static JPanel test;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {


        // set look and feel
        try {
            UIManager.setLookAndFeel(
                    new com.jtattoo.plaf.mint.MintLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e) { }

        frame = new JFrame("Polite Messaging");
        Terminal.setFrame(frame);
        startConnection = new StartConnection().getStartConnectionPanel();
        test = new StartMenu().getStartMenuPanel();


        frame.setContentPane(test);
        frame.pack();
        frame.setSize(700,600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
