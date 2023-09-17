/*
 *   REMARKS: Smarter CPU that doesn't just discard at random: instead, makes the simple, but reasonable move to check for any strong hands (such as
 *            Straight Flushes, etc., and if those aren't immediately found, keep the duplicates (pairs, trips, quads) and discard the singles OR,
 *            if all cards are singles, keep the highest single.
 *            Frankly, the programmer (me) does not know how to play poker, so this AI happens to just play the way I would (very safely)
 */

public class SmartCPU extends AI{


    //coded each type of hand to have a number (used to compare strengths of these hands)
    private final int STRAIGHT_FLUSH = 9;
    private final int FOUR_OF_A_KIND = 8;
    private final int FULL_HOUSE = 7;
    private final int FLUSH = 6;
    private final int STRAIGHT = 5;

    public SmartCPU(){
        super();

    }//default

    /*
     *---------------------------------------------------------------------------------------------------
     *   selectDiscards()
     *
     * PURPOSE: Selects which cards this CPU will discard. If the CPU finds any of the more complicated, but rare hands from straight flush
     *          to regular straight, it won't change the hand, otherwise it finds low singles and discards them:
     *          If there are duplicates (doubles, etc...) it will leave them alone, otherwise, if all cards are singles, the CPU will discard all except
     *          the highest single.
     * PARAMETERS: none.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void selectDiscards() {
        hand.evaluateHand(); //sets a hand's strength field

        Card card1 = null;
        Card card2 = null;
        boolean duplicate = false;
        boolean allSelected = true;
        int highestIndex;

        if(hand.getHandWeight() != STRAIGHT_FLUSH
                || hand.getHandWeight() != FOUR_OF_A_KIND
                || hand.getHandWeight() != FULL_HOUSE
                || hand.getHandWeight() != FLUSH
                || hand.getHandWeight() != STRAIGHT){

            //if it's not one of these, remove some small singles....

            for(int i = 0; i < Handable.HAND_SIZE; i++){
                //select all cards that don't have duplicates and isn't the highest card.
                //check each card in hand.

                duplicate = false;

                for(int j = 0; j < Handable.HAND_SIZE; j++){
                    if(hand.getCard(i) instanceof Card){
                        card1 = (Card)hand.getCard(i);
                    }
                    if(hand.getCard(j) instanceof Card){
                        card2 = (Card)hand.getCard(j);
                    }

                    if(i != j && card1 != null && card2 != null && (card1.getValue()) == card2.getValue()){ //don't check itself
                        duplicate = true;
                    } //if the values of the two cards are the same, change duplicate to true.

                }
                if (duplicate == false){

                    if(hand.getCard(i).getSelected() == false) {
                        hand.getCard(i).switchSelectedState();
                    } //select this card
                } //otherwise, it creates at least a pair, so save it.
            }


            for(int i = 0; i < Handable.HAND_SIZE; i++){
                if(hand.getCard(i).getSelected() == false){
                    allSelected = false;
                }
            } //checks if all cards in hand were selected (so, all were singles)

            if(allSelected){
                //unselect the highest card in the hand... keep current highest card... discard the rest of the singles.
                highestIndex = 0;
                for(int i = 1; i < Handable.HAND_SIZE; i++){
                    if(hand.getCard(i) instanceof Card){
                        card1 = (Card)hand.getCard(i);
                    }
                    if(hand.getCard(highestIndex) instanceof Card){
                        card2 = (Card)hand.getCard(highestIndex);
                    }

                    if(card1.getValue() > card2.getValue()){
                        //if new card is higher than old highest
                        highestIndex = i;
                    }
                }

                hand.getCard(highestIndex).switchSelectedState();
            }

        }
        //if it IS one of the bigger and rarer hands, it's probably smarter not to change any of them....
    }
}
