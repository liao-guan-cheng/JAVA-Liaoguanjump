package game_object; // 套件名稱為 game_object，這裡存放的是遊戲物件相關的類別

import misc.Animation; // 導入 misc 套件中的 Animation 類別
import user_interface.GameScreen; // 導入 user_interface 套件中的 GameScreen 類別

import static user_interface.GameScreen.GROUND_Y; // 導入 GameScreen 類別的 GROUND_Y 常數
import static user_interface.GameWindow.SCREEN_WIDTH; // 導入 GameWindow 類別的 SCREEN_WIDTH 常數
import static util.Resource.getImage; // 導入 util 套件中的 getImage 靜態方法

import java.awt.Color; // 導入 java.awt 中的 Color 類別
import java.awt.Graphics; // 導入 java.awt 中的 Graphics 類別
import java.awt.Rectangle; // 導入 java.awt 中的 Rectangle 類別
import java.util.ArrayList; // 導入 java.util 中的 ArrayList 類別
import java.util.Iterator; // 導入 java.util 中的 Iterator 類別
import java.util.List; // 導入 java.util 中的 List 類別

import manager.EnemyManager; // 導入 manager 套件中的 EnemyManager 類別

public class Cats { // 定義名為 Cats 的類別

	private class Cat { // 定義內部私有類別 Cat

		private double x; // 貓的 x 座標
		private int y; // 貓的 y 座標
		private Animation cat_run; // 貓的奔跑動畫

		private Cat(double x, int y, Animation cat_run) { // Cat 類別的建構函式
			this.x = x; // 初始化 x 座標
			this.y = y; // 初始化 y 座標
			this.cat_run = cat_run; // 初始化奔跑動畫
		}

	}

	// 用於計算碰撞框的奇怪數字
	// 這個是兩個貓的精靈之間的差異，一個是翅膀向上，一個是向下，這樣貓就不會跳得太瘋狂，這樣做可能更簡單，但...
	private static final int HITBOX_MODELS_DIFF_IN_Y = -10;
	// 用於計算貓的碰撞框的數字，一個是翅膀向上，一個是向下
	private static final int[] HITBOX_WINGS_UP = { 20, 4, -40, -20 };
	private static final int[] HITBOX_WINGS_DOWN = { 20, 4, -40, -28 };
	// 檢查當前精靈的值
	private final int WINGS_DOWN_HEIGHT = getImage("resources/cat-1.png").getHeight();

	private EnemyManager eManager; // 敵人管理器
	private GameScreen gameScreen; // 遊戲畫面
	private List<Cat> cats; // 貓的列表

	// 建構函式，初始化貓類
	public Cats(GameScreen gameScreen, EnemyManager eManager) {
		this.eManager = eManager; // 初始化敵人管理器
		this.gameScreen = gameScreen; // 初始化遊戲畫面
		cats = new ArrayList<Cat>(); // 初始化貓的列表
	}

	// 更新貓的位置
	public void updatePosition() {
		for (Iterator<Cat> i = cats.iterator(); i.hasNext();) {
			Cat cat = i.next(); // 獲取下一個貓
			// 使貓類快一點
			cat.x += (gameScreen.getSpeedX() + gameScreen.getSpeedX() / 5);
			cat.cat_run.updateSprite(); // 更新貓的動畫
		}
	}

	// 檢查是否有足夠的空間創建貓類
	public boolean spaceAvailable() {
		for (Iterator<Cat> i = cats.iterator(); i.hasNext();) {
			Cat cat = i.next(); // 獲取下一個貓
			if (SCREEN_WIDTH - (cat.x + cat.cat_run.getSprite().getWidth()) < eManager.getDistanceBetweenEnemies()) {
				return false; // 如果空間不足，返回 false
			}
		}
		return true; // 如果空間足夠，返回 true
	}

	// 創建一只新的貓
	public boolean createCat() {
		if (Math.random() * 100 < eManager.getCatsPercentage()) { // 如果隨機數小於貓的機率
			Animation cat_run = new Animation(400); // 創建貓的奔跑動畫
			cat_run.addSprite(getImage("resources/cat-1.png")); // 添加精靈到動畫中
			cat_run.addSprite(getImage("resources/cat-2.png")); // 添加精靈到動畫中
			// 將新的貓添加到貓的列表中
			cats.add(new Cat(SCREEN_WIDTH, (int) (Math.random() * (GROUND_Y - cat_run.getSprite().getHeight())),
					cat_run));
			return true; // 返回 true 表示成功創建了貓
		}
		return false; // 返回 false 表示未創建貓
	}

	// 檢查貓是否與廖冠誠發生碰撞
	public boolean isCollision(Rectangle liaoHitBox) {
		for (Iterator<Cat> i = cats.iterator(); i.hasNext();) {
			Cat cat = i.next(); // 獲取下一個貓
			Rectangle catHitBox = getHitbox(cat); // 獲取貓的碰撞框
			if (catHitBox.intersects(liaoHitBox)) // 如果貓的碰撞框與廖冠誠的碰撞框相交
				return true; // 返回 true 表示碰撞發生
		}
		return false; // 返回 false 表示未發生碰撞
	}

	// 獲取貓的碰撞框
	private Rectangle getHitbox(Cat cat) {
		// 根據當前使用的精靈來計算碰撞框
		return new Rectangle((int) cat.x + HITBOX_WINGS_UP[0],
				cat.cat_run.getSprite().getHeight() < WINGS_DOWN_HEIGHT ? cat.y + HITBOX_WINGS_UP[1]
						: cat.y + HITBOX_WINGS_DOWN[1],
				cat.cat_run.getSprite().getWidth() + HITBOX_WINGS_UP[2],
				cat.cat_run.getSprite().getHeight() < WINGS_DOWN_HEIGHT
						? cat.cat_run.getSprite().getHeight() + HITBOX_WINGS_UP[3]
						: cat.cat_run.getSprite().getHeight() + HITBOX_WINGS_DOWN[3]);
	}

	// 清除貓的方法
	public void clearCats() {
		cats.clear(); // 清空貓的列表
	}

	// 繪製貓
	public void draw(Graphics g) {
		for (Iterator<Cat> i = cats.iterator(); i.hasNext();) {
			Cat cat = i.next(); // 獲取下一個貓
			// 根據當前使用的精靈繪製貓
			g.drawImage(cat.cat_run.getSprite(), (int) cat.x,
					cat.cat_run.getSprite().getHeight() < 68 ? cat.y + HITBOX_MODELS_DIFF_IN_Y : cat.y, null);
		}
	}

	// 繪製貓的碰撞框
	public void drawHitbox(Graphics g) {
		g.setColor(Color.RED); // 設置顏色為紅色
		for (Iterator<Cat> i = cats.iterator(); i.hasNext();) {
			Cat cat = i.next(); // 獲取下一個貓
			Rectangle catHitBox = getHitbox(cat); // 獲取貓的碰撞框
			g.drawRect(catHitBox.x, catHitBox.y, (int) catHitBox.getWidth(), (int) catHitBox.getHeight()); // 繪製貓的碰撞框
		}
	}

}
