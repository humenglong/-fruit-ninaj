package feijidazhan;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;
import javax.imageio.ImageIO;

public abstract class FlyingObject {
	protected int height;
	protected int width;
	protected int x;
	protected int y;
	public static final int LIFE = 0; // 设计三种状态
	public static final int DEAD = 1;
	public static final int REMOVE = 2;
	int state = LIFE;

	public boolean isLife() {
		return state == LIFE;
	}

	public boolean isDead() {
		return state == DEAD;
	}

	public boolean isRemove() {
		return state == REMOVE;
	}

	FlyingObject(int height, int width, int x, int y) {
		this.height = height;
		this.width = width;
		this.x = x;
		this.y = y;
	}

	FlyingObject(int height, int width) {
		Random rand = new Random();
		x = rand.nextInt(400 - this.width);
		y = -this.height;
	}

	abstract void step();

	public static BufferedImage loadImage(String fileName) {// 读取图片
		try {
			BufferedImage img = ImageIO.read(FlyingObject.class.getResource(fileName)); // 同包中读文件
			return img;
		} catch (Exception e) { // 捉取异常捉取主函数
			e.printStackTrace();
			throw new RuntimeException();
		}
	}

	public abstract BufferedImage getImage();

	public void paintObject(Graphics g) {
		g.drawImage(this.getImage(), this.x, this.y, null);

	}

	public boolean outOfBounds() {
		return this.y >= World.HEIGHT;
	}

	public boolean hit(FlyingObject other) {
		int x1 = this.x - other.width; // x1:敌人的x-子弹的宽
		int x2 = this.x + this.width; // x2:敌人的x+敌人的宽
		int y1 = this.y - other.height; // y1:敌人的y-子弹的高
		int y2 = this.y + this.height; // y2:敌人的y+敌人的高
		int x = other.x; // x:子弹的x
		int y = other.y; // y:子弹的y
		

		return (x >= x1 && x <= x2 && y >= y1 && y <= y2); // x在x1与x2之间，并且，y在y1与y2之间，即为撞上了 //y:子弹的y

	}

	public void goDead() {
		state = DEAD;
	}
}
