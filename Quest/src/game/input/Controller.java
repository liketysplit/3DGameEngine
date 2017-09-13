package game.input;

import game.Game;

public class Controller {
	
	public double x, z, rotation, xa, za, rota;
	
	
	public void tick(boolean up,boolean down,boolean left,boolean right,boolean jump, boolean turnLeft, boolean turnRight){
		
		double rotSpeed = 2;
		double walkSpeed =1;
		double xMove = 0;
		double zMove = 0;
		if(up)
			zMove++; 
		if(down)
			zMove--; 
		if(left)
			xMove--; 
		if(right)
			xMove++;
		if(turnRight)
			rota += rotSpeed; 
		if(turnLeft)
			rota -= rotSpeed; 

		
//		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation) * walkSpeed);
//		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation) * walkSpeed);

		xa += (xMove * Math.cos(rotation) + zMove * Math.sin(rotation) * walkSpeed);
		za += (zMove * Math.cos(rotation) - xMove * Math.sin(rotation) * walkSpeed);
		
		x += xa;
		z += za;
		
		xa *=.1;
		za *=.1;
		rotation += rota;
		rotation *= 0.01;
	}
}
