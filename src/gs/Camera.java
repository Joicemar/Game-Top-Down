package gs;

public class Camera {

	float x, y;
	
	public Camera( float x, float y) {
		this.x= x;
		this.y = y;
	}
	
	public void tick( GameObject Object) {
		
		x += ((Object.getX() - x ) - Game.widht/2) * 0.5f;
		y += ((Object.getY() - y ) - Game.height/2) * 0.5f;
		
		if(x <= 0) x = 0;
		if(x >= Game.widht) x = Game.widht;
		if(y <= 0) y = 0;
		if(y >= Game.height+16) y = Game.height+16;
		
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}
