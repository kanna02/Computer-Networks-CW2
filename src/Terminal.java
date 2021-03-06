import javax.swing.*;
import java.awt.*;

/**
 * A class for managing the GUI frame.
 */
public class Terminal {

    private static JFrame frame;

    /**
     * Sets given panel to frame.
     * @param next identifier of panel
     * @param panel panel
     */
    public static void nextPanel(String next, JPanel panel)  {

        // IF NEXT PANEL IS VIEW MESSAGES //
        if ("view".equals(next)){

            // REMOVE CURRENT PANEL //
            Container contentPane = removePanel(panel);

            // ADD NEW PANEL //
            panel = new ViewMessages().getViewMessagesPanel();
            addPanel(panel, contentPane);
        }
    }

    /**
     * Getter for the frame.
     * @return frame
     */
    public static JFrame getFrame() {
        return frame;
    }

    /**
     * Setter for the frame.
     * @param frame The JFrame
     */
    public static void setFrame(JFrame frame) {
        Terminal.frame = frame;
    }

    /**
     * Method to remove the current JPanel on the JFrame.
     * @param panel Panel to remove
     * @return contentPane
     */
    public static Container removePanel(JPanel panel){
        JPanel contentPane = (JPanel) frame.getContentPane();
        contentPane.removeAll();

        return contentPane;
    }

    /**
     * Method to add a new JPanel on the JFrame.
     * @param panel New JPanel
     * @param contentPane Content Pane
     */
    public static void addPanel(JPanel panel, Container contentPane){

        // ADD PANEL TO CONTENT PANE //
        contentPane.add(panel);
        contentPane.revalidate();
        contentPane.repaint();

        // ADD CONTENT PANE TO FRAME //
        frame.add(contentPane);
        frame.setSize(1200, 600);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}
