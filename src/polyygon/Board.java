package polyygon;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * playing field object
 */
public class Board implements Drawable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    // values to perfectly place the images
    private final int BOARD_HEIGHT = 1024; //px
    private final double BOARD_OFFSET_X = 162;
    private final double BOARD_OFFSET_Y = 80;
    private final double BOARD_GRID_X = 80;
    private final double BOARD_GRID_Y = 100;

    private int field;

    private int state;
    private double scale;
    private Image image;
    private Game game;
    private Cells cells;
    private ArrayList<Coin> coins;
    private ArrayList<Highlight> hlights;
    private double boardX;

    private boolean isSelected;
    private int[] selectedCell;
    private Highlight selectedHlight;
    private ArrayList<Highlight> moveCells;
    private int[] nextCell;
    private int playerCoin;
    private Integer turn;

    private Minimax ai;

    private int whichMode;


    /**
     * constructor for fresh new game (no loading from save)
     *
     * @param game is game (canvas)
     * @param player = 0 -> ai / player = 1 -> human vs human
     * @param field which playing field, 1, 2, 3, or extra 4
     * @param pearl which design of pearl, 0 or 1
     * @param ruby which design of ruby, 0 or 1
     * @param mod mods of game 0 (none), 1 (explore), 2 (jumping)
     */
    public Board(Game game, int player, int field, int pearl, int ruby, int mod) {
        this.game = game;
        scale = (double) game.getHeight() / BOARD_HEIGHT;
        image = Res.field(4);
        //image = Res.field(field);
        cells = new Cells(field);
        Coin.setImages(pearl, ruby);
        coins = new ArrayList<Coin>();
        hlights = new ArrayList<Highlight>();
        isSelected = false;
        selectedCell = new int[2];
        nextCell = new int[2];
        playerCoin = Cells.CELL_PEARL;
        if (player == 1) { //1... human vs. human
            turn = 1;
        } else ai = new Minimax(Cells.CELL_RUB); //0... AI vs. human
        state = -1;

        boardX = game.getWidth() / 2 - (int) (image.getWidth() / 2 * scale);
        updateCells();
        updateHlights();

        whichMode = mod;

        if (mod != 1) { //mod 1 == explore
            highlightOnStart();
        }
    }


    /**
     * constructor for new game from save
     *
     * @param game is game (canvas)
     * @param field which playing field, 1, 2, 3 or extra 4
     * @param pearl which design of pearl, 0 or 1
     * @param ruby which design of ruby, 0 or 1
     * @param cells playing field, array... but edited
     * @param turn :
     *          Human vs. human => turn 1 or 2
     *          AI vs. human => always human turn (null)
     */
    public Board(Game game, int field, int pearl, int ruby, int[] cells, Integer turn, int mod) {

        whichMode = mod;

        this.game = game;
        scale = (double) game.getHeight() / BOARD_HEIGHT;
        image = Res.field(4);
        //image = Res.field(field);
        this.field = field;
        this.cells = new Cells(field);
        this.cells.setCells(cells);
        Coin.setImages(pearl, ruby);
        coins = new ArrayList<Coin>();
        hlights = new ArrayList<Highlight>();
        isSelected = false;
        selectedCell = new int[2];
        nextCell = new int[2];
        playerCoin = Cells.CELL_PEARL;
        if (turn != null) { // if in player vs player mode
            this.turn = turn; // if turn == 1 (Pearl turn)
            if (turn == 2) { // if turn == 2 (Ruby turn)
                playerCoin = Cells.CELL_RUB;
            }
        } else ai = new Minimax(Cells.CELL_RUB); // if in ai play mode
        state = -1;
        boardX = game.getWidth() / 2 - (int) (image.getWidth() / 2 * scale);
        updateCells();
        updateHlights();

        System.out.println(mod);

        if (mod != 1) { //mod 1 == explore
            highlightOnStart();
        }
    }

    /**
     * getter
     * @return field
     */
    public int getField() {
        return field;
    }

    /**
     * getter
     * @return turn
     * this is for saving the game
     */
    public Integer getTurn() {
        return turn;
    }

    /**
     * getter
     * @return cells
     * this is for saving the game
     */
    public Cells getCells() {
        return cells;
    }

    /**
     * getter
     * @return game mod (integer)
     * this is for saving the game
     */
    public Integer getMod() {
        return whichMode;
    }

    /**
     * drawing method to draw the board and the cells and winning message
     */
    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, (int) boardX, 0, (int) (image.getWidth() * scale), (int) (image.getHeight() * scale));
        drawCells(gc);

        if (state == 0) {
            drawMessage("DRAW", gc,game);
        } else if (state == 1) {
            drawMessage("RUBIES WIN!", gc,game);
        } else if (state == 2) {
            drawMessage("PEARLS WIN!", gc,game);
        }
    }

    @Override
    public void update(double dt) {

    }


    /**
     * method to update the cells assets according to cells values
     */
    public void updateCells() {
        coins = new ArrayList<Coin>();
        for (int i = 0; i < Cells.WIDTH; i++) {      //iterate through the board
            for (int j = 0; j < Cells.WIDTH; j++) {
                int cell = cells.getCell(i, j);
                if (cell > 0) {
                    coins.add(new Coin(cell, scale,
                            boardX + (BOARD_OFFSET_X + BOARD_GRID_X * i) * scale, (BOARD_OFFSET_Y + BOARD_GRID_Y * j + i % 2 * BOARD_GRID_Y / 2) * scale));
                    // add new coin according to the player number
                }
            }
        }
    }


    /**
     * method to update highlight assets according
     */
    public void updateHlights() {
        hlights = new ArrayList<Highlight>();
        for (int i = 0; i < Cells.WIDTH; i++) {
            for (int j = 0; j < Cells.WIDTH; j++) {

                double HLIGHT_OFFSET_X = -33;
                double HLIGHT_OFFSET_Y = -31;
                hlights.add(new Highlight(boardX + (BOARD_OFFSET_X + BOARD_GRID_X * i + HLIGHT_OFFSET_X) * scale,
                        (BOARD_OFFSET_Y + BOARD_GRID_Y * j + i % 2 * BOARD_GRID_Y / 2 + HLIGHT_OFFSET_Y) * scale, scale, 1));

            }
        }
    }

    /**
     * highlightOnStart => thanks to this method, the whole playing field will be shown
     * each hex will show up (gray color)
     */
    public void highlightOnStart() {
            for (int i = 0; i < hlights.size(); i++) {
                    if (cells.getCell(i / Cells.WIDTH, i % Cells.WIDTH) != -1) { //-1 means unplayable hex (cell)
                        hlights.get(i).enable(2);
                    }
                }
        }


    /**
     * highlight around the selected cell (hex)
     */
    public void highlightCells() {
        if (isSelected) {
            moveCells = new ArrayList<Highlight>();
            for (int i = 0; i < hlights.size(); i++) {
                double[] cp = hlights.get(i).getCenterPosition();
                double[] cp2 = selectedHlight.getCenterPosition();
                double dist = Math.sqrt(Math.pow(cp2[0] - cp[0], 2) + Math.pow(cp2[1] - cp[1], 2));
                if (dist < BOARD_GRID_X * 2 && dist != 0 && cells.getCell(i / Cells.WIDTH, i % Cells.WIDTH) == Cells.CELL_EMPTY) {

                    if (whichMode == 2) { //Jump mode
                        if ((int) (dist / BOARD_GRID_X) == 1) { //0 (copy) is disabled, just jump
                            hlights.get(i).enable((int) (dist / BOARD_GRID_X));
                            moveCells.add(hlights.get(i));
                        }
                    } else {
                        hlights.get(i).enable((int) (dist / BOARD_GRID_X));
                        moveCells.add(hlights.get(i));
                    }

                }
            }
        } else {//remove (disable) the highlight if not
            for (Highlight hlight : hlights) {
                hlight.disable();
                //change green and yellow to gray
            }
        }
    }


    /**
     * select cell method called when clicking on the game window
     * passing the x,y coordinates of the mouse
     * @param mx x coordinates of the mouse
     * @param my y coordinates of the mouse
     */
    public void selectCell(double mx, double my) {

        Point mp = new Point((int) mx, (int) my); // create a point from the coordinates
        double SELECTION_RADIUS = 30;
        for (int i = 0; i < hlights.size(); i++) { // iterate through all the highlights
            Highlight h = hlights.get(i);
            double[] cp = h.getCenterPosition(); // get the position of the highlight
            double dist = (double) Math.sqrt((mp.getX() - cp[0]) * (mp.getX() - cp[0]) + (mp.getY() - cp[1]) * (mp.getY() - cp[1]));//check the distance of the selected point and the highlight
            if (dist <= SELECTION_RADIUS) { // check if clicked point is within the distance
                int x = i / Cells.WIDTH;
                int y = i % Cells.WIDTH;
                if (h.isEnable() && h == selectedHlight) { //if player reslected the same cell it deselect
                    h.disable();
                    selectedHlight = null;
                    isSelected = false;
                } else if (cells.getCell(x, y) == playerCoin && !isSelected) {// if he selects a player cell highlights the selected cell
                    h.enable(1);
                    selectedCell[1] = y; //y
                    selectedCell[0] = x; //x
                    selectedHlight = h;
                    isSelected = true;
                    System.out.println("selected: " + selectedCell[0] + "; " + selectedCell[1]);
                }
                highlightCells();
            }
        }

        if (isSelected) {
            for (Highlight h : moveCells) { // iterate through the available cells to move to
                int j = hlights.indexOf(h);
                double[] cp = h.getCenterPosition();
                double dist = Math.sqrt((mp.getX() - cp[0]) * (mp.getX() - cp[0]) + (mp.getY() - cp[1]) * (mp.getY() - cp[1]));
                if (dist <= SELECTION_RADIUS) {// if the selected position is within the distance
                    int x = j / Cells.WIDTH;
                    int y = j % Cells.WIDTH;
                    nextCell[0] = x; // get the next move
                    nextCell[1] = y;
                    if (state < 0) {
                        cells = cells.getMove(selectedCell[0], selectedCell[1], x, y);// apply the move
                    }
                    state = cells.getWinPlayer(); // get the state of the game
                    isSelected = false; //unselect the selected cell
                    highlightCells(); //remove the highlight
                    updateCells(); // update the assets of the board

                    if (turn != null) { // if in player vs player mode switch the turn
                        if (turn == 1) { //pearl
                            turn = 2;
                            playerCoin = Cells.CELL_RUB;
                        } else if (turn == 2) { //ruby
                            turn = 1;
                            playerCoin = Cells.CELL_PEARL;
                        }
                    } else { // if in ai play mode

                        /*
                        * STATE:
                        * 0 => draw
                        * 1 => Rubies win
                        * 2 => Pearls win
                        * -1 => the game has not ended
                        */

                        if (state < 0) {
                            cells = ai.move(cells.copy()); // get the ai move
                        }
                        state = cells.getWinPlayer();


                        Timeline beat = new Timeline(
                                new KeyFrame(Duration.seconds(0.5), event -> updateCells())
                                // update the assets after 0.5 after the player moves
                                // AI slow down little bit
                        );
                        beat.play();

                    }
                }
            }
        }


    }


    /**
     * draw method of message (draw or who won)
     *
     * @param text of message
     */
    private void drawMessage(String text, GraphicsContext gc, Game game) {
        int width = 600;
        int height = 100;
        gc.setFill(Color.rgb(13, 14, 12, 0.9f));
        gc.fillRect(game.getWidth() / 2 - width / 2, game.getHeight() / 2 - height / 2, width, height);
        gc.setFill(Color.WHITE);
        gc.fillText(text, game.getWidth() / 2 - 50, game.getHeight() / 2 - 5);
        gc.setFill(Color.WHITE);
        Pane root = (Pane) gc.getCanvas().getParent();
        MenuItem reutrn = new MenuItem("Return", mouseEvent -> {
            game.getGameLoop().stop();
            root.getChildren().clear();
            root.getChildren().add(MainMenu.createContent());
        });
        reutrn.setTranslateX(game.getWidth() / 2 - 110);
        reutrn.setTranslateY(game.getHeight() / 2 + 5);
        root.getChildren().addAll(reutrn);
    }


    /**
     * drawing the cells and highlight into the canvas
     */
    private void drawCells(GraphicsContext gc) {
        for (Highlight h : hlights) {
            h.draw(gc);
        }
        for (Coin c : coins) {
            c.draw(gc);
        }
    }

}
