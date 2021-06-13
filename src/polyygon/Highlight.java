package polyygon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.Serial;
import java.io.Serializable;

/**
 * when clicking on coins, the green and yellow areas will appear
 */
public class Highlight implements Drawable, Serializable {

    private boolean enable;
    private double scale;
    private double x, y;
    private Image image;

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * constructor
     *
     * @param x coordinate
     * @param y coordinate
     * @param scale game.Height / BOARD_HEIGHT
     * @param type of cell(hex)
     *             0 -> green (for copy)
     *             1 -> yellow (for jump)
     */
    public Highlight(double x, double y, double scale, int type) {
        this.x = x;
        this.y = y;
        enable = false;
        this.scale = scale;
        if (type == 0) {
            image = Res.hlight1; //green
        } else {
            image = Res.hlight2; //yellow
        }
    }

    /**
     * drawing method for highlight
     */
    @Override
    public void draw(GraphicsContext gc) {
        if (enable) {
            gc.drawImage(image, (int) x, (int) y, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
        }
    }

    /**
     * update method for highlight
     */
    @Override
    public void update(double dt) {

    }

    /**
     * get the position of the highlight
     */
    public double[] getCenterPosition() {
        return new double[]{image.getWidth() * scale / 2 + x, image.getHeight() * scale / 2 + y};
    }


    /**
     * enable cell(hex) on player click
     * @param type of cell(hex)
     *             0 -> green (for copy)
     *             1 -> yellow (for jump)
     */
    public void enable(int type) {
        enable = true;
        if (type == 0) { //green
            image = Res.hlight1;
        } else if (type == 1) { //yellow
            image = Res.hlight2;
        } else {
            image = Res.hlight3;
        }
    }

    /**
     * change green and yellow to gray
     */
    public void disable() {
        image = Res.hlight3;
    }


    /**
     * getter for enable
     * if cell (hex) is activated
     */
    public boolean isEnable() {
        return enable;
    }

}
