package states;

import java.awt.Graphics;

import main.Handler;

public abstract class State {
	// state manager
	private static State currentState = null;

	// class
	protected Handler handler;

	protected State(Handler handler) {
		this.handler = handler;
	}

	public static void setState(State state) {
		currentState = state;
	}

	public static State getState() {
		return currentState;
	}

	public abstract void tick();

	public abstract void render(Graphics g);
}
