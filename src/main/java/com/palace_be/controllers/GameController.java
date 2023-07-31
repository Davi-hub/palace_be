package com.palace_be.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.palace_be.utils.GameControllerHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.palace_be.Card;
import com.palace_be.DrawData;
import com.palace_be.Game;
import com.palace_be.GameData;
import com.palace_be.GameDataFileHandler;
import com.palace_be.Ids;
import com.palace_be.InitGameRes;
import com.palace_be.JoinGameData;
import com.palace_be.Message;
import com.palace_be.NewGameData;
import com.palace_be.Player;

@RestController
public class GameController {
    Map<UUID, Game> gameMap = new HashMap<>();

    private final GameControllerHelper helper;

    @Autowired
    public GameController(GameControllerHelper helper) {
        this.helper = helper;
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/new-game")
    public ResponseEntity<InitGameRes> newGame(@RequestBody NewGameData newGameData) {
        UUID gameId = UUID.randomUUID();
        Game game = new Game(newGameData.getPlayerNumber());
        game.increaseCurrentPlayerIndex();
        Player firstPlayer = game.getPlayers().get(0);
        firstPlayer.setPlayerId(UUID.randomUUID());
        firstPlayer.setPlayerName(newGameData.getFirstPlayerName());
        gameMap.put(gameId, game);
        InitGameRes res = new InitGameRes(gameId, firstPlayer.getPlayerId(), 0);

        return ResponseEntity.ok(res);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/join")
    public ResponseEntity<InitGameRes> joinGame(@RequestBody JoinGameData joinGameData) {
        Game game = this.gameMap.get(joinGameData.getGameId());
        Player player = game.getPlayers().get(game.getCurrentPlayerIndex());
        player.setPlayerId(UUID.randomUUID());
        player.setPlayerName(joinGameData.getPlayerName());
        int currentPlayerIndex = game.getCurrentPlayerIndex();
        InitGameRes res = new InitGameRes(joinGameData.getGameId(), player.getPlayerId(), currentPlayerIndex);
        game.increaseCurrentPlayerIndex();
        return ResponseEntity.ok(res);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/get-players")
    public ResponseEntity<ArrayList<Player>> getPlayers(@RequestBody Ids ids) {
        Game game = this.gameMap.get(ids.getGameId());
        return ResponseEntity.ok(helper.getGameDataFromFE(game, ids.getPlayerId(), true).getPlayers());
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/get-game")
    public ResponseEntity<GameData> getGame(@RequestBody Ids ids) {
        Game game = null;
        try {
            game = this.gameMap.get(ids.getGameId());

            if (game == null) {
                game = helper.getGameFromJSON(ids.getGameId());
            }
            this.gameMap.put(ids.getGameId(), game);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (game != null) {
            GameData response = helper.getGameDataFromFE(game, ids.getPlayerId(), true);
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.notFound().build();
    }

    @CrossOrigin(origins = "http://localhost:4200")
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

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/is-eb-ready")
    public ResponseEntity<Boolean> isEverybodyReady(@RequestBody Ids ids) {
        Game game = this.gameMap.get(ids.getGameId());
        game.setCurrentPlayerIndex(0);
        boolean everybodyIsReady = game.getPlayers()
                .stream().allMatch(Player::getIsReady);
        if (everybodyIsReady) {
            game.setGameStatus(1);
            GameDataFileHandler.saveGameData(ids.getGameId(), helper.getGameDataFromFE(game, ids.getPlayerId(), false));
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/played-a-card-in-hand")
    public ResponseEntity<Boolean> playedACardInHand(@RequestBody DrawData data) {
        helper.playedACard(gameMap, data, "inHand");
        return ResponseEntity.ok(true);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/played-a-card-on-card")
    public ResponseEntity<Boolean> playedACardOnCard(@RequestBody DrawData data) {
        helper.playedACard(gameMap, data, "onCard");
        return ResponseEntity.ok(true);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/played-a-card-on-table")
    public ResponseEntity<Boolean> playedACardOnTable(@RequestBody DrawData data) {
        helper.playedACard(gameMap, data, "onTable");
        return ResponseEntity.ok(true);
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/get-card-on-table")
    public ResponseEntity<Card> getCardOnTable(@RequestBody DrawData data) {
        Ids ids = data.getIds();
        Game game = this.gameMap.get(ids.getGameId());
        Player player = game.getPlayerById(ids.getPlayerId()).get();
        int index = data.getOnTableIndex().get();
        Card card = player.getCardsOnTable().get(index);
        return ResponseEntity.ok(card);
    }

    @CrossOrigin(origins = "http://localhost:4200")
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
