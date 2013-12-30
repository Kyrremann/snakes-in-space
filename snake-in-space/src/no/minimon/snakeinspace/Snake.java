package no.minimon.snakeinspace;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Snake {

	public enum State {
		LEFT, RIGHT, IDLE;
	}

	// Kanskje jeg skal bruke en Node klasse for å ta vare på hale-deler som man
	// mister eller legger fra seg

	public static final float SPEED = 200;
	public static final int SIZE = 10;

	private Vector2 position;
	private ArrayList<Tail> tails;
	private State state;
	private String name;
	private boolean goingThroughWall;

	public Snake(String name, Vector2 position) {
		this.position = position;
		this.state = State.IDLE;
		this.name = name;

		tails = new ArrayList<Snake.Tail>();
		for (int i = 0; i < 10; i++) {
			tails.add(new Tail(new Vector2(0, 0), 0));
		}

		tails.get(0).angle = (float) Math.toDegrees(Math.atan2(300, 400));
		calculateHeadPiece(1);
	}

	public void setState(State state) {
		this.state = state;
	}

	public void turnLeft() {
		tails.get(0).angle += 7f;
	}

	public void turnRight() {
		tails.get(0).angle -= 7f;
	}

	public void update(float delta) {
		moveSnake();
		calculateHeadPiece(delta);

		calculateTailPiece(0, position);
		for (int i = 0; i < tails.size() - 1; i++) {
			calculateTailPiece(i + 1, tails.get(i).position);
		}
	}

	private void moveSnake() {
		switch (state) {
		case LEFT:
			turnLeft();
			break;
		case RIGHT:
			turnRight();
		default:
			break;
		}
	}

	public void calculateHeadPiece(float delta) {
		position.x += ((float) Math.cos(Math.toRadians(tails.get(0).angle)))
				* delta * SPEED;
		position.y += ((float) Math.sin(Math.toRadians(tails.get(0).angle)))
				* delta * SPEED;
	}

	public void calculateTailPiece(int index, Vector2 in) {
		Tail tail = tails.get(index);
		Vector2 next = tail.position;

		float dx = in.x - next.x;
		float dy = in.y - next.y;

		if (index != 0) {
			tail.angle = (float) Math.toDegrees(Math.atan2(dy, dx));
		}

		if (!goingThroughWall) {
			next.x = in.x
					- ((float) Math.cos(Math.toRadians(tail.angle)) * SIZE);
			next.y = in.y
					- ((float) Math.sin(Math.toRadians(tail.angle)) * SIZE);
		} else {
			if (movePieceToOtherSide(index, in) && index >= tails.size()) {
				goingThroughWall = false;
			}
		}
	}

	public void draw(ShapeRenderer renderer) {
		for (Tail t : tails) {
			drawSnake(t, renderer);
		}
	}

	private void drawSnake(Tail tail, ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderer.identity();
		renderer.setColor(tail.position.x / 265, tail.position.y / 256,
				tail.angle / 256, 255);
		renderer.translate(tail.position.x, tail.position.y, 0);
		renderer.rotate(0, 0, 1, tail.angle);
		renderer.triangle(0, -5, 0, 5, 10, 0);
		renderer.end();
	}

	public void hitDetection(int width, int height) {
		if (hasSnakeHitWall(width, height)) {
			// removeTailPiece(0);
			swapHeadToOtherSide(width, height);
		}
	}

	private void swapHeadToOtherSide(int width, int height) {
		System.out.println("going through");
		if (position.x < 0) {
			position.x = width;
		} else if (position.x > width) {
			position.x = 0;
		} else if (position.y < 0) {
			position.y = height;
		} else if (position.y > height) {
			position.y = 0;
		}
		goingThroughWall = true;
	}

	private boolean movePieceToOtherSide(int index, Vector2 in) {
		int width = 800;
		int height = 600;
		Tail tail = tails.get(index);
		Vector2 next = tail.position;
		float angle = tail.angle;
		if (next.x < 0) {
			next.x = width;
			next.y = in.y - ((float) Math.sin(Math.toRadians(angle)) * SIZE);
		} else if (next.x > width) {
			next.x = 0;
			next.y = in.y - ((float) Math.sin(Math.toRadians(angle)) * SIZE);
		} else if (next.y < 0) {
			next.y = height;
			next.x = in.x - ((float) Math.cos(Math.toRadians(angle)) * SIZE);
		} else if (next.y > height) {
			next.y = 0;
			next.x = in.x - ((float) Math.cos(Math.toRadians(angle)) * SIZE);
		} else {
			next.x = in.x
					- ((float) Math.cos(Math.toRadians(tail.angle)) * SIZE);
			next.y = in.y
					- ((float) Math.sin(Math.toRadians(tail.angle)) * SIZE);
			return false;
		}

		return true;
	}

	private boolean hasSnakeHitWall(int width, int height) {
		Tail tail = getHead();
		return (tail.getX() > width || tail.getX() < 0)
				|| (tail.getY() > height || tail.getY() < 0);
	}

	private Tail getHead() {
		return tails.get(0);
	}

	private void removeTailPiece(int index) {
		tails.remove(index);
		if (index == 0) {
			position.x = getHead().position.x;
			position.y = getHead().position.y;
			getHead().angle = getHead().angle + 180;
		}
	}

	public void addTail() {
		tails.add(new Tail());
	}

	private class Tail {

		public float angle;
		public Vector2 position;

		public Tail() {
			this.position = new Vector2(0f, 0f);
			this.angle = 0;
		}

		public Tail(Vector2 position, float angle) {
			this.position = position;
			this.angle = angle;
		}

		public float getX() {
			return position.x;
		}

		public float getY() {
			return position.y;
		}

		@Override
		public String toString() {
			return String.format("%s: (%f,%f) %f", name, position.x,
					position.y, angle);
		}
	}

	public Vector2 getPosition() {
		return position;
	}
}
