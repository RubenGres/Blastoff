package terrain;

import java.awt.Color;
import java.awt.Graphics;

import main.Handler;
import terrain.liquid.*;
import terrain.ore.GoldCell;

public abstract class Cell {

	// STATIC
	public static Cell[] cells = new Cell[256];

	// solid
	public static Cell emptyCell = new EmptyCell(0);
	public static Cell dirtCell = new DirtCell(1);
	public static Cell gravelCell = new GravelCell(2);
	public static Cell cobbleCell = new CobbleCell(3);
	public static Cell stoneCell = new StoneCell(4);
	public static Cell bedrockCell = new BedrockCell(5);
	public static Cell sandCell = new SandCell(6);
	public static Cell grassCell = new GrassCell(7);

	// liquid
	public static Cell waterCell = new WaterCell(21, 0.8f);

	// ore
	public static Cell goldCell = new GoldCell(30);

	public static final int CELLWIDTH = 40, CELLHEIGHT = 40;

	// CLASS
	protected Color color;
	protected final int id;

	public Cell(Color color, int id) {
		this.color = color;
		this.id = id;
		cells[id] = this;
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

	public void render(Graphics g, int x, int y) {
		g.setColor(this.color);
		g.fillRect(x, y, CELLWIDTH, CELLHEIGHT);
	}
	
	public void breakCell(int x, int y, Handler handler) {
		handler.getWorld().breakCell(x / Cell.CELLWIDTH, y / Cell.CELLHEIGHT);
	}

}
