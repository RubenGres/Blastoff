package entity.pickable;

import gfx.Assets;
import main.Handler;

public class GoldOre extends PickableEntity {

	public static int WIDTH = 20, HEIGHT = 20;
	private int weight = 10;
	private int value;
	
	public GoldOre(float x, float y) {
		super(x, y, WIDTH, HEIGHT, Assets.goldnugget);
	}

	@Override
	public void onCollision() {
		handler.getGame().getEntityManager().getPlayer().addToCargo(weight);
		handler.getGame().getEntityManager().removeEntity(this);		
	}

	
	
}
