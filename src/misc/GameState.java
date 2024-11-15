package misc; // 套件名稱為 misc，這裡存放的是與遊戲相關的雜項類別

public enum GameState { // 定義一個名為 GameState 的列舉型別

	GAME_STATE_START, // GAME_STATE_START 表示遊戲正在開始狀態
	GAME_STATE_IN_PROGRESS, // GAME_STATE_IN_PROGRESS 表示遊戲正在進行中狀態
	GAME_STATE_OVER, // GAME_STATE_OVER 表示恐龍扣血狀態
	GAME_STATE_PAUSED, // GAME_STATE_PAUSED 表示遊戲暫停狀態
	GAME_STATE_INTRO; // GAME_STATE_INTRO 表示遊戲介紹狀態

}
