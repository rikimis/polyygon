package polyygon;

import java.io.Serial;
import java.io.Serializable;

/**
 *  Move object joins to different Points
 */
public class Move implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	private Point from;
	private Point to;


	/**
	 * construstor
	 * @param xfrom coordinate
	 * @param yfrom coordinate
	 * @param xto coordinate
	 * @param yto coordinate
	 */
	public Move(int xfrom, int yfrom, int xto, int yto){
		from = new Point(xfrom, yfrom);
		to = new Point(xto, yto);
	}

	/**
	 * getter
	 * @return from (Point)
	 */
	public Point getFrom() {
		return from;
	}

	/**
	 * getter
	 * @return to (Point)
	 */
	public Point getTo() {
		return to;
	}
	
	
}
