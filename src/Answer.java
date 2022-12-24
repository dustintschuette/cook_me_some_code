import acm.graphics.GCompound;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GRect;

public class Answer {
	public Integer answer_ID;
	private String answerContent;
	public GCompound answerBox;
	private int x=20;
	private int y=275;
	private int width=170;
	private int height=60;
	
	public Answer(String content, Integer id) {
		answer_ID = id;
		answerContent = content;
		answerBox = new GCompound();
	}
	
	void setX(int column) {
		if (column == 1) {
			x=210;
		}
	}
	
	void setY(int row) {
		switch (row){
			case 1: y+=75;
			break;
			case 2: y+=150;
			break;
			case 3: y+=225;
			break;
			case 4: y+=300;
			break;
			case 5: y+=375;
			break;
		}
	}
	
	void createRectLabel(int row, int column) {
		setX(column);
		setY(row);
		GImage box = new GImage("IMAGES/answerBox.png", x, y);
		box.setSize(width, height);
		answerBox.add(box);
		//answerBox.add(new GRect(x,y,width,height));
		GLabel label = new GLabel(answerContent, x+15, y+30);
		label.setFont("Roboto-17");
		answerBox.add(label);
	}
	
	GCompound getAnswerBox() {
		return answerBox;
	}
	
	public String getContent() {
		return answerContent;
	}
}
