package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import physics.Point;
import physics.Vector;
import terrain.Cell;
import main.Handler;

public abstract class Entity {

	protected Handler handler;

	protected Point position;
	protected Vector movement;

	protected int width, height;
	protected Rectangle bounds;
	protected boolean hitbox;

	public Entity(Handler handler, float x, float y, int width, int height, boolean hitbox) {
		this.handler = handler;
		position = new Point(x, y);
		this.width = width;
		this.height = height;
		this.hitbox = hitbox;
		bounds = new Rectangle(0, 0, width, height);
		movement = new Vector(0, 0);
	}

	protected boolean collisionWithCell(int x, int y) {
		return handler.getWorld().getCell(x, y).isSolid();
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	protected void renderHitbox(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect((int) (position.getX() - handler.getGame().getGameCamera().getxOffset()),
				(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), bounds.width, bounds.height);
	}

	public void resetMovement() {
		movement = Vector.g;
	}

	public void move(Vector mov) {
		position = avoidCollision(position, mov);
	}

	public Point avoidCollision(Point pos, Vector mov) {
		mov = mov.multiply(handler.getWorld()
				.getCell((int) (position.getX() / Cell.CELLWIDTH), (int) (position.getY()) / Cell.CELLHEIGHT)
				.getViscuosity());
		
		pos = pos.translate(mov);
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

	public Point getPosition() {
		return position;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}