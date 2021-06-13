package polyygon;

import javafx.scene.canvas.GraphicsContext;

public interface Drawable {

	public void draw(GraphicsContext gc);

	public void update(double dt);
	
}
