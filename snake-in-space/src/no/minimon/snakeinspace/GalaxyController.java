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
			} else if (keyCode == Keys.MENU) {
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
			galaxy.getSnake(player).addState(Snake.State.LEFT);
			break;
		case Keys.RIGHT:
		case Keys.D:
		case Keys.J:
		case Keys.P:
			galaxy.getSnake(player).addState(Snake.State.RIGHT);
			break;
		case Keys.UP:
		case Keys.W:
		case Keys.Y:
		case 9:
		case 96:
			galaxy.getSnake(player).accelerate(true);
			break;
		case Keys.DOWN:
		case Keys.S:
		case Keys.H:
		case Keys.O:
		case 97:
			galaxy.getSnake(player).deceleration(true);
			break;
		case Keys.MENU:
			galaxy.snakeInSpace.setScreen(new MenuScreen(galaxy.snakeInSpace,
					galaxy.width, galaxy.height));
			break;
		default:
			break;
		}
	}

	public void keyUp(int player, int keyCode) {
		player = mapCorrectPlayerIndex(player, keyCode);
		if (player < 0)
			return;
		switch (keyCode) {
		case Keys.LEFT:
		case Keys.A:
		case Keys.G:
		case Keys.I:
			galaxy.getSnake(player).removeState(Snake.State.LEFT);
			break;
		case Keys.RIGHT:
		case Keys.D:
		case Keys.J:
		case Keys.P:
			galaxy.getSnake(player).removeState(Snake.State.RIGHT);
			break;
		case Keys.UP:
		case Keys.W:
		case Keys.Y:
		case 9:
		case 96:
			galaxy.getSnake(player).accelerate(false);
			break;
		case Keys.DOWN:
		case Keys.S:
		case Keys.H:
		case Keys.O:
		case 97:
			galaxy.getSnake(player).deceleration(false);
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
		return player;
	}

	public int indexOf(Controller controller) {
		// System.out.println("indexOf: " + controller.getName() + " = " +
		// Controllers.getControllers().indexOf(controller, true));
		return Controllers.getControllers().indexOf(controller, true);
	}
}
