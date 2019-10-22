package entity.pickable;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.EntityManager;

public abstract class PickableEntity extends Entity{
	
	private BufferedImage texture;

	public PickableEntity(float x, float y, int width, int height, BufferedImage texture) {
		super(x, y, width, height, true);
		this.texture = texture;
	}

	@Override
	public void tick() {
		if(EntityManager.checkCollision(handler.getUserPlayer(), this)){
			onCollision();
		}
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(texture,(int) (position.getX() - handler.getGame().getGameCamera().getxOffset()),
				(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), width, height, null);
	}

	public abstract void onCollision();
	
}
