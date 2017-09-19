package utils;
import java.util.HashMap;
import java.util.Map;

public class FrameTimerManager {

	//frametimer manager
	private Map<String,FrameTimer> frametimers;
	
	public FrameTimerManager(){
		this.frametimers = new HashMap<String,FrameTimer>();
	}
	
	public void tick(){
		for(String str : frametimers.keySet())
			frametimers.get(str).tick();
	}
	
	public void add(String key, boolean autostart, long stopTime){
		frametimers.put(key, new FrameTimer(autostart, stopTime));
	}
	
	public FrameTimer getFrameTimer(String key){
		return frametimers.get(key);
	}
	
	public void removeFrameTimer(String key){
		frametimers.remove(key);
	}
	
}
