package terrain.ore;

import entity.pickable.GoldOre;
import gfx.Assets;
import terrain.Cell;

public class GoldCell extends OreCell {
	public GoldCell(int id) {
		super(id, GoldOre.class, Assets.gold);
	}
}
