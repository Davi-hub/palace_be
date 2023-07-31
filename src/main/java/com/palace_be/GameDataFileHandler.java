package com.palace_be;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GameDataFileHandler {
    private static final String FILE_PATH = "game_data.json";

    public static void saveGameData(UUID gameId, GameData gameData) {
        try {
            Map<String, GameData> dataMap = new HashMap<>();
            // Betöltés az adatok fájlból
            byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));

            // Ellenőrzés, hogy az adatok fájl tartalmazza-e a gameId-t
            if (jsonData.length > 0) {
                ObjectMapper objectMapper = new ObjectMapper();
                dataMap = objectMapper.readValue(jsonData, Map.class);
            }

            // Felülírás vagy hozzáadás az adatokhoz
            dataMap.put(gameId.toString(), gameData);

            // JSON formátumra alakítás és fájlba írás
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonOutput = objectMapper.writeValueAsString(dataMap);

            Files.write(Paths.get(FILE_PATH), jsonOutput.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameData loadGameData(UUID gameId) {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));

            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<Map<String, GameData>> typeReference = new TypeReference<Map<String, GameData>>() {
            };
            Map<String, GameData> dataMap = objectMapper.readValue(jsonData, typeReference);

            GameData gameData = (GameData) dataMap.get(gameId.toString());
            // Game game = objectMapper.convertValue(gameData, Game.class);
            return gameData;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
