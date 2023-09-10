/*
 *   CLASS : Player.java
 *   Author: Michelle Li, 7866927
 *
 *   REMARKS: Class for Players. Hold a player's fields such as their hand and their number of wins so far. Is able to increment number of wins.
 */

public class Player {

    protected Hand hand;
    private int wins;

    //constructor
    public Player(){
        hand = new Hand();
    }

    //getters
    public Hand getHand(){
        return hand;
    }
    public int getWins(){
        return wins;
    }

    //increase number of wins for this Player
    public void increaseWins(){
        wins++;
    }
}
