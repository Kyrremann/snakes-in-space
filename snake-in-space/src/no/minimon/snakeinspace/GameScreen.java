package no.minimon.snakeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {

	private SnakeInSpace snakeInSpace;
	private Galaxy galaxy;
	private GalaxyRenderer renderer;
	private GalaxyController controller;
	private int width, height;
	private int players;

	public GameScreen(SnakeInSpace snakeInSpace, int width, int height,
			int players) {
		this.snakeInSpace = snakeInSpace;
		this.width = width;
		this.height = height;
		this.players = players;
	}

	@Override
	public void show() {
		galaxy = new Galaxy(snakeInSpace, players, width, height);
		renderer = new GalaxyRenderer(galaxy, true);
		controller = new GalaxyController(galaxy);
		if (Ouya.runningOnOuya) {
			Controllers.addListener(controller);
		}
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		galaxy.updateApple(delta);
		for (Snake snake : galaxy.getSnakes()) {
			snake.update(delta, width, height);
			snake.hitDetection(width, height);
		}

		for (Asteroid asteroid : galaxy.getAsteroids()) {
			asteroid.update(delta);
			asteroid.hitDetection(width, height);
		}

		galaxy.updateAppleSnakeInteraction();
		galaxy.snakeAsteroidHitDetection();
		galaxy.asteroidAsteroidHitDetection();

		renderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		galaxy.setSize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		controller.keyDown(-1, keycode);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		controller.keyUp(-1, keycode);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
