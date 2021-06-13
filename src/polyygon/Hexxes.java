package polyygon;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.awt.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;


/**
 * Rectangle with lot of moving Haxxes on background
 */
public class Hexxes implements Drawable, Serializable {

	@Serial
	private static final long serialVersionUID = 1L;
	private double initVx, initVy;
	private ArrayList<Hex> hexxes; //all hexxes
	private Rectangle area; //drawing area
	private Color[] colors; //random colors
	private double timer, timeToChangeAccel; //acceleration
	private double maxAccel; //Max acceleration

	/**
	 * assemble hexxes entity to move in the background
	 *
	 * @param count number of hexes
	 * @param vx x coordinate (for start)
	 * @param vy y coordinate (for start)
	 * @param w width <---->
	 * @param h height
	 */
	public Hexxes(int count, double vx, double vy, int w, int h){
		timer = 0;
		timeToChangeAccel = 1.0f;
		maxAccel = 1.0f;
		area = new Rectangle((int)-15, (int)-15, (int)(w+15*2), (int)(h+15*2));
		initVx = vx;
		initVy = vy;
		hexxes = new ArrayList<Hex>();
		colors = new Color[]{
				Color.web("#d176ef"), //PURPLE
				Color.web("#99e5ff"), //BLUE
				Color.web("#cce699"), //YELLOWGREEN
				Color.web("#d6ff97"), //GREENYELLOW
				Color.web("#ffff80"), //YELLOW
				Color.web("#ffa280"), //ORANGE
				Color.web("#ededab"), //PEA
				Color.web("#ff8080"), //RED
				Color.web("#e38ac3") //REDPURPLE
				};
		
		for(int i = 0; i < count; i++){
			int col = (int)(Math.random()*colors.length);
			hexxes.add(new Hex((double)(Math.random()*area.getMaxX()+area.getMinX()),
					(double)(Math.random()*area.getMaxY()+area.getMinY()), initVx, initVy, colors[col]));
		}
	}

	/**
	 * update the position of the moving hexxes according to time
	 * @param dt deltaTime
	 */
	@Override
	public void update(double dt){
		for(Hex c : hexxes){
			updateHexxes(c, dt);
		}
	}


	/**
	 * @param c Hex object
	 * @param dt deltatime
	 */
	private void updateHexxes(Hex c, double dt){
		if(c.getX() > area.getMaxX() || c.getX() < area.getMinX()){
			c.setX((double)area.getMinX());
			c.setY((double)(Math.random()*area.getMaxY()));
			c.setVx(20);
		}
		if(c.getY() > area.getMaxY() || c.getY() < area.getMinY()){
			c.setY((double)area.getMinY());
			c.setX((double)(Math.random()*area.getMaxY()));
			c.setVy(20);
		}
		
		if(timer > timeToChangeAccel){
			timer = 0;
			c.setAccel((double)Math.random()*maxAccel*2-maxAccel,
					(double)Math.random()*maxAccel*2-maxAccel);
		}else{
			timer += dt;
		}		
		
		c.update(dt);
	}
	
	@Override
	public void draw(GraphicsContext gc){
		for(Hex c : hexxes){
			c.draw(gc);
		}
	}
	
}
