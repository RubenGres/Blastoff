package entity.pickable;
import gfx.Assets;

public class PlutoniumOre extends Ore{

	private int capacity;
	
	public PlutoniumOre(float x, float y) {
		super(x, y, 30, 40, Assets.plutoniumore);
		this.capacity = 20;
	}

	@Override
	public void onCollision() {
		handler.getGame().getEntityManager().getUserPlayer().addFuel(capacity);
		handler.getGame().getEntityManager().removeEntity(this);
	}	
}
