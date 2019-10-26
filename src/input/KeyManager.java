package input;

import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyEventDispatcher  {
	
	private boolean[] keys;
	public boolean down, left, right, space, jetpack, select, pause;
	
	public KeyManager(){
		keys = new boolean[256];
	}

	public void tick(){
		down = keys[KeyEvent.VK_DOWN] || keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_LEFT] || keys[KeyEvent.VK_Q];
		right = keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_D];
		select = keys[KeyEvent.VK_SPACE];
		jetpack = keys[KeyEvent.VK_SHIFT];
		pause = keys[KeyEvent.VK_ESCAPE];
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getID() == KeyEvent.KEY_PRESSED) {
			keys[e.getKeyCode()] = true;
        } else if (e.getID() == KeyEvent.KEY_RELEASED) {
        	keys[e.getKeyCode()] = false;
        }
		
        return false;
    }
	
}
