package main;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import display.Display;
import input.KeyManager;
import input.MouseManager;

public class RenderThread extends Thread {

	// rendering
	private BufferStrategy bs;
	private Graphics g;
	private Display display;
	public int width = 1000, height = 700;
	
	// handler
	private KeyManager keyManager;
	private MouseManager mouseManager;
	
	public RenderThread(KeyManager keyManager, MouseManager mouseManager) {
		this.keyManager = keyManager;
		this.mouseManager = mouseManager;
	}
			
	private void init() {
		display = new Display(Game.title, width, height);
		
		display.getFrame().addKeyListener(keyManager);
		display.getFrame().addMouseListener(mouseManager);
		display.getFrame().addMouseMotionListener(mouseManager);
		display.getCanvas().addMouseListener(mouseManager);
		display.getCanvas().addMouseMotionListener(mouseManager);
	}
	
	public void run() {
		init();
		
		double timePerTick = 1000000000 / Game.FPS;
		double delta = 0;
		long now;
		long lastTime = System.nanoTime();

		while (Game.running) {
			now = System.nanoTime();
			delta += (now - lastTime) / timePerTick;
			lastTime = now;

			if (delta >= 1) {
				render();
				delta--;
			}			
		}
	}
	
	private void render() {
		Canvas canvas = display.getCanvas();
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
		
		bs = canvas.getBufferStrategy();
		if (bs == null) {
			canvas.createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();
		
		// Clear Screen
		g.clearRect(0, 0, width, height);
		// Draw Here!

		if (states.State.getState() != null)
			states.State.getState().render(g);

		// End Drawing!
		bs.show();
		g.dispose();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}	
}
