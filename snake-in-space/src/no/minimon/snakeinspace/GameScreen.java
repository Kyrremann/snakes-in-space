package no.minimon.snakeinspace;

import java.util.ArrayList;

import no.minimon.snakeinspace.controls.GalaxyController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {

	private Galaxy galaxy;
	private GalaxyRenderer renderer;
	private GalaxySounds sounds;

	private FPSLogger logger;

	private int width, height;
	
	private int player_count;
	private ArrayList<Player> players;

	public GameScreen(SnakeInSpace snakeInSpace, GalaxySounds sounds,
			int width, int height, int players) {
		this.sounds = sounds;
		this.width = width;
		this.height = height;
		this.player_count = players;
		
		galaxy = new Galaxy(snakeInSpace, sounds, players, width, height);
		renderer = new GalaxyRenderer(galaxy);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		logger = new FPSLogger();
		
		handleControllers(true);
	}

	/**
	 * adds all players controller listeners if true, else removes them
	 */
	private void handleControllers(boolean addListener) {
		if (addListener){
			for (Player p : players){
				Controllers.addListener(p.controller);
			}
		} else {
			Controllers.removeListener(controller);
		}
	}

	@Override
	public void render(float delta) {
		// logger.log();
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		galaxy.updateApple(delta);

		for (Apple apple : galaxy.getApples()) {
			apple.update(delta);
		}

		for (Snake snake : galaxy.getSnakes()) {
			if (snake.getHead() != null) {
				snake.update(delta, width, height);
			}
		}

		for (Asteroid asteroid : galaxy.getAsteroids()) {
			asteroid.update(delta); // updates position
			asteroid.wallHit(width, height);
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
		handleControllers(false);
	}

	@Override
	public void pause() {
		handleControllers(false);
	}

	@Override
	public void resume() {
		handleControllers(true);
	}

	@Override
	public void dispose() {
		sounds.disposeAll();
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
