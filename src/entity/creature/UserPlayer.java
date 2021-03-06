package entity.creature;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import drill.Drill;
import entity.pickable.Ore;
import gfx.Animation;
import gfx.Assets;
import gfx.GameCamera;
import input.MouseManager;
import physics.Point;
import physics.Vector;
import terrain.Cell;
import terrain.EmptyCell;
import terrain.ore.OreCell;
import utils.FrameTimer;
import utils.FrameTimerManager;

public class UserPlayer extends Player {


	/* jetpack */
	private float jetpackSpeed = 8.3f;
	private float maxfuel = 400;
	private float fuel = getMaxFuel();
	private float jetpackCostPerTick = 0.2f;
	
	/* mining */
	private float miningSpeed = 1;
	private int playerRange = 75;	
	private Point breaking = null;
	private Point fCell = null;
	private Drill drill;
	
	private float maxHealth = 100;

	/* money */
	private int money = 0;
	
	/* cargo */
	private float cargosize = 0;
	private float maxcargo = 100;
	private List<Ore> cargo;

	public UserPlayer(float x, float y) {
		super(x, y);
		
		this.drill = new Drill();
		
		this.cargo = new ArrayList<Ore>();
		setHealth(getMaxHealth());
	}

	@Override
	public void tick() {		
		super.tick();
				
		// Movement
		getInput();
		move(movement);
		
		// TODO move in drill
		int x = (int) position.getX();
		int y = (int) position.getY();
		Point drillPos;
		if (this.facingLeft) {
			drillPos = new Point(x + 10, y + height/2 + 20);
		} else {	
			drillPos = new Point(x + width - 10, y + height/2 + 20);
		}
		
		GameCamera gc = handler.getGame().getGameCamera();
		Point drillDispPos = new Point(drillPos.getX() - gc.getxOffset(), drillPos.getY() - gc.getyOffset());
		float angle = drillDispPos.angleTo(handler.getGame().getMouseManager().getPosition());

		if(angle < -Math.PI/4 && angle > -3*Math.PI/4)
			drillPos.setY(y + 20);
		this.drill.tick((int) drillPos.getX(), (int) drillPos.getY(), angle);

		handler.getGame().getGameCamera().centerOnEntity(this);
	}

	private void getInput() {
		MouseManager mm = this.handler.getGame().getMouseManager();

		resetMovement();

		if (handler.getGame().getKeyManager().jetpack && getFuel() > 0){
				this.movement = this.movement.add(new Vector(0, -this.jetpackSpeed));
				this.setFuel(this.getFuel() - this.jetpackCostPerTick);
				
				if(this.getFuel() < 0)
					this.setFuel(0);
		}

		if (handler.getGame().getKeyManager().right)
			this.movement = this.movement.add(new Vector(this.speed, 0));

		if (handler.getGame().getKeyManager().left)
			this.movement = this.movement.add(new Vector(-this.speed, 0));
		
		this.movement = this.movement.normalize().multiply(speed);
		
		/* casse une cellule */
		this.fCell = getFocusedCell();
		if (mm.isLeftPressed()) {
			if(this.fCell != null) {
				breakCell((int) this.fCell.getX(),(int) this.fCell.getY());				
			}
		} else {
			handler.getGame().getGameState().getFrameTimerManager().removeFrameTimer(FrameTimerManager.timer.BREAKING);
			this.breaking = null;
		}
	}
	
	private Point getFocusedCell() {		
		float a = this.drill.getAngle();
		int step = Cell.CELLWIDTH;
		
		double vX = Math.cos(a) * step;
		double vY = Math.sin(a) * step;
		
		int n = 0;
		int x = this.drill.getX();
		int y = this.drill.getY();		
		Cell c;
		do {
			x += vX;
			y += vY;
			c = handler.getWorld().getCell(x/Cell.CELLWIDTH, y/Cell.CELLHEIGHT);
			n ++;
		}while(n*step < this.playerRange && c instanceof EmptyCell);
				
		GameCamera gc = handler.getGame().getGameCamera();
		
		if(n*step < this.playerRange) {
			return new Point(x/Cell.CELLWIDTH, y/Cell.CELLHEIGHT);			
		} else {
			return null;
		}
	}
	
	private void breakCell(int x, int y) {
		Cell cell = handler.getWorld().getCell(x, y);
		Point cellPos = new Point(x,y);
		FrameTimerManager ftm = handler.getGame().getGameState().getFrameTimerManager();
		
		if(cellPos.equals(this.breaking)) { //same cell
			FrameTimer breaking = ftm.getFrameTimer(FrameTimerManager.timer.BREAKING);			
			if(!breaking.isRunning()) {					
				
				OreCell ore = handler.getWorld().getOreCell(x, y);
				if(ore != null) {
					ore.breakCell(x, y, this.handler);						
				}
				
				this.handler.getWorld().breakCell(x, y);
				
				ftm.removeFrameTimer(FrameTimerManager.timer.BREAKING);
				this.breaking = null;
			}
		} else { //new cell
			ftm.add(FrameTimerManager.timer.BREAKING, true, (long) (cell.getResistance() / this.miningSpeed));
			this.breaking = cellPos;
		}
	}
	
	@Override
	public void render(Graphics g) {
		super.render(g);
		drill.render(g);
	}	
	
	public void addFuel(float amount){
		setFuel(getFuel() + amount);
		if(getFuel() > getMaxFuel())
			setFuel(getMaxFuel());
	}
	
	public boolean addToCargo(Ore ore){
		if(this.getCargoSize() + ore.getWeight() < this.getMaxcargo()) {
			this.setCargoSize(this.getCargoSize() + ore.getWeight());
			this.cargo.add(ore);
			return true;
		}
		return false;
	}
	
	public void sellCargo() {
		for(int i = 0; i < this.cargo.size(); i++) {
			this.money += this.cargo.get(i).getValue();
		}
		
		this.cargosize = 0;
		this.cargo.clear();
	}
	
	/* getters and setters */
	
	public float getCargoSize() {
		return cargosize;
	}

	public void setCargoSize(float cargosize) {
		this.cargosize = cargosize;
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

	public float getFuel() {
		return fuel;
	}

	public void setFuel(float fuel) {
		if(fuel > this.maxfuel) {
			this.fuel = this.maxfuel;
		} else if(fuel < 0) {
			this.fuel = 0;
		} else {
			this.fuel = fuel;
		}			
	}

	public float getMaxFuel() {
		return this.maxfuel;
	}

	public void setMaxFuel(float maxfuel) {
		this.maxfuel = maxfuel;
	}

	public Point getBreaking() {
		return this.breaking;
	}
	
	public Point getFocused() {
		return this.fCell;
	}
	
	public int getMoney() {
		return this.money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
