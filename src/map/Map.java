package map;

import java.awt.Graphics;

import terrain.Cell;

public class Map {

	Cavegen cv;
	int height, width;
	int[][] map;
	int surfaceHeight = 20;
	
	public Map(int width, int height){
		this.height = height;
		this.width = width;
		this.initCv();
		this.generateMap();
	}
	
	private void initCv(){
		float spawnRate = 0.47f;
		int birth = 3; //Lives if more than x neighbours
		int death = 2; //Dies if less than x neighbours
		int iteration = 3;
		this.cv = new Cavegen(width, height - surfaceHeight, spawnRate, birth, death, iteration);
	}
	
	private void generateMap(){
		this.map = new int[width][height];
		
		for(int y = surfaceHeight; y < this.height; y++)
			for(int x = 0; x < this.width; x++){
				if(cv.getCellmap()[x][y - surfaceHeight])
					this.map[x][y] = 1;
				else
					this.map[x][y] = 0;
			}
		
		for(int x = 0; x < this.width; x++)
			this.map[x][height - 1] = 2;
		
		cv = null;
	}
	
	public void render(Graphics g){
		for(int y = 0; y < this.height; y ++){
			for(int x = 0; x < this.width; x ++){
				Cell.getCellById(this.map[x][y]).render(g, x*Cell.CELLWIDTH, y*Cell.CELLHEIGHT);
			}
		}
	}
}
