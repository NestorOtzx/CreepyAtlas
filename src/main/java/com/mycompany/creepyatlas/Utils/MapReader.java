package com.mycompany.creepyatlas.Utils;

import java.nio.file.*;
import java.util.*;

import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Game.Entities.Enemies.*;

public class MapReader {

    public static MapData loadLevel(String levelFile) {
        List<String> lines = readFile(levelFile);
        int rows = lines.size();
        int cols = lines.get(0).length();

        char[][] baseMap = new char[rows][cols];
        Player player = null;
        List<Enemy> enemies = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
            String line = lines.get(y);
            for (int x = 0; x < cols; x++) {
                char c = line.charAt(x);

                if (c == 'P') {
                    player = new Player(x, y);
                    baseMap[y][x] = '.'; 
                } else if (Character.isLetter(c)) {
                    Enemy enemy = createEnemy(c, x, y);
                    enemies.add(enemy);
                    baseMap[y][x] = '.'; 
                } else {
                    baseMap[y][x] = c; 
                }
            }
        }

        if (player == null) {
            throw new IllegalStateException("El nivel no tiene un jugador (P).");
        }

        return new MapData(baseMap, player, enemies);
    }

    private static Enemy createEnemy(char symbol, int x, int y) {
        switch (symbol) {
            case 'A': return new Atlas(x, y); 
            default: return new Enemy(x, y);
        }
    }

    private static List<String> readFile(String levelFile) {
        try {
            Path path = Paths.get(
                Objects.requireNonNull(
                    MapReader.class.getClassLoader().getResource(levelFile)
                ).toURI()
            );
            return Files.readAllLines(path);
        } catch (Exception e) {
            throw new RuntimeException("Error leyendo el nivel: " + levelFile, e);
        }
    }

    public static class MapData {
        private final char[][] baseMap;
        private final Player player;
        private final List<Enemy> enemies;

        public MapData(char[][] baseMap, Player player, List<Enemy> enemies) {
            this.baseMap = baseMap;
            this.player = player;
            this.enemies = enemies;
        }

        public char[][] getBaseMap() { return baseMap; }
        public Player getPlayer() { return player; }
        public List<Enemy> getEnemies() { return enemies; }
    }
}
