package no.minimon.snakeinspace;

import no.minimon.snakeinspace.controls.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen, InputProcessor {

	private final String TITLE = "Snakes in Space";

	private SnakeInSpace snakeInSpace;
	private MenuController controller;
	private BitmapFont font;
	private GalaxySounds sounds;

	private SpriteBatch batch;

	private float stringHeight;
	private float menuX;
	private float menuY;
	private int height;
	private int width;

	public int seleted;

	public MenuScreen(SnakeInSpace snakeInSpace, int width, int height) {
		this.snakeInSpace = snakeInSpace;
		this.width = width;
		this.height = height;

		stringHeight = 15;
		menuX = this.width / 2;
		menuY = this.height / 2;

		controller = new MenuController(this);
		sounds = new GalaxySounds();
		font = new BitmapFont();
		batch = new SpriteBatch();

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
		//ifOuyaAddControllerListener();
		handleControllers();
		Gdx.input.setInputProcessor(this);
		// sounds.theme.play();
	}

	private void handleControllers() {
		for (Controller controller : Controllers.getControllers()) {
			Gdx.app.log("MENUCONTROLLER", controller.getName());
		}
		System.out.println("added menu controller");
		Controllers.addListener(controller); // listen all controllers
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

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		String players1 = "1 player", players2 = "2 player", players3 = "3 player", players4 = "4 player";

		switch (seleted) {
		case 0:
			players1 = "- 1 player -";
			break;
		case 1:
			players2 = "- 2 players -";
			break;
		case 2:
			players3 = "- 3 players -";
			break;
		case 3:
			players4 = "- 4 players -";
			break;
		}
		float width = (font.getBounds(TITLE).width / 2);

		font.draw(batch, TITLE, menuX - width, menuY + stringHeight);
		font.draw(batch, players1, menuX - width, menuY - (stringHeight));
		font.draw(batch, players2, menuX - width, menuY - (stringHeight * 2));
		font.draw(batch, players3, menuX - width, menuY - (stringHeight * 3));
		font.draw(batch, players4, menuX - width, menuY - (stringHeight * 4));

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
	}

	@Override
	public void hide() {
		ifOuyaRemoveControllerListener();
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
		ifOuyaAddControllerListener();
	}

	@Override
	public void dispose() {
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

	public void changeToGameScreen() {
		//ifOuyaRemoveControllerListener();
		Controllers.removeListener(controller);
		System.out.println("removed menu controller");
		snakeInSpace.setScreen(new GameScreen(snakeInSpace, sounds, width,
				height, seleted));
	}

}
