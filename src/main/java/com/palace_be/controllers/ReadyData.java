package com.palace_be.controllers;

import java.util.ArrayList;

import com.palace_be.Card;
import com.palace_be.Ids;

public class ReadyData {
    private ArrayList<Card> cardsOnCards; 
    private ArrayList<Card> cardsInHand;
    private Ids ids;

    public Ids getIds() {
        return ids;
    }

    public void setIds(Ids ids) {
        this.ids = ids;
    }

    public ArrayList<Card> getCardsOnCards() {
        return cardsOnCards;
    }

    public void setCardsOnCards(ArrayList<Card> cardsOnCards) {
        this.cardsOnCards = cardsOnCards;
    }

    public ArrayList<Card> getCardsInHand() {
        return cardsInHand;
    }

    public void setCardsInHand(ArrayList<Card> cardsInHand) {
        this.cardsInHand = cardsInHand;
    }

    

    
}
