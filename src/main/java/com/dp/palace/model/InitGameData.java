package com.dp.palace.model;

import java.util.UUID;

public class InitGameData {
    private UUID gameId;
    private UUID playerId;
    private int playerIndex;

    public InitGameData(UUID gameId, UUID playerId, int playerIndex) {
        this.gameId = gameId;
        this.playerId = playerId;
        this.playerIndex = playerIndex;
    }

    public UUID getGameId() {
        return gameId;
    }

    public void setGameId(UUID gameId) {
        this.gameId = gameId;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public void setPlayerIndex(int playerIndex) {
        this.playerIndex = playerIndex;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }
}
