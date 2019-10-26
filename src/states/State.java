package states;

import java.awt.Graphics;
import main.Handler;
import utils.FrameTimerManager;

public abstract class State {
	// state manager
	private static State currentState = null;

	// class
	protected Handler handler;
	protected boolean initialized;

	//frametimer manager
	protected FrameTimerManager ftm;
	
	protected State() {
		this.handler = Handler.getInstance();
		this.initialized = false;
	}

	public static void setState(State state) {
		if(currentState != null)
			currentState.stop();
		
		if(!state.initialized)
			state.init();
		
		state.prepare();
		currentState = state;
	}

	public static State getState() {
		return currentState;
	}

	public void tick(){
		ftm.tick();
	}
	
	public abstract void init();
	
	public abstract void prepare();

	public abstract void render(Graphics g);
	
	public abstract void stop();
}
