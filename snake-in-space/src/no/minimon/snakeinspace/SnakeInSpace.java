package no.minimon.snakeinspace;

import com.badlogic.gdx.Game;

public class SnakeInSpace extends Game {

	private int width;
	private int height;

	public SnakeInSpace(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void create() {
		setScreen(new MenuScreen(this, width, height));
	}

}
