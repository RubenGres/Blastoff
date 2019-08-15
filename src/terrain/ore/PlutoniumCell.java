package terrain.ore;

import entity.pickable.GoldOre;
import entity.pickable.PlutoniumOre;
import gfx.Assets;

public class PlutoniumCell extends OreCell{

	public PlutoniumCell(int id) {
		super(id, PlutoniumOre.class, Assets.plutonium);
	}

}
