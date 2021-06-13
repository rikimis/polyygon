package polyygon;

import java.io.*;

/**
 * utility class to save and load objects
 * to save the progress of the game
 */
public class SaveLoad {

    // serialization objects
    // to write data
    private static FileOutputStream fos;
    private static ObjectOutputStream oos;

    //to read data
    private static FileInputStream fis;
    private static ObjectInputStream ois;

    public SaveLoad() {
    }


    /**
     * serialize Settings
     * save settings
     */
    public static void serializeSettings(Settings settings) throws IOException {

        fos = new FileOutputStream("settings.txt");
        oos = new ObjectOutputStream(fos);

        oos.writeObject(settings);
        oos.close();
    }

    /**
     * deserialize Settings
     * get the saved settings
     */
    public static Settings deserializeSettings() throws ClassNotFoundException, IOException {

        fis = new FileInputStream("settings.txt");
        ois = new ObjectInputStream(fis);

        Settings settings = (Settings) ois.readObject();

        return settings;
    }

    /**
     * serialize Board
     * save game
     */
    public static void serializeBoard(SavedGame savedGame) throws IOException {

        fos = new FileOutputStream("game.txt");
        oos = new ObjectOutputStream(fos);

        oos.writeObject(savedGame);
        oos.close();
    }

    /**
     * deserialize Board
     * get the saved game
     */
    public static SavedGame deserializeBoard() throws ClassNotFoundException, IOException {

        fis = new FileInputStream("game.txt");
        ois = new ObjectInputStream(fis);

        SavedGame board = (SavedGame) ois.readObject();
        System.out.println("desirialeised turn :"+board.turn);

        return board;
    }


}
