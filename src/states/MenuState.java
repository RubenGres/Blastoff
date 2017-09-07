package states;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import main.Handler;

public class MenuState extends State{

	private static enum items{NEW, LOAD, SETTINGS, EXIT};
	
	public MenuState(Handler handler) {
		super(handler);
		init();
	}
	
	private void init(){
		String[] buttons = {"New game", "Load game", "Settings", "Exit"};
		Menu mainMenu = new Menu(buttons);
	}

	@Override
	public void tick() {
	}

	@Override
	public void render(Graphics g) {
		
	}
	
	private class Menu{
		
		Rectangle[] hitboxes;
		String[] names;
		
		public Menu(String[] buttons) {
			names = buttons;
			hitboxes = new Rectangle[buttons.length];
		}
		
		public int hoverButton() {
			return 0;
		}
	}

}
