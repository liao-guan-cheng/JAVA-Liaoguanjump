package misc; // 套件名稱為 misc，這裡存放的是與遊戲相關的雜項類別

import java.awt.image.BufferedImage; // 導入用於處理圖像的 BufferedImage 類別
import java.util.ArrayList; // 導入用於建立動態陣列的 ArrayList 類別
import java.util.List; // 導入用於建立列表的 List 類別

public class Animation { // 定義一個名為 Animation 的類別

	private List<BufferedImage> sprites; // 儲存動畫圖像的列表
	private int currentSpriteIndex = 0; // 當前顯示的動畫圖像在列表中的索引
	private int updateTime; // 兩幀動畫之間的更新時間間隔（以毫秒為單位）
	private long lastUpdateTime = 0; // 上次更新動畫的時間戳

	public Animation(int updateTime) { // 建構函式，接受一個參數 updateTime，表示動畫的更新間隔
		this.updateTime = updateTime; // 將 updateTime 參數指定給類別的 updateTime 屬性
		sprites = new ArrayList<BufferedImage>(); // 創建一個 ArrayList 來存儲圖像
	}

	// 更新動畫的當前幀
	public void updateSprite() {
		if (System.currentTimeMillis() - lastUpdateTime >= updateTime) { // 如果距離上次更新動畫的時間超過了 updateTime
			currentSpriteIndex++; // 將 currentSpriteIndex 增加
			if (currentSpriteIndex >= sprites.size()) // 如果 currentSpriteIndex 超出了列表的大小
				currentSpriteIndex = 0; // 將 currentSpriteIndex 重置為 0
			lastUpdateTime = System.currentTimeMillis(); // 更新上次更新動畫的時間戳
		}
	}

	// 將一個圖像添加到動畫的 sprites 列表中
	public void addSprite(BufferedImage sprite) {
		sprites.add(sprite); // 將圖像添加到 sprites 列表中
	}

	// 獲取當前顯示的動畫圖像
	public BufferedImage getSprite() {
		if (sprites.size() > 0) { // 如果 sprites 列表中有圖像
			return sprites.get(currentSpriteIndex); // 返回當前 currentSpriteIndex 指向的圖像
		}
		return null; // 否則返回 null
	}

}
