package terrain.ore;

import java.awt.Color;

import entity.pickable.GoldOre;
import main.Handler;
import terrain.Cell;

public class GoldCell extends Cell {

	public GoldCell(int id) {
		super(Color.YELLOW, id);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void breakCell(int x, int y, Handler handler) {
		handler.getGame().getEntityManager().addEntity(new GoldOre(handler, x, y));
		super.breakCell(y, y, handler);
	}

}
