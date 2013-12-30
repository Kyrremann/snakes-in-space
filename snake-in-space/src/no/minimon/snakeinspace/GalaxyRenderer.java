package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GalaxyRenderer {

	private Galaxy galaxy;
	private ShapeRenderer renderer;
	private boolean debug;

	public GalaxyRenderer(Galaxy galaxy, boolean debug) {
		this.galaxy = galaxy;
		this.debug = debug;
		renderer = new ShapeRenderer();
	}

	public void render(float delta) {
		drawBackground();
		drawAstroids();
		drawApples();
		drawPlayers(delta);
	}

	private void drawApples() {
		for (Apple apple : galaxy.getApples()) {
			apple.draw(renderer);
		}
	}

	private void drawPlayers(float delta) {
		for (Snake snake : galaxy.getSnakes()) {
			snake.draw(renderer);
		}

	}

	private void drawAstroids() {
		// TODO Auto-generated method stub
	}

	private void drawBackground() {
		// TODO Auto-generated method stub
	}

}
