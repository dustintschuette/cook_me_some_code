import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.Timer;

import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class PlayPane extends GraphicsPane implements KeyListener, ActionListener{
//Main pane for the game, will include sections for the answer pool, a customer and a problem to solve in the form of a food item
	
	
	
	private MainApplication program;
	private GRect answerPanel;
	private GImage orderPanel;
	private GImage questionPanel;
	private GImage portraitPanel;
	private GImage portraitBack;
	private GImage currentCustomer;
	private GImage gameOverScreen, gameOver;
	private GImage background;
	private GButton showRandomAnswer;
	private GImage pausedTitle;
	private GImage temp;
	private Font OSD;
	private GLabel maxCombo;
	private GImage keanuBall;
	private GImage ballBG;
	private GLabel capturedKeanu;
	private GImage capBG;
	private GImage music, onImg, offImg;
	private GButton on, off;

	private boolean paused;
	public boolean quickPlayOn;
	public boolean infiniteModeOn;
	public boolean inShop = false;
	
	private GObject currObject;
	private ComboBar comboBar;
	private KeanuEncounter wildKeanu;
	
	private GLabel scoreLabel;
	public int score = 200; //10000;
	private int answeredQuestions = 0;
	private GLabel time;

	private static int NUM_CUSTOMERS = 4;
	private static int NUM_HAPPY_DIALOGUE = 6;
	private static int NUM_ANGRY_DIALOGUE = 9;
	private static int numKeanu = 15;
	
	private ArrayList<QuestionCard> QuestionBank;
	private GImage collection, collectionTitle;
	private GImage speech, randomSpeech;
	private boolean customerHappy, customerAngry, customerSpeaking, tutorial;
	private int dialogueTime = 0;
	private int randomDialogueTime =0;
	private int numBalls;
	private ArrayList <GLabel> pauseDialogue;
	public String[] dialogueStrings = {"I heard you can buy rare Keanu in the shop", 
			"You call that a keanu collection?",
			"Wow now that's impressive"};
	
	private double lastX, lastY, startX, startY;
	int currentQuestion;
	Random rand;
	
	private Timer timer;
	
	private GImage maxBG;
	
	private ArrayList<GLabel> currDialogue;
	
	private String[] tutorialDialogue = {"Try dragging the \"sum\" answer card to the first blank....", 
										"Nice, What you see now is the Combo Bar, the higher the combo, the more score you can earn per answer. Now try dragging the \"sum\" answer card to the second empty block",
										"Heheeheheh.... Wrong Answer. when you answer wrong you break your combo",
										"When you feel ready, drag the continue to the final empty spot"};
	
	PlayPane(MainApplication program){
		this.program = program;
		QuestionBank = new ArrayList<QuestionCard>();
		currDialogue = new ArrayList<GLabel>();
		rand = new Random();
		
		wildKeanu = new KeanuEncounter(program);
		
		setText();
		initQuestions();
		initPanels();
		//initToppings();
		comboBar = new ComboBar(program);
		currentQuestion = Math.abs(rand.nextInt()) % QuestionBank.size();
		
		//testing Question cards
		//0 is default for tutorial
		currentQuestion = 0;
		
		
		//testing capturing keanus
		numBalls = 0;
		
		tutorial = true;
		//tutorial = false;
		
		//testing armor
		comboBar.addArmor();
		
	}
	
	



	void testQuestion() {
		//System.out.println("testQuestions called");
		ArrayList<GRect> blanks = new ArrayList<GRect>();
		ArrayList<GLabel> questionBlocks = new ArrayList<GLabel>();
		ArrayList<Integer> answerOrder = new ArrayList<Integer>();
		ArrayList<GLabel> currDialogue = new ArrayList<GLabel>();
		
		answerOrder.add(1);
		answerOrder.add(2);
		answerOrder.add(3);
		answerOrder.add(4);
		answerOrder.add(5);
		answerOrder.add(6);
		
		
		GRect block = new GRect(0, 0, 141, 60 );
		addToGrid(block, 0,0);
		
		blanks.add(block);
		
		GLabel blocks = new GLabel("(");
		addToGrid(blocks, 0, 1);
		
		questionBlocks.add(blocks);
		
		//testQuestion = new QuestionCard(program, blanks, questionBlocks, answerOrder, "Make me a loop");
		
		//testQuestion.show();
		
	}
	public void setQuickPlay() {
		quickPlayOn = !quickPlayOn;
	}
	public void setInfiniteMode() {
		infiniteModeOn = !infiniteModeOn;
	}
	
	
	void initPanels() {
		
		
		//answer panel
		answerPanel = new GRect(0,250,400, 550);
		answerPanel.setFilled(true);
		
		answerPanel.setFillColor(Color.BLUE);
		
		//orderPanel
		orderPanel = new GImage("IMAGES/textBox1.png", 320, 30);
		orderPanel.setSize(600, 200);
		
		//portrait background and frame for the pics
		portraitPanel = new GImage("IMAGES/portrait.png", 940, 15);
		portraitPanel.setSize(245, 195);
		portraitBack = new GImage("IMAGES/portraitBack.png", 950, 25);
		portraitBack.setSize(225, 175);
		
		// game setting flag
		quickPlayOn = false;
		infiniteModeOn = false;
		
		// Dinner background
		background = new GImage("IMAGES/diner.png", 0, 0);
		background.setSize(1200,800);
		
		// Pause panel
		pausedTitle = new GImage("IMAGES/paused.gif", 480,300);
		pausedTitle.setSize(250,100);
		paused = false;
		
		// collections
		collectionTitle = new GImage("IMAGES/collectionTitle.gif", 80,140);
		collectionTitle.setSize(250,70);
		collection = new GImage("IMAGES/questionBox.png", 10,200);
		collection.setSize(400,520);
		
		//customer pics
		getNewCustomer();
		
		//dialogue
		customerHappy = false;
		customerAngry = false;
		customerSpeaking = false;
		
		//question panel
		questionPanel = new GImage( "IMAGES/questionBox.png" , 420, 245);
		questionPanel.setSize(775, 540);
		
		//hint button
		showRandomAnswer = new GButton("HINT", 1118, 267, 60, 30);
		showRandomAnswer.setFillColor(Color.white);
		showRandomAnswer.setColor(Color.blue);
		showRandomAnswer.sendToFront();
		showRandomAnswer.setFont();
		
		//text objects
		scoreLabel = new GLabel("Score : " +score, 30, 30);
		scoreLabel.setColor(Color.CYAN);
		time = new GLabel("Time : 0", 30, 60);
		time.setColor(Color.CYAN);
		
		
		maxCombo = new GLabel("", 500, 500);
		maxCombo.setFont(OSD);
		maxBG = new GImage("IMAGES/answerBox.png", 480, 465);
		maxBG.setSize(240, 60);
		
		capturedKeanu = new GLabel("", 500, 585);
		capturedKeanu.setFont(OSD);
		capBG = new GImage("IMAGES/answerBox.png", 480,550);
		capBG.setSize(240, 60);
		
		keanuBall = new GImage("IMAGES/pokeball.png", 25, 75);
		keanuBall.setSize(75, 75);
		
		ballBG = new GImage("IMAGES/answerBox.png", 10, 65);
		ballBG.setSize(95, 95);
		
		
		music = new GImage("IMAGES/music.gif", 900, 400);
		onImg = new GImage("IMAGES/on.gif", 885, 475);
		onImg.setSize(50,45);
		offImg = new GImage("IMAGES/off.gif", 960, 475);
		offImg.setSize(50,45);
		on = new GButton("on", 885, 475, 50, 45);
		off = new GButton("off", 960, 475, 50, 45);
		on.setVisible(false);
		off.setVisible(false);
	}
	
	private void getNewCustomer() {
		if(currentCustomer == null) {
			
			currentCustomer = new GImage("IMAGES/customer1.png", 950, 25);
			currentCustomer.setSize(225, 175);
			
		}else {
			Random rand = new Random();
			
			int nextCustomer = Math.abs(rand.nextInt()) % NUM_CUSTOMERS;
			
			currentCustomer.setImage("IMAGES/customer"+ nextCustomer + ".png");
			currentCustomer.setSize(225, 175);
		}
		
		
	}
	
	private void getDialogue() {
		if(customerHappy) {
			if(speech == null) {
				speech = new GImage("IMAGES/happy_dialogue (1).gif", 780, 140);
				speech.setSize(80,40);
			}else {
				Random rand = new Random();
				
				int nextDialogue = Math.abs(rand.nextInt(NUM_HAPPY_DIALOGUE)) + 1;
				
				speech.setImage("IMAGES/happy_dialogue ("+ nextDialogue + ").gif");
				speech.setSize(80,40);
			}
		}
		else if(customerAngry) {
			if(speech == null) {
				speech = new GImage("IMAGES/angryDialogue (1).gif", 780, 140);
				speech.setSize(110,40);
			}else {
				Random rand = new Random();
				
				int nextAngryDialogue = Math.abs(rand.nextInt(NUM_ANGRY_DIALOGUE)) + 1;
				
				speech.setImage("IMAGES/angryDialogue ("+ nextAngryDialogue + ").gif");
				speech.setSize(110,40);
			}
		}
	}
	
	private void getRandomDialogue() {
		if(randomSpeech == null) {
			randomSpeech = new GImage("IMAGES/randDialogue (1).gif", 40, 150);
			randomSpeech.setSize(130,60);
		}else {
			Random rand = new Random();
			
			int nextDialogue = Math.abs(rand.nextInt(NUM_HAPPY_DIALOGUE)) + 1;
			
			randomSpeech.setImage("IMAGES/randDialogue ("+ nextDialogue + ").gif");
			randomSpeech.setSize(130,60);
		}
		program.add(randomSpeech);
	}
	
	

	
	private boolean checkWin() { // checks win
		if(QuestionBank.isEmpty()) {
			return true;
		}
		else if(quickPlayOn && answeredQuestions == 4 ) {
			return true;
		}
		else if(infiniteModeOn) {
			initQuestions();
			return false;
		}
		else {
			return false;
		}
	}
	
	//function for quickly moving GRects to a position on a 10x8 grid on the questionSheet
	private void addToGrid (GRect box, double row, double col) {
		box.setSize(75.5, 68.75);
		box.setLocation(col * 75.5 + 430, row *68.75 + 255);
		return;
	}
	
	private void addToGrid (GLabel label, double row, double col) {
		label.setLocation(col * 75.5 + 455, row *68.75 + 285);
		return;
	}
	
	//wrapper function for loading all the txt files
	private void initQuestions() {
		
		LoadQuestionFromFile("TXT/tutorialQuestion.txt", "Welcome to Cook me some code! I'll be your guide, My name is Keanu Reeves. Try dragging the \"sum \" answer card to the first blank");
		
		LoadQuestionFromFile("TXT/QuestionCards.txt", "Build me a Loop to calculate the sum from 1 to 30.");
		
		LoadQuestionFromFile("TXT/question2.txt", "I want a function that reverses an array!");
		
		LoadQuestionFromFile("TXT/question3.txt", "Declare an ArrayList in my class then initialize it in the constructor!");
		
		LoadQuestionFromFile("TXT/question4.txt", "Print out the first element of my ArrayList of Strings to the console! Don't forget to intialize it and fill it up....");
		
		LoadQuestionFromFile("TXT/question5.txt", "Make me a solid Red GRect at 100, 200 with 100 width and 200 height. Don't forget to add it to the screen!");
		
		LoadQuestionFromFile("TXT/question6.txt", "I want a loop to find th biggest number in my Array....");

		LoadQuestionFromFile("TXT/question7.txt", "Implement a timer in my class, and have it increment myInt every 10 milliseconds");
		
		LoadQuestionFromFile("TXT/question8.txt", "Make me a Hashmap! Start by intialiizing a map and then adding an item!");

		

		//testing
		//System.out.println("Size of question's five answer pool: " + QuestionBank.get(4).answerPoolSize());
	}
	
	private void LoadQuestionFromFile(String path, String order) {
		ArrayList<GRect> blanks = new ArrayList<GRect>();
		ArrayList<GLabel> questionBlocks = new ArrayList<GLabel>();
		ArrayList<Integer> answerOrder = new ArrayList<Integer>();
		
		
		
		//Text file format is:
		// #blanks #labels
		//row col
		//row col LabelText
		//AnswerKey
		
		
		String inputFile = Util.loadFileAsString(path);
		
		String[] splitFile = inputFile.split("\\s+");
		
		GRect box = new GRect(0,0,0,0);
		GLabel text;
		
		int numBlanks = Util.parseInt(splitFile[0]);
		int numPhrases = Util.parseInt(splitFile[1]);
		
		
		//add all blanks to the questionCard
		for(int index = 0; index < numBlanks; index ++) {
			double row = Util.parseDouble(splitFile[(index *2) + 2]);
			double col = Util.parseDouble(splitFile[(index *2) + 3]);
			box = new GRect(0,0,0,0);
			addToGrid(box, row, col);
			blanks.add(box);
			
		}
		
		int offset =  2 + (numBlanks * 2);
		//Add all the labels to questionCard
		for(int index = 0; index < numPhrases; index ++) {
			double row = Util.parseDouble(splitFile[(index *3) + offset]);
			double col = Util.parseDouble(splitFile[(index *3) + offset + 1]);
			text = new GLabel(splitFile[(index *3) + offset + 2], 0, 0);
			addToGrid(text, row, col);
			text.setFont("Roboto-14");
			questionBlocks.add(text);
		}
		offset += numPhrases * 3;
		
		for(int index = 0; index < numBlanks; index++) {
			answerOrder.add(Util.parseInt(splitFile[offset + index]));
		}
		QuestionCard question = new QuestionCard(program, blanks, questionBlocks, answerOrder, order, OSD);
		
		
		QuestionBank.add(question);
		
	}
	public void addBall() {
		numBalls++;
	}
	
	public int getArmor() {
		return comboBar.getArmor();
	}
	
	public void addArmor() {
		comboBar.addArmor();
	}
	public int getSlowdown() {
		return comboBar.getSlowdown();
	}
	public void addSlowdown() {
		comboBar.addSlowdown();
	}
	@Override
	public void showContents() {
		// TODO Auto-generated method stub
		program.add(background);
		//program.add(answerPanel);
		program.add(questionPanel);
		program.add(orderPanel);
		program.add(portraitBack);
		program.add(currentCustomer);
		program.add(portraitPanel);
		scoreLabel.setLabel("Score: " + score);
		program.add(scoreLabel);
		program.add(time);
		program.add(showRandomAnswer);
		
		
		//tests
		//program.add(pepperoni);
		//program.add(crust);
		//testQuestion();
		comboBar.resetArmor();
		
		//top layer items
		QuestionBank.get(currentQuestion).show();
		
		//testAnswers();
		
		if(numBalls != 0) {
			program.add(ballBG);
			program.add(keanuBall);
		}
		
		//main timer starts here for anything that needs to be updated based on time
		timer = new Timer(10, this);
		timer.start();

	}

	@Override
	public void hideContents() {
		// TODO Auto-generated method stub
		//program.remove(answerPanel);
		timer.stop();
		program.remove(questionPanel);
		program.remove(orderPanel);
		program.remove(portraitPanel);
		program.remove(currentCustomer);
		program.remove(scoreLabel);
		program.remove(time);
		program.remove(background);
		program.remove(showRandomAnswer);

		QuestionBank.get(currentQuestion).hide();
		
		if(numBalls != 0) {
			program.remove(ballBG);
			program.remove(keanuBall);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		currObject = program.getElementAt(e.getX(), e.getY());
		//see if user clicked on an object
		if(currObject == null) {return;}
		//see if user clicked hint button
		
		if(currObject == showRandomAnswer) {
			if(tutorial) {
				currObject = null;
				return;
			}
			if (score>=10) {
				QuestionBank.get(currentQuestion).revealRandomAnswer();
				
				if(QuestionBank.get(currentQuestion).finished()) {
					QuestionBank.get(currentQuestion).hide();
					QuestionBank.remove(currentQuestion);
					if(checkWin()) {
						// exit game
						gameOverScreen();
					}
					else {
						getNewCustomer();
						getNewCard();
						answeredQuestions++;
						if(answeredQuestions % 3 == 0) {
							dialogueTime = 301;
							inShop = true;
							program.switchToShop();
						}
						else {
							inShop = false;
						}
						
					}
				}
				score -= 10;
				if(score<10) program.remove(showRandomAnswer);
				scoreLabel.setLabel("Score: " + score);
				
			}
			currObject = null;
			return;
		}
		//sound on button
		if(currObject == on) {
			if(program.getSound() == false) {
				program.playMusic();
				program.setSound(true);
				currObject = null;
				return;
			}
		}
		//sound off button
		if(currObject == off) {
			if(program.getSound() == true) {
				program.stopMusic();
				program.setSound(false);
				currObject = null;
				return;
			}
		}
		//see if it's a keanuBall
		if(currObject == keanuBall) {
			System.out.println("clicked keanuball");
			startX = currObject.getX();
			startY = currObject.getY();
			lastX = e.getX();
			lastY = e.getY();
			return;
		}
		
		//see if it is an answer box
		if(! (currObject instanceof GCompound)) {
			currObject = null;
			return;
			}
		
		GCompound offset = (GCompound) currObject;
		startX = currObject.getX();
		startY = currObject.getY();
		lastX = offset.getElement(0).getX();
		lastY = offset.getElement(0).getY();
		//System.out.println("Object start: (" + startX + ", " + startY + ") \n");
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		if(currObject == null) {return;}
		
		
		double xChange = e.getX() - lastX ;
		double yChange = e.getY() - lastY;
		
		currObject.move(xChange, yChange);
		lastX = e.getX();
		lastY = e.getY();
		//System.out.println("Object moving: (" + lastX + ", " + lastY + ") \n");
		//program.remove(happySpeech);
		//program.remove(angrySpeech);
		
	

	}
	@Override
	public void mouseReleased(MouseEvent e) {
		if(currObject == null) {return;}
		
		if(currObject instanceof GButton) {
			currObject = null;
			return;
		}
		
		if(tutorial) {
			tutorial();
		}
		
		
		if(currObject == keanuBall) {
			if(Util.collision(currObject.getBounds(), wildKeanu.getKeanu().getBounds())) {
				if(wildKeanu.capture()) {
					//captured wild keanu
				}else {
					//it got out
				}
				currObject.setLocation(startX, startY);
				currObject = null;
				return;
			}
			else {
				currObject.setLocation(startX, startY);
				currObject = null;
				return;
			}
			
		}
		
		GCompound offset = (GCompound) currObject;
		
		
		
		//System.out.println("Checking for answer.....\n");
		
		//check if answer was right
		if(QuestionBank.get(currentQuestion).checkCollision( currObject.getBounds(), ((GLabel) offset.getElement(1)).getLabel())) {
			//code for score/money here
			//System.out.println("Answer put in right place \n");
			//score += 10;
			//System.out.println("Answer put in right place \n");
			if(score < 10) {
				program.add(showRandomAnswer);
			}
			score += (int) (10 * (comboBar.getCombo()));
			comboBar.addToCombo();
			scoreLabel.setLabel("Score: " + score);
			
			customerHappy = true;
			customerAngry = false;
			getDialogue();
			
		}else {
			System.out.println("Combo: " + comboBar.getCombo());
			if(comboBar.getCombo() != 1.0) {
				System.out.println("Combo break");
				comboBar.breakCombo();
			}
			customerHappy = false;
			customerAngry = true;
			getDialogue();
		}
		dialogueTime=0;
		customerSpeaking = true;
		currObject.setLocation(startX, startY);
		currObject = null;
		
		
		if(QuestionBank.get(currentQuestion).finished()) {
			QuestionBank.get(currentQuestion).hide();
			QuestionBank.remove(currentQuestion);
			if(checkWin()) {
				// exit game
				gameOverScreen();
			}
			else {
				getNewCustomer();
				getNewCard();
				answeredQuestions++;
				if(answeredQuestions % 3 == 0) {
					dialogueTime = 301;
					inShop = true;
					program.switchToShop();
				}
				else {
					inShop = false;
				}
				
			}
		}
	}
	
	public void addToScore(int toAdd) {
		score += toAdd;
	}
	private void gameOverScreen() {
		dialogueTime = 301;
		gameOverScreen = new GImage("gameOver.gif", 0,0);
		gameOverScreen.setSize(1200,800);
		gameOver = new GImage("gameOverLogo.gif", 515, 340);
		
		program.add(gameOverScreen);
		program.add(gameOver);
		
		scoreLabel.setLabel("Score: " + score);
		scoreLabel.setLocation(540,430);
		scoreLabel.setFont(OSD);
		scoreLabel.setColor(Color.getHSBColor(265.59f, 83.95f, 63.53f));
		
		program.add(scoreLabel);
		
	}
	private void getNewCard() {
		// TODO Auto-generated method stub
		currentQuestion = Math.abs(rand.nextInt()) % QuestionBank.size();
		QuestionBank.get(currentQuestion).show();
		comboBar.resetArmor();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		//pause
		if(key == KeyEvent.VK_ESCAPE) {
			showPaused();
		}
	}

	
	private void showPaused() {
		// TODO Auto-generated method stub
		paused = !paused;
		
		//game is paused
		if(paused) {
			maxCombo.setLabel("Highest Combo: " + comboBar.getMaxCombo());
			QuestionBank.get(currentQuestion).hide();
			program.remove(questionPanel);
			program.remove(showRandomAnswer);
			program.add(pausedTitle);
			program.add(maxBG);
			program.add(maxCombo);
			program.add(music);
			program.add(onImg);
			program.add(offImg);
			program.add(on);
			program.add(off);
			program.add(collection);
			program.add(collectionTitle);
			
			if(tutorial) {
				hideDialogue();
			}
			
			ArrayList<GImage> rares = program.rareItems();
			int j =0;
			for(int i = 0; i < rares.size(); i++) {
				temp = rares.get(i);
				temp.setLocation(30+(120*j), 220+(i*120) - (j*4*120));
				program.add(temp);
				if( i == 3 | i==7) {
					j++;
				}
			}
			program.add(capBG);
			int currentKeanus = (rares.size() + wildKeanu.getCap());
			capturedKeanu.setLabel("" + currentKeanus + "/" + numKeanu  + " rare Keanus");
			program.add(capturedKeanu);
			
			if(currentKeanus == 0) {
				pauseDialogue = Util.fixOrderLength(dialogueStrings[0], OSD);
			}else if(currentKeanus < 10) {
				pauseDialogue = Util.fixOrderLength(dialogueStrings[1], OSD);
			}else {
				pauseDialogue = Util.fixOrderLength(dialogueStrings[2], OSD);
			}
			addPauseDialogue();
			
			
		}else {
		//unpause
			program.remove(maxBG);
			program.add(questionPanel);
			QuestionBank.get(currentQuestion).show();
			program.remove(pausedTitle);
			program.remove(maxCombo);
			program.remove(music);
			program.remove(onImg);
			program.remove(offImg);
			program.remove(on);
			program.remove(off);
			program.remove(collection);
			program.remove(collectionTitle);
			program.remove(capBG);
			program.remove(capturedKeanu);
			removePauseDialogue();
			ArrayList<GImage> rares = program.rareItems();
			for(int i = 0; i < rares.size(); i++) {
				temp = rares.get(i);
				program.remove(temp);
				
			}
			program.add(showRandomAnswer);
			
			if(tutorial) {
				if(currDialogue.size() != 0) {
					QuestionBank.get(currentQuestion).hideDialogue();	
					showDialogue();
				}
				
				
			}
	
		}
		
	}
	
	private void addPauseDialogue() {
		// TODO Auto-generated method stub
		for(int i = 0; i < pauseDialogue.size(); i++) {
			program.add(pauseDialogue.get(i));
		}
	}

	private void removePauseDialogue() {
		// TODO Auto-generated method stub
		for(int i = 0; i < pauseDialogue.size(); i++) {
			program.remove(pauseDialogue.get(i));
		}
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

	public int score() {
		return score;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		randomDialogueTime++;
		// TODO Auto-generated method stub
		if(paused || tutorial) {
			return;
		}
		comboBar.update();
		
		
		//wildKeanu don't spawn if the player has no way to capture them
		if(numBalls > 0) {
			wildKeanu.update();
		}
		
		// Customer answer response
		if(customerSpeaking) {
			program.add(speech);
			dialogueTime++;
			if(dialogueTime > 300) {
				program.remove(speech);
				customerSpeaking = false;
				dialogueTime=0;
			}
		}
		
		// random dialogue
		if(!inShop) {
			if(randomDialogueTime % 350 == 0 ) {
				getRandomDialogue();
			}
			else if(randomDialogueTime % 699 ==0) {
				if(randomSpeech != null) {
				program.remove(randomSpeech);	
				}
				
				randomDialogueTime = 0;
			}
		}
	}
	

	public void hideDialogue() {
		for(int i = 0; i <currDialogue.size(); i++) {
			program.remove(currDialogue.get(i));
		}
	}
	public void showDialogue() {
		for(int i = 0; i <currDialogue.size(); i++) {
			program.add(currDialogue.get(i));
		}
	}
	
	private void tutorial() {
		System.out.println("in tutorial");
		GCompound offset = (GCompound) currObject;
		
		
		//hide questionCard base dialogue
		if(currDialogue.size() != 0) {
			hideDialogue();
		}else {
			QuestionBank.get(currentQuestion).hideDialogue();
			hideDialogue();
		}
		
		
		
		if(QuestionBank.get(currentQuestion).checkCollision( currObject.getBounds(), ((GLabel) offset.getElement(1)).getLabel())) {
			comboBar.addToCombo();
			//they filled the first blank
			if(QuestionBank.get(currentQuestion).getRemainingBlank() == 2) {
				currDialogue = Util.fixOrderLength(tutorialDialogue[1], OSD);
				showDialogue();
			}else if(QuestionBank.get(currentQuestion).getRemainingBlank() == 1) {
				currDialogue = Util.fixOrderLength(tutorialDialogue[3], OSD);
				showDialogue();
			}
			
		}else {
			
			//they failed the first blank
			if(QuestionBank.get(currentQuestion).getRemainingBlank() == 3) {
				currDialogue = Util.fixOrderLength(tutorialDialogue[0], OSD);
				showDialogue();
			//they took the bait
			}else if(QuestionBank.get(currentQuestion).getRemainingBlank() == 2) {
				currDialogue = Util.fixOrderLength(tutorialDialogue[2], OSD);
				showDialogue();
			}
			
			
			
		}
		//tutorial finished
		if(QuestionBank.get(currentQuestion).getRemainingBlank() == 0) {
			hideDialogue();
			tutorial = false;
			QuestionBank.get(currentQuestion).hide();
			QuestionBank.remove(currentQuestion);
			getNewCard();
			getNewCustomer();
		}
		
	}
}

