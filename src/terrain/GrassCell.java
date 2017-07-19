package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class GrassCell extends Cell {

	BufferedImage grass;
	
	public GrassCell(int id) {
		super(Color.GREEN.darker(), id);
		try {
			this.grass = ImageIO.read(new File("res/tiles/grass.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void render(Graphics g, int x, int y){
		g.drawImage(grass, x, y, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
	}
}
