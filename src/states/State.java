package states;

import java.awt.Graphics;
import main.Handler;
import utils.FrameTimerManager;

public abstract class State {
	// state manager
	private static State currentState = null;

	// class
	protected Handler handler;

	//frametimer manager
	protected FrameTimerManager ftm = new FrameTimerManager();
	
	protected State(Handler handler) {
		this.handler = handler;
	}

	public static void setState(State state) {
		currentState = state;
	}

	public static State getState() {
		return currentState;
	}

	public void tick(){
		ftm.tick();
	}

	public FrameTimerManager getFrameTimerManager() {
		return ftm;
	}

	public abstract void render(Graphics g);
}
