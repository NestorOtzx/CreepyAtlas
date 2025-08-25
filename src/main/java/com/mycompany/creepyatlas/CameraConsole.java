/*
 * Hagamos una cámara dentro de la consola, con una función Draw que dibujará en consola una matriz de caracteres que vendrá siendo,
 * la cámara debe soportar varias capas que se almacenaran en una lista dinámica, por ejemplo, se tendrá la capa del mapa, la capa de los enemigos 
 * y la capa de los personajes, no es necesario nombrar las capas dentro de la implementación de la cámara esto le servirá a la cámara para saber
 * el orden de dibujado, la matriz debe dibujarse a partir de un centro y limitarse a dibujar n pixeles a los laterales y m pixeles arribas y abajo.
 * La función Draw debe recibir como parámetro el centro en coordenadas x e y, debe recibir n y m, y debe recibir las capas como una lista dinámica
 * Debe manejar errores con los parametros y manejar casos de borde en caso de que n y m supere el limite, debe dibujar espacios.
 */
package com.mycompany.creepyatlas;
import java.util.List;

public class CameraConsole {

    /**
     * Dibuja en consola un área de las capas según un centro y límites.
     *
     * @param cx     Coordenada X del centro
     * @param cy     Coordenada Y del centro
     * @param n      Número de píxeles a la izquierda/derecha del centro
     * @param m      Número de píxeles arriba/abajo del centro
     * @param layers Lista dinámica de capas (cada capa es una matriz char[][])
     */
    public static void draw(int cx, int cy, int n, int m, List<char[][]> layers) {
        if (layers == null || layers.isEmpty()) {
            System.out.println("No hay capas para dibujar.");
            return;
        }

        // Se asume que todas las capas tienen la misma dimensión
        int rows = layers.get(0).length;
        int cols = layers.get(0)[0].length;

        if (n < 0 || m < 0) {
            System.out.println("Error: n y m deben ser positivos.");
            return;
        }
        if (cx < 0 || cy < 0 || cx >= cols || cy >= rows) {
            System.out.println("Error: centro fuera de los límites.");
            return;
        }

        // Limites de la ventana de dibujo
        int xMin = cx - n;
        int xMax = cx + n;
        int yMin = cy - m;
        int yMax = cy + m;

        // Iterar sobre cada fila dentro de la ventana
        for (int y = yMin; y <= yMax; y++) {
            StringBuilder line = new StringBuilder();
            for (int x = xMin; x <= xMax; x++) {
                char pixel = ' '; // por defecto espacio

                // Dibujar en orden las capas
                for (char[][] layer : layers) {
                    if (y >= 0 && y < layer.length &&
                        x >= 0 && x < layer[0].length) {
                        char c = layer[y][x];
                        if (c != ' ') { // espacio se considera transparente
                            pixel = c;
                        }
                    }
                }
                line.append(pixel);
            }
            System.out.println(line);
        }
    }
}
