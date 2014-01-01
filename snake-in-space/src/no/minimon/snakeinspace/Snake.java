package no.minimon.snakeinspace;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Snake implements Movable {

	public enum State {
		LEFT, RIGHT, IDLE;
	}

	public static final float SPEED = 200; // pixels per second
	public static final int SIZE = 10;
	private static final float TURN_SPEED = 360; // degrees per second

	private ArrayList<Tail> tails;
	private State state;
	private int applesEaten;
	private int tailsLost;
	private int player;
	private String name;

	public Snake(int player, String name, Vector2 position) {
		this.name = name;
		this.state = State.IDLE;
		this.applesEaten = 0;
		this.tailsLost = 0;
		this.player = player;

		tails = new ArrayList<Tail>();
		for (int i = 0; i < 10; i++) {
			tails.add(new Tail());
		}

		getHead().direction = new Vector2(3, 4).nor();
		getHead().position = position;
	}

	public void setState(State state) {
		this.state = state;
	}

	public int getApplesEaten() {
		return applesEaten;
	}

	public int getTailsLost() {
		return tailsLost;
	}

	/*
	 *  turn left at TURN_SPEED degrees per second
	 */
	public void turnLeft(float delta) {
		getHead().direction.rotate(TURN_SPEED * delta);
	}

	/*
	 * turn right at TURNSPEED degrees per second
	 */
	public void turnRight(float delta) {
		getHead().direction.rotate(TURN_SPEED * delta * -1);
	}

	public void update(float delta, int width, int height) {
		// turn head piece of snake
		turnSnake(delta);

		// update rotation and position of all pieces (head ignores rotation!)
		for (int i = 0; i < tails.size(); i++) {
			calculatePieceLocation(delta, i, width, height);
		}
	}

	private void turnSnake(float delta) {
		if (getHead() != null){
			switch (state) {
			case LEFT:
				turnLeft(delta);
				break;
			case RIGHT:
				turnRight(delta);
			default:
				break;
			}
		}
	}

	/**
	 * update location of piece at index
	 * 
	 * @param index
	 *            - the piece to update
	 * @param delta
	 *            - the amount to update (1 = per second)
	 * @param height
	 *            height of screen
	 * @param width
	 *            width of screen
	 */
	public void calculatePieceLocation(float delta, int index, int width,
			int height) {
		Tail piece = tails.get(index);

		// if head piece, move straight forward (angle predetermined)
		if (index == 0) {
			piece.position.add(piece.direction.cpy().scl(SPEED * delta));
		}
		// if other piece, calculate new angle, then move straight forward
		else {
			
			Tail prev = tails.get(index - 1);

			// ASSUMPTION: when segments separated by more than SPEED -> wrap

			// OPTIMIZE: shrink repeated code here!!!
			// if dist to prev is greater than SPEED dictates = it has wrapped
			if (piece.position.cpy().sub(prev.position).x > SPEED) {
				// prev has wrapped towards west

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.x += width; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				piece.direction = dest.cpy().sub(piece.position);
				piece.direction.nor(); // normalize direction

				piece.position = dest.cpy()
						.sub(piece.direction.cpy().scl(SIZE));
			} else if (piece.position.cpy().sub(prev.position).x < -SPEED) {
				// prev has wrapped towards east

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.x -= width; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				piece.direction = dest.cpy().sub(piece.position);
				piece.direction.nor(); // normalize direction

				piece.position = dest.cpy()
						.sub(piece.direction.cpy().scl(SIZE));
			} else if (piece.position.cpy().sub(prev.position).y > SPEED) {
				// prev has wrapped towards south

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.y += height; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				piece.direction = dest.cpy().sub(piece.position);
				piece.direction.nor(); // normalize direction

				piece.position = dest.cpy()
						.sub(piece.direction.cpy().scl(SIZE));
			} else if (piece.position.cpy().sub(prev.position).y < -SPEED) {
				// prev has wrapped towards north

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.y -= height; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				piece.direction = dest.cpy().sub(piece.position);
				piece.direction.nor(); // normalize direction

				piece.position = dest.cpy()
						.sub(piece.direction.cpy().scl(SIZE));
			} else {
				// continue 'normally'

				// point piece directly at prev piece
				piece.direction = prev.position.cpy().sub(piece.position);
				piece.direction.nor(); // normalize direction

				// set location to SIZE distance from preceding piece
				piece.position = prev.position.cpy().sub(
						piece.direction.cpy().scl(SIZE));
			}
		}
		// if outside boundaries, wrap to other side
		if (piece.position.x < 0) {
			piece.position.x += width; // wrap to east
		} else if (piece.position.x > width) {
			piece.position.x -= width; // wrap to west
		}
		if (piece.position.y < 0) {
			piece.position.y += height; // wrap to north
		} else if (piece.position.y > height) {
			piece.position.y -= height; // wrap to south
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
		// renderer.setColor(tail.position.x / 265, tail.position.y / 256,
		// tail.direction.x / 256, 255);
		renderer.setColor(getPlayerColor());
		renderer.translate(tail.position.x, tail.position.y, 0);
		renderer.rotate(0, 0, 1, tail.direction.angle());
		renderer.triangle(0, -5, 0, 5, 10, 0);
		renderer.end();
	}

	public Color getPlayerColor() {
		switch (player) {
		case 0:
			return Color.ORANGE;
		case 1:
			return Color.BLUE;
		case 2:
			return Color.RED;
		case 3:
			return Color.GREEN;
		default:
			return Color.WHITE;
		}
	}

	public void hitDetection(int width, int height) {
		if (hasSnakeHitWall(width, height)) {
			// swapHeadToOtherSide(width, height);
		}
	}

	private boolean hasSnakeHitWall(int width, int height) {
		Tail tail = getHead();
		return (tail.position.x > width || tail.position.x < 0)
				|| (tail.position.y > height || tail.position.y < 0);
	}

	private Tail getHead() {
		if (tails.isEmpty()){
			return null;
		}
		return tails.get(0);
	}

	public boolean removeTailPiece(Tail tail) {
		tailsLost++;
		return tails.remove(tail);
	}

	public void addTail() {
		tails.add(new Tail());
	}

	/**
	 * 
	 * @return position of first segment, null if snake is 'dead' / empty
	 */
	public Vector2 getPosition() {
		if (tails.isEmpty()){
			return null;
		}
		return getHead().position;
	}

	public void eatApple() {
		applesEaten++;
		addTail();
	}

	public ArrayList<Tail> getTails() {
		return tails;
	}

	public String getName() {
		return name;
	}
	
	public int getPlayerId() {
		return player;
	}
}
