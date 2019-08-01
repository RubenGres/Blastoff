package gfx;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {
	
	/* tiles */
	private static int tilesetlength = 16;
	public static BufferedImage[][] grass = new BufferedImage[tilesetlength][4],
									sand = new BufferedImage[tilesetlength][4],
									bedrock = new BufferedImage[tilesetlength][4],
									dirt = new BufferedImage[tilesetlength][4],
									cobble = new BufferedImage[tilesetlength][4],
									stone = new BufferedImage[tilesetlength][4],
									gravel = new BufferedImage[tilesetlength][4];
									
	public static BufferedImage[] breaking = new BufferedImage[tilesetlength];

	public static BufferedImage gold, lava;
	
	/* textures */
	public static  BufferedImage player = loadTexture("res/textures/player.png"),
			fueltank =  loadTexture("res/textures/fueltank.png"),
			goldnugget = loadTexture("res/textures/ores/goldnugget.png"),
			backpack = loadTexture("res/textures/backpack.png"),
			gradient = loadTexture("res/textures/gradient.png"),
			bkg = loadTexture("res/textures/bg/sky.png"),
			guibar = loadTexture("res/textures/guibar.png"),
			guibarbg = loadTexture("res/textures/guibarbg.png");
	
	public static void preload() {
		
		loadTiles();		
		
		int width = 16, height = 16;
		BufferedImage tileset = loadTexture("res/textures/breaking.png");
		
		for(int i = 0; i < tilesetlength; i++) {
			breaking[i] = tileset.getSubimage(i * width, 0, width, height);
		}
	}
	
	private static void loadTiles() {
		int width = 16, height = 16;
		BufferedImage tileset = loadTexture("res/tiles/tileset.png");
		Assets.gold = tileset.getSubimage(7 * width, height, width, height);		
		for(int i = 0; i < tilesetlength; i++) {
			for(int j = 0; j < 4; j++) {
				grass[i][j] = Assets.rotate(tileset.getSubimage(i * width, 0, width, height), j * Math.PI/2);
				sand[i][j] = Assets.rotate(tileset.getSubimage(i * width, height, width, height), j * Math.PI/2);
				dirt[i][j] = Assets.rotate(tileset.getSubimage(i * width, 2*height, width, height), j * Math.PI/2);
				bedrock[i][j] = Assets.rotate(tileset.getSubimage(i * width, 3*height, width, height), j * Math.PI/2);
				cobble[i][j] = Assets.rotate(tileset.getSubimage(i * width, 4*height, width, height), j * Math.PI/2);
				stone[i][j] = Assets.rotate(tileset.getSubimage(i * width, 5*height, width, height), j * Math.PI/2);
				gravel[i][j] = Assets.rotate(tileset.getSubimage(i * width, 6*height, width, height), j * Math.PI/2);
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
