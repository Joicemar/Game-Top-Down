package gs;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Block extends GameObject {

	public Block(int x, int y, ID id) {
		super(x, y, id );
	}

	public void tick() {
		
	}

	public void render(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 32, 48);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, 32, 48);
	}

}
