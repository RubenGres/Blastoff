package terrain;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import gfx.Assets;
import main.Handler;
import map.GameWorld;
import terrain.ore.GoldCell;
import terrain.ore.OreCell;
import terrain.ore.PlutoniumCell;

public abstract class Cell {

	// STATIC
	public static Cell[] cells = new Cell[256];

	// solid
	public static Cell empty = new EmptyCell(0);
	public static Cell grass = new GrassCell(1);
	public static Cell dirt = new DirtCell(2);
	public static Cell gravel = new GravelCell(3);
	public static Cell marble = new MarbleCell(4);
	public static Cell granite = new GraniteCell(5);
	public static Cell stone = new StoneCell(6);
	public static Cell obsidian = new ObsidianCell(7);

	// ore
	public static OreCell gold = new GoldCell(30);
	public static OreCell plutonium = new PlutoniumCell(31);


	public static final int CELLWIDTH = 45, CELLHEIGHT = 45;

	// CLASS
	protected final int id;
	
	//breaking
	protected int resistance = 10;
	
	protected BufferedImage[][] tiles;
	
	public Cell(BufferedImage[][] tiles, int id) {
		this.id = id;
		cells[id] = this;
		this.tiles = tiles;
	}

	public static BufferedImage getTileImage(BufferedImage[][] tiles, int tileid) {
		if(tiles == null)
			return null;
		
		if(tiles == Assets.grass) {
			if(tileid == 0b1110) return tiles[6][0];
			if(tileid == 0b1011) return tiles[5][0];
		}
		
		if(tileid == 0b0000) return tiles[0][0];		
		if(tileid == 0b0001) return tiles[1][3];
		if(tileid == 0b0010) return tiles[1][2];
		if(tileid == 0b0011) return tiles[5][3];
		if(tileid == 0b0100) return tiles[1][1];
		if(tileid == 0b0101) return tiles[3][0];
		if(tileid == 0b0110) return tiles[5][2];
		if(tileid == 0b0111) return tiles[2][2];		
		if(tileid == 0b1000) return tiles[1][0];
		if(tileid == 0b1001) return tiles[5][0];
		if(tileid == 0b1010) return tiles[3][1];
		if(tileid == 0b1011) return tiles[2][3];
		if(tileid == 0b1100) return tiles[6][0];
		if(tileid == 0b1101) return tiles[2][0];
		if(tileid == 0b1110) return tiles[2][1];		
		if(tileid == 0b1111) return tiles[4][0];
		return null;
	}

	public static Cell getCellById(int id) {
		return cells[id];
	}

	// hitbox
	public boolean isSolid() {
		return true;
	}

	public int getId() {
		return id;
	}
	
	public float getViscuosity(){
		return 1;
	}

	public void render(Graphics g, int displayX, int displayY, int x, int y) {
		GameWorld world = Handler.getInstance().getWorld();
		
		int tileid = 0b0000;
		
		Boolean leftEmpty, rightEmpty, downEmpty, topEmpty;
		leftEmpty = world.getCell(x - 1, y) instanceof EmptyCell;
		rightEmpty = world.getCell(x + 1, y) instanceof EmptyCell;
		downEmpty = world.getCell(x, y + 1) instanceof EmptyCell;
		topEmpty = world.getCell(x, y - 1) instanceof EmptyCell;
		
		if(topEmpty) tileid += 0b1000;
		if(rightEmpty) tileid += 0b0100;
		if(downEmpty) tileid += 0b0010;
		if(leftEmpty) tileid += 0b0001;
				
		g.drawImage(Cell.getTileImage(this.tiles, tileid), displayX, displayY, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
		
		OreCell ore = world.getOreCell(x, y);
		if(ore != null)
			ore.render(g, displayX, displayY, x, y);
	}

	public int getResistance() {
		return this.resistance;
	}

}
