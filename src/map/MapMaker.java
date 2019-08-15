package map;

import org.j3d.texture.procedural.PerlinNoiseGenerator;

import terrain.Cell;
import terrain.liquid.LavaCell;
import terrain.ore.OreCell;

public class MapMaker {
	
	private int width, height;
	private GameWorld gameWorld;
	private int highestPoint; // for the transition to follow ground level
	
	public MapMaker(GameWorld gameWorld, int width, int height) {
		this.width = width;
		this.height = height;
		this.gameWorld = gameWorld;
	}

	// GENERATION
	public void generateMap() {
		int surfaceHeight = 0;
		
		Cavegen cv = initCv(width, height, surfaceHeight);		

		for (int y = surfaceHeight; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (cv.getCellmap()[x][y - surfaceHeight]) {
					this.gameWorld.setCell(x, y, Cell.obsidian);
				} else {
					this.gameWorld.setCell(x, y, Cell.empty);
				}
			}
		}
		
		addSurfaceLayer(Cell.grass);

		highestPoint = getHighestPoint(Cell.grass);
		
		Cavegen goldGen = new Cavegen(width, height, 0.17f, 4, 1, 2);
		Cavegen plutGen = new Cavegen(100, 100, 0.10f, 2, 5, 6);
		
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				if(this.gameWorld.getCell(i, j) != Cell.empty) {
					if(goldGen.getCellmap()[i][j])
						this.gameWorld.setOreCell(i, j, Cell.gold);
					if(plutGen.getCellmap()[i][j])
						this.gameWorld.setOreCell(i, j, Cell.plutonium);
				}
			}
		}
		
		// Adding layers
		addLayer(0, height - 7, Cell.obsidian, 5);
		addLayer(0, height - 20, Cell.gravel, 5);
		addLayer(0, height - 40, Cell.marble, 5);
		addLayer(0, height - 60, Cell.stone, 5);
		addLayer(0, highestPoint + 15, Cell.dirt, 10);

		addLavaToBottom(height - 10);
	
	}

	private Cavegen initCv(int width, int height, int surfaceHeight) {
		float percentFilled = 0.4f; // Percentage of filled cell to start with
		int birth = 3; // Lives if more than x neighbors
		int death = 2; // Dies if less than x neighbors
		int iteration = 3; // Number of iterations
		Cavegen cv = new Cavegen(width, height - surfaceHeight, percentFilled, birth, death, iteration);
		cv.show();
		return cv;
	}

	private void addSurfaceLayer(Cell cell) {
		int amplitude = 15;
		int zoom = 7;
		PerlinNoiseGenerator pnl = new PerlinNoiseGenerator(991283);
		
		for (int x = 0; x < width; x++) {
			float noise = pnl.noise1(((float) x) / zoom);
			int y = (int) (((noise + 1) / 2) * amplitude);
			if(this.gameWorld.getCell(x,y) != Cell.empty)
				this.gameWorld.setCell(x, y, cell);
			
			for(int j = y-1; j >= 0; j--)
				this.gameWorld.setCell(x, j, Cell.empty);
		}
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

	private void addLavaToBottom(int yStart) {
		for (int y = yStart; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (this.gameWorld.getCell(x, y) == Cell.empty)
					new LavaCell(x,y);
			}			
		}
	}

	private void transition(int yStart, int yEnd, int x, Cell cell) {
		int size = yEnd - yStart;
		float[] progressionPercentage = new float[size];
		for (int i = 0; i < size; i++) {
			progressionPercentage[i] = 1.6f / i;
		}

		for (int y = yStart; y < yStart + size; y++) {
			if (this.gameWorld.getCell(x, y) != Cell.empty & this.gameWorld.getCell(x, y) != Cell.grass) {
				if (Math.random() < progressionPercentage[y - yStart]) {
					this.gameWorld.setCell(x, y, cell);					
				}
			}
		}
	}

	/**
	 * Fills a layer with a particular cell and a transitions to the next layer at
	 * the bottom
	 * 
	 * @param y1             Smaller height value of the layer
	 * @param y2             Biggest height value of the layer
	 * @param cell           Cell to fill the layer with
	 * @param transitionSize Number of lines the transitions will take (included in
	 *                       the layer)
	 */
	private void addLayer(int y1, int y2, Cell cell, int transitionSize) {
		for (int x = 0; x < width; x++) {
			int yOffset = getFirstCellOccurence(x, Cell.grass) - highestPoint;

			int yMaxLoop = y2 - transitionSize + yOffset;

			for (int y = y1 + yOffset; y < yMaxLoop; y++) {
				if (y < height) {
					Cell ccell = this.gameWorld.getCell(x, y);
					if (ccell != Cell.empty && ccell != Cell.grass) {						
							this.gameWorld.setCell(x, y,cell);
					}
				}
			}

			int yTransitionEnd = Math.min(height, y2 + yOffset);
			int yTransitionStart = Math.min(yTransitionEnd, y2 - transitionSize + yOffset);
			transition(yTransitionStart, yTransitionEnd, x, cell);
		}

		// addOreLayer(y1, y2, oreCell);

	}

	private int getFirstCellOccurence(int x, Cell cell) {
		int y = 0;
		while (this.gameWorld.getCell(x, y) != cell) {
			y++;
			if (y == height)
				return 0;
		}
		return y;
	}

}
