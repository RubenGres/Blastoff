package entity.creature;

import entity.Entity;
import main.Handler;
import physics.Point;
import physics.Vector;
import terrain.Cell;

public abstract class Creature extends Entity {

	protected float speed;
	public static final float DEFAULT_SPEED = 5f;
	public static final int DEFAUL_CREATURE_WIDTH = 64, DEFAULT_CREATURE_HEIGHT = 64;

	public Creature(Handler handler, float x, float y, int width, int height, float speed) {
		super(handler, x, y, width, height, true);
		this.speed = speed;
	}
}