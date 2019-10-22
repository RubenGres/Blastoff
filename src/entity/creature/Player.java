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

	/* render */
	private Animation animation;
	private static int ANIMSPEED = 10;
	protected boolean facingLeft;
	
	public Player(float x, float y) {
		super(x, y);
		bounds.x = 0;
		bounds.y = 15;
		width=30;
		height=54;
		bounds.width = width;
		bounds.height = 39;
		
		this.animation = new Animation(ANIMSPEED, Assets.playerAnim);
		this.setZ_index(15);
	}

	
	@Override
	public void tick() {
		super.tick();
		this.animation.tick();
	}
	
	@Override
	public void render(Graphics g) {
		int x = (int) (position.getX() - handler.getGame().getGameCamera().getxOffset());
		int y = (int) (position.getY() - handler.getGame().getGameCamera().getyOffset());
		MouseManager mm = handler.getGame().getMouseManager();
		
		BufferedImage frame = Assets.playerIDLE;	
		
		//TODO
		//facingLeft = mm.getMouseX() < x + this.width/2;
		this.facingLeft = true;
		
		if(movement.getX() != 0) {
			frame = this.animation.getCurrentFrame();
		}
		
		if (facingLeft) {
			g.drawImage(frame, x  + width, y, -width, height, null);
		} else {
			g.drawImage(frame, x, y, width, height, null);
		}		
	}
}