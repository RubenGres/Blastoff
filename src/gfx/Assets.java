package gfx;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	
	private static int tilesetlength = 16;
	public static BufferedImage[][] grass = new BufferedImage[tilesetlength][4],
									sand = new BufferedImage[tilesetlength][4],
									dirt = new BufferedImage[tilesetlength][4];

	public static BufferedImage gold, lava,	gravel, cobble,	stone;
	
	public static  BufferedImage player = loadTexture("res/textures/player.png"),
			fueltank =  loadTexture("res/textures/fueltank.png"),
			goldnugget = loadTexture("res/textures/ores/goldnugget.png"),
			backpack = loadTexture("res/textures/backpack.png");
	
	public static void preload() {
		int width = 16, height = 16;
		BufferedImage tileset = loadTexture("res/tiles/tileset.png");
		
		Assets.gold = tileset.getSubimage(7 * width, height, width, height);
		
		for(int i = 0; i < tilesetlength; i++) {
			for(int j = 0; j < 4; j++) {
				grass[i][j] = Assets.rotate(tileset.getSubimage(i * width, 0, width, height), j * Math.PI/2);
				sand[i][j] = Assets.rotate(tileset.getSubimage(i * width, height, width, height), j * Math.PI/2);
				dirt[i][j] = Assets.rotate(tileset.getSubimage(i * width, 2*height, width, height), j * Math.PI/2);
			}
		}
	}
	
	public static BufferedImage rotate(BufferedImage image, double angle) {
	    int w = image.getWidth(), h = image.getHeight();        
	    BufferedImage result = new BufferedImage(w, h, image.getType());
	    Graphics2D g = result.createGraphics();
	    g.rotate(angle, w / 2, h / 2);
	    g.drawRenderedImage(image, null);
	    return result;
	}
	
	public static BufferedImage loadTexture(String path){
		try {
			return ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
