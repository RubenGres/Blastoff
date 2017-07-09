package gfx;

import entity.Entity;
import main.Handler;
import terrain.Cell;

public class GameCamera {
	
	private Handler handler;
	private float xOffset, yOffset;
	
	public GameCamera(Handler handler){
		this.handler = handler;
		xOffset = 0;
		yOffset = 0;
	}
	
	public void checkBlankSpace(){
		if(xOffset < 0){
			xOffset = 0;
		}else if(xOffset > handler.getWorld().getWidth() * Cell.CELLWIDTH - handler.getGame().width){
			xOffset = handler.getWorld().getWidth() * Cell.CELLWIDTH - handler.getGame().width;
		}
		
		if(yOffset < 0){
			yOffset = 0;
		}else if(yOffset > handler.getWorld().getHeight() * Cell.CELLHEIGHT - handler.getGame().height){
			yOffset = handler.getWorld().getHeight() * Cell.CELLHEIGHT - handler.getGame().height;
		}
	}
	
	public void centerOnEntity(Entity e){
		xOffset = e.getX() - handler.getGame().width / 2 + e.getWidth() / 2;
		yOffset = e.getY() - handler.getGame().height / 2 + e.getHeight() / 2;
		checkBlankSpace();
	}
	
	public void move(float xAmt, float yAmt){
		xOffset += xAmt;
		yOffset += yAmt;
		checkBlankSpace();
	}

	public float getxOffset() {
		return xOffset;
	}

	public void setxOffset(float xOffset) {
		this.xOffset = xOffset;
	}

	public float getyOffset() {
		return yOffset;
	}

	public void setyOffset(float yOffset) {
		this.yOffset = yOffset;
	}

}