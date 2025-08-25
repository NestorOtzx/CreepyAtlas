package com.mycompany.creepyatlas;

import com.mycompany.creepyatlas.Entities.*;
import java.util.*;

public class Game {
    private static final int MAP_HEIGHT = 15;
    private static final int MAP_WIDTH = 30;

    private static final int CAMERA_HALF_WIDTH = 7;
    private static final int CAMERA_HALF_HEIGHT = 4;

    private final char[][] baseMap;
    private final char[][] entityLayer;
    private final List<char[][]> renderLayers;

    private final Player player;
    private final List<Entity> entities;

    public Game() {
        baseMap = createBaseMap(MAP_HEIGHT, MAP_WIDTH);
        entityLayer = new char[MAP_HEIGHT][MAP_WIDTH];

        player = new Player(9, 6);
        entities = new ArrayList<>();
        entities.add(player);
        entities.add(new Enemy(10, 5));
        entities.add(new Enemy(12, 7));
        entities.add(new Enemy(8, 6));

        renderLayers = new ArrayList<>();
        renderLayers.add(baseMap);
        renderLayers.add(entityLayer);
    }

    private char[][] createBaseMap(int rows, int cols) {
        char[][] map = new char[rows][cols];
        for (int y = 0; y < rows; y++) {
            Arrays.fill(map[y], '.');
        }
        return map;
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

    public void draw() {
        refreshEntityLayer();
        CameraConsole.draw(
                player.getX(),
                player.getY(),
                CAMERA_HALF_WIDTH,
                CAMERA_HALF_HEIGHT,
                renderLayers
        );
    }

    public void start() {
        draw();
    }
}
