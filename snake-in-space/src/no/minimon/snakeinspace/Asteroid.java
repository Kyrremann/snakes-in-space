package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Asteroid {

	private final int SPEED;
	private final float ROTATE_SPEED;
	public final int SIZE;

	public Vector2 position;
	public float angle;
	private float rotate;

	public Asteroid(int speed, float rotateSpeed, int size, Vector2 position, float angle) {
		SPEED = speed;
		SIZE = size;
		ROTATE_SPEED = rotateSpeed;
		this.angle = angle;
		this.position = position;
		this.rotate = rotateSpeed;
	}

	public void update(float delta) {
		position.x += ((float) Math.cos(Math.toRadians(angle))) * delta * SPEED;
		position.y += ((float) Math.sin(Math.toRadians(angle))) * delta * SPEED;
		angle += .1f;
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
		} else if (position.y < 0) {
			position.y = height;
		} else if (position.y > height) {
			position.y = 0;
		}
	}

	/*
	 * With walls I mean the wall + SIZE * 3
	 */
	private boolean hasAsteroidHitWall(int width, int height) {
		return (position.x > (width + (SIZE * 3)) || position.x < (0 - (SIZE * 3)))
				|| (position.y > (height + (SIZE * 3)) || position.y < (0 - (SIZE * 3)));
	}

	public void draw(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderer.identity();
		renderer.setColor(33, 33, 33, 255);
		renderer.translate(position.x, position.y, 0);
		renderer.rotate(0, 0, 1, rotate);
		renderer.polygon(new float[] {0,0, 12,1, 18,10, 13,16, -1,17, -4,14, 1,10, -6,10, -5,3});
		renderer.end();
	}

}
