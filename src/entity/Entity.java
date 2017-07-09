package entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.Handler;

public abstract class Entity {

	protected Handler handler;
	protected float x, y;
	protected int width, height;
	protected Rectangle bounds;
	protected boolean hitbox;
	protected final float g = 5f;

	public Entity(Handler handler, float x, float y, int width, int height, boolean hitbox) {
		this.handler = handler;
		this.x = x;
		this.y = y;
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

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

}