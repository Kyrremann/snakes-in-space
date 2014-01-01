package no.minimon.snakeinspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.Vector2;

public class Galaxy {

	public SnakeInSpace snakeInSpace;
	public int width;
	public int height;

	private List<Snake> snakes;
	private List<Apple> apples;
	private List<Asteroid> asteroids;
	private Random random;
	private GalaxySounds sounds;

	public Galaxy(SnakeInSpace snakeInSpace, GalaxySounds galaxySounds, int players, int width, int height) {
		this.snakeInSpace = snakeInSpace;
		this.sounds = galaxySounds;
		this.width = width;
		this.height = height;

		random = new Random();
		snakes = new ArrayList<Snake>(players);
		apples = new ArrayList<Apple>();
		asteroids = new ArrayList<Asteroid>(4);

		createAsteroids();
		createPlayers(players);
	}

	private void createPlayers(int players) {
		for (int i = 0; i <= players; i++)
			snakes.add(new Snake(i, "Player " + i, new Vector2(100, 100)));
	}

	private void createAsteroids() {
		for (int i=0; i< 10; ++i) {
			asteroids.add(createAsteroid());			
		}
	}

	private Asteroid createAsteroid() {
		float vel_x = (float) random.nextGaussian();
		float vel_y = (float) random.nextGaussian();
		
		Vector2 vel = new Vector2(vel_x, vel_y);
		
		// turn into unit vector
		vel.nor();
		// currently just a random unit vector (direction), adding speed
		float speed = Math.abs((float) random.nextGaussian()*80) + 20;
		// speed distribution of velocity should look something like this:
		// (0) ______ (20) ``'-,_ (100)
		vel.scl(speed);
		
		return new Asteroid(
				getRandomInt(-2, 2),
				20, 
				new Vector2(getRandomPositionClearOfEverythingElse()),
				vel );
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
		if (apples.size() < snakes.size()) {
			Vector2 position = getRandomPositionClearOffSnakes();
			apples.add(new Apple(position));
		}
	}

	public void updateAppleSnakeInteraction() {
		Apple remove = null;
		for (Apple apple : apples) {
			for (Snake snake : snakes) {
				if (snake.getPosition() != null) {
					if (GalaxyUtils.circlesIntersect(apple.getPosition(), 5,
							snake.getPosition(), 7)) {
						snake.eatApple();
						sounds.playNom(getRandomInt(0, sounds.nomSounds.size() - 1));
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
		Vector2 position = new Vector2(getRandomFloat(10, width),
				getRandomFloat(10, height));

		while (GalaxyUtils.isIntersectionWith(position, snakes)) {
			position.x = getRandomFloat(10, width);
			position.y = getRandomFloat(10, height);
		}

		return position;
	}

	private Vector2 getRandomPositionClearOfEverythingElse() {
		Vector2 position = new Vector2(getRandomFloat(10, width),
				getRandomFloat(10, height));

		while (GalaxyUtils.isIntersectionWith(position, snakes)
				|| GalaxyUtils.isIntersectionWith(position, asteroids)) {
			position.x = getRandomFloat(10, width);
			position.y = getRandomFloat(10, height);
		}

		return position;
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
					if (GalaxyUtils.circlesIntersect(tail.position, 7,
							asteroid.position, 7)) {
						remove = tail;
						sounds.explosion.play();
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
				if (asteroid.equals(asteroid2))
					continue;
				if (GalaxyUtils.circlesIntersect(asteroid.position, 10,
						asteroid2.position, 10)) {
					asteroid.velocity.x *= -1;

					sounds.blop.play();
					return;
				}
			}
		}
	}
}
