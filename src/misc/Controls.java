package misc; // 套件名稱為 misc，這裡存放的是與遊戲相關的雜項類別

import user_interface.GameScreen; // 導入遊戲畫面類別

import java.awt.event.ActionEvent; // 導入用於處理動作事件的類別
import javax.swing.JComponent; // 導入用於創建 GUI 元件的類別
import javax.swing.JLabel; // 導入用於顯示標籤的類別
import javax.swing.AbstractAction; // 導入用於創建抽象動作的類別
import javax.swing.KeyStroke; // 導入用於處理按鍵綁定的類別

@SuppressWarnings(value = { "serial" }) // 忽略編譯器生成的警告
public class Controls {

	private static final int FOCUS_STATE = JComponent.WHEN_IN_FOCUSED_WINDOW; // 定義在焦點處於視窗中時執行動作的常數

	// 定義按鍵的字串常數
	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	private static final String W_UP = "W_UP";
	private static final String S_DOWN = "S_DOWN";
	private static final String SPACE_UP = "SPACE_UP";
	private static final String DEBUG_MENU = "DEBUG_MENU";
	private static final String P_PAUSE = "P";
	private static final String ESCAPE_PAUSE = "ESCAPE";

	// 定義釋放按鍵的字串常數
	private static final String RELEASED_UP = "RELEASED_UP";
	private static final String RELEASED_DOWN = "RELEASED_DOWN";
	private static final String RELEASED_W_UP = "RELEASED_W_UP";
	private static final String RELEASED_S_DOWN = "RELEASED_S_DOWN";
	private static final String RELEASED_SPACE_UP = "RELEASED_SPACE_UP";

	// 創建 GUI 元件，用於處理按鍵事件
	public JLabel pressUp = new JLabel();
	public JLabel releaseUp = new JLabel();
	public JLabel pressDown = new JLabel();
	public JLabel releaseDown = new JLabel();
	public JLabel pressDebug = new JLabel();
	public JLabel pressPause = new JLabel();

	private boolean isPressedUp = false; // 標記是否按下了向上按鍵
	private boolean isPressedDown = false; // 標記是否按下了向下按鍵

	GameScreen gameScreen; // 用於處理遊戲畫面的對象

	public Controls(GameScreen gameScreen) { // 建構函式，接受一個 GameScreen 對象作為參數
		this.gameScreen = gameScreen; // 將傳入的 GameScreen 對象賦值給類別的 gameScreen 屬性
		// 為按鍵綁定動作事件
		// PRESS RELEASE ARROW UP //
		pressUp.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("UP"), UP);
		pressUp.getActionMap().put(UP, new PressUpAction());
		releaseUp.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("released UP"), RELEASED_UP);
		releaseUp.getActionMap().put(RELEASED_UP, new ReleaseUpAction());
		// PRESS RELEASE ARROW DOWN //
		pressDown.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("DOWN"), DOWN);
		pressDown.getActionMap().put(DOWN, new PressDownAction());
		releaseDown.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("released DOWN"), RELEASED_DOWN);
		releaseDown.getActionMap().put(RELEASED_DOWN, new ReleaseDownAction());
		// PRESS RELEASE W //
		pressUp.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("W"), W_UP);
		pressUp.getActionMap().put(W_UP, new PressUpAction());
		releaseUp.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("released W"), RELEASED_W_UP);
		releaseUp.getActionMap().put(RELEASED_W_UP, new ReleaseUpAction());
		// PRESS RELEASE S //
		pressDown.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("S"), S_DOWN);
		pressDown.getActionMap().put(S_DOWN, new PressDownAction());
		releaseDown.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("released S"), RELEASED_S_DOWN);
		releaseDown.getActionMap().put(RELEASED_S_DOWN, new ReleaseDownAction());
		// PRESS RELEASE SPACE //
		pressUp.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("SPACE"), SPACE_UP);
		pressUp.getActionMap().put(SPACE_UP, new PressUpAction());
		releaseUp.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("released SPACE"), RELEASED_SPACE_UP);
		releaseUp.getActionMap().put(RELEASED_SPACE_UP, new ReleaseUpAction());
		// PRESS RELEASE BACKTICK //
		pressDebug.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("BACK_QUOTE"), DEBUG_MENU);
		pressDebug.getActionMap().put(DEBUG_MENU, new PressDebugAction());
		// PRESS RELEASE P //
		pressPause.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("P"), P_PAUSE);
		pressPause.getActionMap().put(P_PAUSE, new PressPauseAction());
		// PRESS RELEASE ESCAPE //
		pressPause.getInputMap(FOCUS_STATE).put(KeyStroke.getKeyStroke("ESCAPE"), ESCAPE_PAUSE);
		pressPause.getActionMap().put(ESCAPE_PAUSE, new PressPauseAction());
	}

	public boolean isPressedUp() { // 返回是否按下了向上按鍵的狀態
		return isPressedUp;
	}

	public boolean isPressedDown() { // 返回是否按下了向下按鍵的狀態
		return isPressedDown;
	}

	// 處理按下向上按鍵時的動作
	private class PressUpAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			isPressedUp = true; // 標記為按下了向上按鍵
		}
	}

	// 處理釋放向上按鍵時的動作
	private class ReleaseUpAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameScreen.releaseUpAction(); // 調用 GameScreen 中的 releaseUpAction 方法處理釋放向上按鍵時的動作
			isPressedUp = false; // 標記為未按下向上按鍵
		}
	}

	// 處理按下向下按鍵時的動作
	private class PressDownAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			isPressedDown = true; // 標記為按下了向下按鍵
		}
	}

	// 處理釋放向下按鍵時的動作
	private class ReleaseDownAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameScreen.releaseDownAction(); // 調用 GameScreen 中的 releaseDownAction 方法處理釋放向下按鍵時的動作
			isPressedDown = false; // 標記為未按下向下按鍵
		}
	}

	// 處理按下 Debug 鍵時的動作
	private class PressDebugAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameScreen.pressDebugAction(); // 調用 GameScreen 中的 pressDebugAction 方法處理按下 Debug 鍵時的動作
		}
	}

	// 處理按下 Pause 鍵時的動作
	private class PressPauseAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			gameScreen.pressPauseAction(); // 調用 GameScreen 中的 pressPauseAction 方法處理按下 Pause 鍵時的動作
		}
	}

}
