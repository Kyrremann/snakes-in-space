package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Asteroid implements Movable {

	private final float ROTATE_SPEED;

	public int radius;
	public Vector2 position;
	public Vector2 velocity; // direction and speed
	private float rotate;

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

	public void hitDetection(int width, int height) {
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
		renderer.begin(ShapeType.Line);
		renderer.identity();
		renderer.setColor(33, 33, 33, 255);
		renderer.translate(position.x, position.y, 0);
		renderer.rotate(0, 0, 1, rotate);
		renderer.polygon(new float[] { 0, 0, 12, 1, 18, 10, 13, 16, -1, 17, -4,
				14, 1, 10, -6, 10, -5, 3 });
		renderer.end();
	}

	@Override
	public Vector2 getPosition() {
		return position;
	}

}
