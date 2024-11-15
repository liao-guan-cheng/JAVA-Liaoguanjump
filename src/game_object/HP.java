package game_object;

import user_interface.GameScreen;

// 導入必要的類
import static user_interface.GameWindow.SCREEN_HEIGHT;
import static user_interface.GameWindow.SCREEN_WIDTH;
import static util.Resource.getImage;

import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.print.attribute.standard.Media;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import manager.SoundManager;
import misc.GameState;

public class HP {
    private static final int HP_caculate = 1;
    private static final int HP_LENGTH = 3;
    private static final int NUMBER_WIDTH = 20;
    private static final int NUMBER_HEIGHT = 21;
    private static final int currentHP_X = 51;
    private static final int currentHP_Y = SCREEN_HEIGHT / 25;
    private static final int HP_WIDTH = 50;
    private static final int HP_HEIGHT = 40;
    private static final int HP_X = 0;
    private static final int HP_Y = 0;
    private Clip injuredSound;
    private GameScreen gameScreen;
    private static BufferedImage numbers;
    private static int hp;
    private static BufferedImage HP;
    private Clip clip;

    private Clip playSound(String soundFileName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundFileName));
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            return clip;
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
            return null;
        }
    }

    // 建構函式
    public HP(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        numbers = getImage("resources/numbers.png");
        HP = getImage("resources/HP.png");
        hp = 3;
    }

    // 分數增加
    public void HPup() {
        hp += HP_caculate;
    }
    public void HPdec() {
        if(hp > 0){
            hp -= HP_caculate;
            clip = playSound("resources/injuried.wav");
            clip.start();
        }
    }
    public void reset(){
        hp = 3;
    }
    public boolean isGameOver(){
        return hp <= 0;
    }
    // 從精靈圖中截取單個數字
    private static BufferedImage cropImage(BufferedImage image, int number) {
        return image.getSubimage(number * NUMBER_WIDTH, 0, NUMBER_WIDTH, NUMBER_HEIGHT);
    }

    // 分數轉換為數字陣列
    private static int[] HPToArray(int HPType) {
        int HPArray[] = new int[HP_LENGTH];
        for (int i = 0; i < HP_LENGTH; i++) {
            int number = HPType % 10;
            HPType = (HPType - number) / 10;
            HPArray[i] = number;
        }
        return HPArray;
    }

    // 繪製hp
    public static void drawHP(Graphics h) {
        Graphics2D h2d = (Graphics2D) h;
        h2d.drawImage(HP, HP_X, HP_Y, HP_WIDTH, HP_HEIGHT, null);
        int HPArray[] = HPToArray(hp);
        h2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        for (int i = 0; i < HP_LENGTH; i++) {
            h2d.drawImage(cropImage(numbers, HPArray[HP_LENGTH - i - 1]), currentHP_X + i * NUMBER_WIDTH, currentHP_Y, null);
        }
    }
}