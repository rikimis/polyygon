package polyygon;

import java.io.Serial;
import java.io.Serializable;

/**
 * saved game class that gets serialized and deserialized
 */
public class SavedGame implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public int[] cells; //playing field, array
    public Integer turn; //null, 1, 2
    /*
    * Human vs. human => turn 1 or 2
    * AI vs. human => always human turn (null)
    *
    */
    public int selectedField; //which field (0, 1, 2) ...or extra 3

    /*
    mod 0 = none
    mod 1 = Explore
    mod 2 = Jump
     */
    public int mod; //game mod 0, 1, 2
}
