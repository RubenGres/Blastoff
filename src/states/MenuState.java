package states;

import java.awt.Color;
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
	
	Menu mainMenu;
	
	public MenuState(Handler handler) {
		super(handler);
		init();
	}
	
	private void init(){
		String[] buttons = {"New game", "Load game", "Settings", "Exit"};
		mainMenu = new Menu(buttons);
	}

	@Override
	public void tick() {
		mainMenu.getInput();
		mainMenu.getChoice();
	}

	@Override
	public void render(Graphics g) {
		mainMenu.render(g);
	}
	
	private class Menu{
		private String[] names;
		private int selection = 0;
		
		private Color selected = Color.BLACK;
		private Color textColor = Color.GRAY;
		
		public Menu(String[] buttons) {
			names = buttons;
		}
		
		public void render(Graphics g) {
			for(int i=0; i < names.length; i++){
				g.setColor(textColor);
				if(selection == i)
					g.setColor(selected);
				g.drawString(names[i], 0, 30*i + 100);
			}
		}

		public void getInput(){
			if(handler.getGame().getKeyManager().down){
				this.changeSelection(1);
			}else if(handler.getGame().getKeyManager().up){
				this.changeSelection(-1);
			}
			
			if(handler.getGame().getKeyManager().select)
				System.out.println("Enter");
		}
		
		public void changeSelection(int deplacement){
			if(selection + deplacement < names.length && selection + deplacement >= 0)
				selection += deplacement;
		}
		
		public int getChoice() {
			System.out.println(names[selection]);
			return selection;
		}
	}

}
