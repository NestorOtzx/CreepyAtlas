package com.mycompany.creepyatlas;
import java.util.*;

public class Game {
    private static final int MAP_ROWS = 15;
    private static final int MAP_COLS = 30;

    private static final int PLAYER_START_X = 9;
    private static final int PLAYER_START_Y = 6;

    private static final int[][] ENEMY_START_POSITIONS = {
        {10, 5}, {12, 7}, {8, 6}
    };

    private static final int N = 7; 
    private static final int M = 4;

    private char[][] map;
    private char[][] enemiesLayer;
    private char[][] playerLayer;
    private List<char[][]> layers;

    // Estado
    private int playerX, playerY;
    private List<int[]> enemies;

    public Game() {
        map = new char[MAP_ROWS][MAP_COLS];
        for (int y = 0; y < MAP_ROWS; y++) {
            for (int x = 0; x < MAP_COLS; x++) {
                map[y][x] = '.';
            }
        }

        enemiesLayer = new char[MAP_ROWS][MAP_COLS];
        playerLayer = new char[MAP_ROWS][MAP_COLS];

        this.playerX = PLAYER_START_X;
        this.playerY = PLAYER_START_Y;

        enemies = new ArrayList<>();
        for (int[] pos : ENEMY_START_POSITIONS) {
            enemies.add(new int[]{pos[0], pos[1]});
        }

        layers = new ArrayList<>();
        layers.add(map);
        layers.add(enemiesLayer);
        layers.add(playerLayer);
    }

    public void updateView() {
        for (char[] row : enemiesLayer) Arrays.fill(row, ' ');
        for (char[] row : playerLayer) Arrays.fill(row, ' ');

        for (int[] pos : enemies) {
            int ex = pos[0], ey = pos[1];
            if (ey >= 0 && ey < enemiesLayer.length &&
                ex >= 0 && ex < enemiesLayer[0].length) {
                enemiesLayer[ey][ex] = 'E';
            }
        }

        if (playerY >= 0 && playerY < playerLayer.length &&
            playerX >= 0 && playerX < playerLayer[0].length) {
            playerLayer[playerY][playerX] = 'P';
        }
    }

    public void movePlayer(int dx, int dy) {
        playerX += dx;
        playerY += dy;
    }

    public void draw() {
        updateView();
        CameraConsole.draw(playerX, playerY, N, M, layers);
    }

    public void start() {
        draw();
    }
}
