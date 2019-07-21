package terrain;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gfx.Assets;
import main.Handler;

public class GrassCell extends Cell {

	public GrassCell(int id) {
		super(Color.GREEN, id);
		this.tiles = Assets.grass;
	}
	
	@Override
	public void render(Graphics g, int displayX, int displayY, int x, int y) {
		super.renderG(g, displayX, displayY, x, y);
	}
}
