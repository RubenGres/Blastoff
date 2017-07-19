package terrain.liquid;

import java.awt.Color;

import terrain.Cell;

public abstract class LiquidCell extends Cell{
	
	public float viscuosity;

	public LiquidCell(Color color, int id, float viscuosity) {
		super(color, id);
		this.viscuosity = viscuosity;
	}
	
	public void tick(){
		//TODO
	}
	
	@Override
	public boolean isSolid(){
		return false;
	}
	
	@Override
	public float getViscuosity() {
		return viscuosity;
	}
}
