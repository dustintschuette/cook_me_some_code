import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class ComboBar {
	private GImage comboBar;
	private GImage comboCountTitle;
	private GRect comboBarFiller;
	private GRect comboBarBackground;
	private float lastAnswer;
	private float tickSpeed;
	private int currentCombo;
	private boolean onScreen;
	MainApplication program;
	private int maxCombo;
	private GLabel comboLabel;
	private int defaultFontSize = 40;
	private int maxFontSize = 75;
	private Random rand;
	private ArrayList<GImage> armor;
	private static int armorX = 30;
	private static int armorY = 245;
	public int currArmor;
	private int slowdown;

	
	ComboBar(MainApplication program){
		this.program = program;
		comboBarBackground = new GRect(10, 210, 1190, 20);
		comboBarBackground.setFillColor(Color.BLACK);
		comboBarBackground.setFilled(true);
		comboBarFiller = new GRect(15, 215, 1185, 10);
		comboBarFiller.setFillColor(Color.RED);
		comboBarFiller.setFilled(true);
		comboCountTitle = new GImage("IMAGES/comboCount.gif",450, 150);
		currentCombo = 0;
		maxCombo = 0;
		tickSpeed = 2;
		comboLabel = new GLabel("", 750, 190);
		comboLabel.setFont("Roboto-20");
		armor = new ArrayList<GImage>();
		currArmor = -1;
		slowdown = 0;
		rand = new Random();
	}
	
	
	public void update() {
		if(currentCombo == 0) {
			return;
		}
		lastAnswer -= (tickSpeed - (slowdown * 0.3));
		
		
		if(lastAnswer <= 0) {
			breakCombo();
		}else {
			float size = ((lastAnswer/1000) * (1185));
			comboBarFiller.setSize(size, 10);
		}
		
		
	}
	
	public int getMaxCombo() {
		return maxCombo;
	}
	
	
	public double getCombo() {
		if(currentCombo == 0) {
			return 1;
		}else {
			return (1.0 + (currentCombo/5.0));
		}
	}
	
	public void addToCombo() {
		System.out.println("Combo added");
		lastAnswer = 1000;
		currentCombo++;
		if(currentCombo > maxFontSize - defaultFontSize) {
			comboLabel.setFont("Roboto-" + maxFontSize);
		}else {
			comboLabel.setFont("Roboto-" + (defaultFontSize + currentCombo));	
		}
		
		comboLabel.setColor(new Color(getRand(255), getRand(255), getRand(255)));
		if(!onScreen) {
			program.add(comboBarBackground);
			program.add(comboBarFiller);
			program.add(comboCountTitle);
			comboLabel.setLabel("" + currentCombo);
			comboLabel.setFont("Roboto-" + defaultFontSize);
			program.add(comboLabel);
		}
	}
	
	public void breakCombo() {
		if(currArmor >= 0 ) {
			program.remove(armor.get(currArmor));
			currArmor -= 1;
			lastAnswer = 1000;
			return;
		}
		program.remove(comboBarBackground);
		program.remove(comboBarFiller);
		program.remove(comboCountTitle);
		program.remove(comboLabel);
		onScreen = false;
		
		if(currentCombo > maxCombo) {
			maxCombo = currentCombo;
		}
		currentCombo = 0;
	}
	
	private int getRand(int range) {
		return Math.abs((rand.nextInt() % range)) + 1;
	}
	
	public void addArmor() {
		GImage tempArmor = new GImage("IMAGES/shield.png");
		tempArmor.setSize(25,25);
		tempArmor.setLocation(armorX + (armor.size()*30), armorY);
		armor.add(tempArmor);
		System.out.println("Armor added, current armor: " + armor.size());
	}
	
	public int getArmor() {
		return armor.size();
	}
	public int getSlowdown() {
		return slowdown;
	}
	public void addSlowdown() {
		slowdown += 1;
	}
	
	public void resetArmor() {
		currArmor = armor.size()-1;
		for(int i = 0; i < armor.size(); i++) {
			program.remove(armor.get(i));
		}
		for(int i = 0; i < armor.size(); i++) {
			program.add(armor.get(i));
		}
	}

}
