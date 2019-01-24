package terrain.liquid;
import java.awt.Color;

public class LavaCell extends LiquidCell{

	public float lavaDamage=0.2f;
	
	public LavaCell() {
		super(Color.red, 20, 0.2f);
	}

}
