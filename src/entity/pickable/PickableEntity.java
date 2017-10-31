package entity.pickable;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.Entity;
import entity.EntityManager;
import gfx.Assets;
import main.Handler;

public abstract class PickableEntity extends Entity{
	
	private BufferedImage texture;

	public PickableEntity(Handler handler, float x, float y, int width, int height, BufferedImage texture) {
		super(handler, x, y, width, height, true);
		this.texture = texture;
	}

	@Override
	public void tick() {
		if(EntityManager.checkCollision(handler.getGame().getEntityManager().getPlayer(), this)){
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
