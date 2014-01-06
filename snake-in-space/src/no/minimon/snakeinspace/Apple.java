package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Apple implements Movable {

	private Vector2 position;
	private int radius;
	private float rotation;
	
	// TEST sprite
	public static Sprite sprite;
	public static Texture texture;
	public static SpriteBatch spriteBatch;

	public Apple(Vector2 position) {
		this.position = position;
		this.radius = 10;
		this.rotation = GalaxyUtils.getRandomFloat(0, 360);
	}

	public void draw(ShapeRenderer renderer) {
//		renderer.begin(ShapeType.Line);
//		renderer.identity();
//		renderer.setColor(0, 255, 0, 255);
//		renderer.translate(position.x, position.y, 0);
//		renderer.circle(0, 0, radius);
//		renderer.end();
		
		// TEST sprite
		spriteBatch.begin();
		// this function has 16 arguments! :D
		spriteBatch.draw(texture,
		        position.x-(radius+5),
		        position.y-(radius+5),
		        radius+5,
		        radius+5,
		        (float) (radius*2)+10,
		        (float) (radius*2)+10,
		        1f,
		        1f,
		        rotation,
		        0,
		        0,
		        32,
		        32,
		        false,
		        false);
		spriteBatch.end();
	}

	public Vector2 getPosition() {
		return position;
	}

	@Override
	public int getRadius() {
		return this.radius;
	}

	public void update(float delta) {
		rotation += 15*delta;
	}

}
