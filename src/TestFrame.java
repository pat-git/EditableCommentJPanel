
import java.awt.*;
import java.io.IOException;
import javax.swing.*;

/**
 * The TestFrame is a test/example class to test the functionality of the comment manager.
 *
 * @version 1.0
 * @author Patrick Schießl
 */
public final class TestFrame extends JFrame {

    /**
     * The main text area.
     */
    private JTextArea textArea;
    /**
     * The reference to the comment manager.
     */
    private CommentManager commentManager;

    /**
     * The main method of the Chatsystem. Creates a MainFrame
     * which is a JFrame.
     *
     * @param args the arguments/start parameters of this program which will not
     *             be used
     */
    public static void main(String[] args) throws IOException {
        new TestFrame();
    }

    /**
     * Creates a new MainFrame which is a JFrame and initializes it. This
     * MainFrame visualizes the Chatsystem.
     */
    private TestFrame() throws IOException {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("EditableComments");
        setSize(getScreenSize().width / 2, getScreenSize().height / 2);
        setLocation(getScreenSize().width / 3, getScreenSize().height / 3);
        revalidate();
        createTestScenario();
    }

    /**
     * Creates and setups all components that are needed to test the scenario.
     */
    private void createTestScenario() {
        getContentPane().setLayout(new BorderLayout());
        JButton addCommentButton = new JButton("Add Comment");
        getContentPane().add(addCommentButton, BorderLayout.SOUTH);
        createTextArea();
        addButtonListener(addCommentButton);
    }

    /**
     * Creates the text area with a example text.
     */
    private void createTextArea() {
        textArea = new JTextArea(100, 100);
        textArea.setEditable(true);
        textArea.setBounds(0, 0, getScreenSize().width / 2, getScreenSize().height / 2);
        textArea.setText("Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor " +
                "invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. \nAt vero eos et accusam et " +
                "justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum " +
                "dolor sit amet. \nLorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod " +
                "tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua.\n At vero eos et " +
                "accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est " +
                "Lorem ipsum dolor sit amet.");
        commentManager = new CommentManager(textArea);
        getContentPane().add(commentManager.getMainTextAreaPane(), BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * Adds the button listener to the given button. This button listener will add a comment on the currently selected
     * offset (the position of the mouse caret in the text area) to the selected offset + 4. This method has only be
     * created to test the CommentManager.
     *
     * @param addCommentButton The button which should add comments.
     */
    private void addButtonListener(JButton addCommentButton) {
        addCommentButton.addActionListener(e -> {
            int offset = textArea.getCaretPosition();
            commentManager.addComment(offset, offset + 4);
        });
    }


    /**
     * Returns the screen-size (amount of pixels) of the main monitor.
     *
     * @return the screen-size as a {@code Dimension}
     */
    private Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }




}