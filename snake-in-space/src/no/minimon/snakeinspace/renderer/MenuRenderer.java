package no.minimon.snakeinspace.renderer;

import no.minimon.snakeinspace.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MenuRenderer {

	private final String TITLE = "SNAKES\nIN\nSPACE!";

	private BitmapFont font_menu, font_title;
	private ShapeRenderer renderer;
	private MenuScreen menuScreen;
	private SpriteBatch batch;

	private float menu_box_start;
	private float half_screen_height;
	private float font_title_height;

	private int small_font_size;
	private int box_height;
	private int box_width;

	public MenuRenderer(MenuScreen menuScreen) {
		this.menuScreen = menuScreen;
		
		renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font_menu = new BitmapFont();
		font_title = new BitmapFont();
		
		font_menu.setScale(1.5f);
		font_title.setScale((float) (Gdx.graphics.getHeight() / 120)); // 5 

		half_screen_height = Gdx.graphics.getHeight() / 2;
		
		font_title_height = font_title.getBounds(TITLE).height;
		small_font_size = (int) font_menu.getBounds("TEST").height;
		
		menu_box_start = Gdx.graphics.getWidth() - 200;
		box_height = (int) (small_font_size * 1.2);
		box_width = 210;

	}

	public void render(float delta) {
		batch.begin();

		drawTitle(batch);

		renderer.begin(ShapeType.Filled);
		renderer.identity();
		renderer.setColor(Color.DARK_GRAY);
		switch (menuScreen.seleted) {
		case 0:
			renderer.rect(menu_box_start - 10, half_screen_height
					+ (int) (small_font_size * 1.5), box_width, box_height);
			break;
		case 1:
			renderer.rect(menu_box_start - 10, half_screen_height, box_width,
					box_height);
			break;
		case 2:
			renderer.rect(menu_box_start - 10, half_screen_height
					- (int) (small_font_size * 1.7), box_width, box_height);
			break;
		case 3:
			renderer.rect(menu_box_start - 10, half_screen_height
					- (int) (small_font_size * 3.2), box_width, box_height);
			break;
		}
		renderer.end();

		font_menu.draw(batch, "Single player", menu_box_start,
				(float) (half_screen_height + (small_font_size * 2.5)));
		font_menu.draw(batch, "Multiplayer < " + menuScreen.multiplayer + " >",
				menu_box_start, half_screen_height + (small_font_size * 1));
		font_menu.draw(batch, "Options", menu_box_start, half_screen_height
				- (int) (small_font_size * .5));
		font_menu.draw(batch, "Exit", menu_box_start,
				(float) (half_screen_height - (small_font_size * 2)));

		batch.end();
	}

	private void drawTitle(SpriteBatch batch) {
		/*
		renderer.begin(ShapeType.Line);
		renderer.identity();
		renderer.line(0, half_screen_height, Gdx.graphics.getWidth(), half_screen_height);
		renderer.line(0, half_screen_height - font_height, 
				Gdx.graphics.getWidth(), half_screen_height - font_height);
		renderer.line(0, half_screen_height - (int) (font_height * 1.5), 
				Gdx.graphics.getWidth(), half_screen_height - (int) (font_height * 1.5));
		renderer.line(0, half_screen_height - (int) (font_height * 2.5), 
				Gdx.graphics.getWidth(), half_screen_height - (int) (font_height * 2.5));
		renderer.line(0, half_screen_height - (int) (font_height * 3), 
				Gdx.graphics.getWidth(), half_screen_height - (int) (font_height * 3));
		renderer.line(0, half_screen_height - (int) (font_height * 4), 
				Gdx.graphics.getWidth(), half_screen_height - (int) (font_height * 4));
		renderer.line(0, half_screen_height - (int) (font_height * 2), 
				Gdx.graphics.getWidth(), half_screen_height - (int) (font_height * 2));
		renderer.end();
		*/
		
		
		font_title.drawMultiLine(batch, TITLE, 
				(Gdx.graphics.getWidth() / 9) * 4, // x
				half_screen_height + (font_title_height * 2), // y 
				0, // alignment width
				HAlignment.RIGHT); // alignment
	}

}
