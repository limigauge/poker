//-----------------------------------------
// REMARKS: This is the class containing the main method that will run the game. 
//-----------------------------------------

public class PokerGame
{
	public static void main(String[] args)
	{
		//Build a game logic, feed it into the PokerTableDisplay
		GameLogicable gl = new GameLogic(); //Insert a call to the constructor of your class that implements GameLogicable
		PokerTableDisplay ptd = new PokerTableDisplay(gl);
	}
}
	
