package game;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import game.graphics.Screen;
import game.input.UserInput;

public class Display extends Canvas implements Runnable{

	public static final  int WIDTH = 1024;
	public static final int HEIGHT = 768;

	public static String title = "Quest";

	private boolean running = false;

	private int[] pixels;

	private Thread thread;
	private Screen screen;
	private BufferedImage img;
	private Game game;
	private UserInput input;


	public Display (){
		
		Dimension size = new Dimension(WIDTH,HEIGHT);
		setPreferredSize(size);
		setMinimumSize(size);
		setMaximumSize(size);
		
		screen = new Screen(WIDTH,HEIGHT);
		game = new Game();
		img = new BufferedImage(WIDTH,HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		input = new UserInput();
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	public void start(){
		if(running)
			return;

			running = true;
			thread = new Thread(this);
			thread.start();
	}

	public void stop(){
		if(!running)
			return;
		running =false;

		try{
		thread.join();
		} catch (Exception e){
		e.printStackTrace();
		System.exit(0);

		}

	}

	public void run(){

		int frames = 0;
		int tickSum = 0;

		long prevTime = System.nanoTime();

		double unprocessedSecs = 0;
		double secsPerTick = 1 / 60.0;

		boolean tick = false;

		while(running){
			long currTime = System.nanoTime();
			long passTime = currTime -prevTime;

			prevTime = currTime;
			unprocessedSecs += passTime / 1000000000.0;

			while (unprocessedSecs > secsPerTick){

			tick();
			unprocessedSecs -= secsPerTick;
			tick = true;
			tickSum++;


				if (tickSum % 60 == 0){
					System.out.println(frames + "FPS");
					prevTime += 1000;
					frames = 0;
				render();
				}
			}
			if (tick){
				render();
				frames++;
			}
			render();
			frames++;
		}

	}

	private void render() {

		BufferStrategy bs = this.getBufferStrategy();

		if (bs == null) {
			createBufferStrategy(3);
			return;
		}


		screen.render(game);

		for(int i = 0; i < WIDTH*HEIGHT; i++){
			pixels[i] = screen.pixels[i];
		}

		Graphics graphics = bs.getDrawGraphics();
		graphics.drawImage(img, 0, 0, WIDTH, HEIGHT,null);
		graphics.dispose();
		bs.show();
	}

	private void tick() {

			game.tick(input.key);

	}

	public static void main(String [] args){

		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setTitle(title);

		game.start();


	}
}
