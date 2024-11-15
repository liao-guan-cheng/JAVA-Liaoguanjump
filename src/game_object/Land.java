package game_object;

import user_interface.GameScreen;

import static user_interface.GameWindow.SCREEN_HEIGHT;
import static user_interface.GameWindow.SCREEN_WIDTH;
import static util.Resource.getImage;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Land {

	private GameScreen gameScreen; // 遊戲畫面的參考
	private double x = 0; // 地面的 X 座標
	private int y; // 地面的 Y 座標
	// 縮放後地面圖片的尺寸
	private int landWidthScaled;
	private int landHeightScaled;
	private BufferedImage land; // 地面的圖片

	// 建構子
	public Land(GameScreen gameScreen) {
		this.gameScreen = gameScreen; // 設定對遊戲畫面的參考
		land = getImage("resources/land.png"); // 載入地面圖片
		y = SCREEN_HEIGHT - land.getHeight() * 2 - 4; // 計算地面的 Y 座標
		landWidthScaled = land.getWidth() * 2; // 計算縮放後的地面寬度
		landHeightScaled = land.getHeight() * 2; // 計算縮放後的地面高度
	}

	// 根據遊戲速度更新地面的位置
	public void updatePosition() {
		// 以兩位小數點四捨五入更新地面的 X 座標
		x += Math.round(gameScreen.getSpeedX() * 100d) / 100d;
	}

	// 重置地面的位置
	public void resetLand() {
		x = 0; // 將地面的 X 座標重置為 0
	}

	// 繪製地面
	public void draw(Graphics g) {
		// 使用縮放後的尺寸在當前位置繪製地面
		g.drawImage(land, (int) x, y, landWidthScaled, landHeightScaled, null);
		// 若第一塊地面結束了，則繪製另一塊地面
		if (landWidthScaled - SCREEN_WIDTH <= (int) Math.abs(x)) {
			g.drawImage(land, (int) (landWidthScaled + x), y, landWidthScaled, landHeightScaled, null);
		}
		// 若地面超出了畫面範圍，則將其 X 座標重置為 0
		if (landWidthScaled <= (int) Math.abs(x)) {
			x = 0;
		}
	}

}
