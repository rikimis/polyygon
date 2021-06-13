package polyygon;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * for AI, I decided to use the Minimax algorithm
 *
 * The algorithm is the basis of most computer programs for
 * playing games such as Tic Tac Toe, checkers or chess.
 *
 * The algorithm goes through all possible moves, scores the created positions and
 * selects the move that brings the most advantageous position
 *
 * ...I only beat AI once :-)
 */
public class Minimax implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final int BIGVALUE = 100000;
    private final int MAX_DEPTH = 2; // how deep the ai sees into the future
    private int player; //player number == 1 is default value
    private int startPlayer;//player number == 1 is default value
    private Cells bestCells;


    /**
     * constructor
     * @param coin (player number) == 1 (1 is default value)
     */
    public Minimax(int coin) {
        player = coin;
        startPlayer = player;
        bestCells = null;
    }

    /**
     * get the ai move
     * @param cells playing field
     */
    public Cells move(Cells cells) {
        bestCells = null;
        getBestMove(startPlayer, 0, cells);
        return bestCells;
    }

    /**
     * getAllMoves => get all available moves for the player pl
     *
     * @param cls playing field
     * @param pl (int) RUBY(1) or PEARLS(2)
     * @return get all available moves for the player pl
     */
    private ArrayList<Move> getAllMoves(Cells cls, int pl) {
        ArrayList<Move> moves = new ArrayList<Move>();

        for (int i = 0; i < Cells.WIDTH * Cells.WIDTH; i++) { //iterate through the board (9*9)
            Point from = new Point(i % Cells.WIDTH, i / Cells.WIDTH); //x == i
            //for example: 0-0, 0-1 ...to 8-8
            if (cls.getCell(from.x, from.y) == pl) { //whether the cell matches player
                for (int j = 0; j < Cells.WIDTH * Cells.WIDTH; j++) { //(9*9)
                    Point to = new Point(j % Cells.WIDTH, j / Cells.WIDTH);//y == i
                    //for example: 0-0, 0-1 ...to 8-8
                    if (cls.getCell(to.x, to.y) == Cells.CELL_EMPTY) { //if cell is empty (int 0)
                        float dist = from.getRealDist(to);
                        if (dist <= Cells.MIN_RADIUS * 2 && dist > 0) //2.6f default
                            moves.add(new Move(from.x, from.y, to.x, to.y));
                    }
                }
            }
        }
        return moves;
    }


    /**
     * ai method to get the best move according to eval method
     *
     * @param pl 1 is default value
     * @param depth starts with 0
     * @param cls
     * @return
     */
    private int getBestMove(int pl, int depth, Cells cls) {
        if (depth >= MAX_DEPTH - 1) return evaluate(pl, cls);// if depth is 1 or more
        ArrayList<Move> bestMoves = new ArrayList<Move>();
        int max = -BIGVALUE;
        ArrayList<Move> moves = getAllMoves(cls, pl);
        int nextpl = (pl == 1) ? 2 : 1; // true result : false result
        //nextpl = 2
        int val = 0;

        for (Move m : moves) { //for move in allMoves
            val = -getBestMove(nextpl, depth + 1, cls.copy().getMove(m.getFrom().x, m.getFrom().y, m.getTo().x, m.getTo().y));

            if (val > max) {
                max = val;
                bestMoves = new ArrayList<Move>();
                bestMoves.add(new Move(m.getFrom().x, m.getFrom().y, m.getTo().x, m.getTo().y));
            } else if (val == max) {
                bestMoves.add(new Move(m.getFrom().x, m.getFrom().y, m.getTo().x, m.getTo().y));
            }

        }

        if (depth == 0) {
            Move best = bestMoves.get((int) (Math.random() * bestMoves.size())); //choose one
            bestCells = cls.getMove(best.getFrom().x, best.getFrom().y, best.getTo().x, best.getTo().y);
        }
        return val;
    }


    /**
     * how much coins AI have minus player's coins
     * @param pl player number == 1 is default value
     * @param cls playing field, cls.cells = array of ints
     */
    private int evaluate(int pl, Cells cls) { //evaluation (rating) method
        int value;
        int result = (pl == 1) ? 2 : 1; // true result : false result
        int[] count = cls.getCoinsCount(); //get number of coins the players has on the board
        //count = [ruby, pearl]..ruby is AI
        value = count[pl - 1] - count[result - 1];
        // count[0] - count[1]
        return value;
    }
}
