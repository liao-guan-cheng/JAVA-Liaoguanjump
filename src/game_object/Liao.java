package game_object; // 套件名稱為 game_object，這裡存放的是遊戲物件相關的類別

import static user_interface.GameScreen.GRAVITY; // 導入 GameScreen 類別的 GRAVITY 常數
import static user_interface.GameScreen.GROUND_Y; // 導入 GameScreen 類別的 GROUND_Y 常數
import static user_interface.GameScreen.SPEED_Y; // 導入 GameScreen 類別的 SPEED_Y 常數
import static util.Resource.getImage; // 導入 util 套件中的 getImage 靜態方法

import java.awt.Color; // 導入 java.awt 中的 Color 類別
import java.awt.Graphics; // 導入 java.awt 中的 Graphics 類別
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.Rectangle; // 導入 java.awt 中的 Rectangle 類別
import java.awt.image.BufferedImage; // 導入 java.awt.image 中的 BufferedImage 類別

import manager.SoundManager; // 導入 manager 套件中的 SoundManager 類別
import misc.Animation; // 導入 misc 套件中的 Animation 類別
import misc.Controls; // 導入 misc 套件中的 Controls 類別
import misc.LiaoState; // 導入 misc 套件中的 LiaoState 列舉

public class Liao { // 定義名為 Liao 的類別

	// 從 x, y, 寬度和高度減去的值以獲得準確的碰撞框
	private static final int[] HITBOX_RUN = { 12, 26, -32, -42 }; // LIAO_RUN 狀態的碰撞框值
	private static final int[] HITBOX_DOWN_RUN = { 24, 8, -60, -24 }; // LIAO_DOWN_RUN 狀態的碰撞框值

	public static final double X = 120; // Liao 的起始 x 座標

	Controls controls; // 控制按鍵的物件

	private double maxY; // 最大的 y 座標值
	private double highJumpMaxY; // 高跳的最大 y 座標值
	private double lowJumpMaxY; // 低跳的最大 y 座標值

	public static double y = 0; // 目前的 y 座標值
	private double speedY = 0; // 速度的 y 分量

	private LiaoState liaoState; // 目前的 Liao 狀態
	private BufferedImage liaoJump; // 跳躍時的 Liao 圖片
	private BufferedImage liaoDead; // 死亡時的 Liao 圖片
	private Animation liaoRun; // Liao 奔跑時的動畫
	private Animation liaoDownRun; // Liao 俯身奔跑時的動畫
	private SoundManager jumpSound; // 跳躍音效
	private SoundManager injuriedSound; // 受傷音效
	private boolean isFlickering = false; // 是否正在閃爍
	private long lastFlickerTime; // 上次閃爍時間
	private static final long FLICKER_DURATION = 500; // 閃爍持續時間（毫秒）

	// Liao 類別的建構函式，初始化 Liao
	public Liao(Controls controls) {
		this.controls = controls; // 初始化控制物件
		liaoRun = new Animation(150); // 初始化 Liao 奔跑動畫
		liaoRun.addSprite(getImage("resources/liao-run-1.png")); // 添加奔跑圖片
		liaoRun.addSprite(getImage("resources/liao-run-2.png")); // 添加奔跑圖片
		liaoDownRun = new Animation(150); // 初始化 Liao 俯身奔跑動畫
		liaoDownRun.addSprite(getImage("resources/liao-down-run-1.png")); // 添加俯身奔跑圖片
		liaoDownRun.addSprite(getImage("resources/liao-down-run-2.png")); // 添加俯身奔跑圖片
		liaoJump = getImage("resources/liao-jump.png"); // 初始化跳躍圖片
		liaoDead = getImage("resources/liao-dead.png"); // 初始化死亡圖片
		jumpSound = new SoundManager("resources/jump.wav"); // 初始化跳躍音效
		jumpSound.startThread(); // 啟動音效執行緒
		injuriedSound = new SoundManager("resources/injuried.wav"); // 初始化受傷音效
		injuriedSound.startThread(); // 啟動音效執行緒
		y = GROUND_Y - liaoJump.getHeight(); // 設置起始 y 座標
		maxY = y; // 初始化最大 y 座標
		highJumpMaxY = setJumpMaxY(GRAVITY); // 設置高跳的最大 y 座標
		lowJumpMaxY = setJumpMaxY(GRAVITY + GRAVITY / 2); // 設置低跳的最大 y 座標
		liaoState = LiaoState.LIAO_JUMP; // 初始化 Liao 狀態為跳躍
		lastFlickerTime = 0;
	}

	// 取得目前的 Liao 狀態
	public LiaoState getLiaoState() {
		return liaoState;
	}

	// 設置 Liao 狀態
	public void setLiaoState(LiaoState liaoState) {
		this.liaoState = liaoState;
	}

	// 設置跳躍的最大 y 座標值
	public double setJumpMaxY(double gravity) {
		speedY = SPEED_Y; // 初始化速度的 y 分量
		y += speedY; // 更新 y 座標
		double jumpMaxY = y; // 初始化跳躍的最大 y 座標
		while (true) { // 進入無限循環
			speedY += gravity; // 更新速度的 y 分量
			y += speedY; // 更新 y 座標
			if (y < jumpMaxY) // 如果目前的 y 座標小於跳躍的最大 y 座標
				jumpMaxY = y; // 更新跳躍的最大 y 座標
			if (y + speedY >= GROUND_Y - liaoRun.getSprite().getHeight()) { // 如果目前的 y 座標大於等於地面的 y 座標減去 Liao 奔跑時的高度
				speedY = 0; // 將速度的 y 分量設置為 0
				y = GROUND_Y - liaoRun.getSprite().getHeight(); // 更新 y 座標
				break; // 跳出循環
			}
		}
		return jumpMaxY; // 返回跳躍的最大 y 座標
	}

	// 取得 Liao 的碰撞框
	public Rectangle getHitbox() {
		switch (liaoState) { // 根據 Liao 的狀態選擇不同的碰撞框
			case LIAO_RUN:
			case LIAO_JUMP:
			case LIAO_DEAD:
				return new Rectangle((int) X + HITBOX_RUN[0], (int) y + HITBOX_RUN[1],
						liaoDead.getWidth() + HITBOX_RUN[2], liaoDead.getHeight() + HITBOX_RUN[3]); // 返回 Liao
																									// 奔跑、跳躍或死亡時的碰撞框
			case LIAO_DOWN_RUN:
				return new Rectangle((int) X + HITBOX_DOWN_RUN[0], (int) y + HITBOX_DOWN_RUN[1],
						liaoDownRun.getSprite().getWidth() + HITBOX_DOWN_RUN[2],
						liaoDownRun.getSprite().getHeight() + HITBOX_DOWN_RUN[3]); // 返回 Liao 俯身奔跑時的碰撞框
		}
		return null; // 如果狀態不明，返回空值
	}

	// 更新 Liao 的位置
	public void updatePosition() {
		if (y < maxY) // 如果目前的 y 座標小於最大的 y 座標
			maxY = y; // 更新最大的 y 座標
		liaoRun.updateSprite(); // 更新 Liao 奔跑動畫
		liaoDownRun.updateSprite(); // 更新 Liao 俯身奔跑動畫
		switch (liaoState) { // 根據 Liao 的狀態進行處理
			case LIAO_RUN: // 如果是 Liao 奔跑狀態
				y = GROUND_Y - liaoRun.getSprite().getHeight(); // 更新 y 座標
				maxY = y; // 更新最大的 y 座標
				break;
			case LIAO_DOWN_RUN: // 如果是 Liao 俯身奔跑狀態
				y = GROUND_Y - liaoDownRun.getSprite().getHeight(); // 更新 y 座標
				break;
			case LIAO_JUMP: // 如果是 Liao 跳躍狀態
				if (y + speedY >= GROUND_Y - liaoRun.getSprite().getHeight()) { // 如果下一步的 y 座標大於等於地面的 y 座標減去 Liao 奔跑時的高度
					speedY = 0; // 將速度的 y 分量設置為 0
					y = GROUND_Y - liaoRun.getSprite().getHeight(); // 更新 y 座標
					liaoState = LiaoState.LIAO_RUN; // 更新 Liao 的狀態為奔跑
				} else if (controls.isPressedUp()) { // 如果按下向上鍵
					speedY += GRAVITY; // 更新速度的 y 分量
					y += speedY; // 更新 y 座標
				} else { // 如果未按下向上鍵
					if (maxY <= lowJumpMaxY - (lowJumpMaxY - highJumpMaxY) / 2) // 如果目前的最大 y 座標小於等於低跳的最大 y 座標減去高跳的最大 y
						// 座標減去的一半
						speedY += GRAVITY; // 更新速度的 y 分量
					else // 如果目前的最大 y 座標大於低跳的最大 y 座標減去高跳的最大 y 座標減去的一半
						speedY += GRAVITY + GRAVITY / 2; // 更新速度的 y 分量
					if (controls.isPressedDown()) // 如果按下向下鍵
						speedY += GRAVITY; // 更新速度的 y 分量
					y += speedY; // 更新 y 座標
				}
				break;

			default:
				break;
		}
	}

	// Liao 跳躍
	public void jump() {
		if (y == GROUND_Y - liaoRun.getSprite().getHeight()) { // 如果 Liao 在地面上
			jumpSound.play(); // 播放跳躍音效
			speedY = SPEED_Y; // 初始化速度的 y 分量
			y += speedY; // 更新 y 座標
		}
	}

	public void injuried() {
		injuriedSound.play();
	}

	public void hurt() {
		liaoState = LiaoState.LIAO_HURT;
		isFlickering = true; // 开始闪烁
		lastFlickerTime = System.currentTimeMillis();
	}

	// 重置 Liao
	public void resetLiao() {
		y = GROUND_Y - liaoJump.getHeight(); // 將 y 座標設置為地面的 y 座標減去 Liao 跳躍時的高度
		liaoState = LiaoState.LIAO_RUN; // 將 Liao 狀態設置為奔跑
	}

	// 處理 Liao 遊戲結束
	public void liaoGameOver() {
		if (y > GROUND_Y - liaoDead.getHeight()) // 如果 y 座標大於地面的 y 座標減去 Liao 死亡時的高度
			y = GROUND_Y - liaoDead.getHeight(); // 更新 y 座標
		liaoState = LiaoState.LIAO_DEAD; // 更新 Liao 狀態為死亡
	}

	// 繪製 Liao
	public void draw(Graphics g) {
		switch (liaoState) { // 根據 Liao 的狀態進行繪製
			case LIAO_RUN: // 如果是奔跑狀態
				g.drawImage(liaoRun.getSprite(), (int) X, (int) y, null); // 繪製 Liao 奔跑動畫
				break;
			case LIAO_DOWN_RUN: // 如果是俯身奔跑狀態
				g.drawImage(liaoDownRun.getSprite(), (int) X, (int) y, null); // 繪製 Liao 俯身奔跑動畫
				break;
			case LIAO_JUMP: // 如果是跳躍狀態
				g.drawImage(liaoJump, (int) X, (int) y, null); // 繪製 Liao 跳躍圖片
				break;
			case LIAO_DEAD: // 如果是死亡狀態
				g.drawImage(liaoDead, (int) X, (int) y, null); // 繪製 Liao 死亡圖片
				break;
			case LIAO_HURT:
				long currentTime = System.currentTimeMillis();
				if ((currentTime - lastFlickerTime) % 600 < 300) {
					// 闪烁效果
					Graphics2D g2d = (Graphics2D) g;
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
					g2d.drawImage(liaoJump, (int) X, (int) y, null); // 绘制受伤状态
					g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
				}
				if (currentTime - lastFlickerTime >= FLICKER_DURATION) {
					liaoState = LiaoState.LIAO_RUN;
					isFlickering = false;
				}
				break;
			default:
				break;
		}
	}

	// 繪製 Liao 的碰撞框
	public void drawHitbox(Graphics g) {
		g.setColor(Color.GREEN); // 設置繪製顏色為綠色
		g.drawRect(getHitbox().x, getHitbox().y, getHitbox().width, getHitbox().height); // 繪製碰撞框
	}
}
