package terrain;
import java.awt.Color;

public class Cell {
	
	//STATIC
	public static Cell[] cells = new Cell[256];
	public static Cell stoneCell = new StoneCell(1);
			
	//CLASS
	protected Color color;
	protected final int id;
	
	public Cell(Color color, int id){
		this.color = color;
		this.id = id;
		
		cells[id] = this;
	}
	
	// hitbox
	public boolean isSolid(){
		return false;
	}
	
	public int getId(){
		return id;
	}
	
}
