package main;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import display.Display;
import entity.EntityManager;
import entity.creature.Player;
import entity.pickable.PlutoniumOre;
import gfx.GameCamera;
import input.KeyManager;
import input.MouseManager;
import map.GameWorld;
import states.GameState;
import states.MenuState;
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
	public MenuState menuState;

	public Game(String title, int width, int height) {
		Game.title = title;

		keyManager = new KeyManager();
		mouseManager = new MouseManager();		
	}

	private void init() {
		Handler.getInstance(this);
		
		this.gameState = new GameState();
		this.menuState = new MenuState();
		
		State.setState(gameState);
		
		gameState.init();
	}

	private void tick() {		
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
		
		render_thread = new RenderThread(keyManager, mouseManager);
		render_thread.start();
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

	public MenuState getMenuState() {
		return menuState;
	}

	public int getWidth() {
		return render_thread.getWidth();
	}

	public int getHeight() {
		return render_thread.getHeight();
	}

	public GameWorld getMap() {
		return gameState.getMap();
	}

}