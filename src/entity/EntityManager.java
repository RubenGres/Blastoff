package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;

import entity.creature.Player;
import main.Handler;
import physics.Vector;
import terrain.Cell;

public class EntityManager {
	
	private Handler handler;
	private Player player;
	
	private ArrayList<Entity> entities;
	private ArrayList<Entity> toRemove = new ArrayList<>();
	
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			if(a.position.getX() + a.getHeight() < b.position.getY() + b.getHeight())
				return -1;
			return 1;
		}
	};
	
	public EntityManager(Handler handler, Player player){
		this.handler = handler;
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
	}
	
	public static boolean checkCollision(Entity a, Entity b){
		Rectangle rec1 = new Rectangle((int) a.getPosition().getX(), (int) a.getPosition().getY(), a.bounds.width, a.bounds.height);
		Rectangle rec2 = new Rectangle((int) b.getPosition().getX(), (int) b.getPosition().getY(), b.bounds.width, b.bounds.height);
		return rec1.intersects(rec2);
	}
	
	public void tick(){

		for(Entity e : entities){
			if(!(e instanceof Player))
				e.position = e.avoidCollision(e.position, Vector.g);
		    e.tick();
		}
		
		entities.removeAll(toRemove);
		toRemove = new ArrayList<>();
		
		entities.sort(renderSorter);
	}

	public void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
	}
	
	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		toRemove.add(e);
		e = null;
	}
	
	//GETTERS SETTERS

	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

	public Player getPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
}