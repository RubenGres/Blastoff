package map;

import java.awt.Graphics;

import org.j3d.texture.procedural.PerlinNoiseGenerator;

import terrain.Cell;

public class Map {

	public int height, width;
	public int[][] map;
	private int surfaceHeight = 2;

	private int highestPoint;

	public Map(int width, int height) {
		this.height = height;
		this.width = width;
		Cavegen cv = this.initCv();
		this.generateMap(cv);
	}

	private Cavegen initCv() {
		float percentFilled = 0.47f; // Percentage of filled cell
		int birth = 3; // Lives if more than x neighbors
		int death = 2; // Dies if less than x neighbors
		int iteration = 2; // Number of iterations
		return new Cavegen(width, height - surfaceHeight, percentFilled, birth, death, iteration);
	}

	private void generateMap(Cavegen cv) {

		this.map = new int[width][height];

		for (int y = surfaceHeight; y < this.height; y++)
			for (int x = 0; x < this.width; x++) {
				if (cv.getCellmap()[x][y - surfaceHeight])
					this.map[x][y] = Cell.bedrockCell.getId();
				else
					this.map[x][y] = Cell.emptyCell.getId();
			}

		addSurfaceLayer(Cell.grassCell);

		this.highestPoint = this.getHighestPoint(Cell.grassCell);

		// Adding layers
		addLayer(0, height - 6, Cell.stoneCell, 5);
		addLayer(0, height - 20, Cell.cobbleCell, 5);
		addLayer(0, height - 40, Cell.gravelCell, 5);
		addLayer(0, height - 60, Cell.dirtCell, 5);
		addLayer(0, this.highestPoint + 5, Cell.sandCell, 5);

		addLavaToBottom(height - 10);
		addBedrock(); // just to be sure

		cleanSurfaceLayer(Cell.grassCell);

		cv = null;
	}

	private int getHighestPoint(Cell c) {
		int[] heights = new int[width];
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
		int zoom = 10;
		PerlinNoiseGenerator pnl = new PerlinNoiseGenerator(91745718);

		for (int x = 0; x < width; x++) {
			float noise = pnl.noise1(((float) x) / zoom);
			int y = (int) (((noise + 1) / 2) * amplitude);
			this.map[x][y] = cell.getId();
		}
	}

	private void cleanSurfaceLayer(Cell surfaceCell) {
		for (int x = 0; x < width; x++) {

			int y = 0;
			// localize height of the surfaceLayer on x
			y = this.getFirstCellOccurence(x, surfaceCell);

			// Empty top part
			int a = 1;
			while (y - a >= 0) {
				map[x][y - a] = Cell.emptyCell.getId();
				a++;
			}

			// Fill bottom part with sand
			if (map[x][y + 1] == Cell.emptyCell.getId())
				map[x][y + 1] = Cell.sandCell.getId();

		}
	}

	private void addLavaToBottom(int yStart) {
		for (int y = yStart; y < height; y++)
			for (int x = 0; x < width; x++)
				if (this.map[x][y] == Cell.emptyCell.getId())
					this.map[x][y] = Cell.lavaCell.getId();
	}

	private void addWater(int yStart) {
		for (int y = yStart; y < height - 80; y++)
			for (int x = 0; x < width; x++)
				if (this.map[x][y] == Cell.emptyCell.getId())
					this.map[x][y] = Cell.waterCell.getId();
	}

	private void addBedrock() {
		for (int x = 0; x < this.width; x++)
			this.map[x][height - 1] = Cell.bedrockCell.getId();
	}

	private void transition(int yStart, int yEnd, int x, Cell cell) {
		int size = yEnd - yStart;
		float[] progressionPercentage = new float[size];
		for (int i = 0; i < size; i++) {
			progressionPercentage[i] = 1.6f / i;
		}

		for (int y = yStart; y < yStart + size; y++)
			if (this.map[x][y] != Cell.emptyCell.getId() & this.map[x][y] != Cell.grassCell.getId())
				if (Math.random() < progressionPercentage[y - yStart])
					this.map[x][y] = cell.getId();
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
					if (this.map[x][y] != Cell.emptyCell.getId() & this.map[x][y] != Cell.grassCell.getId())
						this.map[x][y] = cell.getId();
			}

			int yTransitionEnd = Math.min(height, y2 + yOffset);
			int yTransitionStart = Math.min(yTransitionEnd, y2 - transitionSize + yOffset);
			transition(yTransitionStart, yTransitionEnd, x, cell);
		}

	}

	private int getFirstCellOccurence(int x, Cell cell) {
		int y = 0;
		while (map[x][y] != cell.getId()) {
			y++;
			if (y == height)
				return 0;
		}
		return y;
	}

	public void render(Graphics g) {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				Cell.getCellById(this.map[x][y]).render(g, x * Cell.CELLWIDTH, y * Cell.CELLHEIGHT);
			}
		}
	}
}
