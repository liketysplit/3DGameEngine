package game.graphics;

import game.Game;

public class Render3D extends Render{

	public double [] zBuffer;
	private double renderDistance = 8000;
	
	public Render3D(int width, int height) {
		super(width, height);
		zBuffer =  new double[width*height];

	}

	public void floor(Game game){

		double rotation = game.controls.rotation;
		double cos = Math.cos(rotation);
		double sin = Math.sin(rotation);
		double moveFB = game.controls.z;
		double moveLR = game.controls.x;

		for (int i = 0; i < height; i++){

			double yDepth = (i - height / 2.0) / height;
			double cielingPos = 20;
			double floorPos = 8;

			double z = floorPos / yDepth;

			if(yDepth < 0){
				z = cielingPos / -yDepth;
			}




			for (int j = 0; j < width; j++){

				double xDepth = (j - width / 2.0) / height;
				xDepth *= z;
				double x =xDepth * cos + z * sin + moveLR;
				double y = z * cos - xDepth * sin + moveFB;
				int yPixels = (int) y;
				int xPixels = (int) x;
				zBuffer[j + i * width] = z;
				pixels[j + i * width] = ((xPixels & 15) * 16) | ((yPixels & 15) * 16) << 8;
				

			}
		}

	}

	public void cieling(){

	}
	
	public void renderDistanceLimiter() {
		for (int i = 0; i < width*height;  i++){
			int color = pixels[i];
			int brightness = (int) (renderDistance / (zBuffer[i]));
			
			if (brightness < 0)
				brightness = 0;
			if (brightness > 255)
				brightness = 255;
			
			int r = (color >> 16) & 0xff;
			int g = (color >> 8) & 0xff;
			int b = (color) & 0xff;
			
			r = r* brightness / 255;
			b = b* brightness / 255;
			g = g* brightness / 255;
			
			pixels [i] = r << 16 | g << 8 | b;
			
			
		}
	}
}
