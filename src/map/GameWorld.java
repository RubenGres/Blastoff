package map;

import java.awt.Graphics;

import main.Handler;
import states.State;
import terrain.Cell;
import terrain.liquid.LavaCell;
import terrain.liquid.LiquidCell;
import terrain.ore.OreCell;
import utils.FrameTimerManager;

public class GameWorld {
	
	private Chunk chunk;
	private int height = Chunk.HEIGHT, width = Chunk.WIDTH;
	private Handler handler;

	public GameWorld() {
		this.handler = Handler.getInstance();
		this.chunk = new Chunk();
	}
	
	public void init() {		
		MapMaker mapmaker = new MapMaker(this, Chunk.WIDTH, Chunk.HEIGHT);
		mapmaker.generateMap();
		
		FrameTimerManager ftm = this.handler.getGame().getGameState().getFrameTimerManager();
		ftm.add(FrameTimerManager.timer.LAVA, true, 30);
	}

	public void tick() {
		FrameTimerManager ftm = this.handler.getGame().getGameState().getFrameTimerManager();
		if (!ftm.getFrameTimer(FrameTimerManager.timer.LAVA).isRunning()) {
			flowLiquids();
			ftm.getFrameTimer(FrameTimerManager.timer.LAVA).restart();
		} 
	}

	public void flowLiquids() {
		// flow x axis
		for (int y = height-1; y >= 0; y--) {
			for (int x = 0; x < width; x++) {
				if(this.chunk.getCell(x,y) instanceof LiquidCell) {
					((LiquidCell) this.chunk.getCell(x, y)).flow();
				}
			}
		}
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
				this.chunk.getCell(x, y).render(g, (int) (x * Cell.CELLWIDTH - handler.getGame().getGameCamera().getxOffset()),
						(int) (y * Cell.CELLHEIGHT - handler.getGame().getGameCamera().getyOffset()), x, y);
			}
		}
	}
	
	public byte[] asBytes() {
		return chunk.asBytes();
	}


	// GETTERS AND SETTERS

	public Cell getCell(int x, int y) {
		return this.chunk.getCell(x, y);
	}
	
	public void setCell(int x, int y, Cell cell) {
		this.chunk.setCell(x, y, cell);
	}
	
	public void setOreCell(int x, int y, OreCell cell) {
		this.chunk.setOreCell(x, y, cell);
	}
	
	public OreCell getOreCell(int x, int y) {
		return this.chunk.getOreCell(x, y);
	}
	
	public void breakCell(int x, int y) {
		this.chunk.breakCell(x, y);
	}
	
	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

}
