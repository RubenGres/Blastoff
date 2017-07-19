package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SandCell extends Cell {
	
	BufferedImage sand;

	public SandCell(int id) {
		super(new Color(232, 172, 39), id);
		try {
			this.sand = ImageIO.read(new File("res/tiles/sand.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics g, int x, int y){
		g.drawImage(sand, x, y, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
	}

}
