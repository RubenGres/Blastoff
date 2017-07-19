package entity.creature;

import entity.Entity;
import main.Handler;
import physics.Point;
import physics.Vector;
import terrain.Cell;

public abstract class Creature extends Entity {

	protected float speed;
	public static final float DEFAULT_SPEED = 5f;
	public static final int DEFAUL_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;

	protected Vector movement;

	public Creature(Handler handler, float x, float y, int width, int height, float speed) {
		super(handler, x, y, width, height, true);
		this.speed = speed;
		movement = new Vector(0, 0);
	}

	public void resetMovement() {
		movement = new Vector(0, 0);
	}

	public void move(Vector mov) {
		Vector adjusted = mov.add(Vector.g);
		adjusted = adjusted.multiply(handler.getWorld().getCell( (int) (position.getX() / Cell.CELLWIDTH), (int) ((position.getY() / Cell.CELLHEIGHT))).getViscuosity());
		Point pos = position.translate(adjusted);
		position = avoidCollision(pos, adjusted);
	}

	public Point avoidCollision(Point pos, Vector mov) {
		if (mov.getX() > 0) {

			int tx = (int) (position.getX() + mov.getX() + bounds.x + bounds.width) / Cell.CELLWIDTH;
			if (collisionWithCell(tx, (int) (position.getY() + bounds.y) / Cell.CELLHEIGHT)
					|| collisionWithCell(tx, (int) (position.getY() + bounds.y + bounds.height) / Cell.CELLHEIGHT)) {
				pos.setX(tx * Cell.CELLWIDTH - bounds.x - bounds.width - 1);
			}

		} else if (mov.getX() < 0) {
			int tx = (int) (position.getX() + mov.getX() + bounds.x) / Cell.CELLWIDTH;
			if (collisionWithCell(tx, (int) (position.getY() + bounds.y) / Cell.CELLHEIGHT)
					|| collisionWithCell(tx, (int) (position.getY() + bounds.y + bounds.height) / Cell.CELLHEIGHT)) {
				pos.setX(tx * Cell.CELLWIDTH + Cell.CELLWIDTH - bounds.x);
			}
		}

		if (mov.getY() < 0) {
			int ty = (int) (position.getY() + mov.getY() + bounds.y) / Cell.CELLHEIGHT;
			if (collisionWithCell((int) (position.getX() + bounds.x) / Cell.CELLWIDTH, ty)
					|| collisionWithCell((int) (position.getX() + bounds.x + bounds.width) / Cell.CELLWIDTH, ty)) {
				pos.setY(ty * Cell.CELLHEIGHT + Cell.CELLHEIGHT - bounds.y);
			}
		}

		if (mov.getY() > 0) {
			int ty = (int) (position.getY() + mov.getY() + bounds.height + bounds.y) / Cell.CELLHEIGHT;
			if (collisionWithCell((int) (position.getX() + bounds.x) / Cell.CELLWIDTH, ty)
					|| collisionWithCell((int) (position.getX() + bounds.x + bounds.width) / Cell.CELLWIDTH, ty)) {
				pos.setY(ty * Cell.CELLHEIGHT - bounds.y - bounds.height - 1);
			}
		}
		return pos;
	}
}