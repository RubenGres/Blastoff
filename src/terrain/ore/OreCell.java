package terrain.ore;

import java.awt.Color;

import terrain.Cell;

public abstract class OreCell extends Cell{

	private int weight;
	private int value;
	
	public OreCell(Color color, int id, int weight, int value) {
		super(color, id);
		this.weight = weight;
		this.value = value;
	}

	public int getWeight() {
		return weight;
	}

	public int getValue() {
		return value;
	}

	
}
