package no.minimon.snakeinspace.renderer;

import java.util.Random;

import no.minimon.snakeinspace.screens.MenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.HAlignment;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;

public class MenuRenderer {

	private final String TITLE = "SNAKES\nIN\nSPACE!";

	private BitmapFont font_menu, font_title;
	private ShapeRenderer renderer;
	private SpriteBatch batch;
	private MenuScreen menuScreen;

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
		box_width = width / 4 + ((width / 4) / 20) + 50;
	}

	public void render(float delta) {
		batch.begin();

		drawTitle(batch);
		meshTest(delta);
		/*
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
					- (int) (small_font_size * 1.8), box_width, box_height);
			break;
		case 3:
			renderer.rect(menu_start - 10, half_screen_height
					- (int) (small_font_size * 3.3), box_width, box_height);
			break;
		case 4:
			renderer.rect(menu_start - 10, half_screen_height
					- (int) (small_font_size * 4.8), box_width, box_height);
			break;
		}
		renderer.end();
		 */
		font_menu.draw(batch, "Single player", menu_start,
				(float) (half_screen_height + (small_font_size * 2.5)));
		font_menu.draw(batch, "Multiplayer < " + menuScreen.multiplayer + " >",
				menu_start, half_screen_height + (small_font_size * 1));
		font_menu.draw(batch, "Scoreboard", menu_start, half_screen_height
				- (int) (small_font_size * .5));
		font_menu.draw(batch, "Options", menu_start, half_screen_height
				- (int) (small_font_size * 2));
		font_menu.draw(batch, "Exit", menu_start,
				(float) (half_screen_height - (small_font_size * 3.5)));

		batch.end();
	}
	
	private Mesh mesh;
	private Random random;
	private float counter;

	private void meshTest(float delta) {
		if (mesh == null) {
            random = new Random();
            
            mesh = new Mesh(true, 4, 6, 
                    new VertexAttribute(Usage.Position, 3, "a_position"),
                    new VertexAttribute(Usage.ColorPacked, 4, "a_color"));

    		mesh.setVertices(giefVertices());
            mesh.setIndices(new short[] { 0, 1, 2, 2, 3, 0 });
        }
		
		counter += delta;
		
		if (counter > .25) {
			counter -= .25;
			mesh.setVertices(giefVertices());
		}
		
		mesh.render(GL10.GL_TRIANGLES, 0, 6);
	}
	
	@SuppressWarnings("unused")
	private float between(int min, int max) {
		return (float) random.nextInt(max - min) + min;
	}
	
	private float between(float number) {
		float min = number - 2;
		float max = number + 2;
		return (float) random.nextInt((int) (max - min)) + min;
	}
	
	private float[] giefVertices() {
		float y = 0;
		float x = menu_start - 10;
		
		switch (menuScreen.seleted) {
		case 0:
			y = half_screen_height + (int) (small_font_size * 1.5);
			break;
		case 1:
			y = half_screen_height;
			break;
		case 2:
			y = half_screen_height - (int) (small_font_size * 1.8);
			break;
		case 3:
			y = half_screen_height - (int) (small_font_size * 3.3);
			break;
		case 4:
			y = half_screen_height - (int) (small_font_size * 4.8);
			break;
		}
		
		return new float[] { 
				/*between(49, 51), between(49, 51), 0, Color.toFloatBits(255, 0, 0, 255),
        		between(99, 101), between(49, 51), 0, Color.toFloatBits(0, 255, 0, 255),
                between(99, 101), between(99, 101), 0, Color.toFloatBits(0, 0, 255, 255),
                between(49, 51), between(99, 101), 0, Color.toFloatBits(0, 255, 0, 255)*/
				/*between(40, 60), between(40, 60), 0, Color.toFloatBits(255, 0, 0, 255),
        		between(90, 110), between(40, 60), 0, Color.toFloatBits(0, 255, 0, 255),
                between(90, 110), between(90, 110), 0, Color.toFloatBits(0, 0, 255, 255),
                between(40, 60), between(90, 110), 0, Color.toFloatBits(0, 255, 0, 255)*/
				
				/*x, y, 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(255, 0, 0, 255),
				x + box_width, y, 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(0, 255, 0, 255),
				x + box_width, y + box_height, 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(0, 0, 255, 255),
                x, y + box_height, 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(0, 255, 0, 255)*/
                between(x), between(y), 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(255, 0, 0, 255),
                between(x + box_width), between(y), 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(0, 255, 0, 255),
                between(x + box_width), between(y + box_height), 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(0, 0, 255, 255),
                between(x), between(y + box_height), 0, Color.DARK_GRAY.toFloatBits(), // Color.toFloatBits(0, 255, 0, 255)
                
				/*between(40, 60), between(40, 60), 0, Color.toFloatBits(255, 0, 0, 255),
        		between(90, 110), between(40, 60), 0, Color.toFloatBits(0, 255, 0, 255),
                between(90, 110), between(90, 110), 0, Color.toFloatBits(0, 0, 255, 255),
                between(40, 60), between(90, 110), 0, Color.toFloatBits(0, 255, 0, 255)*/
         };
		/*
		 * menu_start - 10, half_screen_height
		 * 	+ (int) (small_font_size * 1.5), box_width, box_height
		 */
	}

	private void drawTitle(SpriteBatch batch) {
		/*
		 * renderer.begin(ShapeType.Line); renderer.identity(); renderer.line(0,
		 * half_screen_height, Gdx.graphics.getWidth(), half_screen_height);
		 * renderer.line(0, half_screen_height - font_height,
		 * Gdx.graphics.getWidth(), half_screen_height - font_height);
		 * renderer.line(0, half_screen_height - (int) (font_height * 1.5),
		 * Gdx.graphics.getWidth(), half_screen_height - (int) (font_height *
		 * 1.5)); renderer.line(0, half_screen_height - (int) (font_height *
		 * 2.5), Gdx.graphics.getWidth(), half_screen_height - (int)
		 * (font_height * 2.5)); renderer.line(0, half_screen_height - (int)
		 * (font_height * 3), Gdx.graphics.getWidth(), half_screen_height -
		 * (int) (font_height * 3)); renderer.line(0, half_screen_height - (int)
		 * (font_height * 4), Gdx.graphics.getWidth(), half_screen_height -
		 * (int) (font_height * 4)); renderer.line(0, half_screen_height - (int)
		 * (font_height * 2), Gdx.graphics.getWidth(), half_screen_height -
		 * (int) (font_height * 2)); renderer.end();
		 */

		font_title.drawMultiLine(batch, TITLE,
				(Gdx.graphics.getWidth() / 9) * 4, // x
				half_screen_height + (font_title_height * 2), // y
				0, // alignment width
				HAlignment.RIGHT); // alignment
	}

}
