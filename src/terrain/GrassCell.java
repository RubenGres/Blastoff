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
		super(Assets.grass, id);
	}
}
