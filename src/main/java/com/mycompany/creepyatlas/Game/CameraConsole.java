package com.mycompany.creepyatlas.Game;

import java.util.List;

public class CameraConsole {
    private static final int CAMERA_HALF_HEIGHT = 4;
    private static final int CAMERA_HALF_WIDTH = 4;


    public static void draw(
            int centerX,
            int centerY,
            List<char[][]> layers
    ) {
        if (layers == null || layers.isEmpty()) {
            System.out.println("No layers to draw.");
            return;
        }

        int totalRows = layers.get(0).length;
        int totalCols = layers.get(0)[0].length;

        if (centerX < 0 || centerY < 0 || centerX >= totalCols || centerY >= totalRows) {
            System.out.println("Error: center is out of the bounds of the map.");
            return;
        }

        int xStart = centerX - CAMERA_HALF_WIDTH;
        int xEnd   = centerX + CAMERA_HALF_WIDTH;
        int yStart = centerY - CAMERA_HALF_HEIGHT;
        int yEnd   = centerY + CAMERA_HALF_HEIGHT;

        for (int y = yStart; y <= yEnd; y++) {
            StringBuilder row = new StringBuilder();

            for (int x = xStart; x <= xEnd; x++) {
                char pixel = ' ';

                for (char[][] layer : layers) {
                    boolean insideBounds = 
                            y >= 0 && y < layer.length &&
                            x >= 0 && x < layer[0].length;

                    if (insideBounds && layer[y][x] != ' ') {
                        pixel = layer[y][x];
                    }
                }
                row.append(pixel);
            }
            System.out.println(row);
        }
    }
}
