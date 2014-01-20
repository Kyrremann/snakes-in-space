package no.minimon.snakeinspace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import no.minimon.snakeinspace.physics.Collideable;
import no.minimon.snakeinspace.physics.HasHitBox;
import no.minimon.snakeinspace.physics.Quadtree;
import no.minimon.snakeinspace.utils.GalaxyUtils;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Galaxy {

	private static final int ASTEROID_COUNT = 0;

	public SnakeInSpace snakeInSpace;
	public int width;
	public int height;

	private List<Snake> snakes;
	private List<Apple> apples;
	private List<Asteroid> asteroids;
	private Quadtree quad; // for collisions
	
	private Random random;
	private GalaxySounds sounds;
	HashMap<Collideable, Integer> createApplesClearOfMap;
	
	// DEBUG (double arraylist for vectors)
	public ArrayList<ArrayList <Vector2>> collision_vectors;
	public ArrayList<ArrayList <Vector2>> analog_vectors;

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
		collision_vectors = new ArrayList<ArrayList <Vector2>>();
		analog_vectors = new ArrayList<ArrayList <Vector2>>();
		analog_vectors.add(new ArrayList<Vector2>());
		analog_vectors.add(new ArrayList<Vector2>());

		createApplesClearOfMap = 
				new HashMap<Collideable, Integer>();
		createApplesClearOfMap.put(Collideable.SNAKES, 10);
		createApplesClearOfMap.put(Collideable.APPLES, 10);
		
		createPlayers(players); // create players first (they need space)
		createAsteroids(); // fill in with asteroids (with space for players)
		
		quad = new Quadtree(0, new Rectangle(0, 0, width, height));
	}

	private void createPlayers(int players) {

		// map ( thing, how_far_away_to_spawn )
		HashMap<Collideable, Integer> clearOfMap = 
				new HashMap<Collideable, Integer>();
		clearOfMap.put(Collideable.SNAKES, 100);
		clearOfMap.put(Collideable.ASTEROIDS, 1);
		
		for (int i = 0; i <= players; i++){
			Vector2 position = getRandomPosClear(clearOfMap);
			snakes.add(new Snake(i, "Player " + i, position));
		}
	}

	private void createAsteroids() {
		for (int i = 0; i < ASTEROID_COUNT; ++i) {
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
		
		// map ( thing, how_far_away_to_spawn )
		HashMap<Collideable, Integer> clearOfMap = 
				new HashMap<Collideable, Integer>();
		clearOfMap.put(Collideable.SNAKES, 200);
		clearOfMap.put(Collideable.ASTEROIDS, 1);
		
		Vector2 position = getRandomPosClear(clearOfMap);

		return new Asteroid(GalaxyUtils.getRandomInt(-2, 2), 10, 
				position, vel);
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
			apples.add(new Apple(getRandomPosClear(createApplesClearOfMap)));
		}
	}

	private Vector2 getRandomPosClear(HashMap<Collideable, 
			Integer> clearOfMap) {
		Vector2 position = NewRandomPosition();
		boolean passChecks = false;
		while (!passChecks){
			passChecks = true;
			setRandomPosition(position);
			
			if(clearOfMap.containsKey(Collideable.SNAKES)){
				if(checkClearDistance(position, 
						clearOfMap.get(Collideable.SNAKES), snakes)){
					passChecks = false;
				}
			}
			if(clearOfMap.containsKey(Collideable.ASTEROIDS)){
				if(checkClearDistance(position, 
						clearOfMap.get(Collideable.ASTEROIDS), asteroids)){
					passChecks = false;
				}
			}
			if(clearOfMap.containsKey(Collideable.APPLES)){
				if(checkClearDistance(position, 
						clearOfMap.get(Collideable.APPLES), apples)){
					passChecks = false;
				}
			}
		}
		return position;
	}

	// faster than reallocating a new random vector
	private void setRandomPosition(Vector2 position) {
		position.x = GalaxyUtils.getRandomFloat(10, width-10);
		position.y = GalaxyUtils.getRandomFloat(10, height-10);
	}

	private Vector2 NewRandomPosition() {
		return new Vector2(GalaxyUtils.getRandomFloat(10, width-10),
				GalaxyUtils.getRandomFloat(10, height-10));
	}

	public void updateAppleSnakeInteraction() {
		Apple remove = null;
		for (Apple apple : apples) {
			for (Snake snake : snakes) {
				if (snake.getPosition() != null) {
					if (GalaxyUtils.circlesIntersect(apple.getPosition(), 5,
							snake.getPosition(), 7)) {
						snake.eatApple();
						sounds.playNom(GalaxyUtils.getRandomInt(0,
								sounds.nomSoundsSize() - 1));
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
	
	private boolean checkClearDistance(Vector2 pos, int radius,
			List<? extends Movable> movables) {
		if (GalaxyUtils.isIntersectionWith(pos, radius, movables)){
			return true;
		}
		return false;
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
							sounds.playExplosion(.3f);
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
		boolean useQuadtree = true; // OPTIMIZE (true = faster)
		
		if (useQuadtree) {
			// clear entire quadtree
			quad.clear();
			// add all asteroids to quadtree
			for (Asteroid a1 : getAsteroids()) {
				quad.insert((HasHitBox) a1); // O(n)
			}
			
			// for each asteroid, retrieve a list of potential collideables
			ArrayList<HasHitBox> returnObjects = new ArrayList<HasHitBox>();
			for (Asteroid a1 : getAsteroids()) { // O(n)
				returnObjects.clear();
				quad.retrieve(returnObjects, (HasHitBox) a1);
				// run over potential list of collideables 
				for (HasHitBox object : returnObjects) { // O(subset of n)
					Asteroid a2 = (Asteroid) object;
					if (GalaxyUtils.circlesIntersect(a1.position, a1.radius,
							a2.position, a2.radius)) {
						calcAsteroidAsteroidCollision(a1, a2);
					}
				}
			}
		} else {
			for (Asteroid a : getAsteroids()) { // O(n)
				for (Asteroid a2 : getAsteroids()) { // O(n^2)
					if (GalaxyUtils.circlesIntersect(a.position, a.radius,
							a2.position, a2.radius)) {
						calcAsteroidAsteroidCollision(a, a2);
						return; // potential early finish
					}
				}
			}
		}
	}

	/** asteroid <--> asteroid collision:
	 * calculate collision (velocity)
	 * 
	 * @param a
	 * @param a2
	 */
	private void calcAsteroidAsteroidCollision(Asteroid a, Asteroid a2) {
		if (a.equals(a2))
			return; // avoid self collision

		// get vector to represent "axis of impulse" (B->A)
		Vector2 VCollideAxis= a.position.cpy();
		VCollideAxis.sub(a2.position);

		// DEBUG add the collision axis vector to table
		//					ArrayList<Vector2> temp = new ArrayList<Vector2>();
		//					temp.add(a.position.cpy()); // index 0
		//					temp.add(a2.position.cpy()); // index 1
		//					temp.add(VCollideAxis.cpy()); // index 2

		// since they have collided, they need to be moved out
		// of each others hitboxes into an un-collided state

		// middle = halfway between the 2 asteroids
		Vector2 middle = VCollideAxis.cpy();
		middle.scl(0.5f);

		// DEBUG more vectors to display
		//					temp.add(middle.cpy()); // index 3

		// radius vectors
		Vector2 a2Radius = middle.cpy();
		a2Radius.nor().scl(a2.radius);

		// amount to displace the asteroids (overlapping middle)
		Vector2 a2Displace = middle.cpy();
		a2Displace.sub(a2Radius);
		Vector2 a1Displace = a2Displace.cpy();
		a1Displace.scl(-1);

		// DEBUG vectors
		//					temp.add(a1Displace); // index 4
		//					temp.add(a2Displace); // 5

		// displace the asteroids
		a.position.add(a1Displace);
		a2.position.add(a2Displace);

		// get copies of velocity vectors
		// aligned so that VCollideAxis is the X axis (rotated)
		Vector2 a1VelocityTemp = a.velocity.cpy();
		a1VelocityTemp.rotate(- VCollideAxis.angle());
		Vector2 a2VelocityTemp = a2.velocity.cpy();
		a2VelocityTemp.rotate(- VCollideAxis.angle());

		// DEBUG vectors
		//					temp.add(a1VelocityTemp); // 6
		//					Vector2 dbg_v1 = VCollideAxis.cpy();
		//					dbg_v1.rotate(- VCollideAxis.angle());
		//					temp.add(dbg_v1); // 7
		//					// 7
		//					table.add(temp);

		// swap the x values of the two vectors, y value unchanged
		float tmp = a1VelocityTemp.x;
		a1VelocityTemp.x = a2VelocityTemp.x;
		a2VelocityTemp.x = tmp;

		// rotate the aligned vectors back to 'normal' perspective
		a1VelocityTemp.rotate(VCollideAxis.angle());
		a2VelocityTemp.rotate(VCollideAxis.angle());

		// replace actual velocities with the new post-collision
		// velocities
		a.velocity = a1VelocityTemp;
		a2.velocity = a2VelocityTemp;

		// sounds.blop.play();
		sounds.blop.s.play(.2f);
	}

	public Quadtree getQuadtree() {
		return quad;
	}
}
