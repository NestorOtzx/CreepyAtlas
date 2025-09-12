package com.mycompany.creepyatlas.Game;

import java.util.List;

public class CameraConsole {
    private static final int CAMERA_HALF_HEIGHT = 5;
    private static final int CAMERA_HALF_WIDTH = 30;

    private static char[][] lastFrame;

    public static void draw(int centerX, int centerY, List<char[][]> layers) {
        if (layers == null || layers.isEmpty()) {
            lastFrame = null;
            return;
        }

        int totalRows = layers.get(0).length;
        int totalCols = layers.get(0)[0].length;

        if (!isValidCenter(centerX, centerY, totalCols, totalRows)) {
            lastFrame = null;
            return;
        }

        int xStart = centerX - CAMERA_HALF_WIDTH;
        int xEnd = centerX + CAMERA_HALF_WIDTH;
        int yStart = centerY - CAMERA_HALF_HEIGHT;
        int yEnd = centerY + CAMERA_HALF_HEIGHT;

        int height = yEnd - yStart + 1;
        int width = xEnd - xStart + 1;
        lastFrame = new char[height][width];

        for (int mapY = yStart, row = 0; mapY <= yEnd; mapY++, row++) {
            for (int mapX = xStart, col = 0; mapX <= xEnd; mapX++, col++) {
                char currentChar = ' ';
                for (char[][] layer : layers) {
                    if (isInsideBounds(layer, mapX, mapY) && layer[mapY][mapX] != ' ') {
                        currentChar = layer[mapY][mapX];
                    }
                }
                lastFrame[row][col] = currentChar;
            }
        }
    }

    private static boolean isValidCenter(int centerX, int centerY, int totalCols, int totalRows) {
        return centerX >= 0 && centerY >= 0 && centerX < totalCols && centerY < totalRows;
    }

    private static boolean isInsideBounds(char[][] layer, int x, int y) {
        return y >= 0 && y < layer.length && x >= 0 && x < layer[0].length;
    }

    public static char[][] getLastFrame() {
        return lastFrame;
    }
}
