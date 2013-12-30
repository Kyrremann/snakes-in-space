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
	// private boolean goingThroughWall;
	private int applesEaten;
	private int tailsLost;

	public Snake(String name, Vector2 position) {
		this.position = position;
		this.state = State.IDLE;
		this.applesEaten = 0;
		this.tailsLost = 0;

		tails = new ArrayList<Tail>();
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

		if (!tail.goingThroughWall) {
			next.x = in.x
					- ((float) Math.cos(Math.toRadians(tail.angle)) * SIZE);
			next.y = in.y
					- ((float) Math.sin(Math.toRadians(tail.angle)) * SIZE);
		} else {
			if (movePieceToOtherSide(index, in) && index >= tails.size()) {
				tail.goingThroughWall = false;
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
		if (position.x < 0) {
			position.x = width;
		} else if (position.x > width) {
			position.x = 0;
		} else if (position.y < 0) {
			position.y = height;
		} else if (position.y > height) {
			position.y = 0;
		}
		tails.get(1).goingThroughWall = true;
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

			tail.goingThroughWall = false;
			if (index + 1 < tails.size()) {
				tails.get(index + 1).goingThroughWall = true;
			}
		} else if (next.x > width) {
			next.x = 0;
			next.y = in.y - ((float) Math.sin(Math.toRadians(angle)) * SIZE);

			tail.goingThroughWall = false;
			if (index + 1 < tails.size()) {
				tails.get(index + 1).goingThroughWall = true;
			}
		} else if (next.y < 0) {
			next.y = height;
			next.x = in.x - ((float) Math.cos(Math.toRadians(angle)) * SIZE);

			tail.goingThroughWall = false;
			if (index + 1 < tails.size()) {
				tails.get(index + 1).goingThroughWall = true;
			}
		} else if (next.y > height) {
			next.y = 0;
			next.x = in.x - ((float) Math.cos(Math.toRadians(angle)) * SIZE);

			tail.goingThroughWall = false;
			if (index + 1 < tails.size()) {
				tails.get(index + 1).goingThroughWall = true;
			}
		} else {
			if (tail.goingThroughWall) {
				Tail tail_prev = tails.get(index - 1);
				// continue moving 'forward' (using tail behind's angle)
				next.x = next.x
						+ (float) Math.cos(Math.toRadians(tail_prev.angle)) * 4;
				next.y = next.y
						+ (float) Math.sin(Math.toRadians(tail_prev.angle)) * 4;
			} else {
				next.x = in.x
						- ((float) Math.cos(Math.toRadians(tail.angle)) * SIZE);
				next.y = in.y
						- ((float) Math.sin(Math.toRadians(tail.angle)) * SIZE);
			}
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
		tailsLost++;
		tails.remove(index);
		if (index == 0) {
			position.x = getHead().position.x;
			position.y = getHead().position.y;
			getHead().angle = getHead().angle + 180;
		}
	}
	
	public boolean removeTailPiece(Tail tail) {
		tailsLost++;
		return tails.remove(tail);
	}

	public void addTail() {
		tails.add(new Tail());
	}

	public Vector2 getPosition() {
		return position;
	}

	public void eatApple() {
		applesEaten++;
		addTail();
	}

	public ArrayList<Tail> getTails() {
		return tails;
	}
}
