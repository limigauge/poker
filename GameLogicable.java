public interface GameLogicable
{
	//Constants:
	public static final int MAX_GAME_STATES = 6;  //The total number of states in a game.
	
	//Methods:
	public Handable getCPUHand();  //Returns the hand (Handable) of the CPU player.

	public Handable getHumanHand();  //Returns the hand (Handable) of the human player.
	
	public boolean nextState(String[] messages);  
}
