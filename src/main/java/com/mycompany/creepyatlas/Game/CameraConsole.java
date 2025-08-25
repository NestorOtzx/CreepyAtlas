package com.mycompany.creepyatlas.Game;

import java.util.List;

public class CameraConsole {

    public static void draw(
            int centerX,
            int centerY,
            int halfWidth,
            int halfHeight,
            List<char[][]> layers
    ) {
        if (layers == null || layers.isEmpty()) {
            System.out.println("No hay capas para dibujar.");
            return;
        }

        int totalRows = layers.get(0).length;
        int totalCols = layers.get(0)[0].length;

        if (halfWidth < 0 || halfHeight < 0) {
            System.out.println("Error: las dimensiones deben ser positivas.");
            return;
        }

        if (centerX < 0 || centerY < 0 || centerX >= totalCols || centerY >= totalRows) {
            System.out.println("Error: el centro está fuera de los límites.");
            return;
        }

        int xStart = centerX - halfWidth;
        int xEnd   = centerX + halfWidth;
        int yStart = centerY - halfHeight;
        int yEnd   = centerY + halfHeight;

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
