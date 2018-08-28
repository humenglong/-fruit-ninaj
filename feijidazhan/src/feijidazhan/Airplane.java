package feijidazhan;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Airplane extends FlyingObject implements Enemy{
	static  BufferedImage[] images;    //静态变量BufferedImage类型的名为image
	static {  
		images = new BufferedImage[5];
		for(int i =0;i<images.length;i++) {
			images[i] = loadImage("airplane"+i+".png");//加载图片的
		}
		
	}

	private int speed;

	Airplane(){
		super(36,49);
		speed=2;
	}

	int index=1;
	public BufferedImage getImage(){
		if(isLife()) {                     //判断当前状态是否什么
			return images[0];
		}else if(isDead()) {
			BufferedImage img =images[index++];              //切换
			if(index==images.length) {
				state=REMOVE;
			}
					return img;
		}
		
		return null;
	}
	public void step() {
		y+=speed;
	}
	public int getScore() {
		return 1;
	}
}

