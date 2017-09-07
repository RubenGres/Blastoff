package terrain.liquid;
import java.awt.Color;

public class LavaCell extends LiquidCell{

	public float lavaDamage=0.2f;
	
	public LavaCell(int id, float viscuosity) {
		super(Color.red, id, viscuosity);
		// TODO Auto-generated constructor stub
	}

}
