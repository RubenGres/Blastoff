package gfx;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Assets {

	/* */
	public static final int TILE_WIDTH = 16, TILE_HEIGHT = 16;
	
	/* background */
	public static Background bkg;
	
	/* fonts */
	public static Font font_ka1;
	
	/* tiles */
	private static int tilesetlength = 16;
	public static BufferedImage[][] grass = new BufferedImage[tilesetlength][4],
									dirt = new BufferedImage[tilesetlength][4],
									stone = new BufferedImage[tilesetlength][4],
									obsidian = new BufferedImage[tilesetlength][4],
									gravel = new BufferedImage[tilesetlength][4],
									granite = new BufferedImage[tilesetlength][4],
									marble = new BufferedImage[tilesetlength][4];
									
	public static BufferedImage[] breaking = new BufferedImage[tilesetlength];

	/* ores */
	public static BufferedImage gold, plutonium;
	
	/* entities */
	public static BufferedImage[] playerAnim;
	public static BufferedImage playerIDLE;
	
	/* machines */
	public static  BufferedImage ore_machine = loadTexture("res/textures/machines/ore.png"),
			fuel_machine = loadTexture("res/textures/machines/fuel.png"),
			shop_machine = loadTexture("res/textures/machines/shop.png");
	
	/* textures */
	public static  BufferedImage gradient = loadTexture("res/textures/gradient.png"),
			guibar = loadTexture("res/textures/guibar.png"),
			guibarbg = loadTexture("res/textures/guibarbg.png"),
			healthbar = loadTexture("res/textures/healthbar.png"),
			fuelbar = loadTexture("res/textures/fuelbar.png"),
			arrow = loadTexture("res/textures/arrow.png");
	
	/* ore loot */
	public static  BufferedImage plutoniumore = loadTexture("res/textures/ores/plutonium.png"),
			goldore = loadTexture("res/textures/ores/gold.png");
	
	/* drills */
	public static BufferedImage basicDrill =  loadTexture("res/textures/drill.png");
	
	public static void preload() {
		
		playerIDLE = loadTexture("res/textures/creatures/player.png");
		
		playerAnim = new BufferedImage[2];
		BufferedImage spritesheet = loadTexture("res/textures/creatures/anim.png");		
		playerAnim[0] = spritesheet.getSubimage(0, 0, 30, 54);
		playerAnim[1] = spritesheet.getSubimage(30, 0, 30, 54);
		
		
		Assets.font_ka1 = loadFont("res/gui/fonts/ka1.ttf");
		
		bkg = new Background("res/backgrounds/sky", 8);
		int speed = 1;
		for(int i = 0; i < 8; i++)
			bkg.setSpeed(i, -i*i/50f * speed);
		
		loadCellTiles();
		loadOreTiles();
		
		int TILE_WIDTH = 16, height = 16;
		BufferedImage tileset = loadTexture("res/textures/breaking.png");		
		for(int i = 0; i < tilesetlength; i++) {
			breaking[i] = tileset.getSubimage(i * TILE_WIDTH, 0, TILE_WIDTH, height);
		}
	}
	

	private static Font loadFont(String path) {
		try {
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     Font font = Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(15f);
		     ge.registerFont(font);
		     return font;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void loadOreTiles() {
		BufferedImage tileset = loadTexture("res/tiles/ore_tileset.png");
		Assets.gold = loadFromTileset(tileset, 0, 0);
		Assets.plutonium = loadFromTileset(tileset, 1, 0);
	}

	private static void loadCellTiles() {
		BufferedImage tileset = loadTexture("res/tiles/tileset.png");
		
		for(int i = 0; i < tilesetlength; i++) {
			for(int j = 0; j < 4; j++) {
				grass[i][j] = Assets.rotate(loadFromTileset(tileset, i, 0), j * Math.PI/2);
				dirt[i][j] = Assets.rotate(loadFromTileset(tileset, i, 1), j * Math.PI/2);
				stone[i][j] = Assets.rotate(loadFromTileset(tileset, i, 2), j * Math.PI/2);
				obsidian[i][j] = Assets.rotate(loadFromTileset(tileset, i, 3), j * Math.PI/2);
				gravel[i][j] = Assets.rotate(loadFromTileset(tileset, i, 4), j * Math.PI/2);
				granite[i][j] = Assets.rotate(loadFromTileset(tileset, i, 5), j * Math.PI/2);
				marble[i][j] = Assets.rotate(loadFromTileset(tileset, i, 6), j * Math.PI/2);
			}
		}
	}
	
	private static BufferedImage loadFromTileset(BufferedImage tileset, int x, int y) {
		return tileset.getSubimage(x * TILE_WIDTH, y * TILE_HEIGHT, TILE_WIDTH, TILE_HEIGHT);
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
