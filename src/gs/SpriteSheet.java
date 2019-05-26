package gs;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	
	BufferedImage image;

	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	public BufferedImage cropImage(int col,int row, int widht, int height ) {
		
		return image.getSubimage((col*32)-32, (row*32)-32, widht, height);
		
	}

}
