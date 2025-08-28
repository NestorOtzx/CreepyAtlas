package com.mycompany.creepyatlas.Game;

import java.util.List;

public class CameraConsole {
    private static final int CAMERA_HALF_HEIGHT = 5;
    private static final int CAMERA_HALF_WIDTH = 30;

    private static char[][] lastFrame;

    public static void draw(
            int centerX,
            int centerY,
            List<char[][]> layers
    ) {
        if (layers == null || layers.isEmpty()) {
            System.out.println("No layers to draw.");
            lastFrame = null;
            return;
        }

        int totalRows = layers.get(0).length;
        int totalCols = layers.get(0)[0].length;

        if (centerX < 0 || centerY < 0 || centerX >= totalCols || centerY >= totalRows) {
            System.out.println("Error: center is out of the bounds of the map.");
            lastFrame = null;
            return;
        }

        int xStart = centerX - CAMERA_HALF_WIDTH;
        int xEnd   = centerX + CAMERA_HALF_WIDTH;
        int yStart = centerY - CAMERA_HALF_HEIGHT;
        int yEnd   = centerY + CAMERA_HALF_HEIGHT;

        int height = yEnd - yStart + 1;
        int width = xEnd - xStart + 1;
        lastFrame = new char[height][width];

        for (int y = yStart, yy = 0; y <= yEnd; y++, yy++) {
            for (int x = xStart, xx = 0; x <= xEnd; x++, xx++) {
                char pixel = ' ';

                for (char[][] layer : layers) {
                    boolean insideBounds =
                            y >= 0 && y < layer.length &&
                            x >= 0 && x < layer[0].length;

                    if (insideBounds && layer[y][x] != ' ') {
                        pixel = layer[y][x];
                    }
                }
                lastFrame[yy][xx] = pixel;
            }
        }

        Screen.render();
    }

    // Getter to access the last captured frame
    public static char[][] getLastFrame() {
        return lastFrame;
    }
}
