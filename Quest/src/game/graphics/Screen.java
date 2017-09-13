package game.graphics;

import java.util.Random;

import game.Game;

public class Screen extends Render{

	public Render3D render3D;

	public Screen(int width, int height) {
		super(width, height);
		render3D = new Render3D(width,height);
	}

	public void render(Game game){

		for(int i = 0; i < width*height; i++){
			pixels[i] = 0;
		}



		render3D.floor(game);
		render3D.renderDistanceLimiter();
		draw(render3D, 0 , 0);
	}

}
