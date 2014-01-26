package no.minimon.snakeinspace.physics;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;

public class Quadtree {
	private int MAX_OBJECTS = 5;
	private int MAX_LEVELS = 4;

	private int level; // depth of quadtree
	private ArrayList<HasHitbox> contents; // objects must implement HasHitBox
	private Rectangle bounds; // the boundaries of the quadtree
	private Quadtree[] nodes; // child quadtrees (4 segment divisions)

	public Quadtree(int level, Rectangle bounds) {
		this.level = level;
		contents = new ArrayList<HasHitbox>(); // assume implements
		this.bounds = bounds;
		nodes = new Quadtree[4];
	}

	/*
	 * Clears the quadtree
	 */
	public void clear() {
		contents.clear();

		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i] != null) {
				nodes[i].clear();
				nodes[i] = null;
			}
		}
	}

	/*
	 * Splits the node into 4 subnodes
	 */
	private void split() {
		int subWidth = (int) (bounds.getWidth() / 2);
		int subHeight = (int) (bounds.getHeight() / 2);
		int x = (int) bounds.getX();
		int y = (int) bounds.getY();

		// top left
		nodes[0] = new Quadtree(level + 1, new Rectangle(x + subWidth, y,
				subWidth, subHeight));
		// top right
		nodes[1] = new Quadtree(level + 1, new Rectangle(x + subWidth, y
				+ subHeight, subWidth, subHeight));
		// bottom right
		nodes[2] = new Quadtree(level + 1, new Rectangle(x, y + subHeight,
				subWidth, subHeight));
		// bottom left
		nodes[3] = new Quadtree(level + 1, new Rectangle(x, y, subWidth,
				subHeight));
	}

	/*
	 * Determine which node the object belongs to. -1 means object cannot
	 * completely fit within a child node and is part of the parent node
	 */
	private int getIndex(HasHitbox a1) {
		Hitbox hitbox = (Hitbox) a1.getHitBox();

		double horizontalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
		double verticalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

		int relToHorizMidpoint = hitbox.relToVerticalLine(horizontalMidpoint);
		int relToVertMidpoint = hitbox.relToHorizontalLine(verticalMidpoint);

		if (relToHorizMidpoint == 1) {
			if (relToVertMidpoint == 1)
				return 1; // top right
			else if (relToVertMidpoint == -1)
				return 0; // top left
			else
				return -1; // parent
		} else if (relToHorizMidpoint == -1) {
			if (relToVertMidpoint == 1)
				return 2; // bottom right
			else if (relToVertMidpoint == -1)
				return 3; // bottom left
			else
				return -1; // intersect
		} else
			return -1; // intersect
	}

	/*
	 * Insert the object into the quadtree. If the node exceeds the capacity, it
	 * will split and add all objects to their corresponding nodes.
	 */
	public void insert(HasHitbox a1) {
		if (nodes[0] != null) {
			int index = getIndex(a1);
			if (index != -1) {
				nodes[index].insert(a1);
				return;
			}
		}

		contents.add(a1);

		if (contents.size() > MAX_OBJECTS && level < MAX_LEVELS) {
			if (nodes[0] == null) {
				split();
			}

			int i = 0;
			while (i < contents.size()) {
				int index = getIndex((HasHitbox) contents.get(i));
				if (index != -1) {
					nodes[index].insert((HasHitbox) contents.remove(i));
				} else {
					i++;
				}
			}
		}
	}

	/*
	 * Return all objects that could collide with the given object
	 */
	public List<HasHitbox> retrieve(List<HasHitbox> returnObjects, HasHitbox object) {
		int index = getIndex(object);
		if (index != -1 && nodes[0] != null) {
			nodes[index].retrieve(returnObjects, object); // recursive
		}

		returnObjects.addAll(contents); // parent contents (always added)

		return returnObjects;
	}

	public void draw(ShapeRenderer renderer) {
		renderer.begin(ShapeType.Line);
		renderer.identity();
		renderer.setColor(0, 255, 0, 255);
		renderer.rect(bounds.x, bounds.y, bounds.width, bounds.height);
		renderer.end();
		// recursive draw
		if (nodes[0] != null) {
			nodes[0].draw(renderer);
			nodes[1].draw(renderer);
			nodes[2].draw(renderer);
			nodes[3].draw(renderer);
		}
	}

	// return the count of objects in the quadtree
	public int size() {
		int count = 0;
		count += contents.size();
		if (nodes[0] != null) {
			count += nodes[0].size();
			count += nodes[1].size();
			count += nodes[2].size();
			count += nodes[3].size();
		}
		return count;
	}
}
