import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class AnswerPool {
	ArrayList <Answer> answerPool = new ArrayList<Answer>();
	
	public int lookupID(String target) {
		
		for(int i = 0; i < answerPool.size(); i++) {
			if(target.compareTo(answerPool.get(i).getContent()) == 0) {
				return i;
			}
		}
		//not an answer? Hopefully not
		//System.out.println("target: " + target );
		System.out.println("Answer not found in master lookup: " + target + "\n");
		return -1;
	}
	
	public AnswerPool() {
		init();
	}
	
	void init() {
		String text = Util.loadFileAsString("TXT/AnswerCards.txt");
		
		String[] textArr = text.split("\\s+");
		
		int counter = 0;
		Answer temp;
		while(!textArr[counter].equals("end")) {
			//System.out.println(textArr[counter]);
			temp = new Answer(textArr[counter], counter);
			counter++;
			answerPool.add(temp);
		}
		return;
	}
	
	
	Answer drawAnswerbyID(Integer ID) {
		for(int i=0; i<answerPool.size(); i++) {
			if (answerPool.get(i).answer_ID==ID) {
				return answerPool.get(i);
			}
		}
		return null;
	}
	
	ArrayList<Answer> shuffle(ArrayList<Answer>list){
		Random rand = ThreadLocalRandom.current();
		
		for(int i=list.size()-1; i>0; i--) {
			int index = rand.nextInt(i+1);
			Answer elem = list.get(index);
			list.set(index, list.get(i));
			list.set(i, elem);
		}
		return list;
	}
	
	ArrayList<Answer> drawAnswers(ArrayList<Integer>order) {
		
		if(order.size()>12) {
			System.out.println("Too many answers required! \n");
			return null;
		}
		
		ArrayList<Answer> wrongAnswers = new ArrayList<Answer>();
		wrongAnswers.addAll(answerPool);
		
		for(int i=0; i<order.size(); i++) {
			wrongAnswers.remove(drawAnswerbyID(order.get(i)));
		}
		
		wrongAnswers = shuffle(wrongAnswers);
		
		
		ArrayList<Answer> answerSet = new ArrayList<Answer>();
		
		for(int i=0; i<12-order.size(); i++) {
			answerSet.add(wrongAnswers.get(i));
		}
		
		for(int i=0; i<order.size(); i++) {
			answerSet.add(drawAnswerbyID(order.get(i)));
		}
				
		return answerSet;
	}
	
	
	ArrayList<Answer> placeAnswerSet(ArrayList<Integer>order) {
		ArrayList <Answer> answerSet = drawAnswers(order);
		answerSet = shuffle(answerSet);
		
		/*for(Answer elem:answerSet) {
			System.out.println(elem.answer_ID);
		}*/
		int i=0;
		for(int j=0; j<6; j++) {
			answerSet.get(i).createRectLabel(j,0);
			i++;
			answerSet.get(i).createRectLabel(j,1);
			i++;
		}
		
		return answerSet;
	}
	
	
}

