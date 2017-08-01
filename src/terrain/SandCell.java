package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gfx.Assets;

public class SandCell extends Cell {
	
	BufferedImage sand;

	public SandCell(int id) {
		super(new Color(232, 172, 39), id);
		this.sand = Assets.sand;
	}
	
	@Override
	public void render(Graphics g, int x, int y){
		g.drawImage(sand, x, y, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
	}

}
