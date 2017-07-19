package entity.creature;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gfx.ImageManipulator;
import input.MouseManager;
import main.Handler;
import physics.Point;
import physics.Vector;
import terrain.Cell;

public class Player extends Creature {

	private float jetpackSpeed = Vector.g.getY();
	private BufferedImage playerSprite;
	
	private boolean facingLeft;

	public Player(Handler handler, float x, float y, int width, int height, float speed) {
		super(handler, x, y, width, height, speed);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
		try {
			this.playerSprite = ImageIO.read(new File("res/textures/player.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void tick() {
		// Movement
		getInput();
		
		move(movement);

		handler.getGame().getGameCamera().centerOnEntity(this);

	}

	private void getInput() {
		MouseManager mm = handler.getGame().getMouseManager();

		resetMovement();

		if (handler.getGame().getKeyManager().jetpack)
			movement = movement.add(new Vector(0, -jetpackSpeed));

		if (handler.getGame().getKeyManager().right)
			movement = movement.add(new Vector(speed, 0));

		if (handler.getGame().getKeyManager().left)
			movement = movement.add(new Vector(-speed, 0));

		if (handler.getGame().getKeyManager().up)
			movement = movement.add(new Vector(0, -speed));

		if (handler.getGame().getKeyManager().down)
			movement = movement.add(new Vector(0, speed));

		if (mm.isLeftPressed()) {
			float xOffset = handler.getGame().getGameCamera().getxOffset();
			float yOffset = handler.getGame().getGameCamera().getyOffset();
			int x = (int) (mm.getMouseX() + xOffset);
			int y = (int) (mm.getMouseY() + yOffset);

			if (position.distanceTo(new Point(x, y)) < 100)
				handler.getWorld().breakCell(x / Cell.CELLWIDTH, y / Cell.CELLHEIGHT);
		}
	}

	@Override
	public void render(Graphics g) {
		if(movement.getX() != 0)
			facingLeft = movement.getX() < 0;
		
		if (facingLeft) {
			g.drawImage(playerSprite, (int) (position.getX() - handler.getGame().getGameCamera().getxOffset()) + width,
					(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), -width, height, null);
		} else {
			g.drawImage(playerSprite, (int) (position.getX() - handler.getGame().getGameCamera().getxOffset()),
					(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), width, height, null);			
		}

	}
}
