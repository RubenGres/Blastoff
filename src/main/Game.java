package main;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import display.Display;
import entity.EntityManager;
import entity.FuelTank;
import entity.creature.Player;
import gfx.GameCamera;
import input.KeyManager;
import input.MouseManager;
import map.World;

public class Game implements Runnable {

	private Display display;
	public int width = 1000, height = 700;
	public String title;
	
	//entity manager
	EntityManager em;
	
	//game thread
	private boolean running = false;
	private Thread thread;
	private int FPS = 60;	
	
	//game world
	private static int worldWidth = 300, worldHeight = 100;
	
	private BufferStrategy bs;
	private Graphics g;
	private World map;
	
	//background
	private BufferedImage bg_image = null;
	
	//handler
	private Handler handler;
	private KeyManager keyManager;
	private MouseManager mouseManager;

	//Camera
	private GameCamera gameCamera;
	
	public Game(String title, int width, int height){
		this.title = title;
		keyManager = new KeyManager();
		mouseManager = new MouseManager();
		
		try {
			this.bg_image = ImageIO.read(new File("res/textures/background/sky_bg.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init(){
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);

		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
		
		handler = new Handler(this);
		gameCamera = new GameCamera(handler);
		map = new World(worldWidth, worldHeight, handler);
		
		Player player = new Player(handler, 0, 0);
		this.em = new EntityManager(handler, player);
   		em.addEntity(new FuelTank(handler, 220, 10));
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public World getMap() {
		return map;
	}

	private void tick(){
		this.width = display.getFrame().getWidth();
		this.height = display.getFrame().getHeight();
		keyManager.tick();
		em.tick();
	}
	
	private void render(){
		bs = display.getCanvas().getBufferStrategy();
		if(bs == null){
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		//Clear Screen
		g.clearRect(0, 0, width, height);
		//Draw Here!
		
		g.drawImage(bg_image, 0, 0, width, height, null);
		
		map.render(g);
		em.render(g);
		
		//End Drawing!
		bs.show();
		g.dispose();
	}
	
	public void run(){
		
		init();
		double timePerTick = 1000000000 / FPS;
		double  delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;
		
		while(running){
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;
			
			if(delta >= 1){
				tick();
				render();
				ticks ++;
				delta --;
			}
			
			if(timer >= 1000000000){				
				System.out.println("FPS : " + ticks);
				ticks = 0;
				timer = 0;
			}
		}
		
		stop();
		
	}
	
	public synchronized void start(){
		if(running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public synchronized void stop(){
		if(!running)
			return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public KeyManager getKeyManager() {
		return keyManager;
	}
	
	
	public GameCamera getGameCamera() {
		return gameCamera;
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public EntityManager getEntityManager() {
		return em;
	}
	
}