package polyygon;

import javafx.scene.image.Image;
import java.io.File;
import java.io.Serial;
import java.io.Serializable;

/**
 * class Res => all the resources(pictures) we will need
 */
public class Res implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static Image field0, field1, field2, field3, field4, ruby0, ruby1, pearl0, pearl1, hlight1, hlight2, hlight3, rules1,rules2, mod0, mod1, mod2;


    public static void init() {
        field0 = new Image(new File("images/f0.png").toURI().toString());
        field1 = new Image(new File("images/f1.png").toURI().toString());
        field2 = new Image(new File("images/f2.png").toURI().toString());
        field3 = new Image(new File("images/f3.png").toURI().toString());
        field4 = new Image(new File("images/blank.png").toURI().toString());
        ruby0 = new Image(new File("images/ruby0.png").toURI().toString());
        ruby1 = new Image(new File("images/ruby1.png").toURI().toString());
        pearl0 = new Image(new File("images/pearl0.png").toURI().toString());
        pearl1 = new Image(new File("images/pearl1.png").toURI().toString());
        hlight1 = new Image(new File("images/h1.png").toURI().toString());
        hlight2 = new Image(new File("images/h2.png").toURI().toString());
        hlight3 = new Image(new File("images/h3.png").toURI().toString());
        rules1 = new Image(new File("images/r1.png").toURI().toString());
        rules2 = new Image(new File("images/r2.png").toURI().toString());
        mod0 = new Image(new File("images/mod0.png").toURI().toString());
        mod1 = new Image(new File("images/mod1.png").toURI().toString());
        mod2 = new Image(new File("images/mod2.png").toURI().toString());
    }

    /**
     * @param f => number (int) of playing field
     * @return => Imaqe of playing field (one of three)
     */
    public static Image field(int f) {
        switch (f) {
            case 1 -> {
                return field1;
            }
            case 2 -> {
                return field2;
            }
            case 3 -> {
                return field3;
            }
            case 4 -> {
                return field4;
            }
            default -> {
                return field0;
            }
        }
    }

    /**
     * @param p => number (int) of player (pearl) figure
     * @return => Image of player (pearl) figure
     */
    public static Image pearl(int p) {
        if (p == 1) {
            return pearl1;
        } else return pearl0;
    }

    /**
     * @param r  => number (int) of AI (ruby) figure
     * @return => Image of AI (ruby) figure
     */
    public static Image ruby(int r) {
        if (r == 1) {
            return ruby1;
        } else return ruby0;
    }

    public static Image mod(int r) {
        if (r == 1) {
            return mod1;
        }
        else if (r == 2) {
                return mod2;
            }
        return mod0;
    }

}
