package map;

import terrain.Cell;
import terrain.liquid.LiquidCell;
import terrain.ore.OreCell;

public class Chunk {

	private Cell[][] cellMap;
	private OreCell[][] oreMap;
	private LiquidCell[][] liquidMap;
	public static int WIDTH = 100, HEIGHT = 100;
	
	public Chunk() {
		this.cellMap = new Cell[WIDTH][HEIGHT];
		this.oreMap = new OreCell[WIDTH][HEIGHT];
		this.liquidMap = new LiquidCell[WIDTH][HEIGHT];
	}
	
	public void init() {
		
	}
	
	public void breakCell(int x, int y) {
		if (!(this.getCell(x, y) instanceof LiquidCell)) {
			cellMap[x][y] = Cell.empty;
			oreMap[x][y] = null;
		}
	}	

	public Cell getCell(int x, int y) {
		try {
			return this.cellMap[x][y];
		} catch (Exception e) {
			//e.printStackTrace();
			return Cell.empty;
		}
	}
	
	public void setCell(int x, int y, Cell cell) {
		try {
			cellMap[x][y] = cell;
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public OreCell getOreCell(int x, int y) {
		return this.oreMap[x][y];
	}
	
	public void setOreCell(int x, int y, OreCell cell) {
		oreMap[x][y] = cell;
	}
	
}
