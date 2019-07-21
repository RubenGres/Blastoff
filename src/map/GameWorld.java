package map;

import java.awt.Graphics;

import main.Handler;
import states.State;
import terrain.Cell;
import terrain.liquid.LavaCell;
import terrain.liquid.LiquidCell;
import utils.FrameTimerManager;

public class GameWorld {

	public int height, width;
	private Cell[][] map;
	private Handler handler;

	public GameWorld(int width, int height) {
		this.height = height;
		this.width = width;
		this.handler = Handler.getInstance();
		this.map = new Cell[width][height];
	}
	
	public void init() {		
		MapMaker mapmaker = new MapMaker(this, width, height);
		mapmaker.generateMap();
		
		State.getState().getFrameTimerManager().add(FrameTimerManager.timer.LAVA, true, 30);
	}

	public void tick() {		
		if (!State.getState().getFrameTimerManager().getFrameTimer(FrameTimerManager.timer.LAVA).isRunning()) {
			flowLiquids();
			State.getState().getFrameTimerManager().getFrameTimer(FrameTimerManager.timer.LAVA).restart();
		} 
	}

	public void flowLiquids() {
		// flow x axis
		for (int y = height-1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				if(getCell(x,y) instanceof LiquidCell) {
					((LiquidCell) getCell(x, y)).flow();
				}
			}
		}
	}
	

	public void breakCell(int x, int y) {
		if (!(this.getCell(x, y) instanceof LiquidCell))
			map[x][y] = Cell.emptyCell;
	}

	public void render(Graphics g) {
		int xStart = (int) Math.max(0, handler.getGame().getGameCamera().getxOffset() / Cell.CELLWIDTH);
		int xEnd = (int) Math.min(width,
				(handler.getGame().getGameCamera().getxOffset() + handler.getGame().getWidth()) / Cell.CELLWIDTH + 1);
		int yStart = (int) Math.max(0, handler.getGame().getGameCamera().getyOffset() / Cell.CELLHEIGHT);
		int yEnd = (int) Math.min(height,
				(handler.getGame().getGameCamera().getyOffset() + handler.getGame().getHeight()) / Cell.CELLHEIGHT + 1);

		for (int y = yStart; y < yEnd; y++) {
			for (int x = xStart; x < xEnd; x++) {
				this.map[x][y].render(g, (int) (x * Cell.CELLWIDTH - handler.getGame().getGameCamera().getxOffset()),
						(int) (y * Cell.CELLHEIGHT - handler.getGame().getGameCamera().getyOffset()), x, y);
			}
		}
	}

	public Cell getCell(int x, int y) {
		try {
			return this.map[x][y];
		} catch (Exception e) {
			//e.printStackTrace();
			return Cell.emptyCell;
		}
	}
	
	public void setCell(int x, int y, Cell cell) {
		try {
			map[x][y] = cell;
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	// GETTERS AND SETTERS

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

}
