package com.dp.palace.model;

import java.io.Serializable;
import java.util.UUID;

public class Ids implements Serializable{
    private UUID gameId;
    private UUID playerId;
    private int playerIndex;

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameID) {
        this.gameId = gameID;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerID) {
        this.playerId = playerID;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }
}
