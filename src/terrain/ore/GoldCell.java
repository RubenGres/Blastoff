package terrain.ore;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import entity.pickable.GoldOre;
import gfx.Assets;
import main.Handler;
import terrain.Cell;

public class GoldCell extends Cell {
	
	BufferedImage gold;

	public GoldCell(int id) {
		super(Color.YELLOW, id);
		this.gold = Assets.gold;
	}

	@Override
	public void breakCell(int x, int y, Handler handler) {
		float offset = (float) (Math.random() * (Cell.CELLWIDTH - GoldOre.WIDTH));
		float spawnX = (x / Cell.CELLWIDTH)*Cell.CELLWIDTH + offset;
		handler.getGame().getEntityManager().addEntity(new GoldOre(spawnX, (y / Cell.CELLHEIGHT)*Cell.CELLHEIGHT));
		super.breakCell(x, y, handler);
	}
	
	@Override
	public void render(Graphics g, int displayX, int displayY, int x, int y) {
		g.drawImage(this.gold, displayX, displayY, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
	}

}
