package main;

import map.GameWorld;

public class Handler {

	private Game game;
	private GameWorld world;

	public Handler(Game game) {
		this.game = game;
	}

	public void setWorld(GameWorld world) {
		this.world = world;
	}

	public Game getGame() {
		return game;
	}

	public GameWorld getWorld() {
		return game.getMap();
	}
}