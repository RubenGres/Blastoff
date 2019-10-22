package entity.machine;

import entity.creature.UserPlayer;
import gfx.Assets;

public class OreMachine extends Machine{

	boolean arrow;
	
	public OreMachine(float x, float y) {
		super(x, y, Assets.ore_machine);
	}

	@Override
	protected void onCollision() {
		if (handler.getGame().getKeyManager().select) {
			UserPlayer player = handler.getUserPlayer();
			player.sellCargo();
		}
	}

}
