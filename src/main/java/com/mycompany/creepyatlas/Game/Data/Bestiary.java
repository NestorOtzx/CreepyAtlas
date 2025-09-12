package com.mycompany.creepyatlas.Game.Data;

import java.util.*;

public class Bestiary {
    public static final Map<Character, EntityInformation> enemyInformationBySymbol = new LinkedHashMap<>();

    private static final String BORDER =
            "####################################################################################################################################################################";
    private static final String TITLE =
            "##                                                                            BESTIARY                                                                            ##";
    private static final String HEADER =
            "## id |     Name     |  Health  |  Damage  |Mental Weakness|                                                    Description                                            ##";

    static {
        enemyInformationBySymbol.put('A', new EntityInformation("A", "Atlas", 100000, 10000, 100000,
                "This is the boss of the Beast world in the underground of Celestia. "
                        + "With just two hits, he will send you back to the menu. "
                        + "...I mean, to the underworld. "
                        + "You will have to be a noble and cunning warrior (or just very lucky) to defeat Atlas."
        ));

        enemyInformationBySymbol.put('B', new EntityInformation("B", "BigBox", 140, 10, 20,
                "...Well... ...it's just a cardboard box. ...It won't cause you much injury unless you bump into it."
        ));

        enemyInformationBySymbol.put('C', new EntityInformation("C", "Chubby", 20, 10, 120,
                "This little frog seems to weigh less than it actually does. "
                        + "However, if it jumps on you, try to run fast... "
                        + "...or you will die under its hooves."
        ));

        enemyInformationBySymbol.put('G', new EntityInformation("G", "ToraGe", 92, 10, 91,
                "A beautiful Bengal tiger burning eyes, captivating color, and a roar..."
                        + "so powerful that just getting close to it can kill you."
        ));

        enemyInformationBySymbol.put('H', new EntityInformation("H", "Hydra", 100, 10, 100,
                "Hydra used to be a nymph from the Kingdom of Aqua. "
                        + "Not much is known about her motivations, "
                        + "but her water attack is one of the strongest in the entire Kingdom of Celestia. "
                        + "However, she is a bit weak-minded, almost as fluid as her creation."
        ));

        enemyInformationBySymbol.put('M', new EntityInformation("M", "MaKinda", 1, 10, 95,
                "Ma-KinDa is the name given to this enormous rock by local farmers. "
                        + "It is very steep, and pieces of it fall off if you try to climb it. "
                        + "...Traveler! Watch your step."
        ));

        enemyInformationBySymbol.put('N', new EntityInformation("N", "Ana", 100, 10, 100,
                "Oh, for Celestial's sake... You wanted to hurt a mortal? "
                        + "Just run away! If you hit her, you'll be a ruthless adventurer."
        ));

        enemyInformationBySymbol.put('O', new EntityInformation("O", "PrayPrey", 95, 10, 95,
                "This Bishop is responsible for eliminating all adventurers who cross his territory. "
                        + "...Oh no. He's coming with a torch... "
                        + "...Watch out!"
        ));

        enemyInformationBySymbol.put('S', new EntityInformation("S", "Savepoint", 0, 0, 0,
                "Oh, my goodness... Look what you've done!"
        ));

        enemyInformationBySymbol.put('T', new EntityInformation("T", "MashiTa", 35, 10, 49,
                "You've found a wandering spirit! Eh... wait..."
        ));

        enemyInformationBySymbol.put('K', new EntityInformation("K", "A-Ka", 95, 10, 91,
                "Uh, be very careful with these mosquitoes, "
                        + "because everyone knows that when they bite you, "
                        + "the disease is so painful that it can kill you on the first few bites."
        ));

        enemyInformationBySymbol.put('W', new EntityInformation("W", "WaterFlowbar", 90, 10, 90,
                "If you find a flowbar... running to the shore in a lagoon, you must try. "
                        + "Before its attack drowns you, the nymphs wet you, and your walk stops."
        ));

        enemyInformationBySymbol.put('Y', new EntityInformation("Y", "BiYah", 10, 10, 20,
                "By Atlas himself! You have just discovered Bi-Yah. "
                        + "Talk to him about his seven wives and you may be spared his 'bommbastic' attack."
        ));

        enemyInformationBySymbol.put('Z', new EntityInformation("Z", "Acerco", 1, 10, 1,
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

        for (EntityInformation entity : enemyInformationBySymbol.values()) {
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
