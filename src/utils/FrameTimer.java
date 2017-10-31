package utils;

public class FrameTimer {
	
	private long stopTime;
	private boolean running;
	private long elapsedTime = 0;
	
	public FrameTimer(boolean autostart, long stopTime){
		this.running = autostart;
		this.stopTime = stopTime;
	}
	
	public void start(){
		this.running = true;
	}
	
	public void stop(){
		this.running = false;
	}
	
	public void reset(){
		stop();
		elapsedTime = 0;
	}
	
	public long getElapsedTime(){
		return elapsedTime;
	}
	
	public long getStopTime(){
		return stopTime;
	}
	
	public void restart(){
		elapsedTime = 0;
		this.running = true;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	public void tick(){
		if(running)
			elapsedTime++;
		
		if(elapsedTime >= stopTime) {
			stop();
		}
			
	}
	
}
