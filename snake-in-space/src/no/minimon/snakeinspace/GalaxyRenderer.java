package no.minimon.snakeinspace;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class GalaxyRenderer {

	private ShapeRenderer renderer;
	private BitmapFont font;

	private Galaxy galaxy;

	public GalaxyRenderer(Galaxy galaxy) {
		this.galaxy = galaxy;

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
		// drawTable();
	}

	// DEBUG
//	private void drawTable() {
//		//System.out.println(galaxy.table.size());
//		for (ArrayList<Vector2> vecs : galaxy.table) {
//			renderer.begin(ShapeType.Line);
//			renderer.identity();
//			renderer.setColor(255, 255, 255, 255);
//			renderer.line(vecs.get(1), vecs.get(1).cpy().add(vecs.get(2)));
//			renderer.setColor(255, 0, 0, 255);
//			renderer.line(vecs.get(1), vecs.get(1).cpy().add(vecs.get(3)));
//			renderer.setColor(0, 0, 255, 255);
//			renderer.line(vecs.get(0), vecs.get(0).cpy().add(vecs.get(4)));
//			renderer.line(vecs.get(1), vecs.get(1).cpy().add(vecs.get(5)));
//			renderer.setColor(0, 255, 0, 255);
//			renderer.line(vecs.get(0), vecs.get(0).cpy().add(vecs.get(6)));
//			renderer.setColor(0, 255, 255, 255);
//			renderer.line(vecs.get(0), vecs.get(0).cpy().add(vecs.get(7)));
//			renderer.end();
//		}
//	}

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
			font.draw(batch, String.valueOf(snake.getApplesEaten()), 100,
					(galaxy.height - 100) - (15 * snake.getPlayerId()));
		}
		batch.end();

	}

}
