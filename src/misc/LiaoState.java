package misc; // 套件名稱為 misc，這裡存放的是與遊戲相關的雜項類別

public enum LiaoState { // 定義一個名為 LiaoState 的列舉型別

	LIAO_RUN, // LIAO_RUN 表示 Liao 正在奔跑的狀態
	LIAO_DOWN_RUN, // LIAO_DOWN_RUN 表示 Liao 在低姿勢奔跑的狀態
	LIAO_JUMP, // LIAO_JUMP 表示 Liao 正在跳躍的狀態
	LIAO_DEAD, // LIAO_DEAD 表示 Liao 已死亡的狀態
	LIAO_HURT; // LIAO_HURT 表示 Liao 受傷的狀態
}
