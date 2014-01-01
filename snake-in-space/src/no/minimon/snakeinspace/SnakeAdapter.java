package no.minimon.snakeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.mappings.Ouya;

public class SnakeAdapter extends ControllerAdapter {

	private Galaxy galaxy;

	public SnakeAdapter(Galaxy galaxy) {
		this.galaxy = galaxy;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		if (controller.getName().equals(Ouya.ID)) {
			Gdx.app.log("DOWN", controller.getName());
			if (controller.getButton(Ouya.BUTTON_DPAD_LEFT)) {
				galaxy.getSnake(0).setState(Snake.State.LEFT);
			} else if (controller.getButton(Ouya.BUTTON_DPAD_RIGHT)) {
				galaxy.getSnake(0).setState(Snake.State.RIGHT);
			}
			return true;
		} else {
			return super.buttonDown(controller, buttonIndex);
		}
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		if (controller.getName().equals(Ouya.ID)) {
			/* if (controller.getButton(Ouya.BUTTON_DPAD_LEFT)
					|| controller.getButton(Ouya.BUTTON_DPAD_RIGHT)) { */
			if (buttonIndex == 21 || buttonIndex == 22) {
				galaxy.getSnake(0).setState(Snake.State.IDLE);
			}
			return true;
		} else {
			return super.buttonDown(controller, buttonIndex);
		}
	}

	@Override
	public void connected(Controller controller) {
		System.out.println(controller.getName());
		super.connected(controller);
	}

	@Override
	public void disconnected(Controller controller) {
		System.out.println(controller.getName());
		super.disconnected(controller);
	}

}
