package no.minimon.snakeinspace.screens;

import no.minimon.snakeinspace.SnakeInSpace;
import no.minimon.snakeinspace.controls.ScoreboardController;
import no.minimon.snakeinspace.renderer.ScoreboardRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL10;

public class ScoreboardScreen implements Screen {

	private SnakeInSpace snakeInSpace;
	private ScoreboardController controller;
	private ScoreboardRenderer renderer;

	private int height;
	private int width;
	private int players;

	public ScoreboardScreen(SnakeInSpace snakeInSpace, int height, int width,
			int players) {
		this.snakeInSpace = snakeInSpace;
		this.height = height;
		this.width = width;
		this.players = players;

		controller = new ScoreboardController(this);
		renderer = new ScoreboardRenderer();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render(delta, players);
	}

	@Override
	public void resize(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void show() {
		handleControllers(true);
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(controller);
		handleControllers(false);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		Gdx.input.setInputProcessor(null);
	}

	private void handleControllers(boolean addListener) {
		if (addListener) {
			Controllers.addListener(controller);
		} else {
			Controllers.removeListener(controller);
		}
	}

	public void handleGoBackToMenu() {
		snakeInSpace.setScreen(new MenuScreen(snakeInSpace, width, height));
	}

}
