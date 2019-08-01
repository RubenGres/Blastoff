package terrain.ore;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.pickable.GoldOre;
import gfx.Assets;
import main.Handler;
import terrain.Cell;

public class GoldCell extends Cell {

	public GoldCell(int id) {
		super(null, id);
	}

	@Override
	public void breakCell(int x, int y, Handler handler) {
		float offset = (float) (Math.random() * (Cell.CELLWIDTH - GoldOre.WIDTH));
		float spawnX = x*Cell.CELLWIDTH + offset;
		float spawnY = y*Cell.CELLHEIGHT;
		handler.getGame().getEntityManager().addEntity(new GoldOre(spawnX, spawnY));
		super.breakCell(x, y, handler);
	}
	
	@Override
	public void render(Graphics g, int displayX, int displayY, int x, int y) {
		g.drawImage(Assets.gold, displayX, displayY, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
	}

}
