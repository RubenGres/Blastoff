package entity.machine;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.EntityManager;
import gfx.Assets;

public abstract class Machine extends Entity {
	private boolean arrow;
	BufferedImage image;

	public Machine(float x, float y, BufferedImage image) {
		super(x, y, 56, 80, true);
		this.image = image;
	}

	@Override
	public void tick() {
		arrow = false;
		if (EntityManager.checkCollision(handler.getUserPlayer(), this)) {
			arrow = true;
			onCollision();
		}
	}

	protected abstract void onCollision();
	
	@Override
	public void render(Graphics g) {
		int x = (int) (position.getX() - handler.getGame().getGameCamera().getxOffset());
		int y = (int) (position.getY() - handler.getGame().getGameCamera().getyOffset());
		g.drawImage(image, x, y, width, height, null);

		if (arrow)
			g.drawImage(Assets.arrow, x + width / 2 - 10, y - 30, 20, 20, null);
	}
}
