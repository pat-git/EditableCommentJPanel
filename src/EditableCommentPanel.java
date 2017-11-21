import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * This class represents a editable comment. It's a JPanel which has a text area in it to give the user the possibility
 * to edit the content of the editable comment panel.
 */
public class EditableCommentPanel extends JPanel {

    /**
     * The position (offset) of the comment.
     */
    private int positionOffset;
    /**
     * The top level text area in which this comment is located.
     */
    private JTextArea topLevelTextArea;
    /**
     * The text area of the comment.
     */
    private JTextArea commentTextArea;
    /**
     * The flag that stores if the comment is editable.
     */
    private boolean editAble;
    /**
     * The reference to the commentManager which is managing this comment.
     */
    private CommentManager commentManager;

    /**
     * Creates a EditableCommentPanel with the given position offset and top level text area.
     *
     * @param positionOffset The position (offset) of the EditAbleCommentPanel in the text area.
     * @param topLevelTextArea The text area in which this panel is located.
     */
    EditableCommentPanel(int positionOffset, JTextArea topLevelTextArea, CommentManager commentManager){
        this.positionOffset = positionOffset;
        this.topLevelTextArea = topLevelTextArea;
        this.commentManager = commentManager;
        commentTextArea = new JTextArea();
        addCommentTextAreaListener();
        setBorder(new EditableCommentStyle());
        add(commentTextArea);
        repaint();
        revalidate();
    }

    /**
     * Add the listeners to the comment text area.
     */
    private void addCommentTextAreaListener() {
        commentTextArea.getDocument().addDocumentListener(new DocumentListener() {
            /**
             * Will be invoked when the user inserts text to comment text area. The position of the comment box will
             * then be refreshed.
             *
             * @param e The DocumentEvent.
             */
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshPosition();
            }

            /**
             * Will be invoked when the user removes text from the comment text area. The position of the comment box
             * will then be refreshed.
             *
             * @param e The DocumentEvent.
             */
            @Override
            public void removeUpdate(DocumentEvent e) {
                if(commentTextArea.getText().equals("")){
                    commentManager.deleteComment(positionOffset);
                } else {
                    refreshPosition();
                }
            }

            /**
             * Will be invoked when the user changes the content of the comment text area. The position of the comment
             * box will then be refreshed.
             *
             * @param e The DocumentEvent.
             */
            @Override
            public void changedUpdate(DocumentEvent e) {
                refreshPosition();
            }
        });
        commentTextArea.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component loses the keyboard focus. The comment box will be hidden if invoked.
             *
             * @param e The FocusEvent.
             */
            @Override
            public void focusLost(FocusEvent e) {
                editAble = false;
                setVisible(false);
            }
        });
    }

    /**
     * Refreshes the size of the comment panel.
     */
    private void refreshSize() {
        setSize(new Dimension(commentTextArea.getPreferredSize().width + 22, commentTextArea.getPreferredSize().height + 12));
        revalidate();
        repaint();
    }

    /**
     * The paintComponent method of the JPanel. Will be used to change the appearance of the comment panel.
     *
     * @param g The graphics of the component.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    /**
     * Refreshes the position (and size) of the comment panel.
     */
    private void refreshPosition(){
        refreshSize();
        int width = getSize().width;
        int height = getSize().height;
        Rectangle positionRectangle = null;
        try {
            positionRectangle = topLevelTextArea.modelToView(positionOffset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        if(positionRectangle != null) {
            int x = positionRectangle.x;
            int y = positionRectangle.y + topLevelTextArea.getFont().getSize() + 2;
            setBounds(x, y, width, height);
            repaint();
            revalidate();
        } else {
            // TODO error handling
        }
    }

    /**
     * Sets the text of comment text area.
     *
     * @param text The new text of the text area.
     */
    void setText(String text){
        commentTextArea.setText(text);
        refreshPosition();
    }

    /**
     * Sets the boolean flag editAble. If true the comment will be editable (if the user has the write lock of the
     * line) else the comment will not be editable.
     *
     * @param editAble {@code true} if the comment should be editable (the user must also have the write lock of
     *                      the line in which the comment is located).
     */
    void setEditAble(boolean editAble){
        // TODO check if user has the write lock
        this.editAble = editAble;
    }

    /**
     * Checks if the comment is editable.
     *
     * @return {@code true} if the code is editable, else {@code false}.
     */
    boolean isEditAble() {
        return editAble;
    }
}
