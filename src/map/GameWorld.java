package map;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.j3d.texture.procedural.PerlinNoiseGenerator;

import entity.EntityManager;
import entity.creature.Player;
import entity.pickable.FuelTank;
import main.Handler;
import states.State;
import terrain.Cell;
import terrain.liquid.LiquidCell;
import utils.Couple;

public class GameWorld {

	public int height, width;
	private Cell[][] map;
	private int surfaceHeight = 0;
	private Handler handler;
	private int highestPoint;

	public GameWorld(int width, int height, Handler handler) {
		this.height = height;
		this.width = width;
		this.handler = handler;
		Cavegen cv = this.initCv();
		this.generateMap(cv);
	}

	public void setCell(int x, int y, Cell cell) {
		try {
			map[x][y] = cell;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void tick() {
		if(State.getState().getFrameTimerManager().getFrameTimer("Lava") == null){
			State.getState().getFrameTimerManager().add("Lava", true, 30);
		}

		if(!State.getState().getFrameTimerManager().getFrameTimer("Lava").isRunning()){
			flowLiquids();
			State.getState().getFrameTimerManager().getFrameTimer("Lava").restart();
		}
	}

	public void flowLiquids() {
		
		//flow x axis
		for (int y = 0; y < height; y++)
			for (int x = 0; x < width; x++)
				if (this.getCell(x, y) instanceof LiquidCell) {
					LiquidCell tmp = (LiquidCell) getCell(x, y);
					x = flowCell(x, y, tmp);
				}
		
		//flow y axis
	}

	private int flowCell(int x, int y, LiquidCell currentCell) {

		Cell downCell = this.getCell(x, y + 1);
		Cell leftCell = getCell(x - 1, y);
		Cell rightCell = getCell(x + 1, y);

		int flowX = 0;
		int flowY = 0;

		// flow down
		if (downCell.equals(Cell.emptyCell)) {
			flowY = 1;
		} else { // flow left and right

			if (leftCell.equals(Cell.emptyCell) && rightCell.equals(Cell.emptyCell)) { // random
																						// if
																						// both
																						// possible
				flowX = (int) (Math.random() * 2);
				if (flowX == 0)
					flowX = -1;

			} else if (leftCell.equals(Cell.emptyCell)) {
				flowX = -1;

			} else if (rightCell.equals(Cell.emptyCell)) {
				flowX = 1;
			}

		}

		this.setCell(x, y, Cell.emptyCell);
		this.setCell(x + flowX, y + flowY, currentCell);

		if (flowX == 1)
			x++;

		return x;
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
						(int) (y * Cell.CELLHEIGHT - handler.getGame().getGameCamera().getyOffset()));
			}
		}
	}

	public Cell getCell(int x, int y) {
		try {
			return this.map[x][y];
		} catch (Exception e) {
			return Cell.emptyCell;
		}
	}

	// GENERATION

	private Cavegen initCv() {
		float percentFilled = 0.47f; // Percentage of filled cell
		int birth = 3; // Lives if more than x neighbors
		int death = 2; // Dies if less than x neighbors
		int iteration = 2; // Number of iterations
		return new Cavegen(width, height - surfaceHeight, percentFilled, birth, death, iteration);
	}

	private void generateMap(Cavegen cv) {

		this.map = new Cell[width][height];

		for (int y = surfaceHeight; y < this.height; y++)
			for (int x = 0; x < this.width; x++) {
				if (cv.getCellmap()[x][y - surfaceHeight])
					this.map[x][y] = Cell.bedrockCell;
				else
					this.map[x][y] = Cell.emptyCell;
			}

		addSurfaceLayer(Cell.grassCell);

		this.highestPoint = this.getHighestPoint(Cell.grassCell);

		// Adding layers
		addLayer(0, height - 7, Cell.stoneCell, 5);
		addLayer(0, height - 20, Cell.cobbleCell, 5);
		addLayer(0, height - 40, Cell.gravelCell, 5);
		addLayer(0, height - 60, Cell.dirtCell, 5);
		addLayer(0, this.highestPoint + 15, Cell.sandCell, 10);

		addLavaToBottom(15);
		addBedrock(); // just to be sure

		cleanSurfaceLayer(Cell.grassCell);

		cv = null;
	}

	private int getHighestPoint(Cell c) {
		int min = 0;
		for (int i = 0; i < width; i++) {
			int height = getFirstCellOccurence(i, c);
			if (height < min)
				min = height;
		}
		return min;
	}

	private void addSurfaceLayer(Cell cell) {
		int amplitude = 15;
		int zoom = 9;
		PerlinNoiseGenerator pnl = new PerlinNoiseGenerator(991283);

		for (int x = 0; x < width; x++) {
			float noise = pnl.noise1(((float) x) / zoom);
			int y = (int) (((noise + 1) / 2) * amplitude);
			this.map[x][y + this.surfaceHeight] = cell;
		}
	}

	private void cleanSurfaceLayer(Cell surfaceCell) {
		for (int x = 0; x < width; x++) {

			int y = 0;
			// get height of the surfaceLayer on x
			y = this.getFirstCellOccurence(x, surfaceCell);

			// Clear top cells
			int a = 1;
			while (y - a >= 0) {
				map[x][y - a] = Cell.emptyCell;
				a++;
			}

			// Fill bottom cell with sand
			if (map[x][y + 1] == Cell.emptyCell)
				map[x][y + 1] = Cell.sandCell;

		}
	}

	private void addLavaToBottom(int yStart) {
		for (int y = yStart; y < height; y++)
			for (int x = 0; x < width; x++)
				if (this.map[x][y] == Cell.emptyCell)
					this.map[x][y] = Cell.lavaCell;
	}

	private void addBedrock() {
		for (int x = 0; x < this.width; x++)
			this.map[x][height - 1] = Cell.bedrockCell;
	}

	private void transition(int yStart, int yEnd, int x, Cell cell) {
		int size = yEnd - yStart;
		float[] progressionPercentage = new float[size];
		for (int i = 0; i < size; i++) {
			progressionPercentage[i] = 1.6f / i;
		}

		for (int y = yStart; y < yStart + size; y++)
			if (this.map[x][y] != Cell.emptyCell & this.map[x][y] != Cell.grassCell)
				if (Math.random() < progressionPercentage[y - yStart])
					this.map[x][y] = cell;
	}

	/**
	 * Fills a layer with a particular cell and a transitions to the next layer
	 * at the bottom
	 * 
	 * @param y1
	 *            Smaller height value of the layer
	 * @param y2
	 *            Biggest height value of the layer
	 * @param cell
	 *            Cell to fill the layer with
	 * @param transitionSize
	 *            Number of lines the transitions will take (included in the
	 *            layer)
	 */
	private void addLayer(int y1, int y2, Cell cell, int transitionSize) {

		for (int x = 0; x < this.width; x++) {
			int yOffset = getFirstCellOccurence(x, Cell.grassCell) - this.highestPoint;
			int yMaxLoop;

			yMaxLoop = y2 - transitionSize + yOffset;

			for (int y = y1 + yOffset; y < yMaxLoop; y++) {
				if (y < height)
					if (this.map[x][y] != Cell.emptyCell & this.map[x][y] != Cell.grassCell)
						this.map[x][y] = cell;
			}

			int yTransitionEnd = Math.min(height, y2 + yOffset);
			int yTransitionStart = Math.min(yTransitionEnd, y2 - transitionSize + yOffset);
			transition(yTransitionStart, yTransitionEnd, x, cell);
		}

	}

	private int getFirstCellOccurence(int x, Cell cell) {
		int y = 0;
		while (map[x][y] != cell) {
			y++;
			if (y == height)
				return 0;
		}
		return y;
	}
	
	//GETTERS AND SETTERS

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

}
