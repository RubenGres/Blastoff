package gen;

public class Map {

	Cavegen cv;
	int height, width;
	int[][] map;
	
	public Map(int width, int height){
		float spawnRate = 0.47f;
		int birth = 3; //Lives if more than x neighbours
		int death = 2; //Dies if less than x neighbours
		int iteration = 3;
		this.cv = new Cavegen(width, height, spawnRate, birth, death, iteration);
		this.height = height;
		this.width = width;
		this.generateMap();
	}
	
	private void generateMap(){
		int surfaceHeight = 20;
		this.map = new int[width][height + surfaceHeight];
		
		for(int y = 0; y < this.height; y++)
			for(int x = 0; x < this.width; x++){
				if(cv.getCellmap()[x][y])
					this.map[x][y + surfaceHeight] = 1;
				else
					this.map[x][y + surfaceHeight] = 0;
			}
		cv = null;
	}
	
	public void render(){
		//TODO
	}
}
