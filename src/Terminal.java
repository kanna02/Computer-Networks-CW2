import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * A class for managing the GUI frames.
 */
public class Terminal {

    private static JFrame frame;

    public static void nextPanel(String next, JPanel panel)  {

        // IF NEXT PANEL IS START MENU //
        if ("start".equals(next)) {

            // REMOVE CURRENT PANEL //
            Container contentPane = removePanel(panel);

            // ADD NEW PANEL //
            panel = new StartMenu().getStartMenuPanel();
            addPanel(panel, contentPane);

        }
        // IF NEXT PANEL IS VIEW MESSAGES //
        else if ("view".equals(next)){

            // REMOVE CURRENT PANEL //
            Container contentPane = removePanel(panel);

            // ADD NEW PANEL //
            panel = new ViewMessages().getViewMessagesPanel();
            addPanel(panel, contentPane);
        }
        // IF NEXT PANEL IS NEW MESSAGE
        else if ("new".equals(next)){

            // REMOVE CURRENT PANEL //
            Container contentPane = removePanel(panel);

            // ADD NEW PANEL //
            panel = new NewMessage().getNewMessagePanel();
            addPanel(panel, contentPane);
        }
        // IF NEXT PANEL IS REQUEST FORM
        else if ("request".equals(next)){

            // REMOVE CURRENT PANEL //
            Container contentPane = removePanel(panel);

            // ADD NEW PANEL //
            panel = new RequestForm().getRequestFormPanel();
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
