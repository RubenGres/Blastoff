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
	private int playerRange = 1000000;
	
	private float maxHealth = 100;

	private float cargo = 0;
	private float maxcargo = 100;
	
	private boolean facingLeft;

	public Player(float x, float y) {
		super(x, y);
		bounds.x = 0;
		bounds.y = 15;
		width=30;
		height=54;
		bounds.width = width;
		bounds.height = 39;
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
		MouseManager mm = this.handler.getGame().getMouseManager();

		resetMovement();

		if (handler.getGame().getKeyManager().jetpack && jetpackFuel > 0){
				this.movement = this.movement.add(new Vector(0, -this.jetpackSpeed));
				this.jetpackFuel -= this.jetpackCostPerTick;
				
				if(this.jetpackFuel < 0)
					this.jetpackFuel = 0;
		}

		if (handler.getGame().getKeyManager().right)
			this.movement = this.movement.add(new Vector(this.speed, 0));

		if (handler.getGame().getKeyManager().left)
			this.movement = this.movement.add(new Vector(-this.speed, 0));
		
		this.movement = this.movement.normalize().multiply(speed);
		
		/* casse une cellule */
		if (mm.isLeftPressed()) {
			float xOffset = this.handler.getGame().getGameCamera().getxOffset();
			float yOffset = this.handler.getGame().getGameCamera().getyOffset();
			int x = (int) (mm.getMouseX() + xOffset);
			int y = (int) (mm.getMouseY() + yOffset);

			Point block = new Point(this.position.getX() + this.width/2, this.position.getY() + this.height/2);
			
			if (block.distanceTo(new Point(x, y)) < this.playerRange)
				this.handler.getWorld().getCell(x / Cell.CELLWIDTH, y / Cell.CELLHEIGHT).breakCell(x, y, this.handler);
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
		
		g.fillRect(x, y + yOffset,
				width, (int) (height * (this.cargo/this.maxcargo)));
		
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
		
		int x = (int) (position.getX() - handler.getGame().getGameCamera().getxOffset());
		int y = (int) (position.getY() - handler.getGame().getGameCamera().getyOffset());
		
		if (facingLeft) {
			g.drawImage(Assets.player, x  + width, y, -width, height, null);
		} else {
			g.drawImage(Assets.player, x, y, width, height, null);		
		}
		
		//g.drawRect(x + bounds.x, y + bounds.y, this.bounds.width, this.bounds.height);
	}
}