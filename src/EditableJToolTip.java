
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class EditableJToolTip extends JToolTip{

    public EditableJToolTip (JTextArea textArea){
        this.setBackground(Color.lightGray);
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField textField = new JTextField(getTipText());
                textField.setColumns(10);
                System.out.println(getParent());
                getParent().add(textField);
                textField.requestFocusInWindow();
                revalidate();
                textField.addActionListener(a1 -> {
                    setTipText(textField.getText());
                    textArea.setToolTipText(textField.getText());
                    getParent().remove(textField);
                });
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
