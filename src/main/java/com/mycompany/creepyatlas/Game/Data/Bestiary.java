package com.mycompany.creepyatlas.Game.Data;

import java.util.*;

public class Bestiary {
    static class Enemy {
        String name;
        int healthPoints;
        int attackPoints;
        int mentalWeakness;
        String description;

        public Enemy(String name, int healthPoints, int attackPoints, int mentalWeakness, String description){
            this.name = name;
            this.healthPoints = healthPoints;
            this.attackPoints = attackPoints;
            this.mentalWeakness = mentalWeakness;
            this.description = description;
        }
    }

    private static final Map<String,Enemy> enemies = new LinkedHashMap<>();
    
    static {
        enemies.put("Atlas", new Enemy(
                "Atlas", 100000, 3000, 100000,
                "This is the boss of the Beast world in the underground of Celestia. "
              + "With just two hits, he will send you back to the menu. "
              + "...I mean, to the underworld. "
              + "You will have to be a noble and cunning warrior (or just very lucky) to defeat Atlas."
        ));
        
    }

    // Método auxiliar: dividir descripción en varias líneas
    private static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for (String word : text.split(" ")) {
            if (sb.length() + word.length() + 1 > width) {
                lines.add(sb.toString());
                sb = new StringBuilder();
            }
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(word);
        }
        if (!sb.isEmpty()) lines.add(sb.toString());
        return lines;
    }

    public static void printBestiary() {
        String border = "####################################################################################################################################################################";
        System.out.println(border);
        System.out.println("##                                                                            BESTIARY                                                                            ##");
        System.out.println(border);

        // encabezados
        System.out.println("##     Name     |Health Pts|Attack Pts|Mental Weakness|                                                    Description                                            ##");
        System.out.println(border);

        // imprimir cada enemigo
        for (Enemy e : enemies.values()) {
            List<String> descLines = wrapText(e.description, 90);
            int rows = Math.max(3, descLines.size()); // al menos 3 filas

            for (int i = 0; i < rows; i++) {
                if (i == 0) {
                    // primera fila con todos los datos + primera parte de la descripción
                    System.out.printf("## %-12s | %-8d | %-8d | %-14d | %-90s ##%n",
                            e.name, e.healthPoints, e.attackPoints, e.mentalWeakness,
                            i < descLines.size() ? descLines.get(i) : "");
                } else {
                    // filas siguientes solo muestran la descripción
                    System.out.printf("## %-12s | %-8s | %-8s | %-14s | %-90s ##%n",
                            "", "", "", "",
                            i < descLines.size() ? descLines.get(i) : "");
                }
            }

            System.out.println(border); // separador entre enemigos
        }
    }
}
