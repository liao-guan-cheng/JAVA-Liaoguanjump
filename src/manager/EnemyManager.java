package manager; // 套件名稱為 manager，這裡存放的是管理類別

import game_object.Cats; // 導入 game_object 套件中的 Cats 類別
import game_object.Obstacles; // 導入 game_object 套件中的 Obstacles 類別
import misc.EnemyType; // 導入 misc 套件中的 EnemyType 列舉型別
import user_interface.GameScreen; // 導入 user_interface 套件中的 GameScreen 類別

import java.awt.Graphics; // 導入 awt 套件中的 Graphics 類別
import java.awt.Rectangle; // 導入 awt 套件中的 Rectangle 類別

public class EnemyManager { // 定義一個名為 EnemyManager 的類別

	// 增加新敵人的機率增量
	private static final double PERCENTAGE_INC = 0.0001;
	private static final double DISTANCE_DEC = -0.005; // 敵人間距減少量
	private static final int MINIMUM_DISTANCE = 250; // 最小距離

	private double distanceBetweenEnemies = 750; // 敵人之間的初始距離
	private double obstaclesPercentage = 2; // 障礙物出現的初始機率
	private double catsPercentage = 1; // 貓出現的初始機率

	private Obstacles obstacles; // 障礙物物件
	private Cats cats; // 貓物件

	// 建構函式，初始化障礙物和貓物件
	public EnemyManager(GameScreen gameScreen) {
		obstacles = new Obstacles(gameScreen, this); // 創建障礙物物件
		cats = new Cats(gameScreen, this); // 創建貓物件
	}

	// 取得敵人之間的距離
	public double getDistanceBetweenEnemies() {
		return distanceBetweenEnemies;
	}

	// 取得障礙物出現的機率
	public double getObstaclesPercentage() {
		return obstaclesPercentage;
	}

	// 取得貓出現的機率
	public double getCatsPercentage() {
		return catsPercentage;
	}

	// 更新敵人位置的方法
	public void updatePosition() {
		obstaclesPercentage += PERCENTAGE_INC; // 增加障礙物出現的機率
		catsPercentage += PERCENTAGE_INC; // 增加貓出現的機率
		if (distanceBetweenEnemies > MINIMUM_DISTANCE) // 如果敵人之間的距離大於最小距離
			distanceBetweenEnemies += DISTANCE_DEC; // 減少敵人之間的距離
		obstacles.updatePosition(); // 更新障礙物位置
		cats.updatePosition(); // 更新貓位置
		if (obstacles.spaceAvailable() && cats.spaceAvailable()) { // 如果障礙物和貓的空間都可用
			// 隨機選擇新敵人的類型
			switch (EnemyType.values()[(int) (Math.random() * EnemyType.values().length)]) {
				case OBSTACLE: // 如果是障礙物
					if (obstacles.createObstacles()) // 創建障礙物
						break;
				case CAT: // 如果是貓
					if (cats.createCat()) // 創建貓
						break;
				default:
					obstacles.createObstacles(); // 預設創建障礙物
					break;
			}
		}
	}

	// 判斷是否發生碰撞的方法
	public boolean isCollision(Rectangle hitBox) {
		if (obstacles.isCollision(hitBox) || cats.isCollision(hitBox)) // 如果發生了障礙物或貓的碰撞
			return true; // 返回 true
		return false; // 否則返回 false
	}

	// 清除敵人的方法
	public void clearEnemy() {
		obstacles.clearObstacles(); // 清除障礙物
		cats.clearCats(); // 清除貓
	}

	// 繪製敵人的方法
	public void draw(Graphics g) {
		obstacles.draw(g); // 繪製障礙物
		cats.draw(g); // 繪製貓
	}

	// 繪製碰撞框的方法
	public void drawHitbox(Graphics g) {
		obstacles.drawHitbox(g); // 繪製障礙物的碰撞框
		cats.drawHitbox(g); // 繪製貓的碰撞框
	}
}
