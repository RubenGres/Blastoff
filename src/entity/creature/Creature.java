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

	protected float speed ;
	protected float health ;
	public static final float DEFAULT_SPEED = 5f;
	public static final int DEFAULT_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;
	public static final float DEFAULT_HEALTH = 50;

	public Creature(Handler handler, float x, float y) {
		super(handler, x, y, DEFAULT_CREATURE_WIDTH, DEFAULT_CREATURE_HEIGHT, true);
		this.speed = DEFAULT_SPEED;
		this.health = DEFAULT_HEALTH;
		
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
		health-=damage;	
	}
	
	public void checkDeath(){
		
		if(health<0){
			State.setState(handler.getGame().menuState);
		}
	}
	
}