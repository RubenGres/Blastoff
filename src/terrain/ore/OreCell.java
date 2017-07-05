package terrain.ore;

import java.awt.Color;
import terrain.Cell;

public class OreCell extends Cell{

	private float value, weight;
	
	public OreCell(Color color, int id, float value, float weight) {
		super(color, id);
		this.value = value;
		this.weight = weight;
	}

	public float getValue() {
		return value;
	}

	public float getWeight() {
		return weight;
	}
}
