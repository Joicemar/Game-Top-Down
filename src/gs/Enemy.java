package gs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Enemy extends GameObject{
	
	Handler handler;
	Random r = new Random();
	int choose = 0;
	int hp = 100;
	BufferedImage enemy_image;
	
	public Enemy(int x, int y, ID id, Handler handler, SpriteSheet ss) {
		super(x, y, id, ss);
		this.handler = handler;
		enemy_image = ss.cropImage(1, 1, 12, 20);
	}

	public void tick() {
		x += velX;
		y += velY;
		choose = r.nextInt(10);
		
		for( int i = 0; i < handler.object.size(); i ++) {
			GameObject tempObject = handler.object.get(i);
			
			if( tempObject.getId() == ID.Block) {
				if( getBoundsBig().intersects(tempObject.getBounds())) {
					x +=  -(velX *5) ;
					y +=  -(velY *5) ;
					velX *= -1;
					velY *= -1;
				}else if(choose == 0 ) {
					velX = r.nextInt(4 - -4) + - 4;
					velY = r.nextInt(4 - -4) + - 4;
				}
			}
			if( tempObject.getId() == ID.Bullet) {
				if( getBounds().intersects(tempObject.getBounds())) {
					this.hp -= 50;
				}
			}
		}
		
		if(this.hp <= 0) {
			handler.removeObject(this);
		}
		
		
	}

	public void render(Graphics g) {
		
		g.drawImage(enemy_image, x, y, 32, 48, null);
		
		Graphics2D g2 = (Graphics2D) g;
//		g2.setColor(Color.white);
//		g2.draw(getBoundsBig());
		
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

	public Rectangle getBoundsBig() {
		return new Rectangle(x-16, y-16, 64, 64);
	}

}
