
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.text.BadLocationException;

/**
 * The MainFrame is the main frame of the GUI (graphical user interface)
 * of the Chatsystem.
 *
 * @version 1.0
 */
public final class MainFrame extends JFrame {

    private JTextArea textArea;
    private Map<Integer, String> comments = new HashMap<>();
    private boolean debugCursor;

    /**
     * The main method of the Chatsystem. Creates a MainFrame
     * which is a JFrame.
     *
     * @param args the arguments/start parameters of this program which will not
     *             be used
     */
    public static void main(String[] args) throws IOException {
        new MainFrame();
    }

    /**
     * Creates a new MainFrame which is a JFrame and initializes it. This
     * MainFrame visualizes the Chatsystem.
     */
    private MainFrame() throws IOException {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setTitle("EditableComments");
        setSize(getScreenSize().width / 4, getScreenSize().height / 4);
        setLocation(getScreenSize().width / 5, getScreenSize().height / 5);
        setLayout(new BorderLayout());
        //debugCursor = true;
        addTextArea();
        addTestComments();
        revalidate();
    }


    private void addTestComments() {
        textArea.append("1234");
        textArea.revalidate();
        comments.put(0, "Mein Kommentar");
        comments.put(1, "Mein Kommentar 2");
        comments.put(2, "Mein Kommentar 3");
        comments.put(3, "Mein Kommentar 4");
        printOutPositions();
    }

    private void printOutPositions() {
        new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for(int i = 0; i < 4; i++){
                Rectangle rectangle = null;
                try {
                    rectangle = textArea.modelToView(i);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
                if(rectangle != null) {
                    double x = rectangle.getX();
                    double y = rectangle.getY();
                    System.out.println("X: " + x + " Y: " + y);
                }
            }
        }).start();
    }

    private void addTextArea() {
        textArea = new JTextArea() {
            @Override
            public JToolTip createToolTip() {
                JToolTip toolTip = new EditableJToolTip(this);
                return toolTip;
            }

            @Override
            public String getToolTipText(MouseEvent event) {
                for (int offset : comments.keySet()) {
                    Rectangle rectangle = null;
                    try {
                        rectangle = textArea.modelToView(offset);
                    } catch (BadLocationException e) {
                        e.printStackTrace();
                    }
                    double x = rectangle.getX();
                    double y = rectangle.getY();
                    if (event.getX() <= x + 2 && event.getX() >= x - 2 && event.getY() <= y + 12 && event.getY() >= y - 7) {
                        return comments.get(offset);
                    }
                }
                if(debugCursor){
                    return "X: " + event.getX() + " Y: " + event.getY();
                }
                // NO TOOLTIP
                return null;
            }
        };
        textArea.setToolTipText("Text");
        textArea.setRows(29);
        textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        getContentPane().add(scrollPane, BorderLayout.NORTH);
    }


    /**
     * Returns the screen-size (amount of pixels) of the main monitor.
     *
     * @return the screen-size as a {@code Dimension}
     */
    private Dimension getScreenSize() {
        return Toolkit.getDefaultToolkit().getScreenSize();
    }

    /**
     * Adds the given text to the textArea.
     * This method is synchronized because it will be used in the networkThread.
     *
     * @param text The text to append
     */
    public synchronized void appendToTextArea(String text) {
        textArea.append("\n" + text);
        textArea.revalidate();
    }
}