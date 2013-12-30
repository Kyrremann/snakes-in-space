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

	private MenuController controller;
	private int height;
	private int width;
	private BitmapFont font;
	private float stringHeight;
	private float menuX;
	private float menuY;
	private boolean seleted;
	private String string;
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

		string = "Snakes in Space";
		font.draw(batch, string, menuX - (font.getBounds(string).width / 2),
				menuY + stringHeight);

		if (seleted) {
			string = "1 player";
			font.draw(batch, string,
					menuX - (font.getBounds(string).width / 2), menuY);
			string = "- 2 player -";
			font.draw(batch, string,
					menuX - (font.getBounds(string).width / 2), menuY
							- (stringHeight));
		} else {
			string = "- 1 player -";
			font.draw(batch, string,
					menuX - (font.getBounds(string).width / 2), menuY);
			string = "2 player";
			font.draw(batch, string,
					menuX - (font.getBounds(string).width / 2), menuY
							- (stringHeight));
		}

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
		if (keycode == Keys.UP || keycode == Keys.DOWN) {
			seleted = !seleted;
			return true;
		} else if (keycode == Keys.ENTER) {
			snakeInSpace.setScreen(new GameScreen(width, height, seleted));
			return true;
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
