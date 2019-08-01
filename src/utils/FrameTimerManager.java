package utils;
import java.util.HashMap;
import java.util.Map;

public class FrameTimerManager {

	//frametimer manager
	public static enum timer {LAVA, WATER, BREAKING};
	private Map<timer, FrameTimer> frametimers;
	
	public FrameTimerManager(){
		this.frametimers = new HashMap<timer,FrameTimer>();
	}
	
	public void tick(){
		for(timer k : frametimers.keySet())
			frametimers.get(k).tick();
	}
	
	public void add(timer key, boolean autostart, long duration){
		frametimers.put(key, new FrameTimer(autostart, duration));
	}
	
	public FrameTimer getFrameTimer(timer key){
		return frametimers.get(key);
	}
	
	public void removeFrameTimer(timer key){
		frametimers.remove(key);
	}
	
}
