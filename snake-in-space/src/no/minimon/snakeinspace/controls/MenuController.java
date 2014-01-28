package no.minimon.snakeinspace.controls;

import tv.ouya.console.api.OuyaController;
import no.minimon.snakeinspace.screens.MenuScreen;
import no.minimon.snakeinspace.utils.Direction;
import no.minimon.snakeinspace.utils.Xbox360Pad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.mappings.Ouya;
import com.badlogic.gdx.math.Vector2;

public class MenuController extends ControllerAdapter {
	
	public static final float ANALOG_DEAD_ZONE = 0.2f;

	private MenuScreen screen;

	private Vector2 left_analog;
	private Vector2 right_analog;
	
	public Direction direction_state = Direction.NONE;

	public MenuController(MenuScreen screen) {
		this.screen = screen;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		keyDown(buttonIndex);
		return true;
	}

	public boolean keyDown(int keyCode) {
		switch (keyCode) {
		case Keys.LEFT:
			if (screen.selected == 1) {
				screen.multiplayer--;
				if (screen.multiplayer < 2) screen.multiplayer = 4;
			}
			break;
		case Keys.RIGHT:
			if (screen.selected == 1) {
				screen.multiplayer++;
				if (screen.multiplayer > 4) screen.multiplayer = 2;
			}
			break;
		case Keys.UP:
			screen.selected--;
			if (screen.selected < 0)
				screen.selected = 4;
			return true;
		case Keys.DOWN:
			screen.selected++;
			if (screen.selected > 4)
				screen.selected = 0;
			return true;
		case 96: // Ouya.BUTTON_A is not constant
		case Keys.ENTER:
		case Xbox360Pad.BUTTON_A:
			screen.handleConfirmButton();
			return true;
		case Keys.MENU:
		case Keys.ESCAPE:
		case Xbox360Pad.BUTTON_BACK:
			Gdx.app.exit();
			return true;
		}

		return false;
	}
	
	/**
	 * analog stick
	 */
	@Override
	public boolean axisMoved(Controller controller, int axisCode, float value){
		if (left_analog == null) {
			left_analog = new Vector2();
		}
		if (right_analog == null) {
			right_analog = new Vector2();
		}

		// OUYA controller
		if (controller.getName().equals(Ouya.ID)){
			switch (axisCode) {
			case OuyaController.AXIS_LS_X: // left analog stick (movement)
				left_analog.x = value;
				//dir.y = this.getAxis(Xbox360Pad.AXIS_LEFT_Y); //?? not work?
				break;
			case OuyaController.AXIS_LS_Y:
				left_analog.y = -value; // our y-axis = flipped
				break;
			case OuyaController.AXIS_RS_X: // right analog stick
				right_analog.x = value;
				break;
			case OuyaController.AXIS_RS_Y:
				right_analog.y = -value; // our y-axis = flipped
				break;
			}
		} 
		// XBOX controller
		else if (controller.getName().toLowerCase().contains("x-box")){
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
		} 
		
		// dead-zone
		if (left_analog.dst(0,0) < ANALOG_DEAD_ZONE ){
			direction_state = Direction.NONE;
			return false;
		}
		float angle = left_analog.angle();
		if (angle > 45 && angle <= 135) {
			direction_state = Direction.NORTH;
		} else if (angle > 135 && angle <= 225) {
			direction_state = Direction.WEST;
		} else if (angle > 225 && angle <= 315) {
			direction_state = Direction.SOUTH;
		} else {
			direction_state = Direction.EAST;
		}
		return true;
	}
}
