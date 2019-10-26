package states;

import java.awt.Button;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import display.Display;

public class StartMenuState extends State implements ActionListener{

	private Canvas canvasBckp = null;	
	private JPanel menuPanel = null;
	
	private JButton[] buttons;
	
	public StartMenuState() {
		super();
		this.buttons = new JButton[3];
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {}

	@Override
	public void init() {
		this.menuPanel = createPanel();
		this.initialized = true;
	}	

	@Override
	public void prepare() {
		Display disp = this.handler.getGame().getDisplay();
		this.canvasBckp = disp.getCanvas(); //save canvas for later
		disp.getFrame().remove(this.canvasBckp); // remove the canvas
		
		disp.getFrame().add(menuPanel);
		disp.getFrame().pack();
	}
	
	@Override
	public void stop() {
		//switch canvas back
		Display disp = this.handler.getGame().getDisplay();
		disp.getFrame().remove(menuPanel);
		disp.getFrame().add(this.canvasBckp);
		disp.getFrame().pack();
		this.canvasBckp = null;
	}
	
	private JPanel createPanel() {
		JPanel menuPanel = new JPanel();
		
		this.buttons[0] = new JButton("new game");
		this.buttons[1] = new JButton("load game");
		this.buttons[2] = new JButton("quit");
		
		for(int i =0; i < this.buttons.length; i++) {
			this.buttons[i].addActionListener(this);
			menuPanel.add(buttons[i]);
		}
		
		return menuPanel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton b = (JButton) e.getSource();
		if(b == this.buttons[0]) {
			
			GameState gameState = new GameState();
			this.handler.getGame().setGameState(gameState);
			State.setState(this.handler.getGame().getGameState());
			
		} else if (b == this.buttons[1]){
			
		} else if (b == this.buttons[2]) {
			this.handler.getGame().getDisplay().getFrame().dispose();
			System.exit(0);
		}
	}

}
