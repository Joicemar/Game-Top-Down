package gs;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable{


	private static final long serialVersionUID = 1L;
	private boolean isRunning = false;
	private Thread thread;
	private Handler handler;
	private BufferedImage level = null;
	public static int widht = 1000, height = 563;
	Camera camera;
	public int ammo = 100;
	public static int map_width;
	public static int map_height;
	
	private BufferedImage sprite_sheet = null;
	private SpriteSheet ss = null;
	BufferedImage floor = null;
	
	public Game() {
		new Window(widht, height, "Wizard Top Down", this);
		start();
		
		handler = new Handler();
		camera = new Camera(0, 0);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener(new MouseInput(handler, camera, this, ss));
//		handler.addObject(new Wizard(100, 100, ID.Player, handler));
		
		BufferedImageLoader loader = new BufferedImageLoader();
		level = loader.loadImage("/level1.png");
		/*Para os tiles do mapa*/
		sprite_sheet = loader.loadImage("/tiles 32x32.png");
		ss = new SpriteSheet(sprite_sheet);
		
		floor = ss.cropImage(2, 2, 32, 32);
		
		loadLevel(level);
	}
	
	public void tick(){
		
		for (int index = 0; index < handler.object.size(); index++) {
			if( handler.object.get(index).getId() == ID.Player) {
				camera.tick(handler.object.get(index));
			}
		}
	
		handler.tick();
	}
	

	public void render(){
		BufferStrategy bs = this.getBufferStrategy();
		if( bs == null) {
			this.createBufferStrategy( 3 );
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		//////////////////////////////////
		g.setColor( Color.GREEN);
		g.fillRect(0, 0, this.widht, this.height);
		g2d.translate(- camera.getX(), - camera.getY());
		
		for( int xx = 0; xx < 30*72; xx += 32) {
			for( int yy = 0; yy < 30*72; yy += 32) {
				g.drawImage(floor, xx, yy, null );
			}
		}
		handler.render(g);
		
		//////////////////////////////////
		g.dispose();
		bs.show();
		
	}
	/*Método que carrega tiles de 32x32 baseado nos pixels da imagem recebida*/
	private void loadLevel( BufferedImage image) {
		map_width = image.getWidth();
		map_height = image.getHeight();
		
		for( int xx = 0; xx < map_width; xx++) {
			for( int yy = 0; yy < map_height; yy++) {
				int pixel = image.getRGB(xx, yy);
				
				if( pixel == 0xffFF0000) {
					handler.addObject(new Block(xx*32, yy*32, ID.Block, ss));
				}
				if( pixel == 0xff0026FF) {
					handler.addObject(new Wizard(xx*32, yy*32, ID.Player, handler, this, ss));
				}
				if( pixel == 0xffFF6A00) {
					handler.addObject(new Enemy(xx*32, yy*32, ID.Enemy, handler, ss));
				}
				if( pixel == 0xffFFD800) {
					handler.addObject(new Create(xx*32, yy*32, ID.Create, ss ));
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
