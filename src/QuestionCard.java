import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;
import acm.graphics.GRectangle;

import java.awt.Color;
import java.awt.Font;
import java.util.*;



public class QuestionCard {
	
		ArrayList<GRect> blanks;
		ArrayList<GLabel> questionBlocks;
		ArrayList<GLabel> filledInRows;
		ArrayList<Integer> answerOrder;
		ArrayList<GLabel> orderLabels;
		
		MainApplication program;
		AnswerPool AnswerBank;
		ArrayList<Answer> answerCards;
		Font OSD;
		
		QuestionCard(MainApplication program, ArrayList<GRect> blanks, ArrayList<GLabel> questionBlocks, ArrayList<Integer> answerOrder, String order, Font OSD){
			this.blanks = blanks;
			this.questionBlocks = questionBlocks;
			this.answerOrder = answerOrder;
			this.OSD = OSD;
			orderLabels = new ArrayList<GLabel>();
			
			//fit order to screen
			orderLabels = Util.fixOrderLength(order, OSD);
			this.program = program;
			filledInRows = new ArrayList<GLabel>();
			
			AnswerBank = new AnswerPool();
			
			ArrayList <Integer>newOrder = new ArrayList<Integer>();
			for (Integer elem : answerOrder) {
				if(!newOrder.contains(elem)) {
					newOrder.add(elem);
				}
			}
			
			answerCards = AnswerBank.placeAnswerSet(newOrder);
			
		}
		
		
		


		public void show() {
			//System.out.println("Show called \n");
			for(int i = 0; i < blanks.size(); i++) {
				program.add(blanks.get(i));
			}
			for(int i = 0; i <questionBlocks.size(); i++) {
				program.add(questionBlocks.get(i));
			}
			for(int i = 0; i <orderLabels.size(); i++) {
				program.add(orderLabels.get(i));
			}
			
			
			for(int i=0; i<answerCards.size(); i++) {
				program.add(answerCards.get(i).getAnswerBox());
			}
			for(int i = 0; i < filledInRows.size(); i++) {
				program.add(filledInRows.get(i));
			}
		}
		
		
		public void hide() {
			for(int i = 0; i < blanks.size(); i++) {
				program.remove(blanks.get(i));
			}
			for(int i = 0; i <orderLabels.size(); i++) {
				program.remove(orderLabels.get(i));
			}
			
			for(int i = 0; i <questionBlocks.size(); i++) {
				program.remove(questionBlocks.get(i));
			}
			for(int i=0; i<answerCards.size(); i++) {
				program.remove(answerCards.get(i).getAnswerBox());
			}
			for(int i = 0; i < filledInRows.size(); i++) {
				program.remove(filledInRows.get(i));
			}
			
			for(int i = 0; i < filledInRows.size(); i++) {
				program.remove(filledInRows.get(i));
			}
		}
		
		public boolean checkCollision(GRectangle gRectangle, String target) {
			
			Iterator<GRect> it = blanks.iterator();
			int ID = AnswerBank.lookupID(target);
			int index = 0;
			
			while(it.hasNext()) {
				GRect temp = it.next();
				
				if(Util.collision(gRectangle.getBounds(), temp.getBounds())) {
					//check to see if it's the right answer
					//System.out.println("COLLISION FOUND CHECKING ID. EXPECTING: " + answerOrder.get(index) + ". GOT:" + ID + "\n");
					if(ID == answerOrder.get(index)) {
						program.remove(blanks.get(index));
						//create new answer and remove blank from board
						GLabel newAnswer = new GLabel(target, blanks.get(index).getX() + 20, blanks.get(index).getY() + 30);
						newAnswer.setFont("Serif-12");
						questionBlocks.add(newAnswer);
						program.add(newAnswer);
						
						//remove GRect from list and answer from list
						answerOrder.remove(index);
						it.remove();
						//see if text needs to be condensed
						checkRowDone();
						
						return true;
					}
				}
				index++;
			} 
			 
			return false;
		}
		
		public void revealRandomAnswer() {
			//generate random index in answerOrder
			Random rand = new Random();
			int index = rand.nextInt(answerOrder.size());
			
			//get ID and String content of the answer by index
			int ID = answerOrder.get(index);
			String target = AnswerBank.drawAnswerbyID(ID).getContent();
			
			//remove blank from question board
			program.remove(blanks.get(index));
			
			//add answer to question board
			GLabel newAnswer = new GLabel(target, blanks.get(index).getX() + 20, blanks.get(index).getY() + 30);
			questionBlocks.add(newAnswer);
			program.add(newAnswer);
			answerOrder.remove(index);
			
			//remove blank from arraylist
			blanks.remove(index);
			
			//see if text needs to be condensed
			checkRowDone();
		}
		
		
		
		public boolean finished() {
			return blanks.size() == 0;
		}
		public int  getId(String target) {
			return AnswerBank.lookupID(target);
					
		}
		private void checkRowDone() {
			questionBlocks.sort(Comparator.comparingDouble(GLabel::getX));
			for(int row = 0; row < 8; row ++) {
				GRectangle rowBox = new GRectangle(0,  row *68.75 + 265, 1200, 0);
				boolean clear = true;
				for(int index = 0; index < blanks.size(); index ++) {
					if(Util.collision(rowBox, blanks.get(index).getBounds())) {
						clear = false;
					}
				}
				if(clear) {
					//means row is clear, now check for GLabels and condense them
					String wholeRowText = "";
					
					 Iterator<GLabel> it = questionBlocks.iterator();
					 while(it.hasNext()) {
					      GLabel temp = it.next();
					      if(temp.getY() == (row *68.75 + 285)) {
							wholeRowText += (" " +  temp.getLabel());
							program.remove(temp);
							it.remove();
							
						}
					 }
					 //if there was text to condense
					 if(wholeRowText.compareTo("") != 0) {
						 //System.out.println("text to condense: " + wholeRowText);
						 GLabel wholeRow = new GLabel(wholeRowText, 75.5 + 440, row *68.75 + 285);
						 wholeRow.setFont("Roboto-20");
						 filledInRows.add(wholeRow);
						 program.add(filledInRows.get(filledInRows.size()-1));
					 }
				}else {
					//System.out.println("Row " + row + " not clear \n");
				}
				
				
			}
		}
		
		public int answerPoolSize() {
			return questionBlocks.size();
		}
		
		public void hideDialogue() {
			for(int i = 0; i <orderLabels.size(); i++) {
				program.remove(orderLabels.get(i));
			}
		}
		public void showDialogue() {
			for(int i = 0; i <orderLabels.size(); i++) {
				program.add(orderLabels.get(i));
			}
		}
		public int getRemainingBlank() {
			return blanks.size();
		}
		
}
