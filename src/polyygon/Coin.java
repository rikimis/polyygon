package polyygon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import java.io.Serial;
import java.io.Serializable;


/**
 * Player's or AI's coins...Ruby or Pearl
 */
public class Coin implements Drawable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final int type;
    private final double scale;
    private static Image image1, image2;
    double x, y;


    /**
     * add new coin according to the player number
     *
     * @param type (int) Pearl (2) or Ruby (1)
     * @param scale game.Height / BOARD_HEIGHT
     * @param x coordinate (in board)...397.90625
     * @param y coordinate (in board)...196.875
     */
    public Coin(int type, double scale, double x, double y) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    /**
     * @param pearl which design of pearl, 0 or 1
     * @param ruby which design of ruby, 0 or 1
     */
    public static void setImages(int pearl, int ruby) {
        image2 = Res.pearl(pearl);
        image1 = Res.ruby(ruby);
    }

    /**
     * drawing method
     */
    @Override
    public void draw(GraphicsContext gc) {
        Image img;
        if (type == Cells.CELL_PEARL) img = image2;
        else if (type == Cells.CELL_RUB) img = image1;
        else return;
        gc.drawImage(img, (int) x, (int) y, (int) (img.getWidth() * scale), (int) (img.getHeight() * scale));
    }

    /**
     * update method
     */
    @Override
    public void update(double dt) {
    }


}
