package map;

import java.util.ArrayList;
import java.util.List;

import terrain.Cell;
import terrain.liquid.LiquidCell;
import terrain.ore.OreCell;

public class Chunk {

	private Cell[][] cellMap;
	private OreCell[][] oreMap;
	
	//TODO make liquid it's own layer
	private LiquidCell[][] liquidMap;
	
	public static int WIDTH = 100, HEIGHT = 100;
	
	public Chunk() {
		this.cellMap = new Cell[WIDTH][HEIGHT];
		this.oreMap = new OreCell[WIDTH][HEIGHT];
		this.liquidMap = new LiquidCell[WIDTH][HEIGHT];
		
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				this.cellMap[i][j] = null;
				this.oreMap[i][j] = null;
				this.liquidMap[i][j] = null;
			}
		}
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


	public byte[] asBytes() {
		List<Byte> lc = new ArrayList<Byte>();
		List<Byte> lo = new ArrayList<Byte>();
		
		for(int i = 0; i < HEIGHT; i++) {
			for(int j = 0; j < WIDTH; j++) {
				lc.add(new Integer(this.cellMap[i][j].getId()).byteValue());
				
				if(oreMap[i][j] == null) {
					lo.add((byte) 0);
				} else {
					Byte id = new Integer(this.oreMap[i][j].getId()).byteValue();
					lo.add(id);
				}
			}			
		}
				
		lc.addAll(lo);
		byte[] bytes = new byte[lc.size()];
		int j=0;
		
		for(Byte b: lc)
		    bytes[j++] = b.byteValue();
		
		return bytes;
	}
	
}
