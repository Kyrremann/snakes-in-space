package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Apple implements Movable {

	private Vector2 position;
	private int radius;

	public Apple(Vector2 position) {
		this.position = position;
		this.radius = 10;
	}

	public void draw(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderer.identity();
		renderer.setColor(0, 255, 0, 255);
		renderer.translate(position.x, position.y, 0);
		renderer.circle(0, 0, radius);
		renderer.end();
	}

	public Vector2 getPosition() {
		return position;
	}

	@Override
	public int getRadius() {
		return this.radius;
	}

}
