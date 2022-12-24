
import java.awt.Button;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.File;

import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.Program;

public class SoundPane extends GraphicsPane{
	private MainApplication program;
	private GButton on, off, returnButton;
	private GImage background;
	private boolean soundOn = true;
	private GRect audioBar;
	 
	
	public SoundPane(MainApplication app) {
		super();
		program = app;
		background = new GImage("IMAGES/menuback.gif", 0,0);
		background.setSize(1200,800);
		on = new GButton("on", 200, 400, 50, 50);
		off = new GButton("off", 400, 400, 50, 50);
		returnButton = new GButton("return", 1100, 700, 70, 70);
		audioBar = new GRect(710, 170, 32, 32);
		audioBar.setFilled(true);
		audioBar.setFillColor(Color.black);
	}


	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(background);
		program.add(on);
		program.add(off);
		program.add(returnButton);
		
	}
	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		program.remove(background);
		program.remove(on);
		program.remove(off);
		program.remove(returnButton);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		System.out.println("x: " + e.getX() + " y: " + e.getY());
		
		if(obj == returnButton) {
			program.switchToMenu();
			program.remove(on);
			program.remove(off);
		}
		
		if(obj == on) {
			if(soundOn == false) {
				program.playMusic();
				soundOn = true;
				return;
			}
		}
		
		if(obj == off) {
			if(soundOn == true) {
				//Sound.stop();
				program.stopMusic();
				soundOn = false;
				return;
			}
		}
	}
	
	public void setSound(boolean onOff) {
		soundOn = onOff;
	}
	
	public boolean getSoundOn(){
		return soundOn;
	}
}
