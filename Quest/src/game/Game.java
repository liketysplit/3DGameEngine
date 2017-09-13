package game;

import java.awt.event.KeyEvent;

import game.input.Controller;

public class Game {

	public int time;
	public boolean up, down, left, right, jump, rightTurn, leftTurn;
	
	public Controller controls;
	
	public Game (){
		
		controls = new Controller();
		
		
	}
	
	

	public void tick(boolean[] key){
		time ++;
		
		up = key[KeyEvent.VK_UP] || key[KeyEvent.VK_W];
		down = key[KeyEvent.VK_DOWN] || key[KeyEvent.VK_S];
		left = key[KeyEvent.VK_A];
		right =  key[KeyEvent.VK_D];
		leftTurn = key[KeyEvent.VK_LEFT];
		rightTurn = key[KeyEvent.VK_RIGHT];
		jump = key[KeyEvent.VK_SPACE];
		
		controls.tick(up, down, left, right, jump, leftTurn, rightTurn);
	}

}
