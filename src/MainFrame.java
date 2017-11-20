
import java.awt.*;
import java.awt.event.*;
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
        setSize(getScreenSize().width / 2, getScreenSize().height / 2);
        setLocation(getScreenSize().width / 3, getScreenSize().height / 3);
        //debugCursor = true;
        revalidate();
        createPanelToolTip();
    }

    private void createPanelToolTip() {
        getContentPane().setLayout(new BorderLayout());
        JPanel mainTextAreaPanel = new JPanel();
        mainTextAreaPanel.setLayout(null);
        JButton addCommentButton = new JButton("Add Comment");
        addButtonListener(addCommentButton, mainTextAreaPanel);
        getContentPane().add(mainTextAreaPanel, BorderLayout.CENTER);
        getContentPane().add(addCommentButton, BorderLayout.SOUTH);
        Dimension size = getContentPane().getSize();
        textArea = new JTextArea();
        textArea.setRows(29);
        textArea.setColumns(100);
        textArea.setEditable(true);
        textArea.setBounds(0, 0, size.width, size.height);
        mainTextAreaPanel.add(textArea);
        repaint();
        revalidate();
    }

    private void addButtonListener(JButton addCommentButton, JComponent textAreaComponent) {
        addCommentButton.addActionListener(e -> {
            int offset = textArea.getCaretPosition();
            Rectangle position = null;
            try {
                position = textArea.modelToView(offset);
            } catch (BadLocationException e1) {
                e1.printStackTrace();
            }
            if(position != null){
                addComment(textAreaComponent, position);
            }
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

    private void addComment(JComponent parentComponent, Rectangle position){
        JPanel commentPanel = new JPanel();
        EditableToolTipPanel editableToolTipPanel = new EditableToolTipPanel(commentPanel);
        commentPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                editableToolTipPanel.setVisible(true);
                editableToolTipPanel.requestFocusInWindow();
                editableToolTipPanel.setClickedToEdit(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                editableToolTipPanel.setVisible(true);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(!editableToolTipPanel.hasClickedToEdit()){
                    editableToolTipPanel.setVisible(false);
                }
            }
        });
        parentComponent.add(commentPanel);
        parentComponent.add(editableToolTipPanel);
        JLabel commentLabel = new JLabel("C");
        commentPanel.add(commentLabel);
        commentPanel.setBounds(position.x, position.y,20, 20);
        editableToolTipPanel.setVisible(false);
        editableToolTipPanel.setText("Test");
    }
}