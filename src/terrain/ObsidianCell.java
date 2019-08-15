package terrain;
import gfx.Assets;

public class ObsidianCell extends Cell {

	public ObsidianCell(int id) {
		super(Assets.obsidian, id);
		this.resistance = 90;
	}
}
