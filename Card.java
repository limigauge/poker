/*
 *   CLASS : Card.java
 *   Author: Michelle Li, 7866927
 *
 *   REMARKS: The Card class that implements the methods of Cardable. A Card object stores information of each card such as its suit, number, whether
 *            or not the card has been selected in game, and whether the card should be displayed face up. There is a toString method that creates a string
 *            for representing the value on the card as well as its suit.
 */

public class Card implements Cardable {

    private boolean selected;
    private boolean faceUp;
    private Suit type;
    private int value;

    public Card(int value, Suit type) {
        selected = false;
        faceUp = false;
        this.type = type;
        this.value = value;
    }

    //getters and setters

    public boolean getSelected() {
        return selected;
    }

    public boolean getFaceUp() {
        return faceUp;
    }

    public Suit getSuit(){
        return type;
    }

    public void switchSelectedState(){
        selected = !selected;
    } //if selected, unselect; if unselected, select

    public void resetSelected(){
        selected = false;
    } //resets selected to false state

    public void setFaceUp(boolean face){
        faceUp = face;
    }

    /*
     *---------------------------------------------------------------------------------------------------
     *   toString
     *
     * PURPOSE: Returns the String representation of the value and suit of the  Card.
     *          Changes values from 11-14 to J, Q, K, A and also uses the code for the symbols of the
     *          suits.
     * PARAMETERS: none.
     *
     * RETURNS: String - String representation of card.
     * ---------------------------------------------------------------------------------------------------
     */

    public String toString(){
        String out = "";

        if(value == 11){
            out += "J";
        } else if (value == 12){
            out += "Q";
        } else if (value == 13){
            out += "K";
        } else if (value == 14){
            out += "A";
        } else if (value < 11 && value > 1){
            out += value;
        } //add value to String

        if (type == Suit.HEART){
            out += "\u2665";
        } else if (type == Suit.SPADE){
            out += "\u2660";
        } else if (type == Suit.DIAMOND){
            out += "\u2666";
        } else {
            out += "\u2663";
        }

        return out;
    }


    //getter for the number of on card
    public int getValue(){
        return value;
    }
}
