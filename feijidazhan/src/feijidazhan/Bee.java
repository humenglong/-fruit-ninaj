package feijidazhan;

import java.util.Random;
import java.awt.image.BufferedImage;

public class Bee extends FlyingObject {
	static BufferedImage[] images;
	static {
		images = new BufferedImage[5];
		 for(int i =0;i<images.length;i++) {
			 images[i]=loadImage("bee"+i+".png");
		 }
		
	}
	private int xSpeed;
	private int ySpeed;
	private int awardType;

	public void step() {
		x+=xSpeed;
		y+=ySpeed;

	};

	Bee() {
		super(50, 60);
		xSpeed = 2;
		ySpeed = 2;
		Random rand = new Random();
		awardType = rand.nextInt(2);

	}
	int index=1;                               //设置下标
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
		}
		return null;
		
	}

}
