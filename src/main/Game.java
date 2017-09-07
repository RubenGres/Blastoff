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
import map.GameWorld;
import states.GameState;
import states.MenuState;
import states.State;

public class Game implements Runnable {

	private Display display;
	public int width = 1000, height = 700;
	public String title;

	// game thread
	private boolean running = false;
	private Thread thread;
	private int FPS = 60;

	// rendering
	private BufferStrategy bs;
	private Graphics g;

	// handler
	private Handler handler;
	private KeyManager keyManager;
	private MouseManager mouseManager;

	// States
	private GameState gameState;
	private MenuState menuState;

	public Game(String title, int width, int height) {
		this.title = title;

		keyManager = new KeyManager();
		mouseManager = new MouseManager();		
	}

	private void init() {
		display = new Display(title, width, height);
		display.getFrame().addKeyListener(keyManager);

		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);

		handler = new Handler(this);

		this.gameState = new GameState(handler);
		this.menuState = new MenuState(handler);
		State.setState(menuState);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public GameWorld getMap() {
		return gameState.getMap();
	}

	private void tick() {
		this.width = display.getFrame().getWidth();
		this.height = display.getFrame().getHeight();
		
		keyManager.tick();

		if (State.getState() != null)
			State.getState().tick();
	}

	private void render() {
		bs = display.getCanvas().getBufferStrategy();
		if (bs == null) {
			display.getCanvas().createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		// Clear Screen
		g.clearRect(0, 0, width, height);
		// Draw Here!

		if (State.getState() != null)
			State.getState().render(g);

		// End Drawing!
		bs.show();
		g.dispose();
	}

	public void run() {

		init();
		double timePerTick = 1000000000 / FPS;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();
		long timer = 0;
		int ticks = 0;

		while (running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			timer += now - lastTime;
			lastTime = now;

			if (delta >= 1) {
				tick();
				render();
				ticks++;
				delta--;
			}

			if (timer >= 1000000000) {
				System.out.println("FPS : " + ticks);
				ticks = 0;
				timer = 0;
			}
		}

		stop();

	}

	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}

	public synchronized void stop() {
		if (!running)
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
		return gameState.getGameCamera();
	}

	public MouseManager getMouseManager() {
		return mouseManager;
	}

	public EntityManager getEntityManager() {
		return gameState.getEntityManager();
	}

}