package gs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Block extends GameObject {

	BufferedImage block_image;
	
	public Block(int x, int y, ID id, SpriteSheet ss) {
		super(x, y, id, ss );
		block_image = ss.cropImage(2, 1, 32, 32);
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.drawImage(block_image, x, y, null);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 32);
	}

}
