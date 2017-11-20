
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private Map<List<Integer>, EditableToolTipPanel> comments = new HashMap<>();

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
        revalidate();
        createPanelToolTip();
    }

    private void createPanelToolTip() {
        getContentPane().setLayout(new BorderLayout());
        JLayeredPane mainTextAreaPane = new JLayeredPane();
        mainTextAreaPane.setLayout(null);
        JButton addCommentButton = new JButton("Add Comment");
        getContentPane().add(mainTextAreaPane, BorderLayout.CENTER);
        getContentPane().add(addCommentButton, BorderLayout.SOUTH);
        Dimension size = getSize();
        textArea = new JTextArea(100, 100);
        textArea.setEditable(true);
        textArea.setBounds(0, 0, getScreenSize().width / 2, getScreenSize().height / 2);
        textArea.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int offset = textArea.viewToModel(mouseEvent.getPoint());
                EditableToolTipPanel selectedComment = getEditableToolTipPanel(offset);
                if (selectedComment != null) {
                    selectedComment.setVisible(true);
                    selectedComment.setClickedToEdit(true);
                }
            }

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {
               int offset = textArea.viewToModel(mouseEvent.getPoint());
               EditableToolTipPanel selectedComment = getEditableToolTipPanel(offset);
                if (selectedComment != null) {
                    selectedComment.setVisible(true);
                }
            }

            @Override
            public void mouseExited(MouseEvent mouseEvent) {
                int offset = textArea.viewToModel(mouseEvent.getPoint());
                EditableToolTipPanel selectedComment = getEditableToolTipPanel(offset);
                if (selectedComment != null) {
                    selectedComment.setVisible(false);
                    selectedComment.setClickedToEdit(true);
                }
            }
        });
        mainTextAreaPane.add(textArea, 0 ,0);
        addButtonListener(addCommentButton, mainTextAreaPane);
        repaint();
        revalidate();
    }

    private void addButtonListener(JButton addCommentButton, JLayeredPane textAreaPane) {
        addCommentButton.addActionListener(e -> {
            int offset = textArea.getCaretPosition();
            addComment(textAreaPane, offset);
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

    private void addComment(JLayeredPane pane, int offset){
        EditableToolTipPanel editableToolTipPanel = new EditableToolTipPanel(offset, textArea);
        pane.add(editableToolTipPanel, 1, 2);
        List<Integer> offsetRange = new ArrayList<>();
        offsetRange.add(offset);
        offsetRange.add(offset+1);
        comments.put(offsetRange, editableToolTipPanel);
        editableToolTipPanel.setVisible(false);
        editableToolTipPanel.setText("Test");
    }

    public EditableToolTipPanel getEditableToolTipPanel(int selectedOffset) {
        System.out.println(selectedOffset);
        for(List<Integer> offSetRange : comments.keySet()){
            for(Integer offset : offSetRange){
                if(offset == selectedOffset){
                    return comments.get(offSetRange);
                }
            }
        }
        return null;
    }
}