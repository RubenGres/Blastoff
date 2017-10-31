package entity.pickable;
import gfx.Assets;
import main.Handler;

public class FuelTank extends PickableEntity{

	private int capacity = 20;
	
	public FuelTank(Handler handler, float x, float y) {
		super(handler, x, y, 30, 40, Assets.fueltank);
	}

	@Override
	public void onCollision() {
		handler.getGame().getEntityManager().getPlayer().addFuel(capacity);
		handler.getGame().getEntityManager().removeEntity(this);
	}	
}
