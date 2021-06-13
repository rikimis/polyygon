package polyygon;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * set buttons the user can choose what mode he wants to play
 * versus human
 * versus AI
 * return
 */
public class MenuBox extends VBox {
    public MenuBox(MenuItem... items) {
        getChildren().add(createSeperator());

        for (MenuItem item : items) {
            getChildren().addAll(item, createSeperator());
        }
    }

    public void addIndex(MenuItem item, int i) {
        getChildren().add(i, createSeperator());
        getChildren().add(i + 1, item);
    }

    private Line createSeperator() {
        Line sep = new Line();
        sep.setEndX(210);
        sep.setStroke(Color.DARKGREY);
        return sep;
    }

}
