package gs;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{


	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private BufferedImage level = null;
	
	public Game() {
		new Window(1000, 563, "Wizard Top Down", this);
		start();
		
		handler = new Handler();
		this.addKeyListener(new KeyInput(handler));
//		handler.addObject(new Wizard(100, 100, ID.Player, handler));
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level1.png");
		
		loadLevel(level);
	}
	
	public void tick(){
		
		handler.tick();
	}
	

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if( bs == null) {
			this.createBufferStrategy( 3 );
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		//////////////////////////////////
		g.setColor( Color.GREEN);
		g.fillRect(0, 0, 1000, 563);
		
		handler.render(g);
		
		//////////////////////////////////
		g.dispose();
		bs.show();
		
	}
	/*Método que carrega tiles de 32x32 baseado nos pixels da imagem recebida*/
	private void loadLevel( BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		
		for( int xx = 0; xx < width; xx++) {
			for( int yy = 0; yy < height; yy++) {
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if( red == 255) {
					handler.addObject(new Block(xx*32, yy*32, ID.Block));
				}
				if( blue == 255) {
					handler.addObject(new Wizard(xx*32, yy*32, ID.Player, handler));
				}
			}
		}
	}
	
	public void start() {
		isRunning = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		isRunning = false;
		try {
			thread.join();
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning) {
			long now = System.nanoTime();
			delta+= ( now - lastTime) / ns;
			lastTime = now;
			while( delta >= 1) {
				tick();
				delta --;
			}
			render();
			frames ++;
			
			if( System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frames = 0;
			}
			
		}
		stop();
		
	}
	
	public static void main(String[] args) {
		new Game();
	}



	
}
