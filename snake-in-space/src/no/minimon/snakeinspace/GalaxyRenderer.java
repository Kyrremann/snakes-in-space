package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GalaxyRenderer {

	private ShapeRenderer renderer;
	private BitmapFont font;

	private Galaxy galaxy;
	private boolean debug;

	public GalaxyRenderer(Galaxy galaxy, boolean debug) {
		this.galaxy = galaxy;
		this.debug = debug;

		renderer = new ShapeRenderer();
		font = new BitmapFont();
	}

	public void render(float delta) {
		drawBackground();
		drawAstroids();
		drawApples();
		drawPlayers(delta);
		drawScoreboard();
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
		for (Asteroid asteroid : galaxy.getAsteroids()) {
			asteroid.draw(renderer);
		}
	}

	private void drawBackground() {
		// TODO Auto-generated method stub
	}

	private void drawScoreboard() {
		SpriteBatch batch = new SpriteBatch();
		batch.begin();
		for (Snake snake : galaxy.getSnakes()) {
			font.setColor(snake.getPlayerColor());
			font.draw(batch, String.valueOf(snake.getApplesEaten()), 10,
					galaxy.height - (15 * snake.getPlayerId()));
		}
		batch.end();

	}

}
