package no.minimon.snakeinspace;

import no.minimon.snakeinspace.controls.GalaxyController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen implements Screen, InputProcessor {

	private SnakeInSpace snakeInSpace;
	private Galaxy galaxy;
	private GalaxyRenderer renderer;
	private GalaxyController controller;
	private GalaxySounds sounds;

	private FPSLogger logger;

	private int width, height;
	private int players;
	//private PrintWriter writer; // DEBUG

	public GameScreen(SnakeInSpace snakeInSpace, GalaxySounds sounds,
			int width, int height, int players) {
		this.snakeInSpace = snakeInSpace;
		this.sounds = sounds;
		this.width = width;
		this.height = height;
		this.players = players;
		
		// DEBUG
//		try {
//			writer = new PrintWriter("data.txt", "utf-8");
//		} catch (FileNotFoundException | UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		galaxy = new Galaxy(snakeInSpace, sounds, players, width, height);
		renderer = new GalaxyRenderer(galaxy);
		controller = new GalaxyController(galaxy);
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
		logger = new FPSLogger();
		
		handleControllers(true);
	}

	/**
	 * adds a controller listener if there is none
	 */
	private void handleControllers(boolean addListener) {
		if (addListener){
			Controllers.addListener(controller);
		} else {
			Controllers.removeListener(controller);
		}
	}

	@Override
	public void render(float delta) {
//		DEBUG
		//		int total_energy = 0;
//		for (Asteroid a : galaxy.getAsteroids()){
//			total_energy += a.velocity.len();
//		}
//		
//		writer.println(total_energy);
//		writer.flush();
		
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
