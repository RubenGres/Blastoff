package input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys;
	public boolean down, left, right, space, jetpack, select;
	
	public KeyManager(){
		keys = new boolean[256];
	}

	public void tick(){
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_Q];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		select = keys[KeyEvent.VK_SPACE];
		jetpack = keys[KeyEvent.VK_SHIFT];
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
	
}
