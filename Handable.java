import java.util.LinkedList;

public interface Handable extends Comparable<Handable>

{
	//Constants:
	public static final int HAND_SIZE = 5;
	
	//Methods:
	public Cardable getCard(int i); 
	
	public void draw(Deckable d, boolean faceUp); 
	
	public void showAllCards(); 
	
	public LinkedList<Cardable> discard();  
	
	public LinkedList<Cardable> returnCards();  
	
	public String evaluateHand();  
}
