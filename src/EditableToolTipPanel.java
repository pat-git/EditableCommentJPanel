import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class EditableToolTipPanel extends JPanel {

    private int positionOffset;
    private Rectangle currentAbsolutePosition;
    private JTextArea textArea;
    private JPanel parentPanel;
    private boolean clickedToEdit;

    public EditableToolTipPanel(){
        textArea = new JTextArea();
        textArea.getDocument().addDocumentListener(new DocumentListener() {
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
        textArea.addFocusListener(new FocusAdapter() {
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
        add(textArea);
        repaint();
        revalidate();
    }

    private void refreshSize() {
        setSize(new Dimension(textArea.getPreferredSize().width + 22,textArea.getPreferredSize().height + 12));
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
        int x = parentPanel.getX();
        int y = parentPanel.getY() + parentPanel.getHeight() + 2;
        setBounds(x , y , width, height);
        repaint();
        revalidate();
    }

    public void setText(String text){
        textArea.setText(text);
        refreshPosition();
    }

    public void setClickedToEdit(boolean clickedToEdit){
        this.clickedToEdit = clickedToEdit;
    }

    public boolean hasClickedToEdit() {
        return clickedToEdit;
    }
}
