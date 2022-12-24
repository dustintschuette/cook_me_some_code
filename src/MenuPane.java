

import java.awt.Button;
import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GButton rect, quickButton, fullButton, infiniteButton, shopButton, soundButton;
	private GImage title, background, play, quickPlay, fullPlay, infinite, shop;

	private GImage neo;
	
	private GImage rareItems;
	

	public MenuPane(MainApplication app) {
		super();
		program = app;
		rect = new GButton("Play", 350, 400, 400, 100);
		rect.setFillColor(Color.RED);
		rect.setVisible(false);
		title = new GImage("IMAGES/titlecard.gif", 340, 130);
		title.setSize(500, 100);
		background = new GImage("IMAGES/menuback.gif", 0,0);
		background.setSize(1200,800);
		play = new GImage("IMAGES/play.gif",500,420);
		neo = new GImage("IMAGES/neomenu.gif",920,520);
		soundButton = new GButton("sound", 30, 720, 50, 50);
		
		// quick play and full game options
		quickPlay = new GImage("IMAGES/quickplay.gif",500, 420);
		quickButton = new GButton("quick", 500, 420, 160, 60);
		quickButton.setVisible(false);
		fullPlay = new GImage("IMAGES/fullplay.gif",450, 360);
		fullButton = new GButton("full", 450, 360, 250, 60);
		fullButton.setVisible(false);
		
		//infinite mode
		infinite = new GImage("IMAGES/infiniteMode.gif",470, 490);
		infiniteButton = new GButton("infinite", 470, 490, 230, 60);
		infiniteButton.setVisible(false);
		
		//shop
		//shop = new GImage("IMAGES/shopTitle.gif",540,560);
		//shopButton = new GButton("shop",540,560,160,60);
		//shopButton.setVisible(false);
		
		
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(play);
		program.add(rect);
		program.add(title);
		program.add(soundButton);
		program.add(neo);
	}

	@Override
	public void hideContents() {
		program.remove(rect);
		program.remove(play);
		program.remove(background);
		program.remove(title);
		program.remove(neo);
		program.remove(quickPlay);
		program.remove(quickButton);
		program.remove(fullPlay);
		program.remove(fullButton);
		program.remove(infinite);
		program.remove(infiniteButton);
		//program.remove(shop);
		//program.remove(shopButton);
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		System.out.println("x: " + e.getX() + " y: " + e.getY() );
		if(obj == soundButton) {
			program.remove(soundButton);
			program.switchToSound();
		}
		if (obj == rect) {
			// remove current options
			program.remove(rect);
			program.remove(play);
			
			// add new options
			program.add(quickPlay);
			program.add(quickButton);
			program.add(fullPlay);
			program.add(fullButton);
			program.add(infinite);
			program.add(infiniteButton);
			//program.add(shop);
			//program.add(shopButton);
			
		}
		if (obj == quickButton) {
			System.out.println("Quick");
			program.setQuickPlay();
			program.switchToPlay();
			
		}
		if (obj == fullButton) {
			System.out.println("Full");
			program.switchToPlay();
			
		}
		if (obj == infiniteButton) {
			System.out.println("Infinite");
			program.setInfiniteMode();
			program.switchToPlay();
		}
		/*if (obj == shopButton) {
			System.out.println("Shop");
			program.switchToShop();
		}*/
	}
}
