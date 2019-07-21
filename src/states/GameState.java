package states;

import java.awt.Graphics;

import entity.EntityManager;
import entity.creature.Player;
import entity.pickable.FuelTank;
import entity.pickable.GoldOre;
import gfx.Assets;
import gfx.GameCamera;
import main.Handler;
import map.GameWorld;

public class GameState extends State {
	
	//game world
	private static int worldWidth = 300, worldHeight = 100;
	private GameWorld map;
	
	//Camera
	private GameCamera gameCamera;
	
	//entity manager
	EntityManager em;
	
	public GameState() {
		super();
		Assets.preload();
	}
	
	public void init(){
		this.map = new GameWorld(worldWidth, worldHeight);
		this.map.init();
		
		this.em = new EntityManager(new Player(0, 0));

		gameCamera = new GameCamera();
		
   		em.addEntity(new FuelTank(220, 10));
   		em.addEntity(new GoldOre(300, 10));
   		
	}

	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void tick() {
		super.tick();
		map.tick();
		em.tick();
	}

	@Override
	public void render(Graphics g) {
		map.render(g);
		em.render(g);
	}
	
	public GameWorld getMap(){
		return map;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}

}
