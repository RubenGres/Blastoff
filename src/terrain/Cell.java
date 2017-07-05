package terrain;
import java.awt.Color;
import java.awt.Graphics;
import terrain.liquid.*;
import terrain.ore.GoldCell;

public abstract class Cell {
	
	//STATIC
	public static Cell[] cells = new Cell[256];

	//solid
	public static Cell emptyCell = new EmptyCell(0);
	public static Cell dirtCell = new DirtCell(1);
	public static Cell gravelCell = new GravelCell(2);
	public static Cell cobbleCell = new CobbleCell(3);
	public static Cell stoneCell = new StoneCell(4);
	public static Cell bedrockCell = new BedrockCell(5);
	public static Cell sandCell = new SandCell(6);
	
	//liquid
	public static Cell lavaCell = new LavaCell(20);
	
	//ore
	public static Cell goldCell = new GoldCell(30);
	
	public static final int CELLWIDTH = 4, CELLHEIGHT = 4;
	
	//CLASS
	protected Color color;
	protected final int id;
	
	/***
	 * Constructor
	 * @param color
	 * @param id
	 */
	public Cell(Color color, int id){
		this.color = color;
		this.id = id;
		cells[id] = this;
	}
	
	public static Cell getCellById(int id){
		return cells[id];
	}
	
	// hitbox
	public boolean isSolid(){
		return true;
	}
	
	public int getId(){
		return id;
	}
	
	public void render(Graphics g, int x, int y){
		g.setColor(this.color);
		g.fillRect(x, y, CELLWIDTH, CELLHEIGHT);
	}
	
}
