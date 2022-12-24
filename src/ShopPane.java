
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;

public class ShopPane extends GraphicsPane {
	private MainApplication program; // you will use program to get access to
										// all of the GraphicsProgram calls
	private GImage backgroundImg, itemsPanel, collection, collectionTitle;
	public int score;
	private GLabel scoreLabel;
	private GImage portraitPanel, portraitBack;
	private GImage shopkeeper ,shopSign, chatBox;
	private GImage rare1,rare2,rare3,rare4,rare5 ,rare6,rare7, rare8, rare9, rare10, shield, slowdown;
	private GObject obj;
	private GLabel returnButton;
	private GImage returnBG;

	
	private String[] dialogue = {"I've got a selection of rare keanus on sale stranger. 50 each...",
								"That will keep your combo from dropping once per question, it costs: ",
								"Want your combo meter to decrease slower? That's what you're looking for. It can be yours for: ",
								"You can't afford that! It costs: ",
								"SOLD OUT"};
	Font OSD , OSDL;
	ArrayList <GLabel> dialogueLabels;
	ArrayList <GImage> rareItems = new ArrayList<GImage>();
	
	public ShopPane(MainApplication app) {
		this.program = app;
		setText();
		returnBG = new GImage("IMAGES/arrow.png", 50, 500);
		returnBG.setSize(300, 225);
		returnButton = new GLabel("Return To Game", 100, 610);
		returnButton.setFont(OSD);
		
		backgroundImg = new GImage("IMAGES/shop.jpg",0,0);
		backgroundImg.setSize(1300,900);
		scoreLabel = new GLabel("Score : " + score(), 30, 30);
		scoreLabel.setColor(Color.cyan);
		

		itemsPanel = new GImage( "IMAGES/questionBox.png" , 420, 245);
		itemsPanel.setSize(775, 540);
		
		collectionTitle = new GImage("IMAGES/collectionTitle.gif", 80,140);
		collectionTitle.setSize(250,70);
		collection = new GImage("IMAGES/questionBox.png", 10,200);
		collection.setSize(400,520);
		

		//Shop keeper
		portraitPanel = new GImage("IMAGES/portrait.png", 940, 15);
		portraitPanel.setSize(245, 195);
		portraitBack = new GImage("IMAGES/portraitBack.png", 950, 25);
		portraitBack.setSize(225, 175);
		shopkeeper = new GImage("IMAGES/shopKeeper.jpg", 950, 25);
		shopkeeper.setSize(225, 175);
		chatBox = new GImage("IMAGES/textBox1.png", 320, 30);
		chatBox.setSize(600, 200);
		//title
		shopSign= new GImage("IMAGES/shopTitle.gif", 480, 50);
		shopSign.setSize(270, 50);
		
		//shop items
		rare1 = new GImage("IMAGES/rare1.jpg",470, 295);
		rare1.setSize(120,120);
		
		rare2 = new GImage("IMAGES/rare2.jpg",520+120, 295);
		rare2.setSize(120,120);
		
		rare3 = new GImage("IMAGES/rare3.jpeg",570+240, 295);
		rare3.setSize(120,120);
		
		rare4 = new GImage("IMAGES/rare4.gif",620+360, 295);
		rare4.setSize(120,120);
		
		rare5 = new GImage("IMAGES/rare5.gif",470 + (2*170), 295 + 120+50);
		rare5.setSize(120,120);
		
		rare6 = new GImage("IMAGES/rare6.gif",470 + (3*170), 295 + 120+50);
		rare6.setSize(120,120);
		
		rare7 = new GImage("IMAGES/rare7.gif",470, 295 + (2*170));
		rare7.setSize(120,120);
		
		rare8 = new GImage("IMAGES/rare8.gif",470 + (1*170), 295 + (2*170));
		rare8.setSize(120,120);
		
		rare9 = new GImage("IMAGES/rare9.gif",470+ (2*170), 295 + (2*170));
		rare9.setSize(120,120);
		
		rare10 = new GImage("IMAGES/rare10.gif",470+ (3*170), 295 + (2*170));
		rare10.setSize(120,120);
		
		shield = new GImage("IMAGES/shield.png",470, 295 + 120+50);
		shield.setSize(120,120);
		
		slowdown = new GImage("IMAGES/slowDown.gif", 470 + 120 + 50, 295 + 120+50);
		slowdown.setSize(120,120);
	
	}

	@Override
	public void showContents() {
		//set dialogue back to default
		score = score();
		scoreLabel.setLabel("Score: " + score);
		dialogueLabels = Util.fixOrderLength(dialogue[0], OSD);
		
		
		program.add(backgroundImg);
		program.add(collection);
		program.add(collectionTitle);
		program.add(scoreLabel);
		program.add(itemsPanel);
		program.add(portraitBack);
		program.add(shopkeeper);
		program.add(portraitPanel);
		program.add(shopSign);
		program.add(chatBox);
		program.add(rare1);
		program.add(rare2);
		program.add(rare3);
		program.add(rare4);
		program.add(shield);
		program.add(slowdown);
		program.add(rare5);
		program.add(rare6);
		program.add(rare7);
		program.add(rare8);
		program.add(rare9);
		program.add(rare10);
		program.add(returnBG);
		program.add(returnButton);
		
		updateDialogue();
	}

	@Override
	public void hideContents() {
		program.remove(backgroundImg);
		program.remove(collection);
		program.add(collectionTitle);
		program.remove(scoreLabel);
		program.remove(itemsPanel);
		program.remove(portraitBack);
		program.remove(shopkeeper);
		program.remove(portraitPanel);
		program.remove(shopSign);
		program.remove(chatBox);
		program.remove(rare1);
		program.remove(rare2);
		program.remove(rare3);
		program.remove(rare4);
		program.remove(shield);
		program.remove(slowdown);
		program.remove(rare5);
		program.remove(rare6);
		program.remove(rare7);
		program.remove(rare8);
		program.remove(rare9);
		program.remove(rare10);
		program.remove(returnBG);
		program.remove(returnButton);
		
		removeCurrentDialogue();
	}
	public void updateDialogue() {
		for(int i = 0; i< dialogueLabels.size(); i++) {
			program.add(dialogueLabels.get(i));
		}
	}
	public void removeCurrentDialogue() {
		for(int i = 0; i< dialogueLabels.size(); i++) {
			program.remove(dialogueLabels.get(i));
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		System.out.println("x: " + e.getX() + " y: " + e.getY() );
		if(obj == null) {return;}
		if(obj == rare1 || obj == rare2 || obj == rare3 || obj == rare4|| obj == rare5|| obj == rare6|| obj == rare7|| obj == rare8|| obj == rare9|| obj == rare10) {
			if(obj == rare1 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare1);
				program.remove(rare1);
			}
			if(obj == rare2 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare2);
				program.remove(rare2);
			}
			if(obj == rare3 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare3);
				program.remove(rare3);
			}
			if(obj == rare4 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare4);
				program.remove(rare4);
			}
			if(obj == rare5 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare5);
				program.remove(rare5);
			}
			if(obj == rare6 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare6);
				program.remove(rare6);
			}
			if(obj == rare7 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare7);
				program.remove(rare7);
			}
			if(obj == rare8 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare8);
				program.remove(rare8);
			}
			if(obj == rare9 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare9);
				program.remove(rare9);
			}
			if(obj == rare10 && score() >= 50) {
				System.out.println("RARE");
				updateScore(-50);
				scoreLabel.setLabel("Score: " + score);
				rareItems.add(rare10);
				program.remove(rare10);
			}
		}
		if(obj == shield && program.getArmor() < 3 && score() >= ((program.getArmor() + 1) * 100)) {
			System.out.println("SHIELD ITEM");
			updateScore(-1 * ((program.getArmor() + 1) * 100));
			program.addArmor();
			removeCurrentDialogue();
			dialogueLabels = Util.fixOrderLength(dialogue[1] + ((program.getArmor() + 1) * 100), OSD);
			updateDialogue();
			
		}
		if(obj == slowdown && program.getSlowdown() < 3 && score() >= ((program.getSlowdown() + 1) * 100)) {
			System.out.println("SLOW DOWN ITEM");
			updateScore(-1 * ((program.getSlowdown() + 1) * 100));
			program.addSlowdown();
			removeCurrentDialogue();
			dialogueLabels = Util.fixOrderLength(dialogue[2] + ((program.getSlowdown() + 1) * 100), OSD);
			updateDialogue();
		}
		if(obj == returnButton || obj == returnBG) {
			program.switchToPlay();
			
		}
		scoreLabel.setLabel("Score : " + score());
		
	}
	public void mouseMoved(MouseEvent e) {
		obj = program.getElementAt(e.getX(), e.getY());
		if(obj == null) {return;}
		if(obj == rare1 || obj == rare2 || obj == rare3 || obj == rare4|| obj == rare5|| obj == rare6|| obj == rare7|| obj == rare8|| obj == rare9|| obj == rare10) {
			removeCurrentDialogue();
			dialogueLabels = Util.fixOrderLength(dialogue[0], OSD);
			updateDialogue();
		}
		if(obj == shield) {
			removeCurrentDialogue();
			
			if(program.getArmor() > 2) {
				dialogueLabels = Util.fixOrderLength(dialogue[4], OSD);
			}else if(score() < ((program.getArmor()) + 1) * 100){
				dialogueLabels = Util.fixOrderLength(dialogue[3] + ((program.getArmor() + 1) * 100), OSD);
			}else {
				dialogueLabels = Util.fixOrderLength(dialogue[1] + ((program.getArmor() + 1) * 100), OSD);
			}
			updateDialogue();
		}
		if(obj == slowdown) {
			removeCurrentDialogue();
			
			if(program.getSlowdown() > 2) {
				dialogueLabels = Util.fixOrderLength(dialogue[4], OSD);
			}else if(score() < ((program.getSlowdown() + 1) * 100)) {
				dialogueLabels = Util.fixOrderLength(dialogue[3] + ((program.getSlowdown() + 1) * 100), OSD);
			}else {
				dialogueLabels = Util.fixOrderLength(dialogue[2] + ((program.getSlowdown() + 1) * 100), OSD);
			}
			updateDialogue();
		}
		
	}
	
	public int score() {
		return program.getScore();
	}
	public void updateScore(int toAdd) {
		program.addToScore(toAdd);
	}
	public ArrayList<GImage> getRareItems(){
		return rareItems;
	}
	private void setText() {
		try {
		    //create the font to use. Specify the size!
		    OSD = Font.createFont(Font.TRUETYPE_FONT, new File("FONTS/OSD.ttf")).deriveFont(20f);
		    
		} catch (IOException e) {
		    e.printStackTrace();
		} catch(FontFormatException e) {
		    e.printStackTrace();
		}

		

	}
}
