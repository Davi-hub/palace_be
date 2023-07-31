package com.palace_be;

import java.util.ArrayList;

public class GameData {
    private int playerNumber;
    private ArrayList<Player> players;
    private ArrayList<Card> deck;
    private ArrayList<Card> pile;
    private int currentPlayerIndex;
    private int gameStatus;
    private Player winner;
    private int playerIndex;

    public GameData() {

    }

    public int getPlayerNumber() {
        return playerNumber;
    }
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public ArrayList<Card> getDeck() {
        return deck;
    }
    public void setDeck(ArrayList<Card> deck) {
        this.deck = deck;
    }
    public ArrayList<Card> getPile() {
        return pile;
    }
    public void setPile(ArrayList<Card> pile) {
        this.pile = pile;
    }
    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }
    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
    public int getGameStatus() {
        return gameStatus;
    }
    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }
    public Player getWinner() {
        return winner;
    }
    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
