import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Scanner;

import display.Display;
import map.*;


public class Main {

	public static void main(String[] args){
		Game game = new Game("Nice map bruh", 1000, 400);
		game.start();
	}

}