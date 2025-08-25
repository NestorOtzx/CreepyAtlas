package com.mycompany.creepyatlas.Game;

import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Utils.ConsoleCommand;
import com.mycompany.creepyatlas.Utils.CommandReader;
import com.mycompany.creepyatlas.Utils.MapReader;

import java.util.*;

public class Game {
    private static final int CAMERA_WIDTH = 4;
    private static final int CAMERA_HEIGHT = 4;

    private final char[][] baseMap;
    private final char[][] entityLayer;
    private final List<char[][]> renderLayers;

    private final Player player;
    private final List<Entity> entities;

    public Game() {
        MapReader.MapData mapData = MapReader.loadLevel("levels/level1.txt");

        baseMap = mapData.getBaseMap();
        entityLayer = new char[baseMap.length][baseMap[0].length];

        player = mapData.getPlayer();
        entities = new ArrayList<>();
        entities.add(player);
        entities.addAll(mapData.getEnemies());

        renderLayers = new ArrayList<>();
        renderLayers.add(baseMap);
        renderLayers.add(entityLayer);
    }

    private void refreshEntityLayer() {
        for (char[] row : entityLayer) {
            Arrays.fill(row, ' ');
        }

        for (Entity entity : entities) {
            int x = entity.getX();
            int y = entity.getY();
            if (y >= 0 && y < entityLayer.length &&
                x >= 0 && x < entityLayer[0].length) {
                entityLayer[y][x] = entity.getSymbol();
            }
        }
    }

    public void movePlayer(int deltaX, int deltaY) {
        player.move(deltaX, deltaY);
    }

    public void update() {
        refreshEntityLayer();
        CameraConsole.draw(
                player.getX(),
                player.getY(),
                CAMERA_WIDTH,
                CAMERA_HEIGHT,
                renderLayers
        );
        ConsoleCommand command = CommandReader.readCommand();
        System.out.println(command);
    }

    public void start() {
        update();
    }
}
