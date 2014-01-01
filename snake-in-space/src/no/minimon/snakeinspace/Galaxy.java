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

	public Galaxy(int players) {
		random = new Random();
		snakes = new ArrayList<Snake>(2);
		apples = new ArrayList<Apple>();
		asteroids = new ArrayList<Asteroid>(4);
		createAsteroids();
		for (int i = 0; i <= players; i++)
			snakes.add(new Snake("Player " + i, new Vector2(100, 100)));
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
				if (snake.getPosition() != null){
					if (GalaxyUtils.circlesIntersect(apple.getPosition(), 5,
							snake.getPosition(), 7)) {
						snake.eatApple();
						remove = apple;
						break;

					}
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

	public void snakeAsteroidHitDetection() {
		for (Snake snake : getSnakes()) {
			for (Asteroid asteroid : getAsteroids()) {
				Tail remove = null;
				for (Tail tail : snake.getTails()) {
					if (GalaxyUtils.circlesIntersect(tail.position, 7, asteroid.position, 7)) {
						remove = tail;
						break;
					}
				}
				
				if (remove != null) {
					snake.removeTailPiece(remove);
				}
			}
		}
	}

	public void asteroidAsteroidHitDetection() {
		for (Asteroid asteroid : getAsteroids()) {
			for (Asteroid asteroid2 : getAsteroids()) {
				if (asteroid.equals(asteroid2)) continue;
				if (GalaxyUtils.circlesIntersect(asteroid.position, 10, asteroid2.position, 10)) {
					asteroid.angle += 180;
					asteroid2.angle += 180;
					return;
				}
			}
		}
	}
}
