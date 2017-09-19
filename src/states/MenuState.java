package states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

import org.lwjgl.input.Keyboard;

import main.Handler;

public class MenuState extends State {

	Menu mainMenu;

	public MenuState(Handler handler) {
		super(handler);
		init();
	}

	private void init() {
		String[] buttons = { "New game", "Load game", "Settings", "Exit" };
		mainMenu = new Menu(buttons);
	}

	@Override
	public void tick() {		
		
		super.tick();
		
		mainMenu.getChoice();
		
		if (mainMenu.isSelecting()) {
			switch (mainMenu.getChoice()) {
			case 0: //new game
				State.setState(handler.getGame().gameState);
				break;
			case 1: //load game
				break;
			case 2: //settings
				break;
			case 3: //exit
				System.exit(0);
				break;
			}
		}
		
		mainMenu.tick();
	}

	@Override
	public void render(Graphics g) {
		mainMenu.render(g);
	}

	private class Menu {
		private String[] name;
		private Rectangle[] hitbox;
		private int selection = 0;
		private boolean selecting;

		private Color selected = Color.BLACK;
		private Color textColor = Color.GRAY;

		public Menu(String[] buttons) {
			name = buttons;
			hitbox = new Rectangle[buttons.length];
		}

		public void tick() {
			selecting = false;
			getInput();
		}

		public void render(Graphics g) {
			for (int i = 0; i < name.length; i++) {

				g.setFont(new Font("Arial", 0, 30));
				g.setColor(textColor);
				if (selection == i)
					g.setColor(selected);

				int w = (int) g.getFontMetrics().getStringBounds(name[i], g).getWidth();
				int h = g.getFontMetrics().getHeight();
				int x = (handler.getGame().getWidth() - w) / 2; // Center horizontally
				int y = (h + 5) * i + 100;

				hitbox[i] = new Rectangle(x, y, w, h);
				g.drawString(name[i], x, y);
			}
		}

		public void getInput() {
			int selection = 0;

			if (handler.getGame().getKeyManager().down) {
				selection = 1;
			} else if (handler.getGame().getKeyManager().up) {
				selection = -1;
			}

			this.changeSelection(selection);

			selecting = handler.getGame().getKeyManager().select;
		}

		public void changeSelection(int deplacement) {
			if (selection + deplacement < name.length && selection + deplacement >= 0)
				selection += deplacement;
		}

		public int getChoice() {
			return selection;
		}

		public boolean isSelecting() {
			return selecting;
		}
	}

}
