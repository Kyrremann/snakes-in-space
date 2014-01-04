package no.minimon.snakeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen, InputProcessor {

	private final String TITLE = "Snakes in Space";

	private SnakeInSpace snakeInSpace;
	private MenuController controller;
	private BitmapFont font;
	private GalaxySounds sounds;

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

		sounds = new GalaxySounds();
		font = new BitmapFont();
		stringHeight = 15;
		menuX = this.width / 2;
		menuY = this.height / 2;
	}

	@Override
	public void show() {
		controller = new MenuController(this);
		if (Ouya.runningOnOuya) {
			Controllers.addListener(controller);
		}
		Gdx.input.setInputProcessor(this);
		// sounds.theme.play();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(.1f, .1f, .1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		SpriteBatch batch = new SpriteBatch();
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
		System.out.println("HIDE");
		Controllers.removeListener(controller);
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		System.out.println("RESUME");
		Controllers.addListener(controller);
		// TODO Auto-generated method stub

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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

	public void changeToGameScreen() {
		Controllers.removeListener(controller);
		snakeInSpace.setScreen(new GameScreen(snakeInSpace, sounds, width,
				height, seleted));
	}

}
