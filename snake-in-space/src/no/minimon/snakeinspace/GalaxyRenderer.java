package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GalaxyRenderer {

	private ShapeRenderer renderer;
	private BitmapFont font;
	private SpriteBatch batch;

	private Galaxy<?> galaxy;

	public GalaxyRenderer(Galaxy<?> galaxy) {
		this.galaxy = galaxy;

		renderer = new ShapeRenderer();
		font = new BitmapFont();
		batch = new SpriteBatch();
	}

	public void render(float delta) {
		drawBackground();
		drawApples();
		drawAsteroids();
		drawPlayers(delta);
		drawScoreboard();
		drawQuadTree();
	}

    private void drawQuadTree() {
		galaxy.getQuadtree().draw( renderer );
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

	private void drawAsteroids() {
		for (Asteroid asteroid : galaxy.getAsteroids()) {
			asteroid.draw(renderer);
		}
	}

	private void drawBackground() {
		// TODO Auto-generated method stub
	}

	private void drawScoreboard() {
		batch.begin();
		for (Snake snake : galaxy.getSnakes()) {
			font.setColor(snake.getPlayerColor());
			font.draw(batch, String.valueOf(snake.getApplesEaten()), 100,
					(galaxy.height - 100) - (15 * snake.getPlayerId()));
		}
		batch.end();
	}

}
