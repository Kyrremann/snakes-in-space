package no.minimon.snakeinspace;

import no.minimon.snakeinspace.controls.GalaxyController;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class GalaxyRenderer {

	private ShapeRenderer renderer;
	private BitmapFont font;
	private SpriteBatch batch;

	private Galaxy galaxy;

	public GalaxyRenderer(Galaxy galaxy) {
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
		// drawQuadTree(); // DEBUG
		drawMovementVector(); // DEBUG
	}

	private void drawMovementVector() {
		renderer.begin(ShapeType.Line);
		renderer.identity();
		Vector2 vDrawPoint = new Vector2(galaxy.width/3, galaxy.height/2);
		for (Vector2 v : galaxy.analog_vectors.get(0)){
			renderer.setColor(Color.WHITE);
			renderer.line(vDrawPoint, v.cpy().scl(100).add(vDrawPoint));
		}
		renderer.setColor(Color.RED);
		renderer.circle(vDrawPoint.x, vDrawPoint.y, 
				100*GalaxyController.ANALOG_DEAD_ZONE);
		vDrawPoint = new Vector2((float) Math.ceil(galaxy.width*(2/3.0)), 
				galaxy.height/2);
		for (Vector2 v : galaxy.analog_vectors.get(1)){
			renderer.setColor(Color.WHITE);
			renderer.line(vDrawPoint, v.cpy().scl(100).add(vDrawPoint));
		}
		renderer.setColor(Color.RED);
		renderer.circle(vDrawPoint.x, vDrawPoint.y, 
				100*GalaxyController.ANALOG_DEAD_ZONE);
		renderer.end();
	}

	private void drawQuadTree() {
		galaxy.getQuadtree().draw(renderer);
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
