package no.minimon.snakeinspace.screens;

import no.minimon.snakeinspace.Apple;
import no.minimon.snakeinspace.Asteroid;
import no.minimon.snakeinspace.GalaxySounds;
import no.minimon.snakeinspace.SnakeInSpace;
import no.minimon.snakeinspace.controls.MenuController;
import no.minimon.snakeinspace.renderer.MenuRenderer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen, InputProcessor {

	private SnakeInSpace snakeInSpace;
	private MenuController controller;
	private GalaxySounds sounds;

	private int height;
	private int width;

	public int seleted, multiplayer = 2;

	private MenuRenderer menuRenderer;

	public MenuScreen(SnakeInSpace snakeInSpace, int width, int height) {
		Gdx.app.log("LC", "CONSTRUCTOR");
		this.snakeInSpace = snakeInSpace;
		this.width = width;
		this.height = height;

		controller = new MenuController(this);
		sounds = new GalaxySounds();
		menuRenderer = new MenuRenderer(this);

		// TEST sprite
		Asteroid.texture = new Texture(Gdx.files.internal("data/asteroid.png"));
		Asteroid.sprite = new Sprite(Asteroid.texture);
		Asteroid.sprite.setSize(32, 32);
		Asteroid.spriteBatch = new SpriteBatch();

		Apple.texture = new Texture(Gdx.files.internal("data/apple.png"));
		Apple.sprite = new Sprite(Apple.texture);
		Apple.sprite.setSize(32, 32);
		Apple.spriteBatch = new SpriteBatch();
	}

	@Override
	public void show() {
		Gdx.app.log("LC", "SHOW");
		handleControllers(true);
		Gdx.input.setInputProcessor(this);
		// sounds.theme.play();
	}

	private void handleControllers(boolean addListener) {
		if (addListener) {
			for (Controller controller : Controllers.getControllers()) {
				Gdx.app.log("MENUCONTROLLER", controller.getName());
			}
			Controllers.addListener(controller);
		} else {
			Controllers.removeListener(controller);
		}
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		menuRenderer.render(delta);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void hide() {
		Gdx.app.log("LC", "HIDE");
		Gdx.input.setInputProcessor(this);
		handleControllers(false);
	}

	@Override
	public void pause() {
		Gdx.app.log("LC", "PAUSE");
	}

	@Override
	public void resume() {
		Gdx.app.log("LC", "RESUME");
	}

	@Override
	public void dispose() {
		Gdx.app.log("LC", "DISPOSE");
		sounds.disposeAll();
		Gdx.input.setInputProcessor(null);

	}

	@Override
	public boolean keyDown(int keycode) {
		return controller.keyDown(keycode);
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
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

	public void changeToGameScreen(int players) {
		handleControllers(false);
		snakeInSpace.setScreen(new GameScreen(snakeInSpace, sounds, width,
				height, --players));
	}

	public void handleConfirmButton() {
		switch (seleted) {
		case 0:
			changeToGameScreen(1);
			break;
		case 1:
			changeToGameScreen(multiplayer);
			break;
		case 2:
			snakeInSpace.setScreen(new ScoreboardScreen(snakeInSpace, height, width, 0));
			break;
		case 3:
			// options
			break;
		case 4:
			Gdx.app.exit();
			break;
		}

	}

}
