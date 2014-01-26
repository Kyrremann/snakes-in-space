package no.minimon.snakeinspace.controls;

import no.minimon.snakeinspace.Galaxy;
import no.minimon.snakeinspace.MenuScreen;
import no.minimon.snakeinspace.Snake;
import no.minimon.snakeinspace.utils.Xbox360Pad;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector2;

public class GalaxyController extends ControllerAdapter {

	public static final float ANALOG_DEAD_ZONE = 0.2f;

	private Galaxy galaxy;
	private Vector2 left_analog;
	private Vector2 right_analog;

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
	
		if (left_analog == null) {
			left_analog = new Vector2();
		}
		if (right_analog == null) {
			right_analog = new Vector2();
		}
		
		switch (axisCode) {
		case Xbox360Pad.AXIS_LEFT_X: // left analog stick (movement)
			left_analog.x = value;
			//dir.y = this.getAxis(Xbox360Pad.AXIS_LEFT_Y); //?? not work?
			break;
		case Xbox360Pad.AXIS_LEFT_Y:
			left_analog.y = -value; // our y-axis = flipped
			break;
		case Xbox360Pad.AXIS_RIGHT_X: // right analog stick
			right_analog.x = value;
			break;
		case Xbox360Pad.AXIS_RIGHT_Y:
			right_analog.y = -value; // our y-axis = flipped
			break;
		}
		if (galaxy.analog_vectors.get(0).size() > 100) {
			galaxy.analog_vectors.get(0).remove(0);
		}
		if (galaxy.analog_vectors.get(1).size() > 100) {
			galaxy.analog_vectors.get(1).remove(0);
		}
		galaxy.analog_vectors.get(0).add(left_analog.cpy()); // DEBUG
		galaxy.analog_vectors.get(1).add(right_analog.cpy()); // DEBUG

		// dead-zone
		if (left_analog.dst(0,0) < ANALOG_DEAD_ZONE){
//			System.out.println("dead-zone: " + 
//					left_analog.x + " , " + left_analog.y);
			galaxy.getSnake(player).setDestDir(null); // do not turn
			return false;
		}
		galaxy.getSnake(player).setDestDir(left_analog.cpy().nor());
		return true;
	}

	public void keyDown(int player, int keyCode) {
		player = mapCorrectPlayerIndex(player, keyCode);
		if (player < 0 || player > galaxy.getSnakes().size()) {
			if (keyCode == Keys.ESCAPE) {
				Controllers.removeListener(this);
				System.out.println("removed game controller");
				galaxy.snakeInSpace.setScreen(new MenuScreen(
						galaxy.snakeInSpace, galaxy.width, galaxy.height));
			} else if (keyCode == Keys.MENU) {
				Controllers.removeListener(this);
				System.out.println("removed game controller");
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
			Controllers.removeListener(this);
			System.out.println("removed game controller");
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
