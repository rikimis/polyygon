package polyygon;

import java.io.Serial;
import java.io.Serializable;

/**
 * settings class that gets serialized and deserialized
 */
public class Settings implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public int field = 0; //0, 1, 2...or extra 3
    public int ruby = 0; //0, 1
    public int pearl = 0; //0, 1
    public int mod = 0; //0 is none, 1 is explore mod, 2 is jump mode

}
