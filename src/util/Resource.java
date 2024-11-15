package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Resource {

	// 從指定路徑獲取圖像
	public static BufferedImage getImage(String path) {
		File file = new File(path);
		BufferedImage image = null;
		try {
			// 如果文件存在，直接讀取圖像
			if (file.exists())
				image = ImageIO.read(file);
			else {
				// 否則，從 ClassLoader 中讀取圖像（適用於 JAR 檔案）
				path = path.substring(path.indexOf("/") + 1);
				image = ImageIO.read(ClassLoader.getSystemClassLoader().getResource(path));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	// 從指定路徑獲取聲音剪輯
	public static Clip getSound(String path) {
		File file = new File(path);
		Clip clip = null;
		try {
			clip = AudioSystem.getClip();
			// 如果文件存在，直接打開聲音剪輯
			if (file.exists())
				clip.open(AudioSystem.getAudioInputStream(file));
			else {
				// 否則，從 ClassLoader 中打開聲音剪輯（適用於 JAR 檔案）
				path = path.substring(path.indexOf("/") + 1);
				clip.open(AudioSystem.getAudioInputStream(ClassLoader.getSystemClassLoader().getResource(path)));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return clip;
	}

	// 檢查是否在 JAR 檔案中運行
	public static boolean isJar() {
		// 通過檢查 URL 是否包含 "file:" 來確定是否在 JAR 檔案中運行
		Matcher m = Pattern.compile("^file:").matcher(ClassLoader.getSystemClassLoader().getResource("").toString());
		return !m.find();
	}

}
