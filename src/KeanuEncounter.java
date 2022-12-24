import java.util.ArrayList;
import java.util.Random;

import acm.graphics.GImage;

public class KeanuEncounter {
	
	MainApplication program;
	
	ArrayList<GImage> wildKeanu;
	ArrayList<GImage> capKeanu;
	double startX, startY, diffX, diffY, spawnY, spawnX;
	int currentKeanu;
	int nextEncounter;
	int currentEncounter = 1000;
	int tickRate = 2;
	int numWild = 5;
	Random rand;
	
	
	KeanuEncounter(MainApplication program){
		
		
		this.program = program;
		currentKeanu = -1;
		spawnY = 500;
		spawnX = 500;
		
		
		
		//initialize objects
		rand = new Random();
		wildKeanu = new ArrayList<GImage>();
		capKeanu = new ArrayList<GImage>();
		
		
		
		
		
		nextEncounter = Math.abs(rand.nextInt() % 2000) + 1000;
		//init wild keanus
		for(int i = 1; i < (numWild + 1); i++) {
			GImage temp = new GImage("IMAGES/wild" + i + ".gif", startX, startY);
			temp.setSize(120, 120);
			wildKeanu.add(temp);
		}
		
		
		
	}
	
	public boolean capture() {
		//could roll here for failure
		
		if(true) {
			capKeanu.add(getKeanu());
			program.remove(wildKeanu.get(currentKeanu));
			wildKeanu.remove(currentKeanu);
			numWild -= 1;
			currentKeanu = -1;
			currentEncounter = 1000;
			nextEncounter = Math.abs(rand.nextInt() % 2000) + 1000;
			return true;	
		}
		
		return false;
	}
	
	public void getEncounter() {
		//add randomKeanu to the screen
	}
	
	
	public void showCollection() {
		for(int i =0; i < capKeanu.size(); i++) {
			program.add(capKeanu.get(i));
		}
		
	}
	
	public void hideCollection() {
		for(int i =0; i < capKeanu.size(); i++) {
			program.remove(capKeanu.get(i));
		}
	}
	
	public void update() {
		
		//no encounters left
		if(numWild == 0) {
			//System.out.println("All keanus captured");
			return;
		}
		
		nextEncounter -= tickRate;
		
		//no new encounter or current Encounter
		if(nextEncounter > 0 && currentEncounter == 1000) {
			return;
		}
		
		//see if new encounter or current encounter
		if(currentEncounter == 1000) {
			//new Encounter
			System.out.println("A wild keanu appears!");
			currentKeanu = Math.abs(rand.nextInt() % numWild);
			wildKeanu.get(currentKeanu).setLocation(spawnX, spawnY);
			program.add(wildKeanu.get(currentKeanu));
		}
		
		currentEncounter -= tickRate;
		//move keanu around the screen
		if(currentKeanu == -1) {
			return;
		}
		int moveX = rand.nextInt() % 3;
		int moveY = rand.nextInt() % 3;
		wildKeanu.get(currentKeanu).move(moveX, moveY);
		
		if(currentEncounter < 1) {
			program.remove(wildKeanu.get(currentKeanu));
			currentEncounter = 1000;
			nextEncounter = Math.abs(rand.nextInt() % 2000) + 1000;
			
		}
	}
	
	public GImage getKeanu() {
		if(currentKeanu == -1) {
			return null;
		}
		return wildKeanu.get(currentKeanu);
	}
	public int getCap() {
		return capKeanu.size();
	}

}
