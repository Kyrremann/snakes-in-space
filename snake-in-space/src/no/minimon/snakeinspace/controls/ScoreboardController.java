package no.minimon.snakeinspace.controls;

import no.minimon.snakeinspace.screens.ScoreboardScreen;
import no.minimon.snakeinspace.utils.Xbox360Pad;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;

public class ScoreboardController extends ControllerAdapter implements
		InputProcessor {

	private ScoreboardScreen screen;

	public ScoreboardController(ScoreboardScreen screen) {
		this.screen = screen;
	}

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		keyDown(buttonIndex);
		return true;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.LEFT:
			break;
		case Keys.RIGHT:
			break;
		case Keys.UP:
			break;
		case Keys.DOWN:
			break;
		case 96: // Ouya.BUTTON_A is not constant
		case Keys.ENTER:
		case Xbox360Pad.BUTTON_A:
		case Keys.MENU:
		case Keys.ESCAPE:
		case Xbox360Pad.BUTTON_BACK:
			screen.handleGoBackToMenu();
			return true;
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

}
