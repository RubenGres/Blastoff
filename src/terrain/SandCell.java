package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gfx.Assets;
import main.Handler;

public class SandCell extends Cell {

	public SandCell(int id) {
		super(Color.BLUE, id);
		this.tiles = Assets.sand;
	}
	
	@Override
	public void render(Graphics g, int displayX, int displayY, int x, int y){
		super.renderG(g, displayX, displayY, x, y);
	}

}
