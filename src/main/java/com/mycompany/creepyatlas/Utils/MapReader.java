package com.mycompany.creepyatlas.Utils;

import java.nio.file.*;
import java.util.*;

import com.mycompany.creepyatlas.Game.Data.Bestiary;
import com.mycompany.creepyatlas.Game.Data.EntityInformation;
import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Game.Entities.Enemies.*;

public class MapReader {

    private static final Map<Character, EnemyFactory> enemyFactories = new HashMap<>();
    static {
        enemyFactories.put('A', Atlas::new);
        enemyFactories.put('M', MaKinDa::new);
        enemyFactories.put('B', BigBox::new);
        enemyFactories.put('C', Chubby::new);
        enemyFactories.put('H', Hydra::new);
        enemyFactories.put('W', WaterFlowbar::new);
        enemyFactories.put('N', Ana::new);
        enemyFactories.put('T', MashiTa::new);
        enemyFactories.put('Y', BiYah::new);
        enemyFactories.put('Z', Acerco::new);
        enemyFactories.put('O', PrayPrey::new);
        enemyFactories.put('K', AKa::new);
        enemyFactories.put('G', ToraGe::new);
    }

    @FunctionalInterface
    private interface EnemyFactory {
        Enemy create(int x, int y, int health, int attackDamage, int mentalHealth);
    }

    public static MapData loadLevel(String levelFile) {
        List<String> lines = readFile(levelFile);
        int rows = lines.size();
        int cols = lines.get(0).length();

        char[][] baseMap = new char[rows][cols];
        Player player = null;
        List<Enemy> enemies = new ArrayList<>();
        List<Savepoint> savePoints = new ArrayList<>();

        for (int y = 0; y < rows; y++) {
            String line = lines.get(y);
            for (int x = 0; x < line.length(); x++) {
                char symbol = line.charAt(x);

                if (symbol == 'P') {
                    player = new Player(x, y, 100, 100, 50, 50);
                    baseMap[y][x] = ' ';
                } else if (symbol == 'S') {
                    savePoints.add(new Savepoint(x, y, 0, 0, 0, savePoints.size()));
                    baseMap[y][x] = ' ';
                } else if (Character.isLetter(symbol)) {
                    Enemy enemy = createEnemy(symbol, x, y);
                    enemies.add(enemy);
                    baseMap[y][x] = ' ';
                } else {
                    baseMap[y][x] = symbol;
                }
            }
        }

        if (player == null) {
            throw new IllegalStateException("The level has no player (P).");
        }

        return new MapData(baseMap, player, enemies, savePoints);
    }

    private static Enemy createEnemy(char symbol, int x, int y) {
        EntityInformation info = Bestiary.enemyInformationBySymbol.get(symbol);
        if (info == null) return new Enemy(x, y, 0, 0, 0);

        EnemyFactory factory = enemyFactories.get(symbol);
        if (factory == null) {
            return new Enemy(x, y, info.getHealth(), info.getMentalHealth(), info.getAttackDamage());
        }

        return factory.create(x, y, info.getHealth(), info.getMentalHealth(), info.getAttackDamage());
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

    
}
