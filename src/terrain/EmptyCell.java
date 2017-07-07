package terrain;

import java.awt.Color;

public class EmptyCell extends Cell{

	public EmptyCell(int id) {
		super(new Color(0,0,0,0), id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid(){
		return false;
	}
}
