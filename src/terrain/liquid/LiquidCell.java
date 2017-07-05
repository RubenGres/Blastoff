package terrain.liquid;

import java.awt.Color;

import terrain.Cell;

public abstract class LiquidCell extends Cell{

	public LiquidCell(Color color, int id) {
		super(color, id);
	}
	
	public void tick(){
		//TODO
	}
	
	@Override
	public boolean isSolid(){
		return false;
	}
}
