package feijidazhan;
import java.awt.image.BufferedImage;


public class Bullet extends FlyingObject{
	static BufferedImage image;
	static {
		
		image=loadImage("bullet.png");
	}
	

	private int speed;

	public void step() {
		y-=speed;
		
	};
	Bullet(int x,int y){
		super(14,8,x,y);
		height=14;
		width=8;
		this.x=x;
		this.y=y;
		speed=3;		
	}
	public BufferedImage getImage() {
		if(isLife()) {
			return image;
		}else {
			state= REMOVE;

		}
		return null;
		
	}
	public boolean outOfBounds() {
		return this.y<=-this.height;
	}

}
