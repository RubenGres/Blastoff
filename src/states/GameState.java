package states;

import java.awt.Graphics;

import entity.EntityManager;
import entity.FuelTank;
import entity.creature.Player;
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
	
	public GameState(Handler handler) {
		super(handler);
		gameCamera = new GameCamera(handler);
		map = new GameWorld(worldWidth, worldHeight, handler);

		init();
	}
	
	public void init(){
		Player player = new Player(handler, 0, 0);
		this.em = new EntityManager(handler, player);
   		em.addEntity(new FuelTank(handler, 220, 10));
	}

	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void tick() {
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
