package no.minimon.snakeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MenuScreen implements Screen, InputProcessor {

	private final String TITLE = "Snakes in Space";

	private MenuController controller;
	private int height;
	private int width;
	private BitmapFont font;
	private float stringHeight;
	private float menuX;
	private float menuY;
	private int seleted;
	private SnakeInSpace snakeInSpace;

	public MenuScreen(SnakeInSpace snakeInSpace, int width, int height) {
		this.width = width;
		this.height = height;
		this.font = new BitmapFont();
		this.stringHeight = 15;
		this.menuX = this.width / 2;
		this.menuY = this.height / 2;
		this.snakeInSpace = snakeInSpace;
	}

	@Override
	public void show() {
		controller = new MenuController();
		if (Ouya.runningOnOuya) {
			Controllers.addListener(controller);
		}
		Gdx.input.setInputProcessor(this);
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
		if (keycode == Keys.UP) {
			seleted--;
			if (seleted < 0) seleted = 3;
			return true;
		} else if (keycode == Keys.DOWN) {
			seleted++;
			if (seleted > 3) seleted = 0;
			return true;
		} else if (keycode == Keys.ENTER) {
			snakeInSpace.setScreen(new GameScreen(snakeInSpace, width, height, seleted));
			return true;
		} else if (keycode == Keys.ESCAPE) {
			Gdx.app.exit();
		}

		return false;
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

}
