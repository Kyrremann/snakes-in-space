package no.minimon.snakeinspace;

import no.minimon.snakeinspace.utils.Xbox360Pad;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class MenuController extends ControllerAdapter {

	private MenuScreen screen;

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
		case Keys.UP:
			screen.seleted--;
			if (screen.seleted < 0)
				screen.seleted = 3;
			return true;
		case Keys.DOWN:
			screen.seleted++;
			if (screen.seleted > 3)
				screen.seleted = 0;
			return true;
		case 96: // Ouya.BUTTON_A is not constant
		case Keys.ENTER:
		case Xbox360Pad.BUTTON_A:
			screen.changeToGameScreen();
			return true;
		case Keys.MENU:
		case Keys.ESCAPE:
		case Xbox360Pad.BUTTON_BACK:
			Gdx.app.exit();
			return true;
		}

		return false;
	}

}
