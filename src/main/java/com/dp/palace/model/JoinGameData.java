package com.dp.palace.model;

import java.util.UUID;

public class JoinGameData {
    private String playerName;
    private UUID gameId;

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getGameId() {
        return gameId;
    }
    
    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }
}
