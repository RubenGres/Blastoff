package entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Comparator;

import entity.creature.Player;
import entity.creature.UserPlayer;
import main.Handler;
import physics.Vector;

public class EntityManager {
	
	private Handler handler;
	private UserPlayer player;
	
	private ArrayList<Entity> entities;
	private ArrayList<Entity> toRemove = new ArrayList<>();
	private ArrayList<Entity> toAdd = new ArrayList<>();
	
	private Comparator<Entity> renderSorter = new Comparator<Entity>(){
		@Override
		public int compare(Entity a, Entity b) {
			if(a.getZ_index() < b.getZ_index())
				return -1;
			return 1;
		}
	};
	
	public EntityManager(UserPlayer player){
		this.handler = Handler.getInstance();
		this.player = player;
		entities = new ArrayList<Entity>();
		addEntity(player);
	}
	
	public static boolean checkCollision(Entity a, Entity b){
		Rectangle rec1 = new Rectangle((int) a.getPosition().getX(), (int) a.getPosition().getY(), a.bounds.width, a.bounds.height);
		Rectangle rec2 = new Rectangle((int) b.getPosition().getX(), (int) b.getPosition().getY(), b.bounds.width, b.bounds.height);
		return rec1.intersects(rec2);
	}
	
	public synchronized void tick(){

		for(Entity e : entities){
			if(!(e instanceof Player))
				e.position = e.avoidCollision(e.position, Vector.g);
		    e.tick();
		}
		
		entities.removeAll(toRemove);
		toRemove = new ArrayList<>();
		
		entities.addAll(toAdd);
		toAdd = new ArrayList<>();
		
		entities.sort(renderSorter);
	}

	public synchronized void render(Graphics g){
		for(Entity e : entities){
			e.render(g);
		}
	}
	
	public void addEntity(Entity e){
		toAdd.add(e);
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

	public UserPlayer getUserPlayer() {
		return player;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

	public void setEntities(ArrayList<Entity> entities) {
		this.entities = entities;
	}
}