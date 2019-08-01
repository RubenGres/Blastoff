package entity.creature;

import java.awt.Graphics;
import gfx.Assets;
import input.MouseManager;
import physics.Point;
import physics.Vector;
import terrain.Cell;
import terrain.EmptyCell;
import utils.FrameTimer;
import utils.FrameTimerManager;

public class Player extends Creature {

	//jetpack
	private float jetpackSpeed = 8.3f;
	private float jetpackMaxFuel = 400;
	private float jetpackFuel = getJetpackMaxFuel();
	private float jetpackCostPerTick = 0.2f;
	private int playerRange = 5 * Cell.CELLHEIGHT;
	
	private Point breaking = null;
	
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
		setHealth(getMaxHealth());
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

		if (handler.getGame().getKeyManager().jetpack && getJetpackFuel() > 0){
				this.movement = this.movement.add(new Vector(0, -this.jetpackSpeed));
				this.setJetpackFuel(this.getJetpackFuel() - this.jetpackCostPerTick);
				
				if(this.getJetpackFuel() < 0)
					this.setJetpackFuel(0);
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
			breakCell(x/Cell.CELLWIDTH, y/Cell.CELLHEIGHT);
		} else {
			handler.getGame().getGameState().getFrameTimerManager().removeFrameTimer(FrameTimerManager.timer.BREAKING);
			this.setBreaking(null);
		}
	}
	
	private void breakCell(int x, int y) {
		Cell cell = handler.getWorld().getCell(x, y);
		if(cell instanceof EmptyCell) {
			return;
		}
		
		Point clickedCell = new Point(x, y);
		Point playerCenter = new Point(this.position.getX() + this.width/2, this.position.getY() + this.height/2);
		if (playerCenter.distanceTo(new Point(x * Cell.CELLHEIGHT, y * Cell.CELLWIDTH)) < this.playerRange) {
			FrameTimerManager ftm = handler.getGame().getGameState().getFrameTimerManager();
			if(clickedCell.equals(this.getBreaking())) { //same cell
				FrameTimer breaking = ftm.getFrameTimer(FrameTimerManager.timer.BREAKING);			
				if(!breaking.isRunning()) {
					this.handler.getWorld().getCell(x, y).breakCell(x, y, this.handler);
					ftm.removeFrameTimer(FrameTimerManager.timer.BREAKING);
					this.setBreaking(null);
				}
			} else { //new cell
				ftm.add(FrameTimerManager.timer.BREAKING, true, cell.getResistance());
				this.setBreaking(clickedCell);
			}
		}
	}

	@Override
	public void render(Graphics g) {
		renderPlayer(g);
	}	
	
	public void addFuel(float amount){
		setJetpackFuel(getJetpackFuel() + amount);
		if(getJetpackFuel() > getJetpackMaxFuel())
			setJetpackFuel(getJetpackMaxFuel());
	}
	
	public void addToCargo(int w){
		if(this.getCargo() < this.getMaxcargo())
			this.setCargo(this.getCargo() + w);
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

	/* getters and setters */
	
	public float getCargo() {
		return cargo;
	}

	public void setCargo(float cargo) {
		this.cargo = cargo;
	}

	public float getMaxcargo() {
		return maxcargo;
	}

	public void setMaxcargo(float maxcargo) {
		this.maxcargo = maxcargo;
	}

	public float getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(float maxHealth) {
		this.maxHealth = maxHealth;
	}

	public float getJetpackFuel() {
		return jetpackFuel;
	}

	public void setJetpackFuel(float jetpackFuel) {
		this.jetpackFuel = jetpackFuel;
	}

	public float getJetpackMaxFuel() {
		return jetpackMaxFuel;
	}

	public void setJetpackMaxFuel(float jetpackMaxFuel) {
		this.jetpackMaxFuel = jetpackMaxFuel;
	}

	public Point getBreaking() {
		return breaking;
	}

	public void setBreaking(Point breaking) {
		this.breaking = breaking;
	}
}