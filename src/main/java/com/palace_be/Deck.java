package com.palace_be;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Deck implements Serializable {
    Random rand = new Random();
    private String backColor;

    private Card heartsTwo = new Card(CardSuit.HEARTS, CardFigure.TWO);
    private Card heartsThree = new Card(CardSuit.HEARTS, CardFigure.THREE);
    private Card heartsFour = new Card(CardSuit.HEARTS, CardFigure.FOUR);
    private Card heartsFive = new Card(CardSuit.HEARTS, CardFigure.FIVE);
    private Card heartsSix = new Card(CardSuit.HEARTS, CardFigure.SIX);
    private Card heartsSeven = new Card(CardSuit.HEARTS, CardFigure.SEVEN);
    private Card heartsEight = new Card(CardSuit.HEARTS, CardFigure.EIGHT);
    private Card heartsNine = new Card(CardSuit.HEARTS, CardFigure.NINE);
    private Card heartsTen = new Card(CardSuit.HEARTS, CardFigure.TEN);
    private Card heartsJack = new Card(CardSuit.HEARTS, CardFigure.JACK);
    private Card heartsQueen = new Card(CardSuit.HEARTS, CardFigure.QUEEN);
    private Card heartsKing = new Card(CardSuit.HEARTS, CardFigure.KING);
    private Card heartsAce = new Card(CardSuit.HEARTS, CardFigure.ACE);

    private Card diamondsTwo = new Card(CardSuit.DIAMONDS, CardFigure.TWO);
    private Card diamondsThree = new Card(CardSuit.DIAMONDS, CardFigure.THREE);
    private Card diamondsFour = new Card(CardSuit.DIAMONDS, CardFigure.FOUR);
    private Card diamondsFive = new Card(CardSuit.DIAMONDS, CardFigure.FIVE);
    private Card diamondsSix = new Card(CardSuit.DIAMONDS, CardFigure.SIX);
    private Card diamondsSeven = new Card(CardSuit.DIAMONDS, CardFigure.SEVEN);
    private Card diamondsEight = new Card(CardSuit.DIAMONDS, CardFigure.EIGHT);
    private Card diamondsNine = new Card(CardSuit.DIAMONDS, CardFigure.NINE);
    private Card diamondsTen = new Card(CardSuit.DIAMONDS, CardFigure.TEN);
    private Card diamondsJack = new Card(CardSuit.DIAMONDS, CardFigure.JACK);
    private Card diamondsQueen = new Card(CardSuit.DIAMONDS, CardFigure.QUEEN);
    private Card diamondsKing = new Card(CardSuit.DIAMONDS, CardFigure.KING);
    private Card diamondsAce = new Card(CardSuit.DIAMONDS, CardFigure.ACE);

    private Card clubsTwo = new Card(CardSuit.CLUBS, CardFigure.TWO);
    private Card clubsThree = new Card(CardSuit.CLUBS, CardFigure.THREE);
    private Card clubsFour = new Card(CardSuit.CLUBS, CardFigure.FOUR);
    private Card clubsFive = new Card(CardSuit.CLUBS, CardFigure.FIVE);
    private Card clubsSix = new Card(CardSuit.CLUBS, CardFigure.SIX);
    private Card clubsSeven = new Card(CardSuit.CLUBS, CardFigure.SEVEN);
    private Card clubsEight = new Card(CardSuit.CLUBS, CardFigure.EIGHT);
    private Card clubsNine = new Card(CardSuit.CLUBS, CardFigure.NINE);
    private Card clubsTen = new Card(CardSuit.CLUBS, CardFigure.TEN);
    private Card clubsJack = new Card(CardSuit.CLUBS, CardFigure.JACK);
    private Card clubsQueen = new Card(CardSuit.CLUBS, CardFigure.QUEEN);
    private Card clubsKing = new Card(CardSuit.CLUBS, CardFigure.KING);
    private Card clubsAce = new Card(CardSuit.CLUBS, CardFigure.ACE);

    private Card spadesTwo = new Card(CardSuit.SPADES, CardFigure.TWO);
    private Card spadesThree = new Card(CardSuit.SPADES, CardFigure.THREE);
    private Card spadesFour = new Card(CardSuit.SPADES, CardFigure.FOUR);
    private Card spadesFive = new Card(CardSuit.SPADES, CardFigure.FIVE);
    private Card spadesSix = new Card(CardSuit.SPADES, CardFigure.SIX);
    private Card spadesSeven = new Card(CardSuit.SPADES, CardFigure.SEVEN);
    private Card spadesEight = new Card(CardSuit.SPADES, CardFigure.EIGHT);
    private Card spadesNine = new Card(CardSuit.SPADES, CardFigure.NINE);
    private Card spadesTen = new Card(CardSuit.SPADES, CardFigure.TEN);
    private Card spadesJack = new Card(CardSuit.SPADES, CardFigure.JACK);
    private Card spadesQueen = new Card(CardSuit.SPADES, CardFigure.QUEEN);
    private Card spadesKing = new Card(CardSuit.SPADES, CardFigure.KING);
    private Card spadesAce = new Card(CardSuit.SPADES, CardFigure.ACE);

    private Card empty = new Card(CardSuit.NULL, CardFigure.NULL, "");
    private Card covered = new Card(CardSuit.NULL, CardFigure.NULL, "b1");

    private ArrayList<Card> deck = new ArrayList<Card>(Arrays.asList(
            heartsTwo, heartsThree, heartsFour, heartsFive, heartsSix, heartsSeven, heartsEight, heartsNine, heartsTen,
            heartsJack, heartsQueen, heartsKing, heartsAce,
            diamondsTwo, diamondsThree, diamondsFour, diamondsFive, diamondsSix, diamondsSeven, diamondsEight,
            diamondsNine, diamondsTen, diamondsJack, diamondsQueen, diamondsKing, diamondsAce,
            clubsTwo, clubsThree, clubsFour, clubsFive, clubsSix, clubsSeven, clubsEight, clubsNine, clubsTen,
            clubsJack, clubsQueen, clubsKing, clubsAce,
            spadesTwo, spadesThree, spadesFour, spadesFive, spadesSix, spadesSeven, spadesEight, spadesNine, spadesTen,
            spadesJack, spadesQueen, spadesKing, spadesAce));

    public Deck() {

    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public Card takeRandomCard() {
        int randomIndex = rand.nextInt(deck.size());
        Card card = this.deck.remove(randomIndex);
        return card;
    }

    public ArrayList<Card> getCoveredDeck() {
        ArrayList<Card> coveredDeck = new ArrayList<Card>();
        for (int i = 0; i < deck.size(); i++) {
            coveredDeck.add(covered);
        }
        return coveredDeck;
    }

    public void setCovered(Card covered) {
        this.covered = covered;
    }

    public Card getCovered() {
        return covered;
    }

    public Card getEmpty() {
        return empty;
    }

    public void setEmpty(Card empty) {
        this.empty = empty;
    }
}
