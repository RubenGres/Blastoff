package gfx;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Handler;

public class Background {
	
	private BufferedImage[] bg;
	private float[] speed;
	private float[] x;
	private int width, height;
	private int dw;
	
	public Background(String path, int depth) {
		this.bg = new BufferedImage[depth];
		this.speed = new float[depth];
		this.x = new float[depth];
		
		for(int i = 0; i < depth; i++) {
			this.speed[i] = 1f;
			this.x[i] = 0;
			this.bg[i] = Assets.loadTexture(path + "/" + i + ".png");
		}
		
		this.width = this.bg[0].getWidth();
		this.height = this.bg[0].getHeight();
	}
	
	public void setSpeed(int z, float f) {
		this.speed[z] = f;
	}
	
	public void tick() {	
		for(int i = 0; i < bg.length; i++) {
			this.x[i] += this.speed[i];
			
			if(this.x[i] > this.dw || this.x[i] < -this.dw) {
				this.x[i] = 0;				
			}
		}
	}

	public void render(Graphics g) {
		tick();
		
		Handler handler = Handler.getInstance();
		int h = handler.getGame().height;
		int w = (int) (this.width * (1.0 * h/this.height));
		this.dw = w;
		
		for(int i = 0; i < bg.length; i++) {
			g.drawImage(bg[i], (int) this.x[i] - w, 0, w, h, null);
			g.drawImage(bg[i], (int) this.x[i], 0, w, h, null);
			g.drawImage(bg[i], (int) this.x[i] + w, 0, w, h, null);
		}
	}
	
}
