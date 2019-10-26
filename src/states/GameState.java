package states;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.KeyboardFocusManager;

import display.Display;
import entity.EntityManager;
import entity.creature.Player;
import entity.creature.UserPlayer;
import entity.machine.FuelMachine;
import entity.machine.OreMachine;
import entity.machine.ShopMachine;
import entity.pickable.PlutoniumOre;
import entity.pickable.GoldOre;
import gfx.Assets;
import gfx.GameCamera;
import gfx.GameInterface;
import input.KeyManager;
import input.MouseManager;
import main.Handler;
import map.GameWorld;
import physics.Point;
import utils.FrameTimerManager;

public class GameState extends State {
	
	//game world
	private static int worldWidth = 100, worldHeight = 100;
	private GameWorld map;
	
	//Camera
	private GameCamera gameCamera;
	
	//entity manager
	EntityManager em;
	
	public GameState() {
		super();
		Assets.preload();
		this.ftm = new FrameTimerManager();
	}
	
	public void init(){
		
		/* Listeners */
		Display display = this.handler.getGame().getDisplay();
		KeyManager keyManager = this.handler.getGame().getKeyManager();
		MouseManager mouseManager = this.handler.getGame().getMouseManager();
		
		KeyboardFocusManager manager = KeyboardFocusManager.getCurrentKeyboardFocusManager();
        manager.addKeyEventDispatcher(keyManager);
		
		display.getFrame().addMouseListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);	
		
		/* world */
		this.map = new GameWorld();
		this.map.init();
		
		this.em = new EntityManager(new UserPlayer(0, 0));
		
		gameCamera = new GameCamera();
		
		em.addEntity(new OreMachine(300, 20));
		em.addEntity(new ShopMachine(400, 20));
		em.addEntity(new FuelMachine(500, 20));
		for(int i = 0; i < 100; i++)
			em.addEntity(new GoldOre(220, 10));
		
		this.initialized = true;
	}
	

	@Override
	public void prepare() {
		this.handler.getGame().startRender();
	}

	public EntityManager getEntityManager() {
		return em;
	}

	@Override
	public void tick() {
		if(handler.getGame().getKeyManager().pause)
			State.setState(handler.getGame().getPauseMenuState());
		
		super.tick();
		map.tick();
		em.tick();
	}

	@Override
	public void render(Graphics g) {		
		Assets.bkg.render(g);
		map.render(g);
		
		GameInterface.showBreakingCell(g);
		GameInterface.showFocusedCell(g);
		em.render(g);
		
		GameInterface.render(g);	
	}
	
	public GameWorld getWorld(){
		return map;
	}

	public GameCamera getGameCamera() {
		return gameCamera;
	}
	
	public FrameTimerManager getFrameTimerManager() {
		return ftm;
	}

	@Override
	public void stop() {
		this.handler.getGame().pauseRender();
	}

}
