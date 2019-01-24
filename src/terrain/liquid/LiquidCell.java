package terrain.liquid;

import java.awt.Color;
import java.awt.Graphics;

import terrain.Cell;

public abstract class LiquidCell extends Cell{
	
	public float viscuosity;
	public byte level;
	
	public LiquidCell(Color color, int id, float viscuosity) {
		super(color, id);
		this.viscuosity = viscuosity;
		this.level = Byte.MAX_VALUE;
	}
	
	public LiquidCell(Color color, int id, float viscuosity, byte level) {
		this(color, id, viscuosity);
		this.level = level;
	}
	
	@Override
	public boolean isSolid(){
		return false;
	}
	
	@Override
	public float getViscuosity() {
		return viscuosity;
	}
	
	@Override
	public void render(Graphics g, int x, int y) {
		g.setColor(this.color);
		int levelHeight = (int) (CELLHEIGHT * ((float) level / Byte.MAX_VALUE));
		g.fillRect(x, y + CELLHEIGHT - levelHeight,
				CELLWIDTH, levelHeight);
	}
}
