package no.minimon.snakeinspace;

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
		if (player < 0 || player > galaxy.getSnakes().size()) {
			if (keyCode == Keys.ESCAPE) {
				galaxy.snakeInSpace.setScreen(new MenuScreen(
						galaxy.snakeInSpace, galaxy.width, galaxy.height));
			}
			return;
		}

		switch (keyCode) {
		case Keys.LEFT:
		case Keys.A:
		case Keys.G:
		case Keys.I:
			galaxy.getSnake(player).setState(Snake.State.LEFT);
			break;
		case Keys.RIGHT:
		case Keys.D:
		case Keys.J:
		case Keys.P:
			galaxy.getSnake(player).setState(Snake.State.RIGHT);
			break;
		case Keys.UP:
		case Keys.W:
		case Keys.Y:
		case 9:
			galaxy.getSnake(player).accelerate(true);
			break;
		case Keys.MENU:
			galaxy.snakeInSpace.setScreen(new MenuScreen(galaxy.snakeInSpace,
					galaxy.width, galaxy.height));
			break;
		default:
			System.out.println(keyCode);
			break;
		}
	}

	public void keyUp(int player, int keyCode) {
		player = mapCorrectPlayerIndex(player, keyCode);
		if (player < 0)
			return;
		switch (keyCode) {
		case Keys.LEFT:
		case Keys.RIGHT:

		case Keys.A:
		case Keys.D:

		case Keys.G:
		case Keys.J:

		case Keys.I:
		case Keys.P:
			galaxy.getSnake(player).setState(Snake.State.IDLE);
			break;
		case Keys.UP:
		case Keys.W:
		case Keys.Y:
		case 9:
			galaxy.getSnake(player).accelerate(false);
			break;
		}

	}

	/**
	 * player list is 0-indexed
	 */
	public int mapCorrectPlayerIndex(int player, int keyCode) {
		if (player == -1) {
			switch (keyCode) {
			case Keys.LEFT:
			case Keys.RIGHT:
			case Keys.UP:
			case Keys.DOWN:
				return 0;
			case Keys.A:
			case Keys.W:
			case Keys.D:
			case Keys.S:
				return 1;
			case Keys.G:
			case Keys.Y:
			case Keys.J:
			case Keys.H:
				return 2;
			case Keys.I:
			case 9:
			case Keys.P:
			case Keys.O:
				return 3;
			}
		}
		return player - 1;
	}

	public int indexOf(Controller controller) {
		return Controllers.getControllers().indexOf(controller, true);
	}
}
