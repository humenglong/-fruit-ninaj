package feijidazhan;

import javax.swing.JFrame;//窗口
import javax.swing.JPanel;//膜板
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class World extends JPanel {
	public static final int HEIGHT = 700;
	public static final int WIDTH = 400;

	Sky sky = new Sky(); // 天空
	Hero hero = new Hero(); // 英雄机
	FlyingObject[] enemies = {}; // 敌人(小敌机、大敌机、小蜜蜂)数组
	Bullet[] bullets = {}; // 子弹数组
	int enterIndex = 0; // 敌人入场

	public void enterAction() {
		enterIndex++; // 这个是每10毫秒加1
		if (enterIndex % 40 == 0) { // 没400毫秒敌人入场
			FlyingObject obj = nextOne();
			enemies = Arrays.copyOf(enemies, enemies.length + 1);
			enemies[enemies.length - 1] = obj;
		}
	}

	int shootIndex = 0;

	public void shootAction() {
		shootIndex++;
		if (shootIndex % 30 == 0) {
			Bullet[] bt = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length + bt.length); // 扩容(bt有几个元素就扩大几个容量)
			System.arraycopy(bt, 0, bullets, bullets.length - bt.length, bt.length); // 数组的追加
		}

	}

	public void stepAction() {
		sky.step();
		for (int i = 0; i < enemies.length; i++) {
			enemies[i].step();
		}
		for (int i = 0; i < bullets.length; i++) {
			bullets[i].step();
		}

	}

	// 程序的启动入口
	public void outOfBoundsAction() {
		int index = 0;
		FlyingObject[] enemyLives = new FlyingObject[enemies.length];
		for (int i = 0; i < enemies.length; i++) {
			FlyingObject f = enemies[i];
			if (!f.outOfBounds()) {
				enemyLives[index] = f;
				index++;
			}
		}
		enemies = Arrays.copyOf(enemyLives, index);
		index = 0;
		Bullet[] bulletsLives = new Bullet[bullets.length]; // 活着子彈的數組
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletsLives[i] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bulletsLives, index);
	}

	int score = 0;

	/** 子弹与敌人的碰撞 */
	public void bulletBangAction() { //每10毫秒走一次
		for(int i=0;i<bullets.length;i++) { //遍历所有子弹
			Bullet b = bullets[i]; //获取每一个子弹
			for(int j=0;j<enemies.length;j++) { //遍历所有敌人
				FlyingObject f = enemies[j]; //获取每一个敌人
				if(b.isLife() && f.isLife() && f.hit(b)) { //撞上了
					b.goDead(); //子弹去死
					f.goDead(); //敌人去死
					if(f instanceof Enemy) { //若被撞敌人能得分
						Enemy e = (Enemy)f;  //则强转为得分接口
						score += e.getScore(); //玩家得分
					}
					if(f instanceof Award) { //若被撞敌人为奖励
						Award a = (Award)f;  //则强转为奖励接口
						int type = a.getAwardType(); //获取奖励类型
						switch(type) { //根据奖励类型让英雄机获取不同的奖励
						case Award.DOUBLE_FIRE:   //若奖励类型为火力值
							hero.addDoubleFire(); //则英雄机增火力
							break;
						case Award.LIFE:    //若奖励类型为命
							hero.addLife(); //则英雄机增命
							break;
						}
					}
				}
			}
		}
	}
	


	

	public void action() {
		// 鼠标侦听器对象
		MouseAdapter l = new MouseAdapter() {
			/** 重写mouseMoved()鼠标移动 */
			public void mouseMoved(MouseEvent e) {
				int x = e.getX(); // 获取鼠标的x坐标
				int y = e.getY(); // 获取鼠标的y坐标
				hero.moveTo(x, y); // 英雄机随着鼠标移动
			}
		};
		this.addMouseListener(l); // 处理鼠标操作事件
		this.addMouseMotionListener(l); // 处理鼠标滑动事件
		Timer timer = new Timer(); // 定时器对象
		int intervel = 10; // 以毫秒为单位
		timer.schedule(new TimerTask() {
			public void run() { // 定时干的事--每10毫秒走一次
				 // 敌人(小敌机、大敌机、小蜜蜂)入场
				shootAction();
				enterAction();
				stepAction();
				bulletBangAction();
				//outOfBoundsAction();
				repaint(); // 重画(调用paint())
			}
		}, intervel, intervel); // 定时计划

	}

	public void paint(Graphics g) {
		sky.paintObject(g); // 画天空
		hero.paintObject(g); // 画英雄机
		for (int i = 0; i < enemies.length; i++) { // 遍历所有敌人
			enemies[i].paintObject(g); // 画敌人
		}
		for (int i = 0; i < bullets.length; i++) { // 遍历所有子弹
			bullets[i].paintObject(g); // 画子弹
		}
		g.drawString("SCORE:"+score,10,25);
		g.drawString("LIFE:"+hero.getLife(),10,45);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame();
		World world = new World();
		frame.add(world);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 700);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		world.action();
	}

	public FlyingObject nextOne() {          //随机生成敌人
		Random rand = new Random();
		int type = rand.nextInt(20);
		if (type < 5) {
			return new Bee();
		} else if (type < 12) {
			return new Airplane();
		} else {
			return new BigAirplane();
		}

	}

}
