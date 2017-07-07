package map;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Cavegen {

	int width, height;

	private float percentFilled;
	private boolean[][] cellmap;
	private int deathLimit, birthLimit, numberOfSteps;

	public Cavegen(int width, int height, float percentFilled, int birthLimit, int deathLimit, int numberOfSteps) {
		this.percentFilled = percentFilled;
		this.deathLimit = deathLimit;
		this.birthLimit = birthLimit;
		this.numberOfSteps = numberOfSteps;
		this.width = width;
		this.height = height;
		this.cellmap = new boolean[width][height];
		generateMap();
	}

	private void generateMap() {
		// Set up the map with random values
		init();
		// And now run the simulation for a set number of steps
		for (int i = 0; i < numberOfSteps; i++) {
			doSimulationStep();
		}
		clearSmallCaves();
	}

	// Returns the number of cells in a ring around (x,y) that are alive.
	private int countAliveNeighbours(int x, int y) {
		int count = 0;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				int neighbour_x = x + i;
				int neighbour_y = y + j;
				// If we're looking at the middle point
				if (i == 0 && j == 0) {
					// Do nothing, we don't want to add ourselves in!
				}
				// In case the index we're looking at it off the edge of the map
				else if (neighbour_x < 0 || neighbour_y < 0 || neighbour_x >= cellmap.length
						|| neighbour_y >= cellmap[0].length) {
					count += 1;
				}
				// Otherwise, a normal check of the neighbour
				else if (cellmap[neighbour_x][neighbour_y]) {
					count += 1;
				}
			}
		}
		return count;
	}

	public boolean[][] getCellmap() {
		return cellmap;
	}

	private void clearSmallCaves() {
		boolean[][] newMap = cellmap;
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (!cellmap[x][y])
					if (countAliveNeighbours(x, y) > 6)
						newMap[x][y] = true;

		cellmap = newMap;
	}

	private void doSimulationStep() {
		boolean[][] newMap = new boolean[cellmap.length][cellmap[0].length];
		// Loop over each row and column of the map
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int nbs = countAliveNeighbours(x, y);
				// The new value is based on our simulation rules
				// First, if a cell is alive but has too few neighbours, kill
				// it.
				if (cellmap[x][y]) {
					newMap[x][y] = nbs > deathLimit;
				} // Otherwise, if the cell is dead now, check if it has the
					// right number of neighbours to be 'born'
				else {
					newMap[x][y] = nbs > birthLimit;
				}
			}
		}
		cellmap = newMap;
	}

	private void init() {
		for (int x = 0; x < width; x++)
			for (int y = 0; y < height; y++)
				if (Math.random() < percentFilled)
					cellmap[x][y] = true;
	}

	public void show(int pixelSize) {
		DisplayCaves disp = new DisplayCaves(this, pixelSize);
	}

	@Override
	public String toString() {
		String str = "";
		for (int x = 0; x < cellmap.length; x++) {
			for (int y = 0; y < cellmap[0].length; y++) {
				if (cellmap[x][y])
					str += "#";
				else
					str += " ";
			}
			str += "\n";
		}
		return str;
	}

	private class DisplayCaves extends JFrame {

		int x, y;

		public DisplayCaves(Cavegen tg, int cellSize) {
			this.x = tg.getCellmap().length;
			this.y = tg.getCellmap()[0].length;
			this.setTitle("2D Cellular automate");
			this.setContentPane(new Panel(tg.getCellmap(), cellSize));
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setSize(x * cellSize, y * cellSize + 28);
			this.setVisible(true);
			this.isAlwaysOnTop();
		}

		private class Panel extends JPanel {

			boolean[][] array;
			int cellSize;
			Color colorAlive = Color.DARK_GRAY;
			Color colorDead = Color.BLUE;

			private static final long serialVersionUID = 6236075465182952575L;

			public Panel(boolean[][] array, int cellSize) {
				this.array = array;
				this.cellSize = cellSize;
			}

			public void paint(Graphics g) {
				//long start = System.currentTimeMillis();
				int xOffset = 0, yOffset = 0;
				for (int i = 0; i < y; i++) {
					for (int j = 0; j < x; j++) {
						if (array[j][i]) {
							g.setColor(colorAlive);
						} else {
							g.setColor(colorDead);
						}
						g.fillRect(xOffset, yOffset, xOffset + cellSize, yOffset + cellSize);
						xOffset += cellSize;
					}
					xOffset = 0;
					yOffset += cellSize;
				}
				//long stop = System.currentTimeMillis();
				// System.out.println("Binary display took " + (int)
				// (stop-start) + "ms");
			}

		}

	}

}
