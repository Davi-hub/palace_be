package com.palace_be;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

public class Player implements Serializable {
    private UUID playerId;
    private String playerName;
    private boolean isReady = false;
    private boolean isCurrentPlayer = false;
    private ArrayList<Card> cardsOnTable = new ArrayList<Card>();
    private ArrayList<Card> cardsOnCards = new ArrayList<Card>();
    private ArrayList<Card> cardsInHand = new ArrayList<Card>();
    private int playerIndex;

    public Player() {
    }

    public Player(Player player) {
        this.playerId = player.playerId;
        this.playerName = player.playerName;
        this.isReady = player.isReady;
        this.cardsOnTable = new ArrayList<Card>(player.cardsOnTable);
        this.cardsOnCards = new ArrayList<Card>(player.cardsOnCards);
        this.cardsInHand = new ArrayList<Card>(player.cardsInHand);
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

    public ArrayList<Card> getCardsOnTable() {
        return cardsOnTable;
    }

    public void setCardsOnTable(ArrayList<Card> cardsOnTable) {
        this.cardsOnTable = cardsOnTable;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    public void addCardToCardsOnTable(Card card) {
        this.cardsOnTable.add(card);
    }

    public void addCardToCardsOnCards(Card card) {
        this.cardsOnCards.add(card);
    }

    public void addCardToCardsInHand(Card card) {
        this.cardsInHand.add(card);
    }

    public void coverCards() {
        this.coverCardInHand();
        this.coverCardOnTable();
    }

    public void coverCardInHand() {
        for (int i = 0; i < this.cardsInHand.size(); i++) {
            this.cardsInHand.set(i, new Card(CardSuit.NULL, CardFigure.NULL, "b1"));
        }
    }

    public void coverCardOnTable() {
        for (int i = 0; i < cardsOnTable.size(); i++) {
            if (cardsOnTable.get(i).getSrc() != "") {
                cardsOnTable.set(i, new Card(CardSuit.NULL, CardFigure.NULL, "b1"));
            }
        }
    }

    public boolean getIsReady() {
        return isReady;
    }

    public void setIsReady(boolean isReady) {
        this.isReady = isReady;
    }

    public boolean getIsCurrentPlayer() {
        return isCurrentPlayer;
    }

    public void setIsCurrentPlayer(boolean isCurrentPlayer) {
        this.isCurrentPlayer = isCurrentPlayer;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
