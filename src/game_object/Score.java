package game_object;

import user_interface.GameScreen;

// 導入必要的類
import static user_interface.GameWindow.SCREEN_HEIGHT;
import static user_interface.GameWindow.SCREEN_WIDTH;
import static util.Resource.getImage;
import static util.Resource.isJar;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import manager.SoundManager;
import misc.GameState;

public class Score {

	// 每次增加的分數值
	private static final double SCORE_INC = 0.1;
	// 分數顯示的長度，最大為 99999
	private static final int SCORE_LENGTH = 5;
	// 單個數字在精靈圖中的寬度和高度
	private static final int NUMBER_WIDTH = 20;
	private static final int NUMBER_HEIGHT = 21;
	// 分數在畫面上的位置
	private static final int CURRENT_SCORE_X = SCREEN_WIDTH - (SCORE_LENGTH * NUMBER_WIDTH + SCREEN_WIDTH / 100);
	private static final int HI_SCORE_X = SCREEN_WIDTH - (SCORE_LENGTH * NUMBER_WIDTH + SCREEN_WIDTH / 100) * 2;
	private static final int HI_X = SCREEN_WIDTH
			- ((SCORE_LENGTH * NUMBER_WIDTH + SCREEN_WIDTH / 100) * 2 + NUMBER_WIDTH * 2 + SCREEN_WIDTH / 100);
	private static final int SCORE_Y = SCREEN_HEIGHT / 25;

	private GameScreen gameScreen;
	private String scoreFileName;
	private File scoreFile;
	private BufferedImage hi;
	private BufferedImage numbers;
	private SoundManager scoreUpSound;

	private double score;
	private int hiScore;

	// 建構函式
	public Score(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		score = 0;
		// 設置最高分數文件的名稱和路徑
		scoreFileName = "best-scores.txt";
		scoreFile = new File("resources/" + scoreFileName);
		// 讀取最高分數
		readScore();
		// 加載分數和"HI"圖像
		hi = getImage("resources/hi.png");
		numbers = getImage("resources/numbers.png");
		// 加載得分音效
		scoreUpSound = new SoundManager("resources/scoreup.wav");
		scoreUpSound.startThread();
		// 新增hp功能
	}

	// 分數增加
	public double scoreIncrement = 0.0;
	public double scoreMultiplier = 0.0;
	// 分數增加
	public void scoreUp() {
		if(score<10){
			scoreIncrement=0.0;
			scoreMultiplier=0.0;
		}
		scoreIncrement=SCORE_INC*scoreMultiplier;
		score += scoreIncrement;
		score += SCORE_INC;
		// 每增加 100 分播放一次音效
		if ((int) score != 0 && score % 100 <= 0.2)
			scoreUpSound.play();
			scoreMultiplier+=0.0003;
	}

	// 從精靈圖中截取單個數字
	private BufferedImage cropImage(BufferedImage image, int number) {
		return image.getSubimage(number * NUMBER_WIDTH, 0, NUMBER_WIDTH, NUMBER_HEIGHT);
	}

	// 分數轉換為數字陣列
	private int[] scoreToArray(double scoreType) {
		int scoreArray[] = new int[SCORE_LENGTH];
		int tempScore = (int) scoreType;
		for (int i = 0; i < SCORE_LENGTH; i++) {
			int number = tempScore % 10;
			tempScore = (tempScore - number) / 10;
			scoreArray[i] = number;
		}
		return scoreArray;
	}

	// 寫入最高分數
	public void writeScore() {
		// 如果分數高於最高分數，則寫入文件
		if (score > hiScore) {
			File file;
			// 檢查程式是否正在從 JAR 文件運行，以確定存儲最佳成績的位置
			if (isJar())
				file = new File(ClassLoader.getSystemClassLoader().getResource("").getPath() + scoreFileName);
			else
				file = scoreFile;
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
				// 將結果、日期、玩家信息（此處為 Liao）以指定格式寫入文件
				bw.write(String.format("result=%s,date=%s,player=%s\n", Integer.toString((int) score),
						new SimpleDateFormat("yyyyMMdd_HHmmss")
								.format(Calendar.getInstance().getTime()),
						"Liao"));
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 讀取最高分數
	private void readScore() {
		// 再次檢查文件的位置以確定從何處讀取最高分數
		if (scoreFile.exists()
				|| new File(ClassLoader.getSystemClassLoader().getResource("").getPath() + scoreFileName).exists()) {
			String line = "";
			File file;
			if (isJar())
				file = new File(ClassLoader.getSystemClassLoader().getResource("").getPath() + scoreFileName);
			else
				file = scoreFile;
			if (file.exists()) {
				try (BufferedReader br = new BufferedReader(new FileReader(file))) {
					while ((line = br.readLine()) != null) {
						Matcher m = Pattern.compile("result=(\\d+),date=([\\d_]+),player=(\\w+)").matcher(line);
						if (m.find()) {
							if (Integer.parseInt(m.group(1)) > hiScore)
								hiScore = Integer.parseInt(m.group(1));
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else
			hiScore = (int) score;
	}

	// 重置分數
	public void scoreReset() {
		if (score > hiScore)
			hiScore = (int) score;
		score = 0;
	}

	// 繪製分數
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		int scoreArray[] = scoreToArray(score);
		for (int i = 0; i < SCORE_LENGTH; i++) {
			// 當分數增加 100 時，用於閃爍動畫
			if ((!((int) score >= 12 && (int) score % 100 <= 12) || (int) score % 3 == 0)
					|| gameScreen.getGameState() == GameState.GAME_STATE_OVER)
				g2d.drawImage(cropImage(numbers, scoreArray[SCORE_LENGTH - i - 1]), CURRENT_SCORE_X + i * NUMBER_WIDTH,
						SCORE_Y, null);
		}
		if (hiScore > 0) {
			int hiScoreArray[] = scoreToArray(hiScore);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
			for (int i = 0; i < SCORE_LENGTH; i++) {
				g2d.drawImage(cropImage(numbers, hiScoreArray[SCORE_LENGTH - i - 1]), HI_SCORE_X + i * NUMBER_WIDTH,
						SCORE_Y, null);
			}
			g2d.drawImage(hi, HI_X, SCORE_Y, null);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
	}
}