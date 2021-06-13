package polyygon;

import java.io.Serial;

/**
 * create a point from the x,y coordinates of the mouse
 * in default playing field is 9 * 9 = 91 cells
 * for example: 0-0, 0-1 ...to 8-8
 */
public class Point extends java.awt.Point {

	@Serial
	private static final long serialVersionUID = 1L;


	/**
	 * Constructs and initializes a point at the specified (x,y) location in the coordinate space.
	 * @param x – the X coordinate of the newly constructed Point
	 * @param y – the Y coordinate of the newly constructed Point
	 */
	public Point(int x, int y){
		super(x, y);
	}

	/**
	 * get real distance from "from point" to "to point"
	 * default radius is set to 1.3f..so it can go double to 2.6f
	 */
	public float getRealDist(Point to){
		float[] coords1 = toCoords(this);
		float[] coords2 = toCoords(to);
		return (float)Math.sqrt(Math.pow(coords1[0]-coords2[0], 2) + Math.pow(coords1[1]-coords2[1], 2));
	}


	/**
	 * for greater accuracy, small recalculation
	 */
	public float[] toCoords(Point p){
		return new float[]{(float) p.x, (float)p.y*1 + (float)p.x%2*0.5f};
	}
}
