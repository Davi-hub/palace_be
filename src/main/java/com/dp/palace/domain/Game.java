package com.dp.palace.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

public class Game implements Serializable {
    private int playerNumber;
    private ArrayList<Player> players = new ArrayList<Player>();
    private Deck deck = new Deck();
    private ArrayList<Card> pile = new ArrayList<Card>();
    private int currentPlayerIndex = 0;
    private int gameStatus = 0;
    private Player winner;
    private int playerIndex;

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public Game() {

    }

    public Game(int playerNumber) {
        this.playerNumber = playerNumber;
        this.initPlayers();
        this.deal();
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

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
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

    public void initPlayers() {
        for (int i = 0; i < this.playerNumber; i++) {
            Player player = new Player();
            players.add(player);
        }
    }

    public void deal() {
        for (int j = 0; j < 3; j++) {
            for (Player player : players) {
                player.addCardToCardsOnTable(deck.takeRandomCard());
                player.addCardToCardsOnCards(deck.takeRandomCard());
                player.addCardToCardsInHand(deck.takeRandomCard());
            }
        }
//        takeCardFromDeckToUsed();
    }

    public void increaseCurrentPlayerIndex() {
        if (currentPlayerIndex == (players.size() - 1)) {
            this.currentPlayerIndex = 0;
        } else {
            this.currentPlayerIndex++;
        }
    }

    public Optional<Player> getPlayerById(UUID playerId) {
        for (Player player : players) {
            if (player.getPlayerId().equals(playerId)) {
                return Optional.of(player);
            }
        }
        return Optional.empty();
    }

    public void takeCardFromDeckToUsed() {
        this.pile.add(this.deck.takeRandomCard());
    }

    // public Map<String, Object> getGameData() {
    //     Map<String, Object> gameData = new HashMap<>();
    //     gameData.put("playerNumber", this.playerNumber);
    //     gameData.put("players", this.players);
    //     gameData.put("deck", this.getDeck());
    //     gameData.put("pile", this.pile);
    //     gameData.put("currentPlayerIndex", this.currentPlayerIndex);
    //     gameData.put("gameStatus", this.gameStatus);
    //     gameData.put("winner", this.winner);
    //     return gameData;
    // }

    

    

}
