package entity.machine;

import entity.creature.Player;
import entity.creature.UserPlayer;
import gfx.Assets;

public class FuelMachine extends Machine{

	public static final int FUEL_PRICE = 1;
	public static final int FUEL_FLOW = 2;
	
	public FuelMachine(float x, float y) {
		super(x, y, Assets.fuel_machine);
	}

	@Override
	protected void onCollision() {
		if (handler.getGame().getKeyManager().select) {
			UserPlayer player = handler.getUserPlayer();
			
			if(player.getFuel() < player.getMaxFuel() && player.getMoney() - FUEL_PRICE >= 0) {
				player.setFuel(player.getFuel() + FUEL_FLOW);
				player.setMoney(player.getMoney() - FUEL_FLOW * FUEL_PRICE);				
			}
		}
	}

	
}
