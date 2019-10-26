package main;

import entity.creature.Player;
import entity.creature.UserPlayer;
import map.GameWorld;

public class Handler {
	
	private static Handler instance;

	private Game game;
	
	private Handler(Game game) {
		this.game = game;
	}
	
	public static Handler getInstance(Game game) {
		if(instance == null)
			instance = new Handler(game);
			
		return instance;
	}
	
	public UserPlayer getUserPlayer() {
		return game.getEntityManager().getUserPlayer();
	}
	
	public static Handler getInstance() {
		if(instance == null)
			throw new IllegalArgumentException("Handler instance has no game attached");
			
		return instance;
	}

	public Game getGame() {
		return game;
	}

	public GameWorld getWorld() {
		return game.getWorld();
	}
}