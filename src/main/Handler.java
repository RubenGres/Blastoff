package main;

import map.World;

public class Handler {

	private Game game;
	private World world;

	public Handler(Game game) {
		this.game = game;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Game getGame() {
		return game;
	}

	public World getWorld() {
		return game.getMap();
	}
}