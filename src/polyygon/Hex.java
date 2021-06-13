package polyygon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.io.Serial;
import java.io.Serializable;


/**
 * Polygon (hex) entity for moving background
 */
public class Hex implements Drawable, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private double x, y, vx, vy, ax, ay;
	private Color color;


	/**
	 * Constructor
	 * it will move not only to the right and left but also up and down
	 */
	public Hex(double x, double y, double vx, double vy, Color col){
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		color = col;
		ax = ay = 0;
	}

	/**
	 * drawing method for hex (polynom) entity for background
	 */
	@Override
	public void draw(GraphicsContext gc){
		gc.setFill(color);
		double size = 12,v = Math.sqrt(3)/2.0;
		gc.fillPolygon(
			new double[] { x, x + size, x+size*(3.0/2.0), x+size, x, x-(size/2.0) },
			new double[] { y, y, y+size*v, y+size*Math.sqrt(3), y+size*Math.sqrt(3), y+size*v }, 6);
		gc.setFill(Color.BLACK);
	}

	/**
	 * upadate coordinates
	 */
	@Override
	public void update(double dt){
		vx += ax * dt;
		vy += ay * dt;
		x += vx * dt;
		y += vy * dt;
	}


	/**
	 * getter
	 */
	public double getX() {
		return x;
	}


	/**
	 * setter
	 */
	public void setX(double x) {
		this.x = x;
	}


	/**
	 * getter
	 */
	public double getY() {
		return y;
	}


	/**
	 * setter
	 */
	public void setY(double y) {
		this.y = y;
	}


	/**
	 * setter
	 */
	public void setVx(double vx) {
		this.vx = vx;
	}


	/**
	 * setter
	 */
	public void setVy(double vy) {
		this.vy = vy;
	}


	/**
	 * setter
	 */
	public void setAccel(double x, double y){
		ax = x;
		ay = y;
	}


}
