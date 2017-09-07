package states;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Handler;

public class MenuState extends State{
	
	private enum items{NewGame, LoadGame, Settings, Exit};
	List<menuItem> menuItems = new ArrayList<menuItem>();

	public MenuState(Handler handler) {
		super(handler);
		init();
	}


	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		int width = 500;
		int height = 40;
		int spaceUnder = 50;
		
		for(int i = 0; i < menuItemNames.size(); i++)
			g.fillRect((handler.getGame().getWidth() - width)/2,
					spaceUnder * i + (handler.getGame().getHeight() - menuItemNames.size()*(height + spaceUnder))/2,
					width,
					height);
	}
	
	private class menuItem{
		
		private Rectangle2D bounds;
		private int x, y;
		Boolean isSelected = false;
		
		private menuItem(String title, int x, int y, Graphics g, int position){
			this.bounds = g.getFontMetrics().getStringBounds(title, g);
			this.x = x;
			this.y = y;
		}
		
	}

}
