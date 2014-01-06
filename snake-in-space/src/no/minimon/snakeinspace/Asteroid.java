package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Asteroid implements Movable {
	
	private final float ROTATE_SPEED;

	public int radius;
	public Vector2 position;
	public Vector2 velocity; // direction and speed
	private float rotate;

	// TEST sprite
	public static Sprite sprite;
	public static Texture texture;
	public static SpriteBatch spriteBatch;

	public Asteroid(float rotateSpeed, int radius, Vector2 position,
			Vector2 velocity) {
		this.radius = radius;
		ROTATE_SPEED = rotateSpeed;
		this.velocity = velocity;
		this.position = position;
		this.rotate = rotateSpeed;
	}

	public void update(float delta) {
		// update position
		position.add(velocity.cpy().scl(delta));

		// update rotation
		rotate += ROTATE_SPEED;
	}

	public void wallHit(int width, int height) {
		if (hasAsteroidHitWall(width, height)) {
			teleportAsteroidToOppositeSize(width, height);
		}
	}

	private void teleportAsteroidToOppositeSize(int width, int height) {
		if (position.x < 0) {
			position.x = width;
		} else if (position.x > width) {
			position.x = 0;
		}
		if (position.y < 0) {
			position.y = height;
		} else if (position.y > height) {
			position.y = 0;
		}
	}

	/*
	 * With walls I mean the wall + SIZE
	 */
	private boolean hasAsteroidHitWall(int width, int height) {
		return (position.x > (width + (radius)) || 
				position.x < (0 - (radius))) 
				|| 
				(position.y > (height + (radius)) ||
						position.y < (0 - (radius)));
	}

	public void draw(ShapeRenderer renderer) {
//		renderer.begin(ShapeType.Line);
//		renderer.identity();
//		renderer.setColor(Color.GRAY);
//		renderer.circle(position.x, position.y, radius);
//		renderer.translate(position.x, position.y, 0);
//		renderer.setColor(33, 33, 33, 255);
//		renderer.rotate(0, 0, 1, rotate);
		//renderer.polygon(new float[] { 0, 0, 12, 1, 18, 10, 13, 16, -1, 17, -4,
		//14, 1, 10, -6, 10, -5, 3 });
//		renderer.scale(1.3f, 1.3f, 1.0f);
//		renderer.polygon(new float[]{ 3, 1, 9, 1, 8, -1, 2, -8, -5, -8, -9, -2,
//				-5, 5, 1, 9, 7, 6});
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
		        rotate,
		        0,
		        0,
		        32,
		        32,
		        false,
		        false);
		spriteBatch.end();
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

	@Override
	public int getRadius() {
		return radius;
	}

}
