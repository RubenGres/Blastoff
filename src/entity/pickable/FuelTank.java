package entity.pickable;
import gfx.Assets;
import main.Handler;

public class FuelTank extends PickableEntity{

	private int capacity = 20;
	
	public FuelTank(float x, float y) {
		super(x, y, 30, 40, Assets.fueltank);
	}

	@Override
	public void onCollision() {
		handler.getGame().getEntityManager().getPlayer().addFuel(capacity);
		handler.getGame().getEntityManager().removeEntity(this);
	}	
}
