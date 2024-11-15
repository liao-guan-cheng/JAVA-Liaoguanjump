package game_object; // 套件名稱為 game_object，這裡存放的是遊戲物件相關的類別

import user_interface.GameScreen; // 導入 user_interface 套件中的 GameScreen 類別

import static user_interface.GameScreen.GROUND_Y; // 導入 GameScreen 類別的 GROUND_Y 常數
import static user_interface.GameWindow.SCREEN_WIDTH; // 導入 GameWindow 類別的 SCREEN_WIDTH 常數
import static util.Resource.getImage; // 導入 util 套件中的 getImage 靜態方法

import java.awt.Color; // 導入 java.awt 中的 Color 類別
import java.awt.Graphics; // 導入 java.awt 中的 Graphics 類別
import java.awt.Rectangle; // 導入 java.awt 中的 Rectangle 類別
import java.awt.image.BufferedImage; // 導入 java.awt.image 中的 BufferedImage 類別
import java.util.ArrayList; // 導入 java.util 中的 ArrayList 類別
import java.util.Iterator; // 導入 java.util 中的 Iterator 類別
import java.util.List; // 導入 java.util 中的 List 類別

import manager.EnemyManager; // 導入 manager 套件中的 EnemyManager 類別

public class Obstacles { // 定義名為 Obstacles 的類別

	private class Obstacle { // 定義內部私有類別 Obstacle

		private BufferedImage obstacleImage; // 障礙物圖片
		private double x; // 障礙物 x 座標
		private int y; // 障礙物 y 座標

		private Obstacle(BufferedImage obstacleImage, double x, int y) { // Obstacle 類別的建構函式
			this.obstacleImage = obstacleImage; // 初始化障礙物圖片
			this.x = x; // 初始化 x 座標
			this.y = y; // 初始化 y 座標
		}

	}

	// 用於計算障礙物碰撞框的數字
	private static final double HITBOX_X = 2.7; // x 座標的倍率
	private static final int HITBOX_Y = 25; // y 座標的偏移量
	// 障礙物精靈的數量
	private static final int OBSTACLES_AMOUNT = 9; // 障礙物圖片的總數量
	// 最大的障礙物組數
	private static final int MAX_OBSTACLE_GROUP = 3; // 最大的障礙物組數

	private EnemyManager eManager; // 敵人管理器
	private GameScreen gameScreen; // 遊戲畫面
	private List<Obstacle> obstacles; // 障礙物列表

	// Obstacles 類別的建構函式，初始化障礙物
	public Obstacles(GameScreen gameScreen, EnemyManager eManager) {
		this.eManager = eManager; // 初始化敵人管理器
		this.gameScreen = gameScreen; // 初始化遊戲畫面
		obstacles = new ArrayList<Obstacle>(); // 初始化障礙物列表
	}

	// 更新障礙物的位置
	public void updatePosition() {
		for (Iterator<Obstacle> i = obstacles.iterator(); i.hasNext();) {
			Obstacle obstacle = i.next(); // 獲取下一個障礙物
			obstacle.x += Math.round(gameScreen.getSpeedX() * 100d) / 100d; // 更新 x 座標
			if ((int) obstacle.x + obstacle.obstacleImage.getWidth() < 0) { // 如果障礙物超出畫面左側
				i.remove(); // 從列表中移除障礙物
			}
		}
	}

	// 檢查是否有足夠的空間創建障礙物
	public boolean spaceAvailable() {
		for (Iterator<Obstacle> i = obstacles.iterator(); i.hasNext();) {
			Obstacle obstacle = i.next(); // 獲取下一個障礙物
			if (SCREEN_WIDTH - (obstacle.x + obstacle.obstacleImage.getWidth()) < eManager
					.getDistanceBetweenEnemies()) { // 如果剩餘空間不足以容納障礙物
				return false; // 返回 false
			}
		}
		return true; // 返回 true
	}

	// 創建障礙物
	public boolean createObstacles() {
		if (Math.random() * 100 < eManager.getObstaclesPercentage()) { // 根據機率創建障礙物
			// 隨機決定障礙物組數
			for (int i = 0,
					numberOfObstacles = (int) (Math.random() * MAX_OBSTACLE_GROUP + 1); i < numberOfObstacles; i++) {
				BufferedImage obstacleImage = getImage(
						"resources/障礙物-" + (int) (Math.random() * OBSTACLES_AMOUNT + 1) + ".png"); // 隨機選擇障礙物圖片
				int x = SCREEN_WIDTH; // 初始化 x 座標
				int y = GROUND_Y - obstacleImage.getHeight(); // 初始化 y 座標
				// 如果是組中的第二或第三個障礙物，根據前一個障礙物的寬度計算 x 座標
				if (i > 0)
					x = (int) obstacles.get(obstacles.size() - 1).x
							+ obstacles.get(obstacles.size() - 1).obstacleImage.getWidth();
				obstacles.add(new Obstacle(obstacleImage, x, y)); // 將新創建的障礙物加入列表中
			}
			return true; // 返回 true
		}
		return false; // 返回 false
	}

	// 檢查是否發生了碰撞
	public boolean isCollision(Rectangle liaoHitBox) {
		for (Iterator<Obstacle> i = obstacles.iterator(); i.hasNext();) {
			Obstacle obstacle = i.next(); // 獲取下一個障礙物
			Rectangle obstacleHitBox = getHitbox(obstacle); // 獲取障礙物的碰撞框
			if (obstacleHitBox.intersects(liaoHitBox)) // 如果障礙物的碰撞框與恐龍的碰撞框相交
				return true; // 返回 true
		}
		return false; // 返回 false
	}

	// 獲取障礙物的碰撞框
	private Rectangle getHitbox(Obstacle obstacle) {
		// 通過計算來調整障礙物的碰撞框，使其與障礙物的形狀更匹配
		return new Rectangle((int) obstacle.x + (int) (obstacle.obstacleImage.getWidth() / HITBOX_X),
				obstacle.y + obstacle.obstacleImage.getHeight() / HITBOX_Y,
				obstacle.obstacleImage.getWidth() - (int) (obstacle.obstacleImage.getWidth() / HITBOX_X) * 2,
				obstacle.obstacleImage.getHeight() - obstacle.obstacleImage.getHeight() / HITBOX_Y);
	}

	// 清除障礙物
	public void clearObstacles() {
		obstacles.clear(); // 清空障礙物列表
	}

	// 繪製障礙物
	public void draw(Graphics g) {
		for (Iterator<Obstacle> i = obstacles.iterator(); i.hasNext();) {
			Obstacle obstacle = i.next(); // 獲取下一個障礙物
			g.drawImage(obstacle.obstacleImage, (int) (obstacle.x), obstacle.y, null); // 繪製障礙物圖片
		}
	}

	// 繪製障礙物的碰撞框
	public void drawHitbox(Graphics g) {
		g.setColor(Color.RED); // 設置顏色為紅色
		for (Iterator<Obstacle> i = obstacles.iterator(); i.hasNext();) {
			Obstacle obstacle = i.next(); // 獲取下一個障礙物
			Rectangle obstacleHitBox = getHitbox(obstacle); // 獲取障礙物的碰撞框
			g.drawRect(obstacleHitBox.x, obstacleHitBox.y, (int) obstacleHitBox.getWidth(),
					(int) obstacleHitBox.getHeight()); // 繪製障礙物的碰撞框
		}
	}

}
