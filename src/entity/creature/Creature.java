package entity.creature;

import entity.Entity;
import main.Handler;
import terrain.Cell;

public abstract class Creature extends Entity{
	
	protected float speed;
	
	public static final float DEFAULT_SPEED = 5f;
	public static final int DEFAUL_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
	
	protected float xMove, yMove;
	
	public Creature(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height, true);
		speed = DEFAULT_SPEED;
		xMove = 0;
		yMove = 0;
	}

	public void move(){
		moveX();
		moveY();
	}
	
	private void moveX() {
		if (xMove > 0) {// Moving right
			int tx = (int) (x + xMove + bounds.x + bounds.width) / Cell.CELLWIDTH;
			if(tx < handler.getWorld().width)
			if (!collisionWithCell(tx, (int) (y + bounds.y) / Cell.CELLHEIGHT)
					&& !collisionWithCell(tx, (int) (y + bounds.y + bounds.height) / Cell.CELLHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Cell.CELLWIDTH - bounds.x - bounds.width - 1;
			}

		} else if (xMove < 0) {// Moving left
			int tx = (int) (x + xMove + bounds.x) / Cell.CELLWIDTH;
			if(tx > 0)
			if (!collisionWithCell(tx, (int) (y + bounds.y) / Cell.CELLHEIGHT)
					&& !collisionWithCell(tx, (int) (y + bounds.y + bounds.height) / Cell.CELLHEIGHT)) {
				x += xMove;
			} else {
				x = tx * Cell.CELLWIDTH + Cell.CELLWIDTH - bounds.x;
			}
		}
	}

	private void moveY() {
		if (yMove < 0) {// Up
			int ty = (int) (y + yMove + bounds.y) / Cell.CELLHEIGHT;
			if(ty > 0)
			if (!collisionWithCell((int) (x + bounds.x) / Cell.CELLWIDTH, ty)
					&& !collisionWithCell((int) (x + bounds.x + bounds.width) / Cell.CELLWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Cell.CELLHEIGHT + Cell.CELLHEIGHT - bounds.y;
			}
		} else if (yMove > 0) {// Down
			int ty = (int) (y + yMove + bounds.y + bounds.height) / Cell.CELLHEIGHT;
			if(ty < handler.getWorld().height)
			if (!collisionWithCell((int) (x + bounds.x) / Cell.CELLWIDTH, ty)
					&& !collisionWithCell((int) (x + bounds.x + bounds.width) / Cell.CELLWIDTH, ty)) {
				y += yMove;
			} else {
				y = ty * Cell.CELLHEIGHT - bounds.y - bounds.height - 1;
			}
		}
	}

}
