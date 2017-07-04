package terrain;
import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	
	//STATIC
	public static Cell[] cells = new Cell[256];

	public static Cell emptyCell = new EmptyCell(0);
	public static Cell stoneCell = new StoneCell(1);
	public static Cell bedrockCell = new BedrockCell(2);
	
	public static final int CELLWIDTH = 4, CELLHEIGHT = 4;
	
	//CLASS
	protected Color color;
	protected final int id;
	
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
		return false;
	}
	
	public int getId(){
		return id;
	}
	
	public void render(Graphics g, int x, int y){
		g.setColor(this.color);
		g.fillRect(x, y, CELLWIDTH, CELLHEIGHT);
	}
	
}
