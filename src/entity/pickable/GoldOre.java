package entity.pickable;

import gfx.Assets;
import main.Handler;

public class GoldOre extends PickableEntity {

	public GoldOre(Handler handler, float x, float y) {
		super(handler, x, y, 30, 30, Assets.goldnugget);
	}

	@Override
	public void onCollision() {
		handler.getGame().getEntityManager().removeEntity(this);		
	}

	
	
}
