package entity.machine;

import java.awt.image.BufferedImage;

import gfx.Assets;

public class ShopMachine extends Machine{

	public ShopMachine(float x, float y) {
		super(x, y, Assets.shop_machine);
	}

	@Override
	protected void onCollision() {
		//TODO open shop
	}

}
