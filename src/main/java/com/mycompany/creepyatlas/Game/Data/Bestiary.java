package com.mycompany.creepyatlas.Game.Data;

import java.util.*;

public class Bestiary {
    static class Enemy {
        String id;
        String name;
        int healthPoints;
        int attack_damage;
        int mentalWeakness;
        String description;

        public Enemy(String id, String name, int healthPoints, int attack_damage, int mentalWeakness, String description){
            this.id = id;
            this.name = name;
            this.healthPoints = healthPoints;
            this.attack_damage = attack_damage;
            this.mentalWeakness = mentalWeakness;
            this.description = description;
        }
    }

    private static final Map<String,Enemy> enemies = new LinkedHashMap<>();
    
static {
    enemies.put("Atlas", new Enemy(
        "A", "Atlas", 100000, 10000, 100000,
        "This is the boss of the Beast world in the underground of Celestia.                                            "
      + "With just two hits, he will send you back to the menu.                                                          "
      + "...I mean, to the underworld.                                                                                   "
      + "You will have to be a noble and cunning warrior (or just very lucky) to defeat Atlas."
    ));

    enemies.put("BigBox", new Enemy(
            "B", "BigBox", 140, 10,20,
            "...Well...                                                                                                     "
            + " ...it's just a cardboard box.                                                                            "
            + "...It won't cause you much injury unless you bump into it."
    ));

    enemies.put("Chubby", new Enemy(
            "C", "Chubby", 20, 10, 120,
            "This little frog seems to weigh less than it actually does.                                                     "
            + "However, if it jumps on you, try to run fast...                                                                 "
            + "...or you will die under its hooves."
    ));

    enemies.put("ToraGe", new Enemy(
            "G", "ToraGe", 92, 10, 91,
            "A beautiful Bengal tiger"
            +"burning eyes, captivating color, and a roar..."
            +"so powerful that just getting close to it can kill you."
    ));

    enemies.put("Hydra", new Enemy(
            "H", "Hydra", 100, 10, 100,
            "Hydra used to be a nymph from the Kingdom of Aqua.                                                                "
            + "Not much is known about her motivations,                                                                          "
            + "but her water attack is one of the strongest in the entire Kingdom of Celestia.                                   "
            + "However, she is a bit weak-minded, almost as fluid as her creation."
    ));

    enemies.put("MaKinda", new Enemy(
            "M", "MaKinda", 1, 10, 95,
            "Ma-KinDa is the name given to this enormous rock by local farmers.                                          "
            + "It is very steep, and pieces of it fall off if you try to climb it.                                         "
            + "...Traveler! Watch your step."
    ));

    enemies.put("Ana", new Enemy(
            "N", "Ana", 100, 10, 100,
            "Oh, for Celestial's sake... You wanted to hurt a mortal?                                                           "
            +"Just run away! If you hit her,                                                                                    " 
            +"you'll be a ruthless adventurer.                                                                                  "                                                                                             
    ));

    enemies.put("PrayPrey", new Enemy(
            "O", "PrayPrey", 95, 10, 95,
            "This Bishop is responsible for eliminating all adventurers who cross his territory.                                "
            +"...Oh no. He's coming with a torch...                                                                             "
            +"...Watch out!                                                                                                     "
    ));

    enemies.put("Savepoint", new Enemy(
            "S", "Savepoint", 0, 0, 0,
            "Oh, my goodness...                                                                                                   "
            +"Look what you've done!                                                                                            "
    )); 

    enemies.put("MashiTa", new Enemy(
            "T", "MashiTa", 35, 10, 49,
            "You've found a wandering spirit!                                                                                   "
            +"Eh...                                                                                                             " 
            +"wait...                                                                                                           "                                                                                           
    ));

    enemies.put("A-Ka", new Enemy(
            "K", "A-Ka", 95, 10, 91,
            "Uh, be very careful with these mosquitoes,                                                                         "
            +"because everyone knows that when they bite you,                                                                   "
            +"the disease is so painful that it can kill you on the first few bites.                                            "
    ));

    enemies.put("WaterFlowbar", new Enemy(
            "W", "WaterFlowbar", 90, 10, 90,
            "If you find a flowbar...                                                                                           "
            + "running to the shore in a lagoon, you must try.                                                                   "
            + "Before its attack drowns you,                                                                                    "
            + "the nymphs wet you,                                                                                              "
            + "and your walk stops.                                                                                             "
    ));

    enemies.put("BiYah", new Enemy(
            "Y", "BiYah", 10, 10, 20,
            "By Atlas himself! You have just discovered Bi-Yah.                                                                 "
            +"Talk to him about his seven wives and you may be spared his 'bommbastic' attack.                                  "
    ));

    enemies.put("Acerco", new Enemy(
            "Z", "Acerco", 1, 10, 1,
            "If you encounter this tiny Gnome, you won't take much physical damage.                                             "
            + "But it can put you to sleep. It has many hit points and very few attack points...                                "
            + "You should try to escape before you fall asleep.                                                                 "
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
        System.out.println("## id |     Name     |  Health  |  Damage  |Mental Weakness|                                                    Description                                            ##");
        System.out.println(border);

        // imprimir cada enemigo
        for (Enemy e : enemies.values()) {
            List<String> descLines = wrapText(e.description, 90);
            int rows = Math.max(3, descLines.size()); // al menos 3 filas

            for (int i = 0; i < rows; i++) {
                if (i == 0) {
                    // primera fila con todos los datos + primera parte de la descripción
                    System.out.printf("## %-2s | %-12s | %-8d | %-8d | %-14d | %-90s ##%n",
                            e.id, e.name, e.healthPoints, e.attack_damage, e.mentalWeakness,
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