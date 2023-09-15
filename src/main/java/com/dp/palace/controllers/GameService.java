package com.dp.palace.controllers;

import com.dp.palace.domain.Card;
import com.dp.palace.domain.Deck;
import com.dp.palace.domain.Game;
import com.dp.palace.domain.Player;
import com.dp.palace.model.DrawData;
import com.dp.palace.model.GameData;
import com.dp.palace.model.Ids;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {

    public Game getGameFromJSON(UUID gameId) {
        GameData gameData = GameRepository.loadGameData(gameId);
        Game game = new Game();
        game.setPlayerNumber(game.getPlayerNumber());
        game.setPlayers(gameData.getPlayers());
        Deck deck = new Deck();
        deck.setDeck(gameData.getDeck());
        game.setDeck(deck);
        game.setPile(gameData.getPile());
        game.setCurrentPlayerIndex(gameData.getCurrentPlayerIndex());
        game.setGameStatus(gameData.getGameStatus());
        game.setWinner(gameData.getWinner());
        game.setPlayerIndex(gameData.getPlayerIndex());
        return game;
    }
    public void playedACard(Map<UUID, Game> gameMap, DrawData data, String cardsFrom) {
        Card card = data.getCards().get(0);
        Ids ids = data.getIds();
        Game game = gameMap.get(ids.getGameId());
        ArrayList<Card> pile = game.getPile();
        Optional<Player> playerOptional = game.getPlayerById(ids.getPlayerId());

        if (playerOptional.isPresent()) {
            Player player = playerOptional.get();
            int cardIndex = -1;
            ArrayList<Card> playersCardArray;

            if (Objects.equals(cardsFrom, "inHand")) {
                playersCardArray = player.getCardsInHand();
            } else if (Objects.equals(cardsFrom, "onCard")) {
                playersCardArray = player.getCardsOnCards();
            } else if (Objects.equals(cardsFrom, "onTable")) {
                playersCardArray = player.getCardsOnTable();
            } else
                return;

            if (playersCardArray.isEmpty()) {
                return;
            }

            for (int i = 0; i < playersCardArray.size(); i++) {
                Card cardInArray = playersCardArray.get(i);
                if (cardInArray.getFigure() == card.getFigure() && cardInArray.getSuit() == card.getSuit()) {
                    cardIndex = i;
                    break;
                }
            }

            Card movedCard = playersCardArray.remove(cardIndex);

            if ((cardsFrom.equals("onCard")) | (cardsFrom.equals("onCard"))) {
                playersCardArray.add(cardIndex, game.getDeck().getEmpty());
            }

            pile.add(movedCard);

            while (!game.getDeck().getDeck().isEmpty() && player.getCardsInHand().size() < 3) {
                player.addCardToCardsInHand(game.getDeck().takeRandomCard());
            }

            if (card.getValue() == 10) {
                pile.clear();
            } else {
                game.increaseCurrentPlayerIndex();
            }

            if (isThereAWinner(player)) {
                game.setWinner(player);
            }

            GameRepository.saveGameData(ids.getGameId(), getGameDataFromFE(game, ids.getPlayerId(), false));
        }
    }

    public GameData getGameDataFromFE(Game game, UUID playerId, boolean covering) {
        Optional<Player> playerInArrOpt = game.getPlayerById(playerId);
        if (playerInArrOpt.isPresent()) {
            Player playerInArr = playerInArrOpt.get();
            int playerIndexInArray = game.getPlayers().indexOf(playerInArr);
            ArrayList<Player> modifiedList = new ArrayList<Player>();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                Player modifiedPlayer = new Player(game.getPlayers().get(i));
                modifiedPlayer.setPlayerIndex(i);

                if (i == game.getCurrentPlayerIndex()) {
                    modifiedPlayer.setIsCurrentPlayer(true);
                }

                if ((i != playerIndexInArray) & covering) {
                    modifiedPlayer.coverCardInHand();
                }

                if (covering) {
                    modifiedPlayer.coverCardOnTable();
                }

                modifiedList.add(modifiedPlayer);
            }

            GameData gameData = new GameData();
            gameData.setPlayerNumber(game.getPlayerNumber());
            gameData.setPlayers(modifiedList);
            if (covering) {
                gameData.setDeck(game.getDeck().getCoveredDeck());
            } else {
                gameData.setDeck(game.getDeck().getDeck());
            }
            gameData.setPile(game.getPile());
            gameData.setCurrentPlayerIndex(game.getCurrentPlayerIndex());
            gameData.setGameStatus(game.getGameStatus());
            gameData.setWinner(game.getWinner());
            gameData.setPlayerIndex(playerIndexInArray);
            return gameData;
        } else {
            return null;
        }
    }

    public boolean isThereAWinner(Player player) {
        if (player.getCardsInHand().isEmpty()) {
            for (int i = 0; i < player.getCardsOnCards().size(); i++) {
                if ((!Objects.equals(player.getCardsOnCards().get(i).getSrc(), ""))) {
                    return false;
                }
            }

            for (int i = 0; i < player.getCardsOnTable().size(); i++) {
                if ((!Objects.equals(player.getCardsOnTable().get(i).getSrc(), ""))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
