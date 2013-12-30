package no.minimon.snakeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;

public class GalaxyController extends ControllerAdapter {

	private Galaxy galaxy;

	public GalaxyController(Galaxy galaxy) {
		this.galaxy = galaxy;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		Gdx.app.log("DOWN", controller.getName());
		keyDown(indexOf(controller), buttonIndex);
		return true;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		keyUp(indexOf(controller), buttonIndex);
		return true;
	}

	public void keyDown(int player, int keyCode) {
		player = mapCorrectPlayerIndex(player, keyCode);
		if (player < 0)
			return;
		switch (keyCode) {
		case Keys.LEFT:
		case Keys.A:
			galaxy.getSnake(player).setState(Snake.State.LEFT);
			break;
		case Keys.RIGHT:
		case Keys.D:
			galaxy.getSnake(player).setState(Snake.State.RIGHT);
		default:
			break;
		}
	}

	public void keyUp(int player, int keyCode) {
		player = mapCorrectPlayerIndex(player, keyCode);
		if (player < 0)
			return;
		galaxy.getSnake(player).setState(Snake.State.IDLE);
	}

	/**
	 * player list is 0-indexed
	 */
	public int mapCorrectPlayerIndex(int player, int keyCode) {
		if (player == -1) {
			if ((keyCode == Keys.LEFT || keyCode == Keys.RIGHT)) {
				return 0;
			} else if ((keyCode == Keys.A || keyCode == Keys.D)) {
				return 1;
			}
		}
		return player - 1;
	}

	public int indexOf(Controller controller) {
		return Controllers.getControllers().indexOf(controller, true);
	}
}
