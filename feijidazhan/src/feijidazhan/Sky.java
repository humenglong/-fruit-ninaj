package feijidazhan;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Sky extends FlyingObject{
	static  BufferedImage image;    //静态变量BufferedImage类型的名为image
	static {  
			image = loadImage("background.png");//加载图片的
	}

	private int speed;
	private int y1;
	Sky(){
		super(700,400,0,0);
		speed=1;
		y1=-this.height;
	}
	public void step() {
		y+=speed;
		y1+=speed;
		if(y>=World.HEIGHT) {
			y=-World.HEIGHT;
		}if(y1>World.HEIGHT) {
			y1=-World.HEIGHT;
		}
	}
	public BufferedImage getImage() {
		return image;
	}
	public void paintObject(Graphics g) {
		g.drawImage(this.getImage(),this.x,this.y,null);
		g.drawImage(this.getImage(),this.x,this.y1,null);
	}
}
