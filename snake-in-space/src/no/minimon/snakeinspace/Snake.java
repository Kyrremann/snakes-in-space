package no.minimon.snakeinspace;

import static no.minimon.snakeinspace.Snake.State.BOOST;
import static no.minimon.snakeinspace.Snake.State.FROM_BOOST;
import static no.minimon.snakeinspace.Snake.State.FROM_SLOW;
import static no.minimon.snakeinspace.Snake.State.LEFT;
import static no.minimon.snakeinspace.Snake.State.RIGHT;
import static no.minimon.snakeinspace.Snake.State.SLOW;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Snake implements Movable {

	public enum State {
		LEFT, RIGHT, BOOST, SLOW, FROM_BOOST, FROM_SLOW;
	}

	private float TURN_SPEED = 360; // degrees per second

	private float BASE_SPEED = 200;
	private float MAX_SPEED = 400;
	private float MIN_SPEED = 100;
	private float SPEED = BASE_SPEED; // pixels per second
	private float ACCELERATION = 1000; // pixels per second per second
	private int ACCELERATION_DIR = 0; // 1 = accel, -1 decel, 0 = no force.

	private ArrayList<Tail> tails;
	private ArrayList<State> states;
	private int applesEaten;
	private int tailsLost;
	private int player;
	private String name;
	private boolean isDead;

	// legg til isAlive sjekk i getSnakes

	public Snake(int player, String name, Vector2 position) {
		this.name = name;
		this.states = new ArrayList<Snake.State>();
		this.applesEaten = 0;
		this.tailsLost = 0;
		this.player = player;

		tails = new ArrayList<Tail>();
		for (int i = 0; i < 10; i++) {
			addTail();
		}

		getHead().direction = new Vector2(3, 4).nor();
		getHead().position = position;
		getHead().isHead = true;
	}

	public void addState(State state) {
		states.add(state);
	}

	public void removeState(State state) {
		states.remove(state);
	}

	public int getApplesEaten() {
		return applesEaten;
	}

	public int getTailsLost() {
		return tailsLost;
	}

	/*
	 * turn left at TURN_SPEED degrees per second
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
		if (isDead)
			return;
		if (tails.isEmpty()) {
			isDead = true;
			return;
		}

		// update direction (based on destination direction)
		update_dir(delta);
		
		// turn head piece of snake
		turnSnake(delta);

		// update orientation and position of all pieces (head ignores rotation)
		for (Tail t : tails){
			t.calculatePieceLocation(delta, width, height);
		}

		// update acceleration
		updateAcceleration(delta);
	}

	/**
	 * if dest dir = null, do nothing,
	 * otherwise update direction
	 */
	private void update_dir( float delta ) {
		Tail t = getHead();
		if (t.destdir == null) {
			return;
		}
		//t.direction = t.direction.cpy().add(t.destdir).nor();
		
		float angle_to_turn = t.direction.angle() - t.destdir.angle();
		if (angle_to_turn >= -360 && angle_to_turn < -180) {
			turnRight(delta);
		} else if (angle_to_turn >= -180 && angle_to_turn < 0) {
			turnLeft(delta);
		} else if (angle_to_turn >= 0 && angle_to_turn < 180) {
			turnRight(delta);
		} else if (angle_to_turn >= 180 && angle_to_turn < 360) {
			turnLeft(delta);
		}
	}

	/**
	 * manipulates SPEED and ACCELERATION_DIR values
	 * 
	 * @param delta
	 */
	private void updateAcceleration(float delta) {
		// update speed
		setSpeed(getSpeed() + ((ACCELERATION * ACCELERATION_DIR) * delta));

		// if max speed, stop accelerating.
		if (states.contains(BOOST) && getSpeed() > MAX_SPEED) {
			setSpeed(MAX_SPEED);
			ACCELERATION_DIR = 0;
		}
		// if min speed, stop decelerating
		if (states.contains(SLOW) && getSpeed() < MIN_SPEED) {
			setSpeed(MIN_SPEED);
			ACCELERATION_DIR = 0;
		}

		// if from max/min speed, stop accelerating/decelerating
		if (states.contains(FROM_BOOST) && getSpeed() < BASE_SPEED) {
			states.remove(FROM_BOOST);
			setSpeed(BASE_SPEED);
			ACCELERATION_DIR = 0;
		} else if (states.contains(FROM_SLOW) && getSpeed() > BASE_SPEED) {
			states.remove(FROM_SLOW);
			setSpeed(BASE_SPEED);
			ACCELERATION_DIR = 0;
		}
	}

	private void turnSnake(float delta) {
		if (getHead() != null) {
			State state = getLastLeftOrRight();
			if (state == null)
				return;

			if (state == LEFT) {
				turnLeft(delta);
			} else if (state == RIGHT) {
				turnRight(delta);
			}
		}
	}

	private State getLastLeftOrRight() {
		if (states.lastIndexOf(LEFT) > states.lastIndexOf(RIGHT)) {
			return LEFT;
		} else if (states.lastIndexOf(LEFT) < states.lastIndexOf(RIGHT)) {
			return RIGHT;
		}

		return null;
	}

	public void draw(ShapeRenderer renderer) {
		for (Tail t : tails) {
			t.draw(renderer);
		}
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

	public Tail getHead() {
		if (tails.isEmpty()) {
			return null;
		}
		return tails.get(0);
	}

	public boolean removeTailPiece(Tail tail) {
		tailsLost++;
		if (getHead() != null && tail.equals(getHead()) && tails.size() != 1)
			tails.get(1).isHead = true;

		return tails.remove(tail);
	}

	/**
	 * adds segments, updating prev pointers in tail segment
	 */
	public void addTail() {
		Tail t = new Tail(this);
		if(tails.isEmpty()) {
			t.prev = null;
		}
		else {
			t.prev = tails.get(tails.size()-1);
		}
		tails.add(t);
}

	/**
	 * 
	 * @return position of first segment, null if snake is 'dead' / empty
	 */
	public Vector2 getPosition() {
		if (tails.isEmpty()) {
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

	/**
	 * starts acceleration (true) or deceleration (false) of snake. this
	 * continues until either MAX_SPEED or BASE_SPEED is hit.
	 * 
	 * @param b
	 */
	public void accelerate(boolean b) {
		if (b) {
			states.add(BOOST);
			states.remove(FROM_SLOW);
			ACCELERATION_DIR = 1;
		} else {
			ACCELERATION_DIR = -1;
			states.remove(BOOST);
			states.add(FROM_BOOST);
		}
	}

	public void deceleration(boolean b) {
		if (b) {
			states.add(SLOW);
			states.remove(FROM_BOOST);
			ACCELERATION_DIR = -1;
		} else {
			ACCELERATION_DIR = 1;
			states.remove(SLOW);
			states.add(FROM_SLOW);
		}
	}

	public void setDestDir(Vector2 destdir) {
		getHead().destdir = destdir;
	}

	public float getSpeed() {
		return SPEED;
	}

	public void setSpeed(float speed) {
		SPEED = speed;
	}

	@Override
	public int getRadius() {
		return getHead().radius;
	}

	public boolean isDead() {
		return tails.isEmpty();
	}
}
