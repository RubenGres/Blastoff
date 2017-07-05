package map;

import java.awt.Graphics;

import terrain.Cell;

public class Map {

	Cavegen cv;
	int height, width;
	int[][] map;
	int surfaceHeight = 5;

	public Map(int width, int height) {
		this.height = height;
		this.width = width;
		this.initCv();
		this.generateMap();
	}

	private void initCv() {
		float percentFilled = 0.47f; // Percentage of filled cell
		int birth = 3; // Lives if more than x neighbors
		int death = 2; // Dies if less than x neighbors
		int iteration = 2; // Number of iterations
		this.cv = new Cavegen(width, height - surfaceHeight, percentFilled, birth, death, iteration);
	}

	private void generateMap() {

		this.map = new int[width][height + 15];

		for (int y = surfaceHeight; y < this.height; y++)
			for (int x = 0; x < this.width; x++) {
				if (cv.getCellmap()[x][y - surfaceHeight])
					this.map[x][y] = Cell.bedrockCell.getId();
				else
					this.map[x][y] = Cell.emptyCell.getId();
			}
		
		
		addLayer(surfaceHeight, height -2 , Cell.stoneCell, 4);
		addLayer(surfaceHeight, height - 20, Cell.cobbleCell, 4);
		addLayer(surfaceHeight, height - 40, Cell.gravelCell, 6);
		addLayer(surfaceHeight, height - 60, Cell.dirtCell, 8);
		addLayer(surfaceHeight, surfaceHeight + 15, Cell.sandCell, 15);
		addLava(height - 10);
		//addWater(surfaceHeight);
		addBedrock();

		cv = null;
	}

	private void addLava(int yStart){
		for(int y = yStart; y < height; y++)
			for(int x = 0; x < width; x++)
				if(this.map[x][y] == Cell.emptyCell.getId())
					this.map[x][y] = Cell.lavaCell.getId();
	}
	
	private void addWater(int yStart){
		for(int y = yStart; y <  height - 80; y++)
			for(int x = 0; x < width; x++)
				if(this.map[x][y] == Cell.emptyCell.getId())
					this.map[x][y] = Cell.waterCell.getId();
	}
	
	private void addBedrock() {
		for (int x = 0; x < this.width; x++)
			this.map[x][height - 1] = Cell.bedrockCell.getId();

	}

	private void transition(int depth, int size, int from, float[] progressionPercentage) {
		for (int y = depth; y < depth + size; y++)
			for (int x = 0; x < width; x++)
				if (this.map[x][y] != 0)
					if (Math.random() < progressionPercentage[y - depth])
						this.map[x][y] = from;
	}

	/**
	 * Fills a layer with a particular cell and a transitions to the next layer at the bottom
	 * @param y1 Smaller height value of the layer
	 * @param y2 Biggest height value of the layer
	 * @param cell Cell to fill the layer with
	 * @param transitionSize Number of lines the transitions will take (included in the layer)
	 */
	private void addLayer(int y1, int y2, Cell cell, int transitionSize) {
		int cellId = cell.getId();
		float[] a = new float[transitionSize];
		for (int i = 0; i < transitionSize; i++) {
			a[i] = 1.6f / i;
		}
		for (int y = y1; y < y2 - a.length; y++)
			for (int x = 0; x < this.width; x++)
				if (this.map[x][y] != 0)
					this.map[x][y] = cellId;

		transition(y2 - a.length, a.length, cellId, a);
	}

	public void render(Graphics g) {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				Cell.getCellById(this.map[x][y]).render(g, x * Cell.CELLWIDTH, y * Cell.CELLHEIGHT);
			}
		}
	}
}
