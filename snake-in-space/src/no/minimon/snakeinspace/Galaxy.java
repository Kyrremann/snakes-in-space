package no.minimon.snakeinspace;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Galaxy {

	private List<Snake> snakes;
	private List<Apple> apples;
	private int width;
	private int height;

	public Galaxy(boolean mode) {
		snakes = new ArrayList<Snake>(2);
		apples = new ArrayList<Apple>();
		if (!mode) {
			createDemoGalaxy();
		} else {
			createTwoPlayerDemo();
		}
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

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
