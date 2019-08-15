package entity.pickable;

import gfx.Assets;

public class GoldOre extends Ore {
		
	public GoldOre(float x, float y) {
		super(x, y, WIDTH, HEIGHT, Assets.goldore);
		this.weight = 10;
		this.value = 100;
	}	
}
