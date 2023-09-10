/*
 *   CLASS : Hand.java
 *   Author: Michelle Li, 7866927
 *
 *   REMARKS: Hand class that implements Handable and TestHand's methods. Keeps track of a hand and is able to draw or discard Cards from the Hand.
 *            Also evaluates the strength of a hand or compares the hand to other hands.
 */

import java.util.LinkedList;

public class Hand implements Handable, TestableHand{

    private Card[] hand;
    private Card[] originalHand; //keep copy of original hand to display in game

    private final int JACK = 11;
    private final int QUEEN = 12;
    private final int KING = 13;
    private final int ACE = 14;

    private int strength; //strength based on type
    private int tiebreaker; //highest single value from type of hand (for tie breakers)


    public Hand(){
        hand = new Card[HAND_SIZE];
        originalHand = new Card[HAND_SIZE];
    }

    //getter for Card in Hand.
    public Cardable getCard(int i){
        return originalHand[i];
    }


    /*
     *---------------------------------------------------------------------------------------------------
     *   draw
     *
     * PURPOSE: Draw cards for the hand until the Hand has HAND_SIZE cards.
     * PARAMETERS:
     *      Deckable d - deck to draw from
     *      boolean faceUp - whether the card drawn for this hand should be flipped to be faceUp.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void draw(Deckable d, boolean faceUp){
        for(int i = 0; i < HAND_SIZE; i++){
            if(originalHand[i] == null){
                originalHand[i] = (Card) d.drawACard(faceUp);
            }
        }

        //copy entire originalHand into hand we will sort
        for(int i = 0; i < HAND_SIZE; i++){
            hand[i] = originalHand[i];
        }
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   showAllCards
     *
     * PURPOSE: Flip all faceUp booleans of each card in Hand to true (reveal hand)
     * PARAMETERS:  none.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void showAllCards(){
        for(int i = 0; i < HAND_SIZE; i++){
            originalHand[i].setFaceUp(true);
            hand[i].setFaceUp(true);
        }
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   discard
     *
     * PURPOSE: Discard all selected cards in hand and stores them into a Cardable LinkedList to return.
     * PARAMETERS:  none.
     *
     * RETURNS: LinkedList<Cardable> - Linked list of all the cards that were discarded from the hand.
     * ---------------------------------------------------------------------------------------------------
     */

    public LinkedList<Cardable> discard(){
        LinkedList<Cardable> discards = new LinkedList<Cardable>();

        for(int i = 0; i < HAND_SIZE; i++){
            if(originalHand[i].getSelected() == true){
                discards.add(originalHand[i]);
                originalHand[i] = null;
            }
        }

        return discards;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   returnCards
     *
     * PURPOSE: Return ALL cards in hand, without regards to whether they were selected. Clear hand. Stores the discarded into a LinkedList and returns it.
     * PARAMETERS: none.
     *
     * RETURNS: LinkedList<Cardable> - LinkedList of the discarded cards.
     * ---------------------------------------------------------------------------------------------------
     */

    public LinkedList<Cardable> returnCards(){
        LinkedList<Cardable> discards = new LinkedList<Cardable>();

        for(int i = 0; i < HAND_SIZE; i++){
            discards.add(originalHand[i]);
            hand[i] = null;
            originalHand[i] = null;
        }

        return discards;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   evaluateHand
     *
     * PURPOSE: Test for each possible type of hand and returns a string describing the hand and strength of the hand. Also sets a few
     *          extra fields that will help with the comparison of each type of hand and their highest values (for simple tie breakers)
     *          Strength field describes the type of hand and how it compares to other types (eg. Royal flush/straight flush has a strength
     *          of 9, which is greater than a Four of a Kind with a strength of 8, so we can compare that a Straight Flush is stronger than a
     *          Four of a kind.)
     * PARAMETERS: none
     *
     * RETURNS: String - message describing the hand and its strength.
     * ---------------------------------------------------------------------------------------------------
     */

    public String evaluateHand(){
        String out = "";

        if(isStraightFlush() != -1){
            strength = 9;
            tiebreaker = isStraightFlush();

            out += "Straight Flush, " + printValue(tiebreaker) + " high.";
        } else if (isFourOfAKind() != -1){
            strength = 8;
            tiebreaker = isFourOfAKind();

            out += "Four of a Kind of " + printValue(tiebreaker) + "s.";
        } else if (isFullHouse() != -1){
            strength = 7;
            tiebreaker = isFullHouse();

            out += "Full House with triple " + printValue(tiebreaker) + "s.";
        } else if (isFlush() != -1){
            strength = 6;
            tiebreaker = isFlush();

            out += "Flush, " + printValue(tiebreaker) + " high.";
        } else if (isStraight() != -1){
            strength = 5;
            tiebreaker = isStraight();

            out += "Straight, " + printValue(tiebreaker) + " high";
        } else if (isThreeOfAKind() != -1){
            strength = 4;
            tiebreaker = isThreeOfAKind();

            out += "Three of a Kind of " +  printValue(tiebreaker) + "s.";
        } else if (isTwoPair() != -1){
            strength = 3;
            tiebreaker = isTwoPair();

            out += "Two Pair, " + printValue(tiebreaker) + " highest pair.";
        } else if (isPair() != -1){
            strength = 2;
            tiebreaker = isPair();

            out += "Single Pair, " + printValue(tiebreaker) + "s.";
        } else {
            strength = 1;
            tiebreaker = hand[HAND_SIZE-1].getValue();

            sortValue();
            out += "High card, " + printValue(tiebreaker) + ".";
        }

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   addCards
     *
     * PURPOSE: Used for testing. Adds a specific array of 5 Cards into the hand as its cards.
     * PARAMETERS:
     *      Cardable[] cards - Array of cards to add to the hand
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void addCards(Cardable[] cards) {
        for(int i = 0; i < HAND_SIZE; i++){
            if(cards[i] instanceof Card) {
                hand[i] = (Card) cards[i];
                originalHand[i] = (Card) cards[i];
            }
        }
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   compareTo
     *
     * PURPOSE: Used to compare this current hand with a Hand that is input. Returns positive if this hand is stronger, otherwise
     *          is parameter's hand is stronger, returns a negative number. If both hands are EXACTLY the same strength (even past any
     *          attempt of a tie breaker) then it will return 0.
     * PARAMETERS:
     *      Handable opponent - Hand of cards to test against.
     *
     * RETURNS: int - describes if this hand is stronger than the opponent's (pos) or otherwise.
     * ---------------------------------------------------------------------------------------------------
     */

    public int compareTo(Handable opponent) {
        evaluateHand();
        int out = 0;
        int[] opponentValues;
        int[] myValues;
        //integer arrays of the values in the hands, in case of checking single tie breakers

        Hand other = null;

        if(opponent instanceof Hand){
            other = (Hand) opponent;
        }
        other.evaluateHand();
        opponentValues = other.getValueArray(); //get array of values from other hand
        myValues = getValueArray();

        out = strength - other.getHandWeight();
        //if this hand is stronger, returns pos and if this hand is weaker, returns neg. If same strength, check tiebreaker highest value for category:

        //simple tie breaker:
        if (out == 0) {
            out = tiebreaker - other.getTiebreaker();
        }

        if (out == 0){
            //still a tie... we have to test certain specific outcomes and singles now.
            //3 possibilities now: two pairs and we need to check the single OR one pair, need to check 3 singles, OR all singles: need to check each remainder.
            //note that both integer arrays are sorted at creation

            if(isTwoPair() != -1){
                out = findASingle(myValues, 1, 1) - findASingle(opponentValues, 1, 1);

            } else if (isPair() != -1){

                for(int i = 1; i <= 3; i++){
                    if (out == 0) {
                        //still a tie...
                        out = findASingle(myValues, 3, i) - findASingle(opponentValues, 3, i);
                    }
                }

            } else {
                //high card
                for(int i = 1; i <= HAND_SIZE; i++){
                    if (out == 0) {
                        //still a tie...
                        out = findASingle(myValues, HAND_SIZE, i) - findASingle(opponentValues, HAND_SIZE, i);
                    }
                }

            }
        }

        //if out is still zero here, it was a real perfect tie....

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   findASingle
     *
     * PURPOSE: Helper method for the compareTo method to compare the size of Singles between hands when a tie breaker is necessary.
     *          Finds all the singles in a hand, puts them into their own array, ordered, and returns the x highest single in the hand.
     * PARAMETERS:
     *      int[] array - Int array of all the values of the cards in the hand (simpler than actual Card[] array for testing)
     *      int numSingles - number of singles in the array. Is the length of the temporary sorted array created with all the singles.
     *      int whichSingle - which single the user wants to be returned? 1st highest, 2nd highest, 3rd... etc.
     *
     * RETURNS: int - the single that was specified using the parameters. This single will bet he one that will be compared for cases such as
     *                two hands with 9-9-5-4-2 and 9-9-5-4-3. For these hands, we need to keep comparing highest values until we reach something that is
     *                different, so this method helps find these values in the correct order.
     * ---------------------------------------------------------------------------------------------------
     */

    private int findASingle(int[] array, int numSingles, int whichSingle){
        //helper to find singles' values for tiebreakers: pass in the int array to search, number of singles total, which of the singles you want (ordered)
        //which single is choosing 1st HIGHEST single, second highest single, third...to fifth (1-5).
        //assumes the array is sorted

        int out = 0;

        if(numSingles == HAND_SIZE){
            //just search array

            out = array[HAND_SIZE-whichSingle];

        } else {

            if (numSingles <= 5 && numSingles > 0 && whichSingle > 0 && whichSingle <= 5 && numSingles >= whichSingle) {
                int[] singleArray = new int[numSingles]; //array of all the singles
                int count = 0; //array counter

                //check index 0
                if (array[0] != array[1]) {
                    singleArray[count] = array[0];
                    count++;
                }

                for (int i = 1; i < HAND_SIZE - 1; i++) {
                    if (array[i] != array[i - 1]
                            && array[i] != array[i + 1]) {
                        singleArray[count] = array[i];
                        count++;
                    }
                }

                //check index 4
                if (array[HAND_SIZE - 1] != array[HAND_SIZE - 2]) {
                    singleArray[count] = array[HAND_SIZE - 1];
                    count++;
                }

                out = singleArray[numSingles - whichSingle];

            }
        }
        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   getValueArray
     *
     * PURPOSE: Converts members of a hand's Card[] array into integers to simplify certain comparisons and avoid casting more than necessary.
     * PARAMETERS: none.
     *
     * RETURNS: int[] - array of all the values as ints in this hand in sorted order.
     * ---------------------------------------------------------------------------------------------------
     */

    public int[] getValueArray(){
        sortValue(); //sort first

        int[] out = new int[HAND_SIZE];

        for(int i = 0; i < HAND_SIZE; i++){
            out[i] = hand[i].getValue();
        }

        return out; //returning array of ints showing the values of the cards in a hand.
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   sortSuit
     *
     * PURPOSE: Sorts the hand by suit so all the suits are together. Used to test for flushes (where first card is the same suit as the last card
     *          when the hand is ordered this way.)
     * PARAMETERS: none.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    private void sortSuit(){
        Card temp[] = new Card[HAND_SIZE];
        int count = 0;

        for(int i = 0; i < 4; i++){
            for (int j = 0; j < HAND_SIZE; j++){
                if(i == 0){
                    if(hand[j].getSuit() == Cardable.Suit.HEART){
                        temp[count] = hand[j];
                        count++;
                    }
                } else if (i == 1){
                    if(hand[j].getSuit() == Cardable.Suit.SPADE){
                        temp[count] = hand[j];
                        count++;
                    }
                } else if (i == 2){
                    if(hand[j].getSuit() == Cardable.Suit.CLUB){
                        temp[count] = hand[j];
                        count++;
                    }
                } else {
                    if(hand[j].getSuit() == Cardable.Suit.DIAMOND){
                        temp[count] = hand[j];
                        count++;
                    }
                }
            }
        }

        for(int i = 0; i < HAND_SIZE; i++){
            hand[i] = temp[i];
        } //copy temp[] into hand[]

    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   sortValue
     *
     * PURPOSE: Sorts a hand by value in increasing order. Helpful for searching for duplicates and straights, and even largest singles.
     *          Total size of hand is small, so selection sort is used.
     * PARAMETERS: none.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */


    public void sortValue(){
        //small n, so using a simple selection sort...

        Card temp;
        int smallestIndex = 0;

        for(int i = 0; i < HAND_SIZE; i++){

            smallestIndex = i;

            for(int j = i+1; j < HAND_SIZE; j++){
                if(hand[j].getValue() < hand[smallestIndex].getValue()){
                    smallestIndex = j;
                }
            }

            temp = hand[i];
            hand[i] = hand[smallestIndex];
            hand[smallestIndex] = temp;

        }
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   sameSuit
     *
     * PURPOSE: Check if all cards in the hand are the same suit.
     * PARAMETERS: none.
     *
     * RETURNS: boolean - whether the hand is all the same suit.
     * ---------------------------------------------------------------------------------------------------
     */

    private boolean sameSuit(){
        //check if all same suit
        boolean out  = false;

        sortSuit();
        if(hand[0].getSuit() == hand[HAND_SIZE-1].getSuit()){
            out = true;
        }

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   draw
     *
     * PURPOSE: check if all cards are the same suit and is therefore a flush. Returns the largest number in the flush.
     * RETURNS: int - representing the most significant number in the Flush (the largest value). Otherwise, not a flush, return -1.
     * ---------------------------------------------------------------------------------------------------
     */

    private int isFlush(){
        int out = -1;
        if (sameSuit()) {
            sortValue();
            out = hand[HAND_SIZE-1].getValue();
        }

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   isStraight
     *
     * PURPOSE: Test if the hand is a Straight. Loops through the (sorted) array and checks to make sure each Card after the current is 1 higher
     * RETURNS: The highest number if the hand is a Straight. Otherwise, return -1.
     * ---------------------------------------------------------------------------------------------------
     */

    private int isStraight(){
        int out;
        int count = 0;

        sortValue();

        out = hand[HAND_SIZE-1].getValue();
        //assuming is a straight with a high of the rightmost card

        while(count < HAND_SIZE-1 && out != -1){
            if(hand[count].getValue()+1 != hand[count+1].getValue()) {
                out = -1 ;
            } //the moment one pair is not in order, we say it's false.
            count++;
        } //for each loop, check if current card value + 1 is equal to the next card in the hand. If not, out = impossible value and loop ends

       // System.out.println(out);
        if(out == -1){
            //if no straight was found with ace being high, check if there's an ace and try with low ace.

            if(hand[HAND_SIZE-1].getValue() == ACE) {
                int[] temp = new int[HAND_SIZE];

                for(int i = 0; i < HAND_SIZE-1; i++){
                    temp[i+1] = hand[i].getValue();
                }//want to change ace to 1, and 1 is lowest, so we are saving a spot at temp[0] for 1 and skipping copying Ace

                temp[0] = 1;
                count = 0;
                out = temp[HAND_SIZE-1]; //assume is straight

                while(count < HAND_SIZE-1 && out != -1){
                    if(temp[count]+1 != temp[count+1]) {
                        out = -1 ;
                    }
                    count++;
                } //for each loop, check if current card value + 1 is equal to the next card in the hand. If not, out = impossible value and loop ends

            }
        }

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   isStraightFlush
     *
     * PURPOSE: Check if hand is a Straight Flush by checking if it is a Straight and a Flush (all same suit)
     * RETURNS: the highest value in the Straight Flush or -1 if it is not a Straight Flush
     * ---------------------------------------------------------------------------------------------------
     */

    private int isStraightFlush(){
        int out = -1;

        if (sameSuit()) {
            out = isStraight();
            //returns with a high if IS a straight, otherwise returns -1.
        }

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   isFourOfAKind
     *
     * PURPOSE: Test if the hand has a Four of a Kind.
     * RETURNS: Returns the value that is a quad or -1 if not a quad.
     * ---------------------------------------------------------------------------------------------------
     */

    private int isFourOfAKind(){
        int out = -1;
        sortValue();

        if(hand[0].getValue() == hand[3].getValue()){
            out = hand[0].getValue();
        } else if (hand[1].getValue() == hand[HAND_SIZE-1].getValue()) {
            out = hand[1].getValue();
        }
        //either card1 = card4 or card2 = card5 when sorted (aaaab or abbbb) and returns with the value that is a four of a kind.

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   isFullHouse
     *
     * PURPOSE: Test if hand is a Full House
     * RETURNS: The value that is the Triple in the Full House (simple tie breaker). Otherwise, if not Full House, return -1.
     * ---------------------------------------------------------------------------------------------------
     */

    private int isFullHouse(){
        int out = -1;
        sortValue();

        if(((hand[0].getValue() == hand[2].getValue())
            && (hand[3].getValue() == hand[HAND_SIZE-1].getValue()))){
            out = hand[0].getValue();

        } else if (((hand[0].getValue() == hand[1].getValue())
                && (hand[2].getValue() == hand[HAND_SIZE-1].getValue()))){
            out = hand[2].getValue();
        }
        //since hand is sorted, either card1 = card3 and card4 = card5 OR card1 = card2 and card3 = card5 (aaabb or aabbb)

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   isThreeOfAKind
     *
     * PURPOSE: Test if there is a Three of a Kind in the hand. For a sorted array of 5, there are 3 ways to have a 3 of a kind,
     *          if array values at 1) index0 = index2, 2) index1 = index3, or 3) index2 = index4.
     * RETURNS: If is a Three of a Kind, returns the value that is a triple, else return -1.
     * ---------------------------------------------------------------------------------------------------
     */

    private int isThreeOfAKind(){
        int out = -1;
        sortValue();

        if(hand[0].getValue() == hand[2].getValue()){
            out = hand[0].getValue();
        } else if (hand[1].getValue() == hand[3].getValue()){
            out = hand[1].getValue();
        } else if (hand[2].getValue() == hand[HAND_SIZE-1].getValue()){
            out = hand[2].getValue();
        }

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   isTwoPair
     *
     * PURPOSE: Test hand to see if there are two pairs. Ultimately, there are 3 ways to get two pairs when you have 5 cards:
     *          1122x 11x22 x1122
     * RETURNS: If there are two pairs, return the number that is the greater pair, else return -1.
     * ---------------------------------------------------------------------------------------------------
     */

    private int isTwoPair(){
        int out = -1;
        sortValue();

        if(hand[0].getValue() == hand[1].getValue()){
            //if we have aaxxx, check xxx for pairs

            if(hand[2].getValue() == hand[3].getValue()){
                if (hand[0].getValue() > hand[2].getValue()){
                    out = hand[0].getValue();
                } else {
                    out = hand[2].getValue();
                }
            } else if (hand[3].getValue() == hand[HAND_SIZE-1].getValue()){
                if (hand[0].getValue() > hand[3].getValue()){
                    out = hand[0].getValue();
                } else{
                    out = hand[3].getValue();
                }
            } //checking for either bbc or bcc

        } else if (hand[1].getValue() == hand[2].getValue()){
            //else, if we have yaaxx, check if xx is a pair

            if(hand[3].getValue() == hand[HAND_SIZE-1].getValue()){
                if(hand[1].getValue() > hand[3].getValue()){
                    out = hand[1].getValue();
                } else {
                    out = hand[3].getValue();
                }
            }
        }
        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   isPair
     *
     * PURPOSE: Test if there is a single pair in the hand. Goes through Hand and checks if any card is equal to the next card in hand since
     *          hand is sorted.
     * RETURNS: If there is a pair, returns the value of the pair, otherwise, return -1.
     * ---------------------------------------------------------------------------------------------------
     */

    private int isPair(){
        int out = -1;
        sortValue();

        for(int i = 0; i < HAND_SIZE-1; i++){
            if(hand[i].getValue() == hand[i+1].getValue()){
                out = hand[i].getValue();
            }
        } //don't need to check specifics anymore because this method can only be called AFTER checking other possibilities.
        //if we're calling this, there can only be one pair at most, so out is just whatever pair is found.

        return out;
    }


    //getters for Hand's strength and tiebreaker fields
    public int getHandWeight(){
        //return strength of hand as a value to compare
        return strength;
    }

    public int getTiebreaker(){
        return tiebreaker;
    }


    /*
     *---------------------------------------------------------------------------------------------------
     *   printValue
     *
     * PURPOSE: Helper method to print String values for the special cases (so all 11, 12, 13, 14 are changed to J, Q, K, and A.
     * PARAMETERS:
     *      int i = value of the card that needs to be made to a String.
     * RETURNS: String of the value passed
     * ---------------------------------------------------------------------------------------------------
     */

    private String printValue(int i){
        String out = "";
        if(i == ACE){
            out += "A";
        } else if (i == KING){
            out += "K";
        } else if (i == QUEEN){
            out += "Q";
        } else if (i == JACK){
            out += "J";
        } else {
            out += i;
        }

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   deselectHand
     *
     * PURPOSE: Used for maintenance to make sure the game runs smoothly and so the player isn't randomly changing the
     *           selected state of cards when they weren't prompted to. Method call will put all cards in the hand to !selected.
     * ---------------------------------------------------------------------------------------------------
     */

    public void deselectHand(){
        for (int i = 0; i < HAND_SIZE; i++){
            if(hand[i] != null ){
                hand[i].resetSelected();
            }
            if(originalHand[i] != null) {
                originalHand[i].resetSelected();
            }
        }
    } //method for ensuring no booleans get switched on or off unintentionally

}
