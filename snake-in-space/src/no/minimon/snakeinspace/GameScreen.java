package no.minimon.snakeinspace;

import org.lwjgl.opencl.APPLESetMemObjectDestructor;

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
	private GalaxySounds sounds;

	public GameScreen(SnakeInSpace snakeInSpace, GalaxySounds sounds,
			int width, int height, int players) {
		this.snakeInSpace = snakeInSpace;
		this.sounds = sounds;
		this.width = width;
		this.height = height;
		this.players = players;
	}

	@Override
	public void show() {
		galaxy = new Galaxy(snakeInSpace, sounds, players, width, height);
		renderer = new GalaxyRenderer(galaxy);
		controller = new GalaxyController(galaxy);
		ifOuyaAddControllerListener();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		galaxy.updateApple(delta);
		
		for (Apple apple : galaxy.getApples()){
			apple.update(delta);
		}

		for (Snake snake : galaxy.getSnakes()) {
			if (snake.getHead() != null){
				snake.update(delta, width, height);
				snake.wallHit(width, height);
			}
		}

		for (Asteroid asteroid : galaxy.getAsteroids()) {
			asteroid.update(delta); // updates position
			asteroid.wallHit(width, height);
		}

		galaxy.updateAppleSnakeInteraction();
		galaxy.snakeAsteroidHitDetection();
		galaxy.asteroidAsteroidHitDetection(); // updates velocities

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
		ifOuyaRemoveControllerListener();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		ifOuyaAddControllerListener();
		// TODO Auto-generated method stub

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


	private void ifOuyaAddControllerListener() {
		if (Ouya.runningOnOuya) {
			Controllers.addListener(controller);
		}
	}
	
	private void ifOuyaRemoveControllerListener() {
		if (Ouya.runningOnOuya) {
			Controllers.removeListener(controller);
		}
	}

}
