package no.minimon.snakeinspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

public class Galaxy {

	private List<Snake> snakes;
	private List<Apple> apples;
	private List<Asteroid> asteroids;
	private int width;
	private int height;
	private Random random;

	public Galaxy(boolean mode) {
		random = new Random();
		snakes = new ArrayList<Snake>(2);
		apples = new ArrayList<Apple>();
		asteroids = new ArrayList<Asteroid>(4);
		if (!mode) {
			createDemoGalaxy();
		} else {
			createTwoPlayerDemo();
		}
		createAsteroids();
	}

	private void createAsteroids() {
		asteroids.add(createAsteroid());
		asteroids.add(createAsteroid());
		asteroids.add(createAsteroid());
		asteroids.add(createAsteroid());
	}
	private Asteroid createAsteroid() {
		return new Asteroid(getRandomInt(75, 175), getRandomInt(-2, 2), getRandomInt(10, 30),
				new Vector2(getRandomFloat(0, 600),	getRandomFloat(0, 800)),
				getRandomFloat(0, 360));
	}

	private void createDemoGalaxy() {
		snakes.add(new Snake("Player One", new Vector2(100f, 100f)));
	}

	private void createTwoPlayerDemo() {
		snakes.add(new Snake("Player One", new Vector2(100f, 100f)));
		snakes.add(new Snake("Player Two", new Vector2(100f, 500f)));
	}

	public Snake getSnake(int index) {
		return snakes.get(index);
	}

	public List<Snake> getSnakes() {
		return snakes;
	}

	public List<Apple> getApples() {
		return apples;
	}

	public List<Asteroid> getAsteroids() {
		return asteroids;
	}

	public void updateApple(float delta) {
		if (apples.isEmpty() || apples.size() < snakes.size()) {
			Vector2 position = getRandomPositionClearOffSnakes();
			apples.add(new Apple(position));
		}
	}

	public void updateAppleSnakeInteraction() {
		Apple remove = null;
		for (Apple apple : apples) {
			for (Snake snake : snakes) {
				if (GalaxyUtils.circlesIntersect(apple.getPosition(), 5,
						snake.getPosition(), 7)) {
					snake.addTail();
					remove = apple;
					break;
				}
			}
		}

		if (remove != null) {
			apples.remove(remove);
			updateAppleSnakeInteraction();
		}
	}

	private Vector2 getRandomPositionClearOffSnakes() {
		Vector2 position = new Vector2(getRandomX(), getRandomY());

		while (GalaxyUtils.isIntersectionWith(position, snakes)) {
			position.x = getRandomX();
			position.y = getRandomY();
		}

		return position;
	}

	private float getRandomX() {
		return Math.abs((float) ((Math.random() * (10 - width)) + 10));
	}

	private float getRandomY() {
		return Math.abs((float) ((Math.random() * (10 - height)) + 10));
	}

	private float getRandomFloat(int min, int max) {
		return Math.abs((random.nextFloat() * (min - max)) + min);
	}

	private int getRandomInt(int min, int max) {
		return random.nextInt((max - min) + 1) + min;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
