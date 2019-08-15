package entity.machine;

import entity.creature.Player;
import gfx.Assets;

public class OreMachine extends Machine{

	boolean arrow;
	
	public OreMachine(float x, float y) {
		super(x, y, Assets.ore_machine);
	}

	@Override
	protected void onCollision() {
		if (handler.getGame().getKeyManager().select) {
			Player player = handler.getPlayer();
			player.sellCargo();
		}
	}

}
