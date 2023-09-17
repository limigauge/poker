/*
 *   REMARKS: Keeps track of the events and variables of the game, such as players, each with their own hands, the deck, the state of the game,
 *            and decides which messages to print to match the situation of the game.
 *            There are two CPUs that can be swapped by commenting/uncommenting the randomAI = false line of code in constructor (should be line 33).
 */

import java.util.LinkedList;

public class GameLogic implements GameLogicable{

    private AI p2;
    private Human p1;
    private Deck deck;
    private int gameNumber;
    private int state;
    private LinkedList discardPile; //List of cards that have been discarded
    private boolean randomAI; //for choose which AI to play

    public GameLogic(){
        state = 1;
        gameNumber = 1;
        discardPile = new LinkedList<Cardable>();
        randomAI = true;

        deck = new Deck();
        deck.shuffle(); //shuffle deck the first time

        p1 = new Human();

        randomAI = false;
        //comment OR uncomment above boolean for smart AI

        if(randomAI) {
            p2 = new RandomCPU();
        } else {
            p2 = new SmartCPU();
        } //changes the CPU type depending on if you comment or uncomment the randomAI boolean


    }

    //getters for CPU and Human hands

    public Handable getCPUHand() {
        return p2.getHand();
    }

    public Handable getHumanHand() {
        return p1.getHand();
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   nextState
     *
     * PURPOSE: Keeps track of the game state and prints messages based on the game and its state. Changes the messages String array to
     *          Display the correct messages describing each state of the game. There are 6 states of the game and will loop around every time
     *          the player wants to replay.
     *          Addition: each state resets the selection state of the cards in the hard (to make sure the player isn't randomly selecting
     *                    cards when they don't need to/aren't prompted to.
     * PARAMETERS:
     *      String[] messages - String array that is altered each state of the game for the printed message
     *
     * RETURNS:
     * ---------------------------------------------------------------------------------------------------
     */

    public boolean nextState(String[] messages) {


        if (state == 1) {

            /*
            *   Draws the hands for both players. The CPU's hand is face down and the player's is face up.
            *   Makes sure none of the cards in the hand have been selected yet.
             */

            p1.getHand().draw(deck, true);
            p2.getHand().draw(deck, false);

            p1.getHand().deselectHand();
            p2.getHand().deselectHand();

            //state 1 messages
            messages[0] = "Beginning of Game " + gameNumber;
            messages[1] = "Player 1, choose which cards to discard";
            messages[2] = "and click on the Proceed button";
            messages[3] = "";

        } else if (state == 2){

            /*
            *   Discards all the selected cards in the human player's hand, and adds them to the discard pile LinkedList.
            *   They will be returned later at the end of the current game.
            *   Allows CPU to select cards to discard at the end of state 2.
             */

            discardPile.addAll(p1.hand.discard());

            //messages differ depending on CPU type.
            messages[0] = "Player 1 has discarded cards";
            if(p2 instanceof RandomCPU){
                messages[1] = "Dumb CPU is thinking...";
            } else {
                messages[1] = "Smarter CPU is thinking...";
            }

            messages[2] = "";
            messages[3] = "";

            p1.getHand().deselectHand();
            p2.getHand().deselectHand();

            p2.selectDiscards();

        } else if (state == 3) {

            /*
            *   Discards the selected cards from CPU's hand and prints a message to match. All discarded cards get appended to the discardPile LinkedList
             */

            discardPile.addAll(p2.hand.discard());

            if(p2 instanceof RandomCPU){
                messages[0] = "Dumb CPU has discarded cards.";
            } else {
                messages[0] = "Smarter CPU has discarded cards.";
            }
            messages[1] = "Each player will be dealt the same number they discarded.";

            p1.getHand().deselectHand();
            p2.getHand().deselectHand();

        } else if (state == 4){

            /*
            *   Draw cards to replenish the cards discarded from both players.
             */

            p1.getHand().draw(deck, true);
            p2.getHand().draw(deck, false);

            p1.getHand().deselectHand();
            p2.getHand().deselectHand();

            messages[0] = "Each player has been dealt new cards.";
            messages[1] = "Click on proceed to see the winner!";

        } else if (state == 5) {
            /*
            *   Shows cards of both players (flips p2's face down cards up for the player to see) and evaluates both of the hands with a short message.
            *   Decides the winner by calling another getWinner method which changes message String array to the correct text output.
             */

            p2.getHand().showAllCards();

            p1.getHand().deselectHand();
            p2.getHand().deselectHand();

            if(p2 instanceof RandomCPU){
                messages[0] = "Dumb CPU has: " + p2.getHand().evaluateHand();
            } else {
                messages[0] = "Smarter CPU has: " + p2.getHand().evaluateHand();
            }
            messages[1] = "Player 1 has: " + p1.getHand().evaluateHand();

            getWinner(messages);

        } else if (state == 6) {

            /*
            *   Prompts the player to start a new game and adds all the cards last in the players' hands into the discardPile List.
            *   Then returns all the Cards in the discard pile back into the deck and shuffles.
            *   Resets state, increments game number, and resets deck (make sure everything is default not selected, face down)
            *   Finally, clears the discardPile so we don't get copies every game
             */

            messages[0] = "Click on Proceed to play a new game!";
            messages[1] = "";
            messages[2] = "";
            messages[3] = "";

            p1.getHand().deselectHand();
            p2.getHand().deselectHand();

            discardPile.addAll(p1.getHand().returnCards());
            discardPile.addAll(p2.getHand().returnCards());

            deck.returnToDeck(discardPile);
            deck.shuffle();

            state = 0; //reset state
            gameNumber++;
            deck.resetDeck();
            discardPile.clear(); //empty discardPile so we don't create duplicates
        }

        state++;
        //state is incremented for next state.

        return true;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   getWinner
     *
     * PURPOSE: Evaluate and compare both players hands and come up with a winner. Amend the messages String[] array to the correct message
     *          depending on the winner. Then, increases the count of the number of wins for the player who won.
     * PARAMETERS:
     *      String[] messages - String array that is displayed in game. Changed to reflect the outcome needed to be displayed.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    private void getWinner(String[] messages){
        if(p1.getHand().compareTo(p2.getHand()) > 0){
            messages[2] = "Player 1 wins!!!";
            p1.increaseWins();
        } else if (p1.getHand().compareTo(p2.getHand()) < 0){
            if(p2 instanceof RandomCPU){
                messages[2] = "Dumb CPU wins...";
            } else {
                messages[2] = "Smarter CPU wins...";
            }
            p2.increaseWins();
        } else {

            messages[2] = "It's a perfect tie!?";
            p1.increaseWins();
            p2.increaseWins();
        }

        messages[3] = "Player 1 has won " + p1.getWins() + " games. ";
        if(p2 instanceof RandomCPU){
            messages[3] += "Dumb CPU has won " + p2.getWins() + " games.";
        } else {
            messages[3] += "Smarter CPU has won " + p2.getWins() + " games.";
        }
    }

}
