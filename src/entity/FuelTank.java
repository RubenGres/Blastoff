package entity;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Assets;
import main.Handler;

public class FuelTank extends Entity{

	private int capacity = 20;
	
	public FuelTank(Handler handler, float x, float y) {
		super(handler, x, y, 30, 40, true);
	}

	@Override
	public void tick() {
		if(EntityManager.checkCollision(handler.getGame().getEntityManager().getPlayer(), this)){
			handler.getGame().getEntityManager().getPlayer().addFuel(capacity);
			handler.getGame().getEntityManager().removeEntity(this);
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawImage(Assets.fueltank,(int) (position.getX() - handler.getGame().getGameCamera().getxOffset()),
				(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), width, height, null);
	}
}
