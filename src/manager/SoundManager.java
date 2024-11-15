package manager; // 套件名稱為 manager，這裡存放的是管理類別

import static util.Resource.getSound; // 導入 util 套件中的 getSound 靜態方法

// 為每個聲音創建新線程的原因是，當我嘗試在主線程中播放聲音時，
// 聲音會出現卡頓和延遲，因此......
public class SoundManager implements Runnable { // 定義一個名為 SoundManager 的類別，實現了 Runnable 接口

	public static int WAITING_TIME = 0; // 聲音管理器的等待時間

	Thread thread; // 宣告一個名為 thread 的線程對象

	private boolean playSound = false; // 標記是否播放聲音
	private String path; // 聲音文件的路徑

	// 建構函式，初始化線程並設置聲音文件的路徑
	public SoundManager(String path) {
		thread = new Thread(this); // 創建新線程並指定 run 方法
		this.path = path; // 設置聲音文件的路徑
	}

	@Override
	public void run() { // 實現 Runnable 接口的 run 方法
		// 不斷運行，並在每次迭代中根據 WAITING_TIME 進行延遲
		// 如果需要播放聲音，則 playSound 設置為 true
		while (true) {
			try {
				Thread.sleep(WAITING_TIME); // 延遲指定的時間
			} catch (InterruptedException e) {
				e.printStackTrace(); // 印出異常信息
			}
			if (playSound) { // 如果需要播放聲音
				getSound(path).start(); // 從指定路徑獲取聲音並播放
				playSound = false; // 播放完成後重置 playSound
			}
		}
	}

	// 啟動線程的方法
	public void startThread() {
		thread.start(); // 開始執行線程
	}

	// 播放聲音的方法
	public void play() {
		playSound = true; // 設置 playSound 為 true，表示需要播放聲音
	}

}
