package feijidazhan;
import java.awt.image.BufferedImage;

public class Hero extends FlyingObject{
	static  BufferedImage[] images;    //静态变量BufferedImage类型的名为image
	static {  
		images = new BufferedImage[2];
		for(int i =0;i<images.length;i++) {
			images[i] = loadImage("hero"+i+".png");//加载图片的
		}
		
	}

	private int life;
	private int doubleFire;
	Hero(){
		super(97,124,400,140);
		int life=3;
		int doubleFire=0;
	}
	public void moveTo(int x,int y) {
		this.x = x-this.width/2;  //英雄机的x=鼠标的x-1/2英雄机的宽
		this.y = y-this.height/2; //英雄机的y=鼠标的y-1/2英雄机的高
	}
	public void step() {
	}
	int index=0;
	public BufferedImage getImage() {
		if(isLife()) {
			return images[index++%images.length];     //来回切换英雄机的图片0和1
		} 
		return null;
	}
	public Bullet[] shoot() {
		int xstep = this.width/4;
		int ystep=20;                      //设置子弹与英雄机的距离
		if(doubleFire >0) {
			Bullet[] bs = new Bullet[2];
			bs[0]=new Bullet(this.x+xstep,this.y-ystep);
			bs[1]=new Bullet(this.x+3*xstep,this.y-ystep);
			return bs;
		}else {
			Bullet[] bs = new Bullet[1];
			bs[0]=new Bullet(this.x+2*xstep,this.y-ystep);
			return bs;
		}
		
	}
	public int addLife() {
		return life++;
	}
	public int addDoubleFire() {
		return doubleFire+=40;
	}
	public int getLife() {
		return life;
	}


 
}
