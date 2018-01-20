package gfx;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {

	public static BufferedImage grass = loadTexture("res/tiles/grass.png"), 
			sand = loadTexture("res/tiles/sand.png"), 
			gravel, 
			cobble, 
			stone;
	
	public static  BufferedImage player = loadTexture("res/textures/player.png"),
			fueltank =  loadTexture("res/textures/fueltank.png"),
			goldnugget = loadTexture("res/textures/ores/goldnugget.png"),
			backpack = loadTexture("res/textures/backpack.png");
	
	public static BufferedImage loadTexture(String path){
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
