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

	private float menu_start;
	private float half_screen_height;
	private float font_title_height;
	private float small_font_size;
	private float box_height;
	private float box_width;

	public MenuRenderer(MenuScreen menuScreen) {
		this.menuScreen = menuScreen;
		
		renderer = new ShapeRenderer();
		batch = new SpriteBatch();
		font_menu = new BitmapFont();
		font_title = new BitmapFont();
		
		int width = Gdx.graphics.getWidth();
		int height = Gdx.graphics.getHeight();
		
		font_menu.setScale(height / 400f);
		font_title.setScale(height / 120); 

		half_screen_height = Gdx.graphics.getHeight() / 2;
		
		font_title_height = font_title.getBounds(TITLE).height;
		small_font_size = font_menu.getBounds("TEST").height;
		
		menu_start = width - width / 4;
		box_height = small_font_size * 1.2f;
		box_width = width / 4 + ((width / 4) / 20);
		
		System.out.println(small_font_size); // 18
		System.out.println(small_font_size * 1.2); // 21.59
	}

	public void render(float delta) {
		batch.begin();

		drawTitle(batch);

		renderer.begin(ShapeType.Filled);
		renderer.identity();
		renderer.setColor(Color.DARK_GRAY);
		switch (menuScreen.seleted) {
		case 0:
			renderer.rect(menu_start - 10, half_screen_height
					+ (int) (small_font_size * 1.5), box_width, box_height);
			break;
		case 1:
			renderer.rect(menu_start - 10, half_screen_height, box_width,
					box_height);
			break;
		case 2:
			renderer.rect(menu_start - 10, half_screen_height
					- (int) (small_font_size * 1.7), box_width, box_height);
			break;
		case 3:
			renderer.rect(menu_start - 10, half_screen_height
					- (int) (small_font_size * 3.2), box_width, box_height);
			break;
		}
		renderer.end();

		font_menu.draw(batch, "Single player", menu_start,
				(float) (half_screen_height + (small_font_size * 2.5)));
		font_menu.draw(batch, "Multiplayer < " + menuScreen.multiplayer + " >",
				menu_start, half_screen_height + (small_font_size * 1));
		font_menu.draw(batch, "Options", menu_start, half_screen_height
				- (int) (small_font_size * .5));
		font_menu.draw(batch, "Exit", menu_start,
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
