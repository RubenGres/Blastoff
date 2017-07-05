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
		int iteration = 3; // Number of iterations
		this.cv = new Cavegen(width, height - surfaceHeight, percentFilled, birth, death, iteration);
	}

	private void generateMap() {

		this.map = new int[width][height];

		for (int y = surfaceHeight; y < this.height; y++)
			for (int x = 0; x < this.width; x++) {
				if (cv.getCellmap()[x][y - surfaceHeight])
					this.map[x][y] = 1;
				else
					this.map[x][y] = 0;
			}

		addDirt(20);
		addBedrock();

		cv = null;
	}

	private void addBedrock() {
		for (int x = 0; x < this.width; x++)
			this.map[x][height - 1] = 2;
	}

	private void transition(int depth, int size, int from, int to, float[] progressionPercentage) {
		for (int y = depth; y < depth + size; y++)
			for (int x = 0; x < width; x++)
				if (this.map[x][y] != 0)
					if (Math.random() < progressionPercentage[y - depth])
						this.map[x][y] = from;
					else
						this.map[x][y] = to;
	}

	private void addDirt(int size) {
		for (int y = surfaceHeight; y < surfaceHeight + size; y++)
			for (int x = 0; x < this.width; x++)
				if (this.map[x][y] != 0)
					this.map[x][y] = 3;
		
		float[] a = {0.8f ,0.5f, 0.3f, 0.1f, 0.05f};
		transition(surfaceHeight + size - a.length, a.length, 3, 1, a);
	}

	public void render(Graphics g) {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				Cell.getCellById(this.map[x][y]).render(g, x * Cell.CELLWIDTH, y * Cell.CELLHEIGHT);
			}
		}
	}
}
