/*
 *   REMARKS: A class for a player CPU that chooses random cards from its hand to discard.
 */

public class RandomCPU extends AI {

    public RandomCPU(){
        super();
    }//default

    /*
     *---------------------------------------------------------------------------------------------------
     *   selectDiscards
     *
     * PURPOSE: Picks random cards in hand to change to selected (which will be discarded by the GameLogic class's call later.
     *          Chooses each set of discards randomly by going through each card and giving it a 50/50 change as to whether the card will be selected.
     * PARAMETERS: none.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void selectDiscards() {
        int coinFlip;

        for(int i = 0; i < Handable.HAND_SIZE; i++){
            coinFlip = (int) Math.floor(2 * Math.random()); //returns either 0 or 1

            if(coinFlip == 0){
                hand.getCard(i).switchSelectedState(); //select card
            } //else, does nothing to this card

        }//goes through entire hand with a 50/50 chance to select (and eventually discard) each card.

    }
}
