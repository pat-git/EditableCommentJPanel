import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CommentManager {

    /**
     * The map in which all editable comment panels are stored. The key represents a list of offsets. The value is the
     * EditableCommentPanel which is attached to the all offsets in the list of offsets.
     */
    private Map<List<Integer>, EditableCommentPanel> comments = new HashMap<>();
    /**
     * The visibleComments stores the last shown visible comment. If another comment (not the current visible comment)
     * will be displayed the current visible comment will be hidden and the other comment will get the new visible
     * comment.
     */
    private EditableCommentPanel visibleComment;
    /**
     * Stores the text area that should be manage by the CommentManager.
     */
    private JTextArea textAreaToManage;
    /**
     * The layered pane in which the textAreaToManage is located.
     */
    private JLayeredPane mainTextAreaPane;
    /**
     * Creates a CommentManager which manages the comments in the given text area.
     *
     * @param textArea The text area of which the comments should be managed by the CommentManager.
     */
    CommentManager(JTextArea textArea){
        this.textAreaToManage = textArea;
        createLayeredPane();
        createTextAreaListener();
    }

    private void createLayeredPane() {
        mainTextAreaPane = new JLayeredPane();
        mainTextAreaPane.setLayout(null);
        mainTextAreaPane.add(textAreaToManage, 0 ,0);
    }

    /**
     * Adds the mouse listener to the text area that should be managed by this class.
     */
    private void createTextAreaListener() {
        textAreaToManage.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent mouseEvent) {
                int offset = textAreaToManage.viewToModel(mouseEvent.getPoint());
                EditableCommentPanel selectedComment = getEditableCommentPanel(offset);
                if (visibleComment != null && !visibleComment.isEditAble()) {
                    visibleComment.setVisible(false);
                }
                if (selectedComment != null) {
                    selectedComment.setVisible(true);
                    visibleComment = selectedComment;
                }
            }
        });
        textAreaToManage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                int offset = textAreaToManage.viewToModel(mouseEvent.getPoint());
                EditableCommentPanel selectedComment = getEditableCommentPanel(offset);
                if (visibleComment != null) {
                    visibleComment.setVisible(false);
                }
                if (selectedComment != null) {
                    visibleComment = selectedComment;
                    selectedComment.setEditAble(true);
                }
            }
        });
        textAreaToManage.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {

            }

            @Override
            public void removeUpdate(DocumentEvent e) {

            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    /**
     * Gets the editable comment panel of a given offset. A editable comment panel will be returned only if the given
     * offset is in the range/list of offsets of a comment.
     *
     * @param selectedOffset The selected offset in the textarea.
     * @return A comment panel if the selected offset is in the list of offsets of the comment. If there's no comment
     *         which contains the selected offset in their offset list it will return {@code null}.
     */
    private EditableCommentPanel getEditableCommentPanel(int selectedOffset) {
        for(List<Integer> offSetRange : comments.keySet()){
            for(Integer offset : offSetRange){
                if(offset == selectedOffset){
                    return comments.get(offSetRange);
                }
            }
        }
        return null;
    }

    /**
     * Adds a comment to the text area. The comment is located from the firstOffset to the lastOffset.
     *
     * @param firstOffset The first offset of the offset range.
     * @param lastOffset The last offset of the offset range.
     */
    void addComment(int firstOffset, int lastOffset){
        boolean offsetRangeAlreadyObtained = false;
        for(int i = firstOffset; i <= lastOffset; i++){
            if(getEditableCommentPanel(i) != null){
                offsetRangeAlreadyObtained = true;
            }
        }
        if(!offsetRangeAlreadyObtained) {
            Highlighter highlighter = textAreaToManage.getHighlighter();
            try {
                highlighter.addHighlight(firstOffset, lastOffset, new CommentHighlightPainter(Color.lightGray));
            } catch (BadLocationException e) {
                e.printStackTrace();
            }
            EditableCommentPanel editableCommentPanel = new EditableCommentPanel(firstOffset, textAreaToManage, this);
            mainTextAreaPane.add(editableCommentPanel, 1, 1);
            List<Integer> offsetRange = new ArrayList<>();
            for (int i = firstOffset; i <= lastOffset; i++) {
                offsetRange.add(i);
            }
            comments.put(offsetRange, editableCommentPanel);
            editableCommentPanel.setVisible(false);
            editableCommentPanel.setText("<<Comment>>");
        } else {
            // TODO warnUser because there's already a comment.
        }
    }

    /**
     * Updates the offsets of the comment panels if the text in the main text area has been changed.
     */
    private  void updateOffsetPosition(){
        // TODO update offsets
    }

    /**
     * Deletes a comment located in the given offset.
     *
     * @param offset The comment that should be deleted.
     */
    void deleteComment(int offset){
        EditableCommentPanel panelToDelete = getEditableCommentPanel(offset);
        // TODO Delete Panel and remove highlighting
    }

    /**
     * Gets the pane in which the main text area is located.
     *
     * @return The main text are pane.
     */
    JLayeredPane getMainTextAreaPane() {
        return mainTextAreaPane;
    }

    /**
     * This class represents the HighlightPainter of a comment.
     */
    class CommentHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter {
        /**
         * Creates the CommentHighLightPainter with the given color.
         *
         * @param color The highlighting color.
         */
        CommentHighlightPainter(Color color) {
            super(color);
        }
    }
}
