import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class EditableToolTipPanel extends JPanel {

    private int positionOffset;
    private JTextArea topLevelTextArea;
    private JTextArea commentTextArea;
    private boolean clickedToEdit;

    public EditableToolTipPanel(int positionOffset, JTextArea topLevelTextArea){
        this.positionOffset = positionOffset;
        this.topLevelTextArea = topLevelTextArea;
        commentTextArea = new JTextArea();
        commentTextArea.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                refreshPosition();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                refreshPosition();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
        commentTextArea.addFocusListener(new FocusAdapter() {
            /**
             * Invoked when a component loses the keyboard focus.
             *
             * @param e
             */
            @Override
            public void focusLost(FocusEvent e) {
                clickedToEdit = false;
                setVisible(false);
            }
        });
        setBorder(new EditableToolTipPanelStyle());
        add(commentTextArea);
        repaint();
        revalidate();
    }

    private void refreshSize() {
        setSize(new Dimension(commentTextArea.getPreferredSize().width + 22, commentTextArea.getPreferredSize().height + 12));
        revalidate();
        repaint();
    }

    public void paintComponent(Graphics g) {
        //((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        super.paintComponent(g);
    }

    public void refreshPosition(){
        refreshSize();
        int width = getSize().width;
        int height = getSize().height;
        Rectangle postionRectangle = null;
        try {
            postionRectangle = topLevelTextArea.modelToView(positionOffset);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        int x = postionRectangle.x;
        int y = postionRectangle.y + topLevelTextArea.getFont().getSize();
        setBounds(x , y , width, height);
        repaint();
        revalidate();
    }

    public void setText(String text){
        commentTextArea.setText(text);
        refreshPosition();
    }

    public void setClickedToEdit(boolean clickedToEdit){
        this.clickedToEdit = clickedToEdit;
    }

    public boolean hasClickedToEdit() {
        return clickedToEdit;
    }
}
