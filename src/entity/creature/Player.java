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
import main.Handler;
import physics.Point;
import physics.Vector;
import terrain.Cell;
import terrain.EmptyCell;
import terrain.ore.OreCell;
import utils.FrameTimer;
import utils.FrameTimerManager;

public class Player extends Creature {

	/* jetpack */
	private float jetpackSpeed = 8.3f;
	private float maxfuel = 400;
	private float fuel = getMaxFuel();
	private float jetpackCostPerTick = 0.2f;
	
	private int playerRange = 75;	
	
	/* mining */
	private float miningSpeed = 1;
	private Point breaking = null;
	private Drill drill;
	
	private float maxHealth = 100;

	/* money */
	private int money = 0;
	
	/* cargo */
	private float cargosize = 0;
	private float maxcargo = 100;
	private List<Ore> cargo;
	
	/* render */
	private Animation animation;
	private static int ANIMSPEED = 10;
	private boolean facingLeft;

	public Player(float x, float y) {
		super(x, y);
		bounds.x = 0;
		bounds.y = 15;
		width=30;
		height=54;
		bounds.width = width;
		bounds.height = 39;
		this.animation = new Animation(ANIMSPEED, Assets.playerAnim);
		this.drill = new Drill();
		
		this.cargo = new ArrayList<Ore>();
		this.setZ_index(15);
		setHealth(getMaxHealth());
	}

	@Override
	public void tick() {		
		super.tick();
		this.animation.tick();
		
		// Movement
		getInput();		
		move(movement);
		
		// TODO move in drill
		int x = (int) position.getX();
		int y = (int) position.getY();
		Point drillPos;
		if (facingLeft) {
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
		Point fCell = getFocusedCell();
		if (mm.isLeftPressed()) {
			if(fCell != null) {
				breakCell((int) fCell.getX(),(int) fCell.getY());				
			}
		} else {
			handler.getGame().getGameState().getFrameTimerManager().removeFrameTimer(FrameTimerManager.timer.BREAKING);
			this.setBreaking(null);
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
		this.a = (int) (x - gc.getxOffset());
		this.b = (int) (y - gc.getyOffset());
		
		if(n*step < this.playerRange) {
			return new Point(x/Cell.CELLWIDTH, y/Cell.CELLHEIGHT);			
		} else {
			return null;
		}
	}
	
	private void breakCell(int x, int y) {
		Cell cell = handler.getWorld().getCell(x, y);
		
		/* si la cellule est vide, pas besoin de la casser */
		if(cell instanceof EmptyCell) {
			return;
		}
		
		Point clickedCell = new Point(x, y);
		Point playerCenter = new Point(this.position.getX() + this.width/2, this.position.getY() + this.height/2);
		
		double dist = playerCenter.distanceTo(new Point(x * Cell.CELLHEIGHT + Cell.CELLHEIGHT/2, y * Cell.CELLWIDTH + Cell.CELLWIDTH/2));
		if (dist < this.playerRange) {
			FrameTimerManager ftm = handler.getGame().getGameState().getFrameTimerManager();
			if(clickedCell.equals(this.getBreaking())) { //same cell
				FrameTimer breaking = ftm.getFrameTimer(FrameTimerManager.timer.BREAKING);			
				if(!breaking.isRunning()) {					
					
					OreCell ore = handler.getWorld().getOreCell(x, y);
					if(ore != null) {
						ore.breakCell(x, y, this.handler);						
					}
					
					this.handler.getWorld().breakCell(x, y);
					
					ftm.removeFrameTimer(FrameTimerManager.timer.BREAKING);
					this.setBreaking(null);
				}
			} else { //new cell
				ftm.add(FrameTimerManager.timer.BREAKING, true, (long) (cell.getResistance() / this.miningSpeed));
				this.setBreaking(clickedCell);
			}
		}
	}

	int a, b;
	@Override
	public void render(Graphics g) {
		int x = (int) (position.getX() - handler.getGame().getGameCamera().getxOffset());
		int y = (int) (position.getY() - handler.getGame().getGameCamera().getyOffset());
		MouseManager mm = handler.getGame().getMouseManager();
		
		BufferedImage frame = Assets.playerIDLE;		

		facingLeft = mm.getMouseX() < x + this.width/2;
		
		if(movement.getX() != 0) {
			frame = this.animation.getCurrentFrame();
		}
		
		if (facingLeft) {
			g.drawImage(frame, x  + width, y, -width, height, null);
		} else {
			g.drawImage(frame, x, y, width, height, null);
		}
		
		drill.render(g);
		
		g.setColor(Color.RED);
		g.fillOval(a, b, 10, 10);
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
		return maxfuel;
	}

	public void setMaxFuel(float maxfuel) {
		this.maxfuel = maxfuel;
	}

	public Point getBreaking() {
		return breaking;
	}

	public void setBreaking(Point breaking) {
		this.breaking = breaking;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}