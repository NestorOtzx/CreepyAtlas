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
            throw new IllegalStateException("The level has no player (P).");
        }

        return new MapData(baseMap, player, enemies);
    }

    private static Enemy createEnemy(char symbol, int x, int y) {
        switch (symbol) {
            case 'A': return new Atlas(x, y);
            case 'M': return new MaKinDa(x, y);
            case 'B': return new BigBox(x, y);
            case 'C': return new Chubby(x, y);
            case 'H': return new Hydra(x, y);
            case 'W': return new WaterFlowbar(x, y);
            case 'N': return new Ana(x, y);
            case 'T': return new MashiTa(x, y);
            case 'Y': return new BiYah(x, y);
            case 'Z': return new Acerco(x, y);
            case 'O': return new PrayPrey(x, y);
            case 'K': return new AKa(x, y);
            case 'G': return new ToraGe(x, y);
            case 'S': return new Savepoint(x, y);
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
            throw new RuntimeException("Error reading the level: " + levelFile, e);
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
