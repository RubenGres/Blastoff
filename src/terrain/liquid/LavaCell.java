package terrain.liquid;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Assets;
import terrain.Cell;

public class LavaCell extends LiquidCell{

	public float lavaDamage=0.2f;
	
	public LavaCell(int x, int y) {
		super(x, y, Color.red, 20, 0.2f);
	}
}
