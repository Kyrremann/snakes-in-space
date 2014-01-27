package no.minimon.snakeinspace;

import no.minimon.snakeinspace.screens.MenuScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class SnakeInSpace extends Game {

	private int width;
	private int height;

	public SnakeInSpace(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		this.width = width;
		this.height = height;
	}

	@Override
	public void create() {
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		setScreen(new MenuScreen(this, width, height));
	}

}
