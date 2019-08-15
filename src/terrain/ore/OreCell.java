package terrain.ore;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.reflect.Constructor;

import entity.pickable.GoldOre;
import entity.pickable.PickableEntity;
import gfx.Assets;
import main.Handler;
import terrain.Cell;

public class OreCell extends Cell{
	
	protected Class<?> loot;
	protected BufferedImage tile;

	public OreCell(int id, Class<?> loot, BufferedImage tile) {
		super(null, id);
		this.loot = loot;
		this.tile = tile;
	}
	
	public void breakCell(int x, int y, Handler handler) {	
		float offset = (float) (Math.random() * (Cell.CELLWIDTH - GoldOre.WIDTH));
		float spawnX = x*Cell.CELLWIDTH + offset;
		float spawnY = y*Cell.CELLHEIGHT;
		
		try {
			Constructor<?> cons = this.loot.getConstructor(float.class, float.class);
			PickableEntity ent = (PickableEntity) cons.newInstance(spawnX, spawnY);
			handler.getGame().getEntityManager().addEntity(ent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics g, int displayX, int displayY, int x, int y) {
		g.drawImage(this.tile, displayX, displayY, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
	}
	
	public OreCell clone() {
		OreCell clone = new OreCell(this.id, this.loot, this.tile);
		return clone;		
	}


}
