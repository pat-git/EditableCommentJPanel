import javax.swing.border.Border;
import java.awt.*;

public class EditableCommentStyle implements Border {

    /**
     * The background color of the text/comment.
     */
    private final Color INNER_COLOR = Color.WHITE;
    /**
     * The color of the border of the comment box/panel.
     */
    private final Color BORDER_COLOR = Color.BLACK;

    /**
     * Paints the border for the specified component with the specified
     * position and size.
     *
     * @param c      the component for which this border is being painted
     * @param g      the paint graphics
     * @param x      the x position of the painted border
     * @param y      the y position of the painted border
     * @param width  the width of the painted border
     * @param height the height of the painted border
     */
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D g2d = (Graphics2D) g;
        width-=1;
        height-=1;

        // y-coordinate of the bottom side of the comment box
        int yBottom = y + height;

        // Draw the background/inner color of the comment box.
        g2d.setPaint(INNER_COLOR);
        g2d.fillRect(x, y, width, yBottom);
        // Draw the border of the comment box.
        g2d.setPaint(BORDER_COLOR);
        g2d.drawRect(x, y, width, yBottom);
    }

    /**
     * Returns the insets of the border.
     *
     * @param c the component for which this border insets value applies
     */
    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(1, 1, 1 , 1);
    }

    /**
     * Returns whether or not the border is opaque.  If the border
     * is opaque, it is responsible for filling in it's own
     * background when painting.
     */
    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
