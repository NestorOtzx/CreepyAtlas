package com.mycompany.creepyatlas.Game.Data;

import java.util.*;

public class Bestiary {
    static class EntityInfo {
        String id;
        String name;
        int health;
        int attackDamage;
        int mentalHealth;
        String description;

        public EntityInfo(String id, String name, int health, int attackDamage, int mentalHealth, String description) {
            this.id = id;
            this.name = name;
            this.health = health;
            this.attackDamage = attackDamage;
            this.mentalHealth = mentalHealth;
            this.description = description;
        }
    }

    private static final Map<Character, EntityInfo> enemies = new LinkedHashMap<>();

    private static final String BORDER =
            "####################################################################################################################################################################";
    private static final String TITLE =
            "##                                                                            BESTIARY                                                                            ##";
    private static final String HEADER =
            "## id |     Name     |  Health  |  Damage  |Mental Weakness|                                                    Description                                            ##";

    static {
        enemies.put('A', new EntityInfo("A", "Atlas", 100000, 10000, 100000,
                "This is the boss of the Beast world in the underground of Celestia. "
                        + "With just two hits, he will send you back to the menu. "
                        + "...I mean, to the underworld. "
                        + "You will have to be a noble and cunning warrior (or just very lucky) to defeat Atlas."
        ));

        enemies.put('B', new EntityInfo("B", "BigBox", 140, 10, 20,
                "...Well... ...it's just a cardboard box. ...It won't cause you much injury unless you bump into it."
        ));

        enemies.put('C', new EntityInfo("C", "Chubby", 20, 10, 120,
                "This little frog seems to weigh less than it actually does. "
                        + "However, if it jumps on you, try to run fast... "
                        + "...or you will die under its hooves."
        ));

        enemies.put('G', new EntityInfo("G", "ToraGe", 92, 10, 91,
                "A beautiful Bengal tiger burning eyes, captivating color, and a roar..."
                        + "so powerful that just getting close to it can kill you."
        ));

        enemies.put('H', new EntityInfo("H", "Hydra", 100, 10, 100,
                "Hydra used to be a nymph from the Kingdom of Aqua. "
                        + "Not much is known about her motivations, "
                        + "but her water attack is one of the strongest in the entire Kingdom of Celestia. "
                        + "However, she is a bit weak-minded, almost as fluid as her creation."
        ));

        enemies.put('M', new EntityInfo("M", "MaKinda", 1, 10, 95,
                "Ma-KinDa is the name given to this enormous rock by local farmers. "
                        + "It is very steep, and pieces of it fall off if you try to climb it. "
                        + "...Traveler! Watch your step."
        ));

        enemies.put('N', new EntityInfo("N", "Ana", 100, 10, 100,
                "Oh, for Celestial's sake... You wanted to hurt a mortal? "
                        + "Just run away! If you hit her, you'll be a ruthless adventurer."
        ));

        enemies.put('O', new EntityInfo("O", "PrayPrey", 95, 10, 95,
                "This Bishop is responsible for eliminating all adventurers who cross his territory. "
                        + "...Oh no. He's coming with a torch... "
                        + "...Watch out!"
        ));

        enemies.put('S', new EntityInfo("S", "Savepoint", 0, 0, 0,
                "Oh, my goodness... Look what you've done!"
        ));

        enemies.put('T', new EntityInfo("T", "MashiTa", 35, 10, 49,
                "You've found a wandering spirit! Eh... wait..."
        ));

        enemies.put('K', new EntityInfo("K", "A-Ka", 95, 10, 91,
                "Uh, be very careful with these mosquitoes, "
                        + "because everyone knows that when they bite you, "
                        + "the disease is so painful that it can kill you on the first few bites."
        ));

        enemies.put('W', new EntityInfo("W", "WaterFlowbar", 90, 10, 90,
                "If you find a flowbar... running to the shore in a lagoon, you must try. "
                        + "Before its attack drowns you, the nymphs wet you, and your walk stops."
        ));

        enemies.put('Y', new EntityInfo("Y", "BiYah", 10, 10, 20,
                "By Atlas himself! You have just discovered Bi-Yah. "
                        + "Talk to him about his seven wives and you may be spared his 'bommbastic' attack."
        ));

        enemies.put('Z', new EntityInfo("Z", "Acerco", 1, 10, 1,
                "If you encounter this tiny Gnome, you won't take much physical damage. "
                        + "But it can put you to sleep. It has many hit points and very few attack points... "
                        + "You should try to escape before you fall asleep."
        ));
    }

    private static List<String> wrapText(String text, int width) {
        List<String> lines = new ArrayList<>();
        StringBuilder lineBuilder = new StringBuilder();
        for (String word : text.split(" ")) {
            if (lineBuilder.length() + word.length() + 1 > width) {
                lines.add(lineBuilder.toString());
                lineBuilder = new StringBuilder();
            }
            if (lineBuilder.length() > 0) lineBuilder.append(" ");
            lineBuilder.append(word);
        }
        if (lineBuilder.length() > 0) lines.add(lineBuilder.toString());
        return lines;
    }

    public static void printBestiary() {
        System.out.println(BORDER);
        System.out.println(TITLE);
        System.out.println(BORDER);
        System.out.println(HEADER);
        System.out.println(BORDER);

        for (EntityInfo entity : enemies.values()) {
            List<String> descriptionLines = wrapText(entity.description, 90);
            int rowCount = Math.max(3, descriptionLines.size());
            for (int i = 0; i < rowCount; i++) {
                if (i == 0) {
                    System.out.printf("## %-2s | %-12s | %-8d | %-8d | %-14d | %-90s ##%n",
                            entity.id, entity.name, entity.health, entity.attackDamage, entity.mentalHealth,
                            i < descriptionLines.size() ? descriptionLines.get(i) : "");
                } else {
                    System.out.printf("## %2s | %-12s | %-8s | %-8s | %-14s | %-90s ##%n",
                            "", "", "", "", "",
                            i < descriptionLines.size() ? descriptionLines.get(i) : "");
                }
            }
            System.out.println(BORDER);
        }
    }
}
