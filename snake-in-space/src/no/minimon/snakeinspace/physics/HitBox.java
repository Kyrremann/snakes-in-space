package no.minimon.snakeinspace.physics;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

public class HitBox {
	private Object shape; // very generic pointer for shape data
	private HitBoxType type; // how to interpret the shape data (casting)
	@SuppressWarnings("unused")
	// TODO (needed for rectangle & polygons)
	private Float angle;

	public HitBox(Object shape, HitBoxType type, Float angle) {
		this.shape = shape;
		this.type = type;
		this.angle = angle;
	}

	public HitBox(Object shape, HitBoxType type) {
		this(shape, type, 0f);
	}

	// check whether this hitbox intersects / collides with another hitbox
	public boolean intersect(HitBox other) {

		switch (this.type) {
		case CIRCLE:
			Circle c1 = (Circle) this.shape;

			switch (other.type) {
			case CIRCLE:
				Circle c2 = (Circle) other.shape;
				return c1.overlaps(c2);
			case RECTANGLE:
				System.err.println("HITBOX: rectangles not supported!");
				break;
			}
			break;
		case RECTANGLE:
			System.err.println("HITBOX: rectangles not supported!");
			break;
		}
		return false;
	}

	/**
	 * Where the object is placed in regards to the given horizontal line
	 * 
	 * @param line
	 *            - defined by a Y coordinate
	 * @return 0 for intersects, 1 above, -1 below
	 */
	public int relToHorizontalLine(double verticalMidpoint) {
		switch (this.type) {
		case CIRCLE:
			Circle c1 = (Circle) this.shape;
			if (c1.y - c1.radius > verticalMidpoint) {
				return 1;
			} else if (c1.y + c1.radius < verticalMidpoint) {
				return -1;
			} else {
				return 0;
			}
		case RECTANGLE:
			System.err.println("HITBOX: rectangles not supported!");
			break;
		}
		// this should never happen
		System.exit(1);
		return 0;
	}

	/**
	 * Where the object is placed in regards to the given vertical line
	 * 
	 * @param line
	 *            - defined by an X coordinate
	 * @return 0 for intersects, 1 above, -1 below
	 */
	public int relToVerticalLine(double horizontalMidpoint) {
		switch (this.type) {
		case CIRCLE:
			Circle c1 = (Circle) this.shape;
			if (c1.x - c1.radius > horizontalMidpoint) {
				return 1;
			} else if (c1.x + c1.radius < horizontalMidpoint) {
				return -1;
			} else {
				return 0;
			}
		case RECTANGLE:
			System.err.println("HITBOX: rectangles not supported!");
		default:
			// this should never happen
			System.exit(1);
			return 0;
		}
	}
	public void setLocation(Vector2 position) {
		switch(this.type){
		case CIRCLE:
			Circle c1 = (Circle) this.shape;
			c1.x = position.x;
			c1.y = position.y;
			break;
		case RECTANGLE:
			System.err.println("HITBOX: rectangles not supported!");
			break;
		default:
			// this should never happen
			System.exit(1);
		}
	}
}
