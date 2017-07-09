package entity.creature;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import input.ImageManipulator;
import main.Handler;

public class Player extends Creature {

	private float jetpackSpeed = 3f;
	private BufferedImage playerSprite;
	
	BufferedImage lastSprite;

	public Player(Handler handler, float x, float y, int width, int height) {
		super(handler, x, y, width, height);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
		try {
			this.playerSprite = ImageIO.read(new File("res/textures/player.png"));
			lastSprite = playerSprite;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		// Movement
		getInput();
		move();

		handler.getGame().getGameCamera().centerOnEntity(this);
		
		yMove = g;
	}

	private void getInput() {
		if (handler.getGame().getKeyManager().jetpack) {
			yMove = -jetpackSpeed;
		}

		if (handler.getGame().getKeyManager().right) {
			xMove = speed;
		} else if (handler.getGame().getKeyManager().left) {
			xMove = -speed;
		}
		
		if(!handler.getGame().getKeyManager().right & !handler.getGame().getKeyManager().left)
			xMove = 0;

		if (handler.getGame().getKeyManager().right && handler.getGame().getKeyManager().left)
			xMove = 0;

	}

	@Override
	public void render(Graphics g) {
		BufferedImage playerSprite_flipped = ImageManipulator.flipHorizontally(playerSprite);
		if (xMove < 0){
			lastSprite = playerSprite_flipped;
		}else if(xMove > 0){
			lastSprite = playerSprite;
		}
		g.drawImage(lastSprite, (int) (x - handler.getGame().getGameCamera().getxOffset()), (int) (y - handler.getGame().getGameCamera().getyOffset()), width, height, null);
	}

}
