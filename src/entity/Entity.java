package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import physics.Point;
import main.Handler;

public abstract class Entity {

	protected Handler handler;
	
	protected Point position;
	
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hitbox;

	public Entity(Handler handler, float x, float y, int width, int height, boolean hitbox) {
		this.handler = handler;
		position = new Point(x,y);
		this.width = width;
		this.height = height;
		this.hitbox = hitbox;

		bounds = new Rectangle(0, 0, width, height);
	}

	protected boolean collisionWithCell(int x, int y) {
		return handler.getWorld().getCell(x, y).isSolid();
	}

	public abstract void tick();

	public abstract void render(Graphics g);

	protected void renderHitbox(Graphics g){
		g.setColor(Color.RED);
		g.drawRect((int) (position.getX() - handler.getGame().getGameCamera().getxOffset()),
					(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), bounds.width, bounds.height);
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