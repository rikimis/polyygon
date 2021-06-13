package polyygon;

import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * class Title == on the main screen, it takes
 * the heading (string) and puts it in the rectangle
 */
public class Title extends StackPane {

    public Title(String name) {
        Rectangle bg = new Rectangle(400, 60);
        bg.setStroke(Color.WHITE);
        bg.setStrokeWidth(2);
        bg.setFill(null);

        Text text = new Text(name);
        text.setFill(Color.WHITE);
        text.setFont(Font.font("Times New Roman", FontWeight.SEMI_BOLD, 50));

        setAlignment(Pos.CENTER);
        getChildren().addAll(bg,text);
    }
}
