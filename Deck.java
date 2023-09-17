/*
*   REMARKS: The Deck class that implements Deckable. Creates a LinkedList of Cardables for a deck and performs operations on the deck
*            such as initializing the unique 52 cards with their suits and allowing players to draw from the deck, return cards to the deck, and
*            shuffle the deck.
 */

import java.util.LinkedList;

public class Deck implements Deckable {

    private LinkedList<Cardable> deck;

    public Deck(){
        deck = new LinkedList<Cardable>();
        initializeDeck();
    }

    /*
    *---------------------------------------------------------------------------------------------------
    *   initializeDeck
    *
    * PURPOSE: Initialize a deck by creating all 52 unique cards for the deck using loops. Called by the constructor to do work.
    * PARAMETERS: none
    *
    * RETURNS:none
    * ---------------------------------------------------------------------------------------------------
     */

    private void initializeDeck(){
        int count = 0;
        //incrementing deck array.

        for (int i = 0; i < 4; i++){
            for(int j = 2; j < 15; j++){
                //from 2 (smallest) to 14 (highest ace)
                if(i == 0){
                    deck.add(new Card(j, Cardable.Suit.HEART));
                } else  if (i == 1){
                    deck.add(new Card(j, Cardable.Suit.SPADE));
                } else if (i == 2){
                    deck.add(new Card(j, Cardable.Suit.DIAMOND));
                } else if (i == 3){
                    deck.add(new Card(j, Cardable.Suit.CLUB));
                }
                count++;
            }
        }
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   shuffle
     *
     * PURPOSE: Shuffles the deck by randomly swapping the card at each index of the LinkedList with another card 3 times using loops.
     * PARAMETERS: none.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void shuffle(){
        Card temp;
        int random;

        for(int j = 0; j < 3; j++){
            for(int i = 0; i < NUM_CARDS; i++) {
                random = (int) Math.floor((NUM_CARDS) * Math.random());
                //get random number between 0 and deck size

                if (deck.get(i) instanceof Card) {
                    temp = (Card) deck.get(i);
                } else {
                    temp = null;
                }

                if (temp != null) {
                    deck.set(i, deck.get(random));
                    deck.set(random, temp);
                    //swap
                }
            }
        } //for every slot in the deck, swaps a card with another card in the deck

    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   returnToDeck
     *
     * PURPOSE: Takes in a LinkedList of discarded cards from the game and adds them back into the deck.
     * PARAMETERS:
     *      LinkedList<Cardable> discarded - Linked List of Cards to return to the deck.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void returnToDeck(LinkedList<Cardable> discarded){
        deck.addAll(discarded);
        //append back all the removed cards
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   drawACard
     *
     * PURPOSE: Draws a card (pops a card from the LinkedList) from this deck and returns it after changing it's faceUp status/boolean to the input param.
     * PARAMETERS:
     *   boolean faceUp - boolean to set to the card that is being drawn for it's faceUp status.
     *
     * RETURNS: Cardable - the card that was drawn.
     * ---------------------------------------------------------------------------------------------------
     */

    public Cardable drawACard(boolean faceUp){
        Card out = null;

        if(deck.peekLast() instanceof Card) {
            out = (Card)deck.pop();
        } //remove last Card

        out.setFaceUp(faceUp);

        return out;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   resetDeck
     *
     * PURPOSE: Maintenance and makes sure that all cards in the deck at the end of a round are not selected and not face up.
     *          This helps avoid small bugs that may occur from messing around with selections/etc. as the game runs for a long time.
     * PARAMETERS: none.
     *
     * RETURNS: none.
     * ---------------------------------------------------------------------------------------------------
     */

    public void resetDeck(){
        for (int i = 0; i < NUM_CARDS; i++){
                deck.get(i).resetSelected();
                deck.get(i).setFaceUp(false);
        }
    } //ensuring no booleans get turned on and off unintentionally

}
