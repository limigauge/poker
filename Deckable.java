import java.util.LinkedList;

public interface Deckable
{
	//Constant:
	public static final int NUM_CARDS = 52;
	
	//Methods:
	public void shuffle();  
	
	public void returnToDeck(LinkedList<Cardable> discarded);  
	
	public Cardable drawACard(boolean faceUp); 
	
	
}
