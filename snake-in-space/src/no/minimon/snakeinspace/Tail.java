package no.minimon.snakeinspace;

import no.minimon.snakeinspace.physics.HasHitbox;
import no.minimon.snakeinspace.physics.Hitbox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;

public class Tail implements HasHitbox {

	public int radius = 7;
	Hitbox hitbox; // TODO use this?
	
	public Vector2 direction;
	public Vector2 position;
	public boolean isHead;
	public Vector2 destdir; // destination direction (used by head)
	
	private Snake s; // pointer to parent snake owning this tail segment
	public Tail prev;

	public Tail(Snake s) {
		this(s, new Vector2(), new Vector2());
	}

	public Tail(Snake s, Vector2 position, Vector2 direction) {
		this.s = s;
		this.position = position;
		this.direction = direction;
				
		this.destdir = new Vector2();
	}

	@Override
	public String toString() {
		return String.format("(%f,%f) %f", position.x, position.y, direction);
	}
	
	/**
	 * update location of piece at index
	 * 
	 * @param delta
	 *				the amount to update (1 = per second)
	 * @param s 
	 * 				the snake this piece belongs to
	 * @param width
	 *       		width of screen
	 * @param height
	 *				height of screen
	 */
	public void calculatePieceLocation(float delta, int width, int height) {
		// if head piece, move straight forward (angle predetermined)
		if (isHead) {
			position.add(direction.cpy().scl(s.getSpeed() * delta));
		}
		// if other piece, calculate new angle, then move straight forward
		else {
			// ASSUMPTION: when segments separated by more than SPEED -> wrap

			// OPTIMIZE: shrink repeated code here!!!
			// if dist to prev is greater than SPEED dictates = it has wrapped
			if (position.cpy().sub(prev.position).x > s.getSpeed()) {
				// prev has wrapped towards west

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.x += width; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				direction = dest.cpy().sub(position);
				direction.nor(); // normalize direction

				position = dest.cpy().sub(direction.cpy().scl(radius*2));
			} else if (position.cpy().sub(prev.position).x < -s.getSpeed()) {
				// prev has wrapped towards east

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.x -= width; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				direction = dest.cpy().sub(position);
				direction.nor(); // normalize direction

				position = dest.cpy()
						.sub(direction.cpy().scl(radius*2));
			} else if (position.cpy().sub(prev.position).y > s.getSpeed()) {
				// prev has wrapped towards south

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.y += height; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				direction = dest.cpy().sub(position);
				direction.nor(); // normalize direction

				position = dest.cpy()
						.sub(direction.cpy().scl(radius*2));
			} else if (position.cpy().sub(prev.position).y < -s.getSpeed()) {
				// prev has wrapped towards north

				// this piece shall move towards a new destination (un-wrapped)
				Vector2 dest = prev.position.cpy();
				dest.y -= height; // 'un-wrapping' the prev's position

				// point piece at NEW DEST (not prev piece)
				direction = dest.cpy().sub(position);
				direction.nor(); // normalize direction

				position = dest.cpy()
						.sub(direction.cpy().scl(radius*2));
			} else {
				// continue 'normally'

				// point piece directly at prev piece
				direction = prev.position.cpy().sub(position);
				direction.nor(); // normalize direction

				// set location to SIZE distance from preceding piece
				position = prev.position.cpy().sub(
						direction.cpy().scl(radius*2));
			}
		}
		// if outside boundaries, wrap to other side
		if (position.x < 0) {
			position.x += width; // wrap to east
		} else if (position.x > width) {
			position.x -= width; // wrap to west
		}
		if (position.y < 0) {
			position.y += height; // wrap to north
		} else if (position.y > height) {
			position.y -= height; // wrap to south
		}
	}

	@Override
	public Hitbox getHitBox() {
		return hitbox;
	}

	public void draw(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderer.identity();

		renderer.translate(position.x, position.y, 0);
		renderer.rotate(0, 0, 1, direction.angle());

		// renderer.setColor(Color.GRAY);
		if (isHead) {
			renderer.setColor(Color.MAGENTA);
			renderer.triangle(-4, -7.5f, -4, 7.5f, 12f, 0);
		} else {
			renderer.setColor(s.getPlayerColor());
			renderer.circle(0, 0, radius);
			renderer.line(-7, 0, 7, 0);
			// renderer.triangle(0, -5, 0, 5, 10, 0);
		}
		renderer.end();
	}
}