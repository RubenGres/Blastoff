package entity.creature;

import java.awt.Color;
import java.awt.Graphics;

import entity.Entity;
import main.Handler;
import physics.Point;
import physics.Vector;
import states.State;
import terrain.Cell;
import terrain.liquid.LavaCell;

public abstract class Creature extends Entity {

	protected float speed;
	private float health;
	
	public static final float DEFAULT_SPEED = 5f;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
	public static final float DEFAULT_HEALTH = 50;

	public Creature(float x, float y) {
		super(x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, true);
		this.speed = DEFAULT_SPEED;
		this.setHealth(DEFAULT_HEALTH);
		
	}

	public void checkLavaContact() {
				if (handler.getWorld().getCell((int) (position.getX() / Cell.CELLWIDTH), (int) (position.getY()) / Cell.CELLHEIGHT) instanceof LavaCell){
					LavaCell temp = (LavaCell) (handler.getWorld().getCell((int) (position.getX() / Cell.CELLWIDTH), (int) (position.getY()) / Cell.CELLHEIGHT));
					hurt(temp.lavaDamage);
				}
				
	}
	
	public void tick(){
		
		checkLavaContact();
		checkDeath();
	}
	
	public void hurt(float damage){
		setHealth(getHealth() - damage);	
	}
	
	public void checkDeath(){
		
		if(getHealth()<0){
			State.setState(handler.getGame().menuState);
		}

	}

	public float getHealth() {
		return health;
	}

	public void setHealth(float health) {
		this.health = health;
	}
}