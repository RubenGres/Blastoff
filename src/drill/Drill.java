package drill;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import gfx.Assets;
import gfx.GameCamera;
import main.Handler;

public class Drill {

	private BufferedImage texture = Assets.basicDrill;
	private int x, y;
	private float angle;
	
	public void tick(int x, int y, float angle) {
		this.x = x;
		this.y = y;
		this.angle = angle;
	}

	public void render(Graphics g) {
		 AffineTransform at = new AffineTransform();

		 GameCamera gc = Handler.getInstance().getGame().getGameCamera();
         // 4. translate it to the center of the component
         at.translate(x - gc.getxOffset(), y - gc.getyOffset());
         at.rotate(angle);
         //at.scale(1.5, 1.5);
         at.translate(-this.texture.getWidth()/2, -this.texture.getHeight()/2);

         // draw the image
         Graphics2D g2d = (Graphics2D) g;
         g2d.drawImage(this.texture, at, null);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public float getAngle() {
		return this.angle;
	}
}
