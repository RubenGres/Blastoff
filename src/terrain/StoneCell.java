package terrain;
import gfx.Assets;

public class StoneCell extends Cell{

	public StoneCell(int id) {
		super(Assets.stone, id);
		this.resistance = 50;
	}
}