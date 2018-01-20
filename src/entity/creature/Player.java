package entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import gfx.Assets;
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
	
	private float maxHealth = 100;

	private float cargo = 0;
	private float maxcargo = 100;
	
	private boolean facingLeft;

	public Player(Handler handler, float x, float y) {
		super(handler, x, y);
		bounds.x = 0;
		bounds.y = 0;
		width=30;
		height=50;
		bounds.width = width;
		bounds.height = height;
		health=maxHealth;
	}

	@Override
	public void tick() {
		
		super.tick();
		// Movement
		getInput();
		
		move(movement);

		handler.getGame().getGameCamera().centerOnEntity(this);

	}

	private void getInput() {
		MouseManager mm = handler
				.getGame()
				.getMouseManager();

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

			Point block = new Point(position.getX() + width/2, position.getY() + height/2);
			
			if (block.distanceTo(new Point(x, y)) < 100)
				handler.getWorld().getCell( x / Cell.CELLWIDTH, y / Cell.CELLHEIGHT).breakCell(x, y, handler);
		}
	}

	@Override
	public void render(Graphics g) {
		renderBar(g, 5, 15, "Fuel : ", Color.YELLOW, this.jetpackFuel, this.jetpackMaxFuel);
		renderBar(g, 5, 40, "Life : ", Color.GREEN, this.health, this.maxHealth);
		renderCargobar(g);
		renderPlayer(g);
	}
	
	private void renderCargobar(Graphics g){
		int width = 80;
		int height = 120;
		int x = handler.getGame().width - width - 60;
		int y = handler.getGame().height - height - 60;
		int yOffset = height - (int) (height * (this.cargo/this.maxcargo));
		
		g.fillRect(x, y + yOffset, width, (int) (height * (this.cargo/this.maxcargo)));		
		g.drawRect(x, y, width, height);
		g.drawImage(Assets.backpack, x, y, width, height, null);
		
	}
	
	public void addFuel(float amount){
		jetpackFuel += amount;
		if(jetpackFuel > jetpackMaxFuel)
			jetpackFuel = jetpackMaxFuel;
	}
	
	private void renderBar(Graphics g, int x, int y, String name, Color c, float current, float max){
		g.setColor(Color.BLACK);
		
		g.drawString(name, x, y);
		g.setColor(c);
		
		g.fillRect(x, y + 1, (int) ((current/max)*500), 10);
		
		g.setColor(Color.BLACK);
		g.drawRect(x, y + 1, 500, 10);
	}
	
	public void addToCargo(int w){
		if(this.cargo < this.maxcargo)
			this.cargo += w;
	}
	
	private void renderPlayer(Graphics g){
		if(movement.getX() != 0)
			facingLeft = movement.getX() < 0;
		
		if (facingLeft) {
			g.drawImage(Assets.player, (int) (position.getX() - handler.getGame().getGameCamera().getxOffset()) + width,
					(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), -width, height, null);
		} else {
			g.drawImage(Assets.player, (int) (position.getX() - handler.getGame().getGameCamera().getxOffset()),
					(int) (position.getY() - handler.getGame().getGameCamera().getyOffset()), width, height, null);			
		}
	}
}