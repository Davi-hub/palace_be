package com.dp.palace.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.dp.palace.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.dp.palace.domain.Card;
import com.dp.palace.domain.Game;
import com.dp.palace.utils.Message;
import com.dp.palace.domain.Player;

@RestController
public class GameController {
    Map<UUID, Game> gameMap = new HashMap<>();

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping("/new-game")
    public ResponseEntity<InitGameData> newGame(@RequestBody NewGameData newGameData) {
        UUID gameId = UUID.randomUUID();
        Game game = new Game(newGameData.getPlayerNumber());
        game.increaseCurrentPlayerIndex();
        Player firstPlayer = game.getPlayers().get(0);
        firstPlayer.setPlayerId(UUID.randomUUID());
        firstPlayer.setPlayerName(newGameData.getFirstPlayerName());
        gameMap.put(gameId, game);
        InitGameData res = new InitGameData(gameId, firstPlayer.getPlayerId(), 0);

        return ResponseEntity.ok(res);
    }

    @PostMapping("/join")
    public ResponseEntity<InitGameData> joinGame(@RequestBody JoinGameData joinGameData) {
        Game game = this.gameMap.get(joinGameData.getGameId());
        Player player = game.getPlayers().get(game.getCurrentPlayerIndex());
        player.setPlayerId(UUID.randomUUID());
        player.setPlayerName(joinGameData.getPlayerName());
        int currentPlayerIndex = game.getCurrentPlayerIndex();
        InitGameData res = new InitGameData(joinGameData.getGameId(), player.getPlayerId(), currentPlayerIndex);
        game.increaseCurrentPlayerIndex();
        return ResponseEntity.ok(res);
    }

    @PostMapping("/get-players")
    public ResponseEntity<ArrayList<Player>> getPlayers(@RequestBody Ids ids) {
        Game game = this.gameMap.get(ids.getGameId());
        return ResponseEntity.ok(gameService.getGameDataFromFE(game, ids.getPlayerId(), true).getPlayers());
    }

    @PostMapping("/get-game")
    public ResponseEntity<GameData> getGame(@RequestBody Ids ids) {
        Game game = null;
        try {
            game = this.gameMap.get(ids.getGameId());

            if (game == null) {
                game = gameService.getGameFromJSON(ids.getGameId());
            }
            this.gameMap.put(ids.getGameId(), game);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (game != null) {
            GameData response = gameService.getGameDataFromFE(game, ids.getPlayerId(), true);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping("/ready")
    public ResponseEntity<Message> playerIsReady(@RequestBody ReadyData readyData) {
        Game game = this.gameMap.get(readyData.getIds().getGameId());
        Optional<Player> playerOptional = game.getPlayerById(readyData.getIds().getPlayerId());
        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            player.setCardsOnCards(readyData.getCardsOnCards());
            player.setCardsInHand(readyData.getCardsInHand());
            player.setIsReady(true);
            return ResponseEntity.ok(new Message("ok"));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/is-eb-ready")
    public ResponseEntity<Boolean> isEverybodyReady(@RequestBody Ids ids) {
        Game game = this.gameMap.get(ids.getGameId());
        game.setCurrentPlayerIndex(0);
        boolean everybodyIsReady = game.getPlayers()
                .stream().allMatch(Player::getIsReady);
        if (everybodyIsReady) {
            game.setGameStatus(1);
            GameRepository.saveGameData(ids.getGameId(), gameService.getGameDataFromFE(game, ids.getPlayerId(), false));
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @PostMapping("/played-a-card-in-hand")
    public ResponseEntity<Boolean> playedACardInHand(@RequestBody DrawData data) {
        gameService.playedACard(gameMap, data, "inHand");
        return ResponseEntity.ok(true);
    }

    @PostMapping("/played-a-card-on-card")
    public ResponseEntity<Boolean> playedACardOnCard(@RequestBody DrawData data) {
        gameService.playedACard(gameMap, data, "onCard");
        return ResponseEntity.ok(true);
    }

    @PostMapping("/played-a-card-on-table")
    public ResponseEntity<Boolean> playedACardOnTable(@RequestBody DrawData data) {
        gameService.playedACard(gameMap, data, "onTable");
        return ResponseEntity.ok(true);
    }

    @PostMapping("/get-card-on-table")
    public ResponseEntity<Card> getCardOnTable(@RequestBody DrawData data) {
        Ids ids = data.getIds();
        Game game = this.gameMap.get(ids.getGameId());
        Player player = game.getPlayerById(ids.getPlayerId()).get();
        int index = data.getOnTableIndex().get();
        Card card = player.getCardsOnTable().get(index);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/draw-the-pile")
    public ResponseEntity<Boolean> drawThePile(@RequestBody Ids ids) {
        Game game = this.gameMap.get(ids.getGameId());
        ArrayList<Card> pile = game.getPile();
        Optional<Player> playerOptional = game.getPlayerById(ids.getPlayerId());

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            player.getCardsInHand().addAll(pile);
            game.getPile().clear();
            game.increaseCurrentPlayerIndex();
        }

        return ResponseEntity.ok(true);
    }
}
