package terrain;

import java.awt.Color;
import java.awt.Graphics;

import gfx.Assets;

public class StoneCell extends Cell{

	public StoneCell(int id) {
		super(Assets.stone, id);
	}
}