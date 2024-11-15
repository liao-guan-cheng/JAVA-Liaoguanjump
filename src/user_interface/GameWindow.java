package user_interface;

import javax.swing.JFrame;

@SuppressWarnings(value = { "serial" })
public class GameWindow extends JFrame {

	public static final int SCREEN_WIDTH = 1200;
	public static final int SCREEN_HEIGHT = 300;

	private GameScreen gameScreen;

	public GameWindow() {
		super("Liao");
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setUndecorated(true);
		setLocationRelativeTo(null);

		gameScreen = new GameScreen();
		add(gameScreen);
	}

	private void startGame() {
		gameScreen.startThread();

	}

	public static void main(String[] args) {
		GameWindow gameWindow = new GameWindow();
		gameWindow.startGame();
		gameWindow.setVisible(true);
	}

}

// 1.GameWindow 類別：
// 這是一個繼承自 JFrame 的類別，用於創建遊戲視窗。
// 在建構函式中，設置了遊戲視窗的標題、大小、關閉動作、位置等屬性。
// setUndecorated(true) 方法設置視窗為無邊框模式，這樣可以避免顯示窗口裝飾（如標題欄、邊框等），使得遊戲視窗更加純粹。
// 創建了一個 GameScreen 物件，並將其添加到遊戲視窗中。

// 2.startGame() 方法：
// 這是一個私有方法，用於啟動遊戲。
// 該方法調用了 GameScreen 中的 startThread() 方法，啟動了遊戲執行緒，從而開始遊戲的運行。

// 3.main() 方法：
// main() 方法是應用程式的入口點。
// 在 main() 方法中，創建了一個 GameWindow 物件，並調用其 startGame() 方法啟動遊戲。
// 最後，調用 setVisible(true) 方法將遊戲視窗設置為可見狀態，使得遊戲開始運行並顯示在屏幕上。