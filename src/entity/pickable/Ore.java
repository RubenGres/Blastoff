package entity.pickable;

import java.awt.image.BufferedImage;

public abstract class Ore extends PickableEntity{
	public static int WIDTH = 20, HEIGHT = 20;
	protected int weight = 10;
	protected int value = 10;

	public Ore(float x, float y, int width, int height, BufferedImage texture) {
		super(x, y, WIDTH, HEIGHT, texture);
	}

	@Override
	public void onCollision() {
		if(handler.getGame().getEntityManager().getUserPlayer().addToCargo(this))
			handler.getGame().getEntityManager().removeEntity(this);		
	}
	
	public int getWeight() {
		return this.weight;
	}
	
	public int getValue() {
		return this.value;
	}
}
