package gfx;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import org.lwjgl.util.Rectangle;

import entity.creature.Player;
import entity.creature.UserPlayer;
import main.Handler;
import physics.Point;
import terrain.Cell;
import utils.FrameTimer;
import utils.FrameTimerManager;

public class GameInterface {
	
	private static final int BARHEIGHT = 115;
	private static final int BARWIDTH = 40;
	
	public static void render(Graphics g) {
		renderHalo(g, 3000);
		
		g.setColor(Color.GRAY);
		g.fillRect(5, 5, 250, 125);
		renderGuiBars(g, 10, 10);
		renderMoney(g, 150, 30);
	}
	
	private static void renderMoney(Graphics g, int x, int y) {
		Handler handler = Handler.getInstance();
		UserPlayer player = handler.getGame().getEntityManager().getUserPlayer();
		
		g.setFont(Assets.font_ka1);
		g.setColor(Color.WHITE);
		g.fillRect(x, y - 13, 100, 17);
		g.setColor(Color.BLACK);
		g.drawString(player.getMoney() + " G", x, y);
	}

	public static void showBreakingCell(Graphics g) {
		Handler handler = Handler.getInstance();
		FrameTimerManager ftm = handler.getGame().getGameState().getFrameTimerManager();
		UserPlayer player = handler.getGame().getEntityManager().getUserPlayer();
		
		FrameTimer breakTimer = ftm.getFrameTimer(FrameTimerManager.timer.BREAKING);
		if(breakTimer != null) {
			float pct = (float) (breakTimer.getElapsedTime() * 1.0 / breakTimer.getDuration());
			int nAnim = (int) (10 * pct);
			
			int x = (int) player.getBreaking().getX();
			int y = (int) player.getBreaking().getY();
			int dispX = (int) (x*Cell.CELLWIDTH - handler.getGame().getGameCamera().getxOffset());
			int dispY = (int) (y*Cell.CELLHEIGHT - handler.getGame().getGameCamera().getyOffset());
			g.drawImage(Assets.breaking[nAnim], dispX, dispY, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
		}
	}

	public static void showFocusedCell(Graphics g) {
		Handler handler = Handler.getInstance();
		UserPlayer player = handler.getGame().getEntityManager().getUserPlayer();
		
		Point fCell = player.getFocused();
		if(player.getFocused() != null) {
			int x = (int) fCell.getX();
			int y = (int) fCell.getY();
			int dispX = (int) (x*Cell.CELLWIDTH - handler.getGame().getGameCamera().getxOffset());
			int dispY = (int) (y*Cell.CELLHEIGHT - handler.getGame().getGameCamera().getyOffset());
			g.setColor(Color.WHITE);
			g.drawRect(dispX, dispY, Cell.CELLWIDTH, Cell.CELLHEIGHT);
		}
	}
	
	private static void renderHalo(Graphics g, int radius) {
		Handler handler = Handler.getInstance();
		int screenW = handler.getGame().getWidth();
		int screenH = handler.getGame().getHeight();
		
		Point pos = handler.getGame().getEntityManager().getUserPlayer().getPosition();
		int x = (int) (pos.getX() - handler.getGame().getGameCamera().getxOffset());
		int y = (int) (pos.getY() - handler.getGame().getGameCamera().getyOffset());
		g.drawImage(Assets.gradient, x - radius/2, y - radius/2, radius, radius, null);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, screenW, y - radius/2);
		g.fillRect(0, y + radius/2, screenW, screenH);
		g.fillRect(0, 0, x - radius/2, screenH);
		g.fillRect(x + radius/2, 0, screenW - (x + radius/2), screenH);
	}
	
	private static void renderGuiBars(Graphics g, int x, int y) {
		Handler handler = Handler.getInstance();
		UserPlayer player = handler.getGame().getEntityManager().getUserPlayer();
		
		int dx = x;
		renderBar(g, dx, y, "FUEL", Assets.fuelbar, player.getFuel(), player.getMaxFuel());
		dx += BARWIDTH + 5;
		renderBar(g, dx, y, "LIFE", Assets.healthbar, player.getHealth(), player.getMaxHealth());
		dx += BARWIDTH + 5;
		renderBar(g, dx, y, "CARGO", Color.CYAN, player.getCargoSize(), player.getMaxcargo());
	}
	
	private static void renderBar(Graphics g, int x, int y, String name, Color c, float current, float max){		
		g.drawImage(Assets.guibarbg, x, y, BARWIDTH, BARHEIGHT, null);
		
		g.setColor(c);
		g.fillRect(x, BARHEIGHT + y, BARWIDTH, -(int) ((current/max) * BARHEIGHT));
		
		g.drawImage(Assets.guibar, x, y, BARWIDTH, BARHEIGHT, null);
	}
	
	private static void renderBar(Graphics g, int x, int y, String name, BufferedImage i, float current, float max){		
		g.drawImage(Assets.guibarbg, x, y, BARWIDTH, BARHEIGHT, null);
		
		float pctFilled = (current/max);
		if(pctFilled > 0.02 && pctFilled <= 1) {
			int barH = (int) (pctFilled * BARHEIGHT);
			int yP = y + BARHEIGHT - barH;
			
			BufferedImage iCropped = i.getSubimage(0, (int) (i.getHeight()*(1 - pctFilled)), i.getWidth(), (int) (i.getHeight() * pctFilled));
			g.drawImage(iCropped, x, yP, BARWIDTH, barH, null);	
		}		
		
		g.drawImage(Assets.guibar, x, y, BARWIDTH, BARHEIGHT, null);
	}
}
