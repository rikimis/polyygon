package polyygon;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.WindowEvent;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.Optional;


/**
 * game object
 */
public class Game extends Canvas implements Runnable, Serializable {


    @Serial
    private static final long serialVersionUID = 1L;
    private int fps;
    private float deltaTime;
    private long lastTime;
    private Thread thread;
    private int player;
    private Hexxes bgCircles;
    private Board board;
    private AnimationTimer gameLoop;


    /**
     * game object constructor
     * @param width px <--->
     * @param height px
     * @param p (int) p = 0 -> ai vs human / p = 1 -> human vs human
     */
    public Game(Double width, Double height, int p) { // game canvas
        super(width, height);
        fps = 60;
        player = p;
        lastTime = 0;
        thread = new Thread(this);
    }

    /**
     * close event when the game has not ended prompt the
     * user to save the game
     */
    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request ...");

        // if the dataset has changed, alert the user with a popup
        if (board.getCells().getWinPlayer() == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.NO);
            alert.getButtonTypes().add(ButtonType.YES);

            alert.setContentText(String.format("The game has not ended\ndo you want to save ?"));
            Optional<ButtonType> res = alert.showAndWait();

            if (res.isPresent()) {
                if (res.get().equals(ButtonType.YES)) {
                    try {
                        SavedGame savedGame = new SavedGame();
                        savedGame.cells = board.getCells().getCells();
                        savedGame.turn = board.getTurn();
                        savedGame.mod = board.getMod();
                        savedGame.selectedField = board.getField();
                        SaveLoad.serializeBoard(savedGame);
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        }

    }


    /**
     * Threat start
     */
    public void start() { // start the game thread
        thread.start();
    }


    /**
     * initialize the game canvas
     *
     * @param field which field (int) 0, 1, 2, 3
     * @param pearl which pearl (int) 0, 1
     * @param ruby which ruby (int) 0, 1
     * @param mod which mod (int) 0, 1, 2
     */
    public void init(int field, int pearl, int ruby, int mod) {
        bgCircles = new Hexxes(100, 30, 0, (int) getWidth(), (int) getHeight());
        board = new Board(this, player, field, pearl, ruby, mod);
    }

    /**
     * load the game canvas from saved game
     * @param savedGame saved game class
     * @param field which field (int) 0, 1, 2, 3
     * @param pearl which pearl (int) 0, 1
     * @param ruby which ruby (int) 0, 1
     * @param mod which mod (int) 0, 1, 2
     */
    public void loadGame(SavedGame savedGame, int field, int pearl, int ruby, int mod) {
        bgCircles = new Hexxes(100, 30, 0, (int) getWidth(), (int) getHeight());
        board = new Board(this, field, pearl, ruby, savedGame.cells, savedGame.turn, mod);
    }


    /**
     * render method to call in the animation timer
     */
    public void render() {
        updateDt();
        update();
        int w = (int) getWidth();
        int h = (int) getHeight();

        GraphicsContext gc = getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, w, h);//fill the whole screen white to redraw the canvas
        gc.setFill(Color.BLACK);
        int[] score = board.getCells().getCoinsCount();
        gc.strokeText("PEARL    " + score[1] + "   -   " + score[0] + "   RUBY", 10, 20); // draw the score message in black
        String turnText = "";
        if (board.getTurn() == null) { //AI will make fast move, so it it always player's turn
            turnText = "";
        }
        else if (board.getTurn() == 1) {
            turnText = "Pearl's turn";
        } else if (board.getTurn() == 2) {
            turnText = "Ruby's turn";
        }

        gc.strokeText(turnText + "",640,20);

        bgCircles.draw(gc); // draw the moving hexxes
        board.draw(gc);// draw the board
    }


    /**
     * update the position of the moving hexxes according to time
     */
    public void update() {
        bgCircles.update(deltaTime);
    }

    /**
     * getter
     * (board)
     */
    public Board getBoard() {
        return board;
    }

    /**
     * getter
     * (gameLoop)
     */
    public AnimationTimer getGameLoop() {
        return gameLoop;
    }

    /**
     * update deltaTime
     */
    private void updateDt() {
        if (lastTime == 0) {
            lastTime = System.nanoTime();
            deltaTime = 1.0f;
        } else {
            deltaTime = (float) (System.nanoTime() - lastTime) / 1000000000;
        }

        lastTime = System.nanoTime();
    }

    /**
     * the method that the thread starts
     */
    @Override
    public void run() {
        this.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        gameLoop = new AnimationTimer() {// animation timer that runs indefinetly
            public void handle(long currentNanoTime) {
                render();
            }
        };
        gameLoop.start();
    }

}
