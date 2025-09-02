package com.mycompany.creepyatlas.Game.Data;

import java.util.*;

public class Bestiary {
    static class Enemy {
        String id;
        String name;
        int healthPoints;
        int attackPoints;
        int mentalWeakness;
        String description;

        public Enemy(String id, String name, int healthPoints, int attackPoints, int mentalWeakness, String description){
            this.id = id;
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
            "A", "Atlas", 100000, 3000, 100000,
            "This is the boss of the Beast world in the underground of Celestia.                                            "
          + "With just two hits, he will send you back to the menu.                                                          "
          + "...I mean, to the underworld.                                                                                   "
          + "You will have to be a noble and cunning warrior (or just very lucky) to defeat Atlas."
        ));

        enemies.put("MaKinda", new Enemy(
                "M", "MaKinda", 3000, 1200, 1,
                "Ma-KinDa is the name given to this enormous rock by local farmers.                                          "
                + "It is very steep, and pieces of it fall off if you try to climb it.                                         "
                + "...Traveler! Watch your step."
        ));

        enemies.put("BigBox", new Enemy(
                "B", "BigBox", 250, 1,1,
                "...Well...                                                                                                     "
                + " ...it's just a cardboard box.                                                                            "
                + "...It won't cause you much injury unless you bump into it."
        ));

        enemies.put("Chubby", new Enemy(
                "C", "Chubby", 300, 1500, 1,
                "This little frog seems to weigh less than it actually does.                                                     "
                + "However, if it jumps on you, try to run fast...                                                                 "
                + "...or you will die under its hooves."
        ));

        enemies.put("Hydra", new Enemy(
                "H", "Hydra", 600, 2000, 1,
                "Hydra used to be a nymph from the Kingdom of Aqua.                                                                "
                + "Not much is known about her motivations,                                                                          "
                + "but her water attack is one of the strongest in the entire Kingdom of Celestia.                                   "
                + "However, she is a bit weak-minded, almost as fluid as her creation."
        ));

        enemies.put("WaterFlowbar", new Enemy(
                "W", "WaterFlowbar", 250, 1400, 1,
                "If you find a flowbar...                                                                                           "
                + "running to the shore in a lagoon, you must try.                                                                   "
                + "Before its attack drowns you,                                                                                    "
                + "the nymphs wet you,                                                                                              "
                + "and your walk stops.                                                                                             "
        ));
        enemies.put("Ana", new Enemy(
                "N", "Ana", 1, 0, 1,
                "Oh, for Celestial's sake... You wanted to hurt a mortal?                                                           "
                +"Just run away! If you hit her,                                                                                    " 
                +"you'll be a ruthless adventurer.                                                                                  "                                                                                             
        ));

        enemies.put("MashiTa", new Enemy(
                "T", "MashiTa", 0, 0, 0,
                "You've found a wandering spirit!                                                                                   "
                +"Eh...                                                                                                             " 
                +"wait…                                                                                                             "
                +"You scared it to death!                                                                                           "
        ));

        // Bi-Yah
        enemies.put("BiYah", new Enemy(
                "Y", "BiYah", 10, 4000, 1,
                "By Atlas himself! You have just discovered Bi-Yah.                                                                 "
                +"Talk to him about his seven wives and you may be spared his 'bommbastic' attack.                                  "
        ));

        // Acerco
        enemies.put("Acerco", new Enemy(
                "Z", "Acerco", 10000000, 1, 1,
                "If you encounter this tiny Gnome, you won't take much physical damage.                                             "
                + "But it can put you to sleep. It has many hit points and very few attack points...                                "
                + "You should try to escape before you fall asleep.                                                                 "
        ));

        // PrayPrey
        enemies.put("PrayPrey", new Enemy(
                "O", "PrayPrey", 100000, 3000, 1,
                "This Bishop is responsible for eliminating all adventurers who cross his territory.                                "
                +"...Oh no. He's coming with a torch...                                                                             "
                +"...Watch out!                                                                                                     "
        ));

        // A-Ka
        enemies.put("A-Ka", new Enemy(
                "K", "A-Ka", 500, 2500, 1,
                "Uh, be very careful with these mosquitoes,                                                                         "
                +"because everyone knows that when they bite you,                                                                   "
                +"the disease is so painful that it can kill you on the first few bites.                                            "
        ));

        // ToraGe
        enemies.put("ToraGe", new Enemy(
                "G", "ToraGe", 1000000000, 100000000, 1000000000,
                "A beautiful rose from Celestia. Sweet aroma, captivating color, and poison..."
                +"so potent that just getting close to it can kill you."
        ));

        // Savepoint (lo puse como “enemigo especial” sin stats)
        enemies.put("Savepoint", new Enemy(
                "S", "Savepoint", 0, 0, 0,
                "Oh, my goodness...                                                                                                   "
                +"Look what you've done!                                                                                            "
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
        System.out.println("## id |     Name     |Health Pts|Attack Pts|Mental Weakness|                                                    Description                                            ##");
        System.out.println(border);

        // imprimir cada enemigo
        for (Enemy e : enemies.values()) {
            List<String> descLines = wrapText(e.description, 90);
            int rows = Math.max(3, descLines.size()); // al menos 3 filas

            for (int i = 0; i < rows; i++) {
                if (i == 0) {
                    // primera fila con todos los datos + primera parte de la descripción
                    System.out.printf("## %-2s | %-12s | %-8d | %-8d | %-14d | %-90s ##%n",
                            e.id, e.name, e.healthPoints, e.attackPoints, e.mentalWeakness,
                            i < descLines.size() ? descLines.get(i) : "");
                } else {
                    // filas siguientes solo muestran la descripción
                    System.out.printf("## %2s | %-12s | %-8s | %-8s | %-14s | %-90s ##%n",
                            "","", "", "", "",
                            i < descLines.size() ? descLines.get(i) : "");
                }
            }

            System.out.println(border); // separador entre enemigos
        }
    }
}
