package no.minimon.snakeinspace.renderer;

import no.minimon.snakeinspace.screens.ScoreboardScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class ScoreboardRenderer {
	
	private final String TITLE = "Hall of fame";
	
	private BitmapFont font_menu, font_title;
	private ShapeRenderer renderer;
	private SpriteBatch batch;
	private ScoreboardScreen screen;
	
	private float menu_start;
	private float half_screen_height;
	private float font_title_height;
	private float small_font_size;
	private float box_height;
	private float box_width;
	private float width, height;
	
	public ScoreboardRenderer(ScoreboardScreen screen) {
		this.screen = screen;
		
		renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font_menu = new BitmapFont();
		font_title = new BitmapFont();
		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		font_menu.setScale(height / 400f);
		font_title.setScale(height / 120); 

		half_screen_height = Gdx.graphics.getHeight() / 2;
		
		font_title_height = font_title.getBounds(TITLE).height;
		small_font_size = font_menu.getBounds("TEST").height;
		
		menu_start = width - width / 4;
		box_height = small_font_size * 1.2f;
		box_width = width / 4 + ((width / 4) / 20);
	}

	public void render(float delta, int players) {
		batch.begin();
		
		drawTitle(batch);

		if (players == 1) {
			renderOnePlayer(delta);
		} else {
			renderMultiPlayer(delta);
		}
		
		batch.end();
	}

	private void drawTitle(SpriteBatch batch2) {
		font_title.drawMultiLine(batch, TITLE,
				width / 2, // x
				height - (height / 10), // y
				0, // alignment width
				HAlignment.CENTER); // alignment
	}

	/*
	 * Should let the user type in their name and post it to the online scoreboard
	 * then load the scores close to the player. It looks like we can't retrieve the 
	 * players Ouya username from the code
	 * 
	 * If the user press a button (say Y or X) the user should 
	 * be able to see the top five or ten scores.
	 * 
	 * If the user press up or down, let the user scroll up and down on the scoreboard,
	 * should probably ask for more then what is around the user, to avoid a lot of calls
	 * 
	 * when user press back (say A or O), the user should be taken back to the menu screen
	 * 
	 * when the user press replay, the user should be taken to the game screen,
	 * should probably have some kind of countdown, and remember the users name
	 */
	private void renderOnePlayer(float delta) {
		
	}

	/*
	 * Should not post scores to the scoreboard, but rather show the players score,
	 * so they can compare for them self.
	 * 
	 * In the future we should store the scores, so that they can have cumulative scores
	 * gathered over several games played in a row
	 * 
	 * Same button functions as for one player
	 */
	private void renderMultiPlayer(float delta) {
		
	}

}
