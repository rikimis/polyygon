package polyygon;

import java.io.*;
import java.util.ArrayList;

/**
 * Cells == playing field, array of ints:
 *
 * -1 => unplayable empty field
 *  0 => playable empty field
 *  1 => can be AI (ruby) or second player
 *  2 => is player (pearl)
 *
 *
 * each line starts at the top left:
 *
 * START   o   o   o   o   o   END
 *          \ / \ / \ / \ /
 *           o   o   o   o
 *
 * For exmaple, first line of MAP number 1:
 *
 * START   -1   -1   1   -1   -1   END
 *           \ /  \ / \ / \  /
 *           -1    0   0   -1
 */
public class Cells implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * CELL_EMPTY => is number of empty cells
     * CELL_RUB => is AI number (ruby)
     * CELL_PEARL => is player number (pearl)
     * WIDTH => width of game field
     */
    public static final int CELL_EMPTY = 0;
    public static final int CELL_RUB = 1;
    public static final int CELL_PEARL = 2;
    public static final int WIDTH = 9; //9
    public static final float MIN_RADIUS = 1.3f; //float

    private int[] cells; // board

    /**
     * @param field => different types of board according to the choosen map
     *                 by the player in the settings
     *
     */
    public Cells(int field) {

        switch (field) {
            case 0 -> { //first map
                try {
                    cells = readFile("maps/f0.txt");
                } catch (Exception e) {
                    cells = new int[]{
                            -1, -1, -1, 0, 2, 0, -1, -1, -1,
                            -1, 0, 0, 0, 0, 0, 0, 0, -1,
                            1, 0, 0, 0, 0, 0, 0, 0, 1,
                            0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0, 0,
                            2, 0, 0, 0, 0, 0, 0, 0, 2,
                            -1, -1, 0, 0, 0, 0, 0, -1, -1,
                            -1, -1, -1, -1, 1, -1, -1, -1, -1
                    };
                }
            }
            case 1 -> { //second map
                try {
                    cells = readFile("maps/f1.txt");
                } catch (Exception e) {
                    cells = new int[]{
                            -1, -1, -1, 0, 2, 0, -1, -1, -1,
                            -1, 0, 0, 0, 0, 0, 0, 0, -1,
                            1, 0, 0, 0, 0, 0, 0, 0, 1,
                            0, 0, 0, 0, -1, 0, 0, 0, 0,
                            0, 0, 0, -1, 0, -1, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0, 0,
                            2, 0, 0, 0, 0, 0, 0, 0, 2,
                            -1, -1, 0, 0, 0, 0, 0, -1, -1,
                            -1, -1, -1, -1, 1, -1, -1, -1, -1
                    };
                }
            }
            case 2 -> { //third map
                try {
                    cells = readFile("maps/f2.txt");
                } catch (Exception e) {
                    cells = new int[]{
                            -1, -1, -1, 0, 2, 0, -1, -1, -1,
                            -1, 0, 0, 0, -1, 0, 0, 0, -1,
                            1, 0, 0, 0, -1, 0, 0, 0, 1,
                            0, 0, 0, 0, -1, 0, 0, 0, 0,
                            -1, 0, -1, 0, -1, 0, -1, 0, -1,
                            0, 0, 0, 0, -1, 0, 0, 0, 0,
                            2, 0, 0, 0, -1, 0, 0, 0, 2,
                            -1, -1, 0, 0, -1, 0, 0, -1, -1,
                            -1, -1, -1, -1, 1, -1, -1, -1, -1
                    };
                }
            }
            case 3 -> { //custom map
                try {
                    cells = readFile("maps/f3.txt");
                } catch (Exception e) {
                    cells = new int[]{ //first map
                            -1, -1, -1, 0, 2, 0, -1, -1, -1,
                            -1, 0, 0, 0, 0, 0, 0, 0, -1,
                            1, 0, 0, 0, 0, 0, 0, 0, 1,
                            0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0, 0,
                            0, 0, 0, 0, 0, 0, 0, 0, 0,
                            2, 0, 0, 0, 0, 0, 0, 0, 2,
                            -1, -1, 0, 0, 0, 0, 0, -1, -1,
                            -1, -1, -1, -1, 1, -1, -1, -1, -1
                    };
                }
            }
        }

    }

    /**
     * redFile => this method reads the txt file, assumes that the data in it is error-free,
     * and generates an array from the file, the playing field.
     */
    public static int[] readFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();
        while (line != null) {
            sb.append(line);
            sb.append(System.lineSeparator());
            line = br.readLine();
        }
        String allfilestring = sb.toString();
        allfilestring = allfilestring.replace("\n", "").replace("\r", "");
        String[] array = allfilestring.split(";");

        ArrayList<Integer> num = new ArrayList<Integer>();

        for (String s : array) {
            s = s.replace(" ","");
            num.add(Integer.valueOf(s));
        }
        int[] ret = new int[num.size()];
        for (int i=0; i < ret.length; i++){ ret[i] = num.get(i);}

        return ret;
    }

    /**
     * getCells => getter of playing field (array of ints)
     */
    public int[] getCells() {
        return cells;
    }

    /**
     * setCells => setter of playing field (array of ints)
     */
    public void setCells(int[] cells) {
        this.cells = cells;
    }

    /**
     * constructor II.
     */
    public Cells(int[] cells) {
        this.cells = cells.clone();
    }


    /**
     *  getMove => get copy of the Cells object after the move from x,y to x,y
     *
     * @param xfrom coordinate x <--
     * @param yfrom coordinate y <--
     * @param xto coordinate --> x
     * @param yto coordinate --> y
     */
    public Cells getMove(int xfrom, int yfrom, int xto, int yto) {

        // if the x,y are not in the board return the same board
        if (getCell(xfrom, yfrom) <= 0) {
            System.out.println("\n\nERROR!!!\n\n");
            return this.copy();
        }
        float minRadius = MIN_RADIUS;
        Cells temp = this.copy();
        float[] from = toCoords(xfrom, yfrom);
        float[] to = toCoords(xto, yto);

        float dist = (float) Math.sqrt(Math.pow(from[0] - to[0], 2) + Math.pow(from[1] - to[1], 2));

        int pl = temp.getCell(xfrom, yfrom);
        // if the toX,toY are one cell away duplicate the cell (copy)
        if (dist <= minRadius && dist > 0) {
            temp.setCell(xto, yto, temp.getCell(xfrom, yfrom));
            // if they are two cells away move the fromX,fromY to toX,toY (jump)
        } else if (dist <= minRadius * 2) {
            temp.setCell(xto, yto, temp.getCell(xfrom, yfrom));
            temp.setCell(xfrom, yfrom, Cells.CELL_EMPTY);
        } else {
            System.out.println("\n\nERROR!!! Too long distance!\n\n");
        }
        // updating the board
        for (int i = 0; i < Cells.WIDTH * Cells.WIDTH; i++) {
            int x = i % Cells.WIDTH;
            int y = i / Cells.WIDTH;
            float[] coords = toCoords(x, y);
            dist = (float) Math.sqrt(Math.pow(to[0] - coords[0], 2) + Math.pow(to[1] - coords[1], 2));
            if (dist <= minRadius && dist > 0) {
                int c = temp.getCell(x, y);
                if (c != pl && c > 0) {
                    temp.setCell(x, y, pl);
                }
            }
        }

        return temp; // return in the cells object
    }


    /**
     * copy method
     */
    public Cells copy() {
        return new Cells(cells);
    }


    /**
     * getter
     * conversion from a two-dimensional field to a one-dimensional one
     */
    public int getCell(int x, int y) {
        return cells[Cells.WIDTH * y + x];
    }


    /**
     * setter
     * conversion from a two-dimensional field to a one-dimensional one
     */
    public void setCell(int x, int y, int val) {
        cells[Cells.WIDTH * y + x] = val;
    }


    /**
     * get number of coins the players has on the board
     * @return int[] array = {number of coins for p1, number of coins for p2}
     */
    public int[] getCoinsCount() {
        int p1 = 0; //ruby
        int p2 = 0; //pearl
        for (int cell : cells) {
            if (cell == Cells.CELL_RUB) p1++; //AI or player1
            if (cell == Cells.CELL_PEARL) p2++; //player 2
        }
        return new int[]{p1, p2};
    }


    /**
     * getWinPlayer => Get the state of the game:
     *
     * @return
     *       0 => draw
     *       1 => Rubies win
     *       2 => Pearls win
     *      -1 => the game has not ended
     */
    public int getWinPlayer() {
        int[] counts = getCoinsCount();
        if (getMovesCount(Cells.CELL_RUB) == 0 || getMovesCount(Cells.CELL_PEARL) == 0) {
            System.out.println("Nothing moves");
        }
        System.out.println("Rubies = " + counts[0] + " Pearls = " + counts[1]);

        if (((counts[1] - counts[0]) == 0 && getEmptyCount() == 0)
                || (getMovesCount(Cells.CELL_RUB) == 0 && getMovesCount(Cells.CELL_PEARL) == 0 && getEmptyCount() > 0)) {
            return 0;//draw
        } else if ((counts[1] - counts[0] < 0 && getEmptyCount() == 0) || (getMovesCount(Cells.CELL_PEARL) == 0 || counts[1] == 0)) {
            return 1;//Rubies win
        } else if ((counts[1] - counts[0] > 0 && getEmptyCount() == 0) || getMovesCount(Cells.CELL_RUB) == 0 || counts[0] == 0) {
            return 2;//Pearls win
        } else return -1;// the game has not ended

    }


    /**
     * get number of available moves the player has
     * @param pl (int) RUBY(1) or PEARLS(2)
     */
    public int getMovesCount(int pl) {
        int result = 0;
        for (int i = 0; i < Cells.WIDTH * Cells.WIDTH; i++) { //9 * 9 (in default)
            Point from = new Point(i % Cells.WIDTH, i / Cells.WIDTH); //x == i
            //for example: 0-0, 0-1 ...to 8-8
            if (getCell(from.x, from.y) == pl) { //whether the cell matches player
                for (int j = 0; j < Cells.WIDTH * Cells.WIDTH; j++) {//9 * 9 (in default)
                    Point to = new Point(j % Cells.WIDTH, j / Cells.WIDTH);
                    //for example: 0-0, 0-1 ...to 8-8
                    if (getCell(to.x, to.y) == Cells.CELL_EMPTY) { //if cell is empty (int 0)

                        float dist = from.getRealDist(to);
                        if (dist <= Cells.MIN_RADIUS * 2 && dist > 0) //2.6f default
                            result++;
                    }
                }
            }
        }
        return result;
    }


    /**
     * @return number of empty cells
     * (empty cell == number zero (default value))
     */
    public int getEmptyCount() {
        int result = 0;
        for (int cell : cells) {
            if (cell == Cells.CELL_EMPTY) result++;
        }
        return result;
    }


    /**
     * return new coordinates
     */
    public float[] toCoords(int x, int y) {
        return new float[]{(float) x, (float) y * 1 + (float) x % 2 * 0.5f};
    }
}
