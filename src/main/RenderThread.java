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
	
	public int width, height;	
	
	public RenderThread() {
	}
	
	public void run() {
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
	
	private synchronized void render() {
		Display disp = Handler.getInstance().getGame().getDisplay();
		if(disp.getCanvas().getParent() == null)
			return;
		
		Canvas canvas = disp.getCanvas();
		this.width = canvas.getWidth();
		this.height = canvas.getHeight();
						
		//du code sale oui, mais du code fonctionnel
		try {
			bs = canvas.getBufferStrategy();
			g = bs.getDrawGraphics();			
		} catch(Exception e) {
			canvas.createBufferStrategy(3);
			return;
		}
		
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
