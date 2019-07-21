package terrain;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Assets;

public class DirtCell extends Cell{

	public DirtCell(int id) {
		super(new Color(168,82,18), id);
		this.tiles = Assets.dirt;
	}
	
	@Override
	public void render(Graphics g, int displayX, int displayY, int x, int y){
		super.renderG(g, displayX, displayY, x, y);
	}


}