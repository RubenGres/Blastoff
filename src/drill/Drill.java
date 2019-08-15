package drill;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import gfx.Assets;

public class Drill {

	private BufferedImage texture = Assets.basicDrill;
	
	public void render(Graphics g, int x, int y, double d) {
		 AffineTransform at = new AffineTransform();

         // 4. translate it to the center of the component
         at.translate(x, y);
         at.rotate(d);
         //at.scale(1.5, 1.5);
         at.translate(-this.texture.getWidth()/2, -this.texture.getHeight()/2);

         // draw the image
         Graphics2D g2d = (Graphics2D) g;
         g2d.drawImage(this.texture, at, null);
	}
	
}
