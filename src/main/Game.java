package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import display.Display;
import entity.EntityManager;
import entity.creature.UserPlayer;
import gfx.GameCamera;
import input.KeyManager;
import input.MouseManager;
import map.GameWorld;
import states.GameState;
import states.PauseMenuState;
import states.StartMenuState;
import states.State;

public class Game implements Runnable {

	// game threads
	public static boolean running = false;
	public static int FPS = 60;
	public static String title;
	
	private Thread tick_thread;
	private RenderThread render_thread;

	// handler
	private KeyManager keyManager;
	private MouseManager mouseManager;

	// States
	public GameState gameState;
	public StartMenuState startMenuState;
	public PauseMenuState pauseMenuState;
	
	//display
	private Display display;
	public static int DEFAULT_W = 1000, DEFAULT_H = 700;

	public Game(String title, int width, int height) {
		Game.title = title;

		keyManager = new KeyManager();
		mouseManager = new MouseManager();		
	}

	private void init() {
		display = new Display(Game.title, DEFAULT_W, DEFAULT_H);
		
		Handler.getInstance(this);
		
		this.startMenuState = new StartMenuState();
		this.pauseMenuState = new PauseMenuState();
		
		State.setState(startMenuState);
	}

	private synchronized void tick() {
		keyManager.tick();

		if (State.getState() != null)
			State.getState().tick();
	}

	public void run() {
		init();
		double timePerTick = 1000000000 / FPS;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;

			if (delta >= 1) {
				tick();
				delta--;
			}			
		}

		stop();
	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		tick_thread = new Thread(this);
		tick_thread.start();
		
		render_thread = new RenderThread();
	}

	public synchronized void stop() {
		if (!running)
			return;
		running = false;
		try {
			tick_thread.join();
			render_thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void startRender() {
		if(!this.render_thread.isAlive()) {
			this.render_thread.start();
		} else if (this.render_thread.isInterrupted()){
			this.render_thread.notify();
		} else {
			this.render_thread.resume();
		}
	}
	
	public void pauseRender() {
		this.render_thread.suspend();
	}

	public void save(String string) throws IOException {
		String path = "saves/"+string;
		File file = new File(path);
		file.mkdir();
		
		/* player data */
	    FileOutputStream playerStream = new FileOutputStream(path + "/player.data");	    
	    UserPlayer player = this.getEntityManager().getUserPlayer();
	    ByteBuffer buffer = ByteBuffer.allocate(8);
	    buffer.putFloat(player.getHealth());	// -> 4 bytes
	    buffer.putFloat(player.getFuel());		// -> 4 bytes
	    playerStream.write(buffer.array());
	    playerStream.close();
	    
	    /* world data */
	    FileOutputStream worldStream = new FileOutputStream(path + "/world.data");
	    byte[] world = this.getGameState().getWorld().asBytes();
	    worldStream.write(world);
	    worldStream.close();
	    
	    System.out.println("Saved game !");
	}
	
	/* Getters and setters */
	public KeyManager getKeyManager() {
		return keyManager;
	}

	public GameCamera getGameCamera() {
		return gameState.getGameCamera();
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public EntityManager getEntityManager() {
		return gameState.getEntityManager();
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}	

	public GameWorld getWorld() {
		return gameState.getWorld();
	}	

	public StartMenuState getStartMenuState() {
		return startMenuState;
	}

	public int getWidth() {
		return render_thread.getWidth();
	}

	public int getHeight() {
		return render_thread.getHeight();
	}

	public Display getDisplay() {
		return display;
	}

	public State getPauseMenuState() {
		return pauseMenuState;
	}

}