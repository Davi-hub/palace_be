package com.palace_be;

import java.io.Serializable;


public class Card implements Serializable{
    private CardSuit suit;
    private CardFigure figure;
    private String src;
    
    public void setSuit(CardSuit suit) {
        this.suit = suit;
    }
    
    public void setFigure(CardFigure figure) {
        this.figure = figure;
    }
    
    public String getSrc() {
        return src;
    }
    
    public void setSrc(String src) {
        this.src = src;
    }
    
    public Card() {

    }

    public Card(CardSuit suit, CardFigure figure) {
        this.suit = suit;
        this.figure = figure;
        this.src = Character.toLowerCase(suit.toString().charAt(0)) + Integer.toString(figure.getValue());
    }

    public Card(CardSuit suit, CardFigure figure, String src) {
        this.suit = suit;
        this.figure = figure;
        this.src = src;
    }

    public CardSuit getSuit() {
        return suit;
    }

    public CardFigure getFigure() {
        return figure;
    }

    public int getValue() {
        return figure.getValue();
    }

    public void setValue(int value) {
        figure.setValue(value);
    }
}
