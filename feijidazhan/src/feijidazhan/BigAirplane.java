package feijidazhan;

import java.util.Random;
import java.awt.image.BufferedImage;

public class BigAirplane extends FlyingObject  implements  Enemy{
	static  BufferedImage[] images;    //静态变量BufferedImage类型的名为image
	static {  
		images = new BufferedImage[5];
		for(int i =0;i<images.length;i++) {
			images[i] = loadImage("bigplane"+i+".png");//加载图片的
		}
		
	}

	private int speed;

	public void step() {
		y+=speed;

	};

	BigAirplane() {
		super(99, 69);
		speed = 2;
	}
	int index=1;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[0];
		}
		if(isDead()) {
			BufferedImage img = images[index++];
			if(index==images.length) {
				state=REMOVE;
				
			}
			return img;
		}	return null;
	}
	public int getScore() {
		return 3;
	}

}
