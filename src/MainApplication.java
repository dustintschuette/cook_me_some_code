import java.io.File;
import java.util.ArrayList;

import acm.graphics.GImage;

public class MainApplication extends GraphicsApplication {
	public static final int WINDOW_WIDTH = 1200;
	public static final int WINDOW_HEIGHT = 850;
	public static final String MUSIC_FOLDER = "Sounds";
	//private static final String[] SOUND_FILES = { "Track0.wav", "Track1.wav" };

	private MenuPane menu;
	private PlayPane playPane;
	private SoundPane soundPane;
	private Sound sound;
	private ShopPane shopPane;
	public boolean quickPlay;
	
	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		
	}

	public void run() {
		System.out.println("Initializing Game");
		menu = new MenuPane(this);
		playPane = new PlayPane(this);
		shopPane = new ShopPane(this);
		soundPane = new SoundPane(this);
		sound = new Sound();
		setupInteractions();
		switchToMenu();
		
	}

	public void switchToMenu() {
		switchToScreen(menu);
		playMusic();
	}
	public void switchToPlay() {
		switchToScreen(playPane);
	}
	
	public void switchToSound() {
		switchToScreen(soundPane);
	}

	public void switchToShop() {
		switchToScreen(shopPane);
	}
	public int getSlowdown() {
		return playPane.getSlowdown();
	}
	public void addSlowdown() {
		playPane.addSlowdown();
	}
	public int getScore() {
		return playPane.score();
	}
	public void addToScore(int toAdd) {
		playPane.addToScore(toAdd);
	}
	public ArrayList<GImage> rareItems(){
		return shopPane.getRareItems();
	}
	
	public void setQuickPlay() {
		playPane.setQuickPlay();
	}
	public void setInfiniteMode() {
		playPane.setInfiniteMode();
	}
	public PlayPane playPane() {
		return playPane;
	}
	public boolean getSound() {
		return soundPane.getSoundOn();
	}
	public void setSound(boolean onOff) {
		soundPane.setSound(onOff);
	}
	public static void main(String[] args) {
		new MainApplication().start();
	}
	
	public void addBall() {
		playPane.addBall();
	}
	
	public int getArmor() {
		return playPane.getArmor();
	}
	
	public void addArmor() {
		playPane.addArmor();
	}
	
	public void playMusic() {
		//if(soundPane.getSoundOn() == true) {
			if(Sound.Audio != null) {
				Sound.stop();
			}
			Sound.play(new File("SOUNDS/NeonRed-Instrumental.wav"));
			System.out.println("sound is playing");
		//}	
	}
	
	public void stopMusic() {
		Sound.stop();
	}
}
