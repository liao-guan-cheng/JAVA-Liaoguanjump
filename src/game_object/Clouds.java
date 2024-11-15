package game_object; // 套件名稱為 game_object，這裡存放的是遊戲物件相關的類別

import user_interface.GameScreen; // 導入 user_interface 套件中的 GameScreen 類別

import static user_interface.GameWindow.SCREEN_HEIGHT; // 導入 GameWindow 類別的 SCREEN_HEIGHT 常數
import static user_interface.GameWindow.SCREEN_WIDTH; // 導入 GameWindow 類別的 SCREEN_WIDTH 常數
import static util.Resource.getImage; // 導入 util 套件中的 getImage 靜態方法

import java.awt.Color; // 導入 java.awt 中的 Color 類別
import java.awt.Graphics; // 導入 java.awt 中的 Graphics 類別
import java.awt.image.BufferedImage; // 導入 java.awt.image 中的 BufferedImage 類別
import java.util.HashSet; // 導入 java.util 中的 HashSet 類別
import java.util.Iterator; // 導入 java.util 中的 Iterator 類別
import java.util.Set; // 導入 java.util 中的 Set 類別

public class Clouds { // 定義名為 Clouds 的類別

	private class Cloud { // 定義內部私有類別 Cloud

		private BufferedImage cloudImage; // 代表雲的圖片
		private double x; // 雲的 x 座標
		private int y; // 雲的 y 座標

		private Cloud(BufferedImage cloudImage, double x, int y) { // Cloud 類別的建構函式
			this.cloudImage = cloudImage; // 初始化雲的圖片
			this.x = x; // 初始化 x 座標
			this.y = y + 2; // 初始化 y 座標
		}

	}

	// 最大的雲朵數量
	private static final int CLOUDS_AMOUNT = 2; // 最大的雲朵數量
	// 出現雲朵的機率
	private static final double CLOUD_PERCENTAGE = 0.3; // 出現雲朵的機率

	private GameScreen gameScreen; // 遊戲畫面
	private Set<Cloud> clouds; // 雲朵集合
	// 調整後的雲朵寬度和高度
	private int cloudWidthScaled; // 調整後的雲朵寬度
	private int cloudHeightScaled; // 調整後的雲朵高度

	// Clouds 類別的建構函式，初始化雲朵
	public Clouds(GameScreen gameScreen) {
		this.gameScreen = gameScreen; // 初始化遊戲畫面
		clouds = new HashSet<Cloud>(); // 初始化雲朵集合
		cloudWidthScaled = getImage("resources/cloud.png").getWidth() * 2; // 設置調整後的雲朵寬度
		cloudHeightScaled = getImage("resources/cloud.png").getHeight() * 2; // 設置調整後的雲朵高度

	}

	// 更新雲朵的位置
	public void updatePosition() {
		isOutOfScreen(); // 檢查雲朵是否超出畫面範圍
		createClouds(); // 創建新的雲朵
	}

	// 檢查雲朵是否超出畫面範圍並移除超出範圍的雲朵
	private void isOutOfScreen() {
		for (Iterator<Cloud> i = clouds.iterator(); i.hasNext();) {
			Cloud cloud = (Cloud) i.next(); // 獲取下一個雲朵
			cloud.x += gameScreen.getSpeedX() / 7; // 根據遊戲速度更新雲朵的 x 座標
			if (cloud.x + cloudWidthScaled < 0) { // 如果雲朵超出畫面左側
				i.remove(); // 從雲朵集合中移除該雲朵
			}
		}
	}

	// 創建新的雲朵
	private void createClouds() {
		if (clouds.size() < CLOUDS_AMOUNT) { // 如果當前雲朵數量小於最大雲朵數量
			for (Iterator<Cloud> i = clouds.iterator(); i.hasNext();) {
				Cloud temp = (Cloud) i.next(); // 獲取下一個雲朵
				// 檢查是否有足夠的空間用於創建新的雲朵
				if (temp.x >= SCREEN_WIDTH - cloudWidthScaled)
					return; // 如果沒有足夠的空間，則不創建新的雲朵
			}
			if (Math.random() * 100 < CLOUD_PERCENTAGE) // 根據機率創建新的雲朵
				clouds.add(new Cloud(getImage("resources/cloud.png"), SCREEN_WIDTH,
						(int) (Math.random() * (SCREEN_HEIGHT / 2)))); // 將新的雲朵添加到雲朵集合中
		}
	}

	// 清除所有雲朵
	public void clearClouds() {
		clouds.clear(); // 清空雲朵集合
	}

	// 繪製所有雲朵
	public void draw(Graphics g) {
		for (Iterator<Cloud> i = clouds.iterator(); i.hasNext();) {
			Cloud cloud = (Cloud) i.next(); // 獲取下一個雲朵
			g.drawImage(cloud.cloudImage, (int) cloud.x, cloud.y, cloudWidthScaled, cloudHeightScaled, null); // 繪製雲朵圖片
		}
	}

	// 繪製所有雲朵的碰撞框（用綠色方框表示）
	public void drawHitbox(Graphics g) {
		g.setColor(Color.GREEN); // 設置顏色為綠色
		for (Iterator<Cloud> i = clouds.iterator(); i.hasNext();) {
			Cloud cloud = (Cloud) i.next(); // 獲取下一個雲朵
			g.drawRect((int) cloud.x, cloud.y, cloudWidthScaled, cloudHeightScaled); // 繪製雲朵的碰撞框
		}
	}

}
