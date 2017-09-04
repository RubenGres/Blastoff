package entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import gfx.Assets;
import gfx.ImageManipulator;
import input.MouseManager;
import main.Handler;
import physics.Point;
import physics.Vector;
import terrain.Cell;

public class Player extends Creature {

	//jetpack
	private float jetpackSpeed = 8.3f;
	private float jetpackMaxFuel = 400;
	private float jetpackFuel = jetpackMaxFuel;
	private float jetpackCostPerTick = 0.2f;
	
	private BufferedImage playerSprite;
	
	private boolean facingLeft;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y, 30, 50, 4);
		bounds.x = 0;
		bounds.y = 0;
		bounds.width = width;
		bounds.height = height;
		this.playerSprite = Assets.player;
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

		if (handler.getGame().getKeyManager().jetpack){
			if(jetpackFuel > 0){
				movement = movement.add(new Vector(0, -jetpackSpeed));
				jetpackFuel -= jetpackCostPerTick;
				if(jetpackFuel < 0)
					jetpackFuel = 0;
			}
		}

		if (handler.getGame().getKeyManager().right)
			movement = movement.add(new Vector(speed, 0));

		if (handler.getGame().getKeyManager().left)
			movement = movement.add(new Vector(-speed, 0));

		if (mm.isLeftPressed()) {
			float xOffset = handler.getGame().getGameCamera().getxOffset();
			float yOffset = handler.getGame().getGameCamera().getyOffset();
			int x = (int) (mm.getMouseX() + xOffset);
			int y = (int) (mm.getMouseY() + yOffset);

			if (new Point(position.getX() + width/2, position.getY() + height/2).distanceTo(new Point(x, y)) < 100)
				handler.getWorld().breakCell(x / Cell.CELLWIDTH, y / Cell.CELLHEIGHT);
		}
	}

	@Override
	public void render(Graphics g) {
		renderFuel(g);
		renderPlayer(g);
		renderHitbox(g);
	}
	
	public void addFuel(float amount){
		jetpackFuel += amount;
		if(jetpackFuel > jetpackMaxFuel)
			jetpackFuel = jetpackMaxFuel;
	}
	
	private void renderFuel(Graphics g){
		g.setColor(Color.BLACK);
		int x = 32;
		
		g.drawString("Fuel: ", 1, 15);
		
		g.setColor(Color.YELLOW);
		g.fillRect(x, 5, (int) ((jetpackFuel/jetpackMaxFuel)*500), 10);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, 5, 500, 10);
	}
	
	private void renderPlayer(Graphics g){
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