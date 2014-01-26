package no.minimon.snakeinspace;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;

import no.minimon.snakeinspace.controls.GalaxyController;

/**
 * Representation of a player (in-game)
 * @author julian
 *
 */
public class Player {
	int score;
	GalaxyController controller;
	Snake snake;
	Color color;
	
	FrameBuffer score_buffer;
	
	public Player( Galaxy galaxy, Color color ){
		score = 0;
		this.color = color;
		controller = new GalaxyController(galaxy);
	}
	
	public void drawAll( GalaxyRenderer renderer ){
		drawSnake(renderer);
		drawScore();
	}
	
	private void drawSnake( GalaxyRenderer renderer){
		snake.draw(renderer);
	}
	
	/**
	 * draws score to the players score buffer
	 */
	private void drawScore(){
		
	}

}
