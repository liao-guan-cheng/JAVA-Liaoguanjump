package manager; // 套件名稱為 manager，這裡存放的是管理類別

import misc.Controls; // 導入 misc 套件中的 Controls 類別
import user_interface.GameScreen; // 導入 user_interface 套件中的 GameScreen 類別

public class ControlsManager { // 定義一個名為 ControlsManager 的類別

	Controls controls; // 控制按鍵的 Controls 對象
	GameScreen gameScreen; // 遊戲畫面的 GameScreen 對象

	// 建構函式，接受一個 Controls 對象和一個 GameScreen 對象作為參數
	public ControlsManager(Controls controls, GameScreen gameScreen) {
		this.controls = controls; // 將傳入的 Controls 對象賦值給類別的 controls 屬性
		this.gameScreen = gameScreen; // 將傳入的 GameScreen 對象賦值給類別的 gameScreen 屬性
	}

	// 更新按鍵狀態的方法
	public void update() {
		// 如果向上按鍵被按下，則調用 GameScreen 中的 pressUpAction 方法
		if (controls.isPressedUp())
			gameScreen.pressUpAction();
		// 如果向下按鍵被按下，則調用 GameScreen 中的 pressDownAction 方法
		if (controls.isPressedDown())
			gameScreen.pressDownAction();
	}

}
