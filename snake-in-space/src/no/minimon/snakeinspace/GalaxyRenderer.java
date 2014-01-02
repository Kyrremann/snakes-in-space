package no.minimon.snakeinspace;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

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
		
		// DEBUG
		drawTable();
	}

	// DEBUG
	private void drawTable() {
		System.out.println(galaxy.table.size());
		for (ArrayList<Vector2> vecs : galaxy.table) {
			renderer.begin(ShapeType.Line);
			renderer.identity();
			renderer.setColor(255, 255, 255, 255);
			renderer.line(vecs.get(0).x, vecs.get(0).y,
					vecs.get(0).x + vecs.get(1).x,
					vecs.get(0).y + vecs.get(1).y);
			renderer.end();
		}
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
