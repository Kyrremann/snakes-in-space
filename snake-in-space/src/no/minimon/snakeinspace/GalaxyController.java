package no.minimon.snakeinspace;

import no.minimon.snakeinspace.utils.Xbox360Pad;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;

public class GalaxyController extends ControllerAdapter {

	private Galaxy galaxy;

	public GalaxyController(Galaxy galaxy) {
		this.galaxy = galaxy;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		System.out.println("button_down: " + buttonIndex);
		keyDown(indexOf(controller), buttonIndex);
		return true;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		System.out.println("button_up: " + buttonIndex);
		keyUp(indexOf(controller), buttonIndex);
		return true;
	}

	/**
	 * directional pad
	 */
	@Override
	public boolean povMoved(Controller controller, int povCode,
			PovDirection value) {
		System.out.println("dpad: " + povCode);
		int player = indexOf(controller);
		switch (value) {
		case north:
			break;
		case south:
			break;
		case east:
			galaxy.getSnake(player).addState(Snake.State.RIGHT);
			break;
		case west:
			galaxy.getSnake(player).addState(Snake.State.LEFT);
			break;
		default:
			break;
		}
		return true;
	}

	/**
	 * analog stick
	 */
	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value){
		int player = indexOf(controller);
		if ( galaxy.getSnake(player) == null) {
			return false;
		}
		if (galaxy.getSnake(player).getHead() == null){
			return false;
		}
		Vector2 dir = galaxy.getSnake(player).getHead().destdir;
		if (dir == null) {
			dir = new Vector2();
		}

		if (Math.abs(value) > 0.175){ // not in dead-zone
//			System.out.println("analog: " + axisCode + ", " + value); // DEBUG
			
			switch (axisCode) {
			case Xbox360Pad.AXIS_LEFT_X: // left analog stick (movement)
				dir.x = value;
				break;
			case Xbox360Pad.AXIS_LEFT_Y:
				dir.y = -value;
				break;
			case Xbox360Pad.AXIS_RIGHT_X: // right analog stick
			case Xbox360Pad.AXIS_RIGHT_Y:
				break;
			}
			galaxy.getSnake(player).setDestDir(dir.nor());
		} else {
			galaxy.getSnake(player).setDestDir(null); // no new destination
		}
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
		case Xbox360Pad.BUTTON_A:
			galaxy.getSnake(player).accelerate(true);
			break;
		case Keys.DOWN:
		case Keys.S:
		case Keys.H:
		case Keys.O:
		case 97:
		case Xbox360Pad.BUTTON_X:
			galaxy.getSnake(player).deceleration(true);
			break;
		case Keys.MENU:
		case Xbox360Pad.BUTTON_BACK:
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
		case Xbox360Pad.BUTTON_A:
			galaxy.getSnake(player).accelerate(false);
			break;
		case Keys.DOWN:
		case Keys.S:
		case Keys.H:
		case Keys.O:
		case 97:
		case Xbox360Pad.BUTTON_X:
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
