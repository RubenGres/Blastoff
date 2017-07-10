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
import physics.Vector;

public class Player extends Creature {

	private float jetpackSpeed = 3f;
	private BufferedImage playerSprite, playerSprite_flipped, lastSprite;

	public Player(Handler handler, float x, float y, int width, int height, float speed) {
		super(handler, x, y, width, height, speed);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
		try {
			this.playerSprite = ImageIO.read(new File("res/textures/player.png"));
			playerSprite_flipped = ImageManipulator.flipHorizontally(playerSprite);
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
		move(movement.add(Vector.g));

		handler.getGame().getGameCamera().centerOnEntity(this);

	}

	private void getInput() {
		resetMovement();
		
		if (handler.getGame().getKeyManager().jetpack)
			movement = movement.add(new Vector(0,-jetpackSpeed));

		if (handler.getGame().getKeyManager().right)
			movement = movement.add(new Vector(speed,0));
		
		if (handler.getGame().getKeyManager().left)
			movement = movement.add(new Vector(-speed,0));
		
		if (handler.getGame().getKeyManager().up)
			movement = movement.add(new Vector(0,-speed));
		
		if (handler.getGame().getKeyManager().down)
			movement = movement.add(new Vector(0,speed));
	}

	@Override
	public void render(Graphics g) {
		if (movement.getX() < 0){
			lastSprite = playerSprite_flipped;
		}else if(movement.getX() > 0){
			lastSprite = playerSprite;
		}
		g.drawImage(lastSprite, (int) (position.getX() - handler.getGame().getGameCamera().getxOffset()), (int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), width, height, null);
	}

}
