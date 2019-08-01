package gfx;

import java.awt.Color;
import java.awt.Graphics;

import entity.creature.Player;
import main.Handler;
import physics.Point;
import terrain.Cell;
import utils.FrameTimer;
import utils.FrameTimerManager;

public class GameInterface {
	
	public static void render(Graphics g) {
		showBreakingCell(g);
		renderHalo(g, 3000);
		
		renderGuiBars(g, 10, 10);
	}
	
	private static void showBreakingCell(Graphics g) {
		Handler handler = Handler.getInstance();
		FrameTimerManager ftm = handler.getGame().getGameState().getFrameTimerManager();
		Player player = handler.getGame().getEntityManager().getPlayer();
		
		FrameTimer breakTimer = ftm.getFrameTimer(FrameTimerManager.timer.BREAKING);
		if(breakTimer != null) {
			float pct = (float) (breakTimer.getElapsedTime() * 1.0 / breakTimer.getDuration());
			int nAnim = (int) (10 * pct);
			
			int x = (int) player.getBreaking().getX();
			int y = (int) player.getBreaking().getY();
			int dispX = (int) (x*Cell.CELLWIDTH - handler.getGame().getGameCamera().getxOffset());
			int dispY = (int) (y*Cell.CELLHEIGHT - handler.getGame().getGameCamera().getyOffset());
			g.drawImage(Assets.breaking[nAnim], dispX, dispY, Cell.CELLWIDTH, Cell.CELLHEIGHT, null);
		};
	}
	
	private static void renderHalo(Graphics g, int radius) {
		Handler handler = Handler.getInstance();
		int screenW = handler.getGame().getWidth();
		int screenH = handler.getGame().getHeight();
		
		Point pos = handler.getGame().getEntityManager().getPlayer().getPosition();
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
		Player player = handler.getGame().getEntityManager().getPlayer();
		
		renderBar(g, x, y, "FUEL", Color.GREEN, player.getJetpackFuel(), player.getJetpackMaxFuel());
		renderBar(g, x + 60, y, "LIFE", Color.RED, player.getHealth(), player.getMaxHealth());
		renderBar(g, x + 120, y, "CARGO", Color.CYAN, player.getCargo(), player.getMaxcargo());
	}
	
	private static void renderBar(Graphics g, int x, int y, String name, Color c, float current, float max){
		int height = 150;
		int width = 50;
		
		g.drawImage(Assets.guibarbg, x, y, width, height, null);
		
		g.setColor(c);
		g.fillRect(x, height + y, width, -(int) ((current/max) * height));
		
		g.drawImage(Assets.guibar, x, y, width, height, null);
		
		g.setColor(Color.BLACK);
		g.drawString(name, x + 10, y + 20);
	}

}
