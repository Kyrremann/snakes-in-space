package no.minimon.snakeinspace;

import no.minimon.snakeinspace.controls.MenuController;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MenuScreen implements Screen, InputProcessor {

	private final String TITLE = "SNAKES\nIN\nSPACE!";

	private SnakeInSpace snakeInSpace;
	private MenuController controller;
	private BitmapFont font, font_big;
	private GalaxySounds sounds;

	private SpriteBatch batch;

	private int height;
	private int width;

	public int seleted, multiplayer = 2;

	private ShapeRenderer renderer;

	float menu_box_start;
	float half_height;
	float font_height;

	int small_font_size;
	int box_height;
	int box_width;

	public MenuScreen(SnakeInSpace snakeInSpace, int width, int height) {
		Gdx.app.log("LC", "CONSTRUCTOR");
		this.snakeInSpace = snakeInSpace;
		this.width = width;
		this.height = height;

		controller = new MenuController(this);
		sounds = new GalaxySounds();
		font = new BitmapFont();
		font.setScale(1.5f);
		font_big = new BitmapFont();
		font_big.setScale(5);
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();

		menu_box_start = Gdx.graphics.getWidth() - 200;
		half_height = Gdx.graphics.getHeight() / 2;
		font_height = font_big.getBounds(TITLE).height * 3;

		small_font_size = (int) font.getBounds("TEST").height;
		box_height = (int) (small_font_size * 1.2);
		box_width = 210;

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
		batch.begin();

		font_big.drawMultiLine(batch, TITLE, Gdx.graphics.getWidth() / 2,
				Gdx.graphics.getHeight() - font_height, 0, HAlignment.RIGHT);

		renderer.begin(ShapeType.Filled);
		renderer.identity();
		renderer.setColor(Color.DARK_GRAY);
		switch (seleted) {
		case 0:
			renderer.rect(menu_box_start - 10, half_height
					+ (int) (small_font_size * 1.5), box_width, box_height);
			break;
		case 1:
			renderer.rect(menu_box_start - 10, half_height, box_width,
					box_height);
			break;
		case 2:
			renderer.rect(menu_box_start - 10, half_height
					- (int) (small_font_size * 1.7), box_width, box_height);
			break;
		case 3:
			renderer.rect(menu_box_start - 10, half_height
					- (int) (small_font_size * 3.2), box_width, box_height);
			break;
		}
		renderer.end();

		font.draw(batch, "Single player", menu_box_start,
				(float) (half_height + (small_font_size * 2.5)));
		font.draw(batch, "Multiplayer < " + multiplayer + " >", menu_box_start,
				half_height + (small_font_size * 1));
		font.draw(batch, "Options", menu_box_start, half_height
				- (int) (small_font_size * .5));
		font.draw(batch, "Exit", menu_box_start,
				(float) (half_height - (small_font_size * 2)));

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
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

	public void changeToGameScreen() {
		handleControllers(false);
		snakeInSpace.setScreen(new GameScreen(snakeInSpace, sounds, width,
				height, seleted));
	}

}
