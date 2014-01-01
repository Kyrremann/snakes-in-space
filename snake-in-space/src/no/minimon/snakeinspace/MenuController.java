package no.minimon.snakeinspace;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.mappings.Ouya;

public class MenuController extends ControllerAdapter {

	private MenuScreen screen;

	public MenuController(MenuScreen screen) {
		this.screen = screen;
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
		case 97: // Ouya.BUTTON_A is not constant 
		case Keys.ENTER:
			screen.changeToGameScreen();
			return true;
		case Keys.MENU:
		case Keys.ESCAPE:
			Gdx.app.exit();
			return true;
		}

		return false;
	}

}
