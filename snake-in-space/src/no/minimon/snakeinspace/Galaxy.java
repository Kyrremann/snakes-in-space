package no.minimon.snakeinspace;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
	
	// DEBUG (double arraylist for vectors)
	public ArrayList<ArrayList <Vector2>> table;

	public Galaxy(SnakeInSpace snakeInSpace, GalaxySounds galaxySounds,
			int players, int width, int height) {
		this.snakeInSpace = snakeInSpace;
		this.sounds = galaxySounds;
		this.width = width;
		this.height = height;

		random = new Random();
		snakes = new ArrayList<Snake>(players);
		apples = new ArrayList<Apple>();
		asteroids = new ArrayList<Asteroid>(4);
		
		// DEBUG
		table = new ArrayList<ArrayList <Vector2>>();

		createAsteroids();
		createPlayers(players);
	}

	private void createPlayers(int players) {
		for (int i = 0; i <= players; i++)
			snakes.add(new Snake(i, "Player " + i, getRandomPositionClearOfEverythingElse(50)));
	}

	private void createAsteroids() {
		for (int i = 0; i < 50; ++i) {
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
		float speed = Math.abs((float) random.nextGaussian() * 80) + 20;
		// speed distribution of velocity should look something like this:
		// (0) ______ (20) ``'-,_ (100)
		vel.scl(speed);

		return new Asteroid(getRandomInt(-2, 2), 10, new Vector2(
				getRandomPositionClearOfEverythingElse(50)), vel);
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
		if (apples.size() < snakes.size() * 2) {
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
						sounds.playNom(getRandomInt(0,
								sounds.nomSounds.size() - 1));
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

	private Vector2 getRandomPositionClearOfEverythingElse(int clearRadius) {
		Vector2 position = new Vector2(getRandomFloat(100, width - 100),
				getRandomFloat(100, height - 100));

		while (GalaxyUtils.isIntersectionWith(position, clearRadius, snakes, clearRadius)
				|| GalaxyUtils.isIntersectionWith(position, clearRadius, asteroids, clearRadius)) {
			position.x = getRandomFloat(100, width - 100);
			position.y = getRandomFloat(100, height - 100);
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
				if (snake.getHead() != null) {
					Tail remove = null;
					for (Tail tail : snake.getTails()) {
						if (GalaxyUtils.circlesIntersect(tail.position,
								snake.collisionSize, asteroid.position,
								asteroid.radius)) {
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
	}

	public void asteroidAsteroidHitDetection() {
		for (Asteroid a : getAsteroids()) {
			for (Asteroid a2 : getAsteroids()) {
				if (a.equals(a2))
					continue; // avoid self collision
				if (GalaxyUtils.circlesIntersect(a.position, a.radius,
						a2.position, a.radius)) {
					// asteroid <--> asteroid collision!
					
					// get vector to represent "axis of impulse" (B->A)
					Vector2 VCollideAxis= a.position.cpy().sub(a2.position);
					
					// DEBUG add the collision axis vector to table
					ArrayList<Vector2> temp = new ArrayList<Vector2>();
					temp.add(a2.position.cpy()); // index 0
					temp.add(VCollideAxis.cpy()); // index 1
					table.add(temp);
					
					// since they have collided, they need to be moved out
					// of each others hitboxes into an un-collided state
					
					// middle = halfway between the 2 asteroids
					Vector2 middle = VCollideAxis.cpy().scl(0.5f);
					
					// radius vectors
					Vector2 a2Radius = middle.cpy().nor().scl(a2.radius);
					
					// amount to displace the asteroids (overlapping middle)
					Vector2 a2Displace = middle.cpy().sub(a2Radius);
					Vector2 a1Displace = a2Displace.cpy().scl(-1);
					
					// displace the asteroids
					a.position.add(a1Displace);
					a2.position.add(a2Displace);
					
					// get copies of velocity vectors
					// aligned so that VCollideAxis is the X axis (rotated)
					Vector2 a1VelocityTemp = a.velocity.cpy().rotate(
							VCollideAxis.angle());
					Vector2 a2VelocityTemp = a2.velocity.cpy().rotate(
							VCollideAxis.angle());

					// swap the x values of the two vectors, y value unchanged
					float tmp = a1VelocityTemp.x;
					a1VelocityTemp.x = a2VelocityTemp.x;
					a2VelocityTemp.x = tmp;

					// rotate the aligned vectors back to 'normal' perspective
					a1VelocityTemp.rotate(-VCollideAxis.angle());
					a2VelocityTemp.rotate(-VCollideAxis.angle());

					// replace actual velocities with the new post-collision
					// velocities
					a.velocity = a1VelocityTemp;
					a2.velocity = a2VelocityTemp;

					// sounds.blop.play();
					sounds.blop.s.play(.1f);
					return;
				}
			}
		}
	}
}
