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
		super(Assets.sand, id);
	}
	
}
