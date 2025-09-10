package com.mycompany.creepyatlas.Utils;

import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Screen;
import java.util.*;

public class CommandReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static void execCommand() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase().replaceAll("\\s+", " ");
            String[] parts = input.split(" ");
            if (parts.length == 0) {
                System.out.println("Command not understood. Try again.");
                continue;
            }

            String main = parts[0];
            if (main.equals("quit")) {
                quitGame();
                break;
            }
            if (Game.getPlayer().getIsDead()) {
                if (handleDeath(main)) break;
                continue;
            }
            if (isStoryOrEndingState(Screen.getState())) break;

            switch (main) {
                case "move": handleMove(parts); break;
                case "noise": handleNoise(parts); break;
                case "attack": handleAttack(parts); break;
                case "forgive": handleForgive(parts); break;
                case "eat": playSound("/audios/eat.wav"); break;
                case "rest": handleRest(); break;
                case "bestiary": handleBestiary(); break;
                default: System.out.println("Unknown command. Try again.");
            }
            break;
        }
    }

    private static void quitGame() {
        Game.setInGame(false);
        System.out.println("Good bye.");
    }

    private static boolean handleDeath(String main) {
        if (main.equals("ok") || main.equals("continue")) {
            System.out.println("...");
            Game.RevivePlayer();
            return true;
        }
        System.out.println("You are dead, write 'continue' to continue.");
        return false;
    }

    private static boolean isStoryOrEndingState(ScreenState state) {
        return state == ScreenState.SCENE_PROLOG_1 ||
               state == ScreenState.SCENE_PROLOG_2 ||
               state == ScreenState.SCENE_PROLOG_3 ||
               state == ScreenState.END_SCREEN_GENOCIDE_1 ||
               state == ScreenState.END_SCREEN_GENOCIDE_2 ||
               state == ScreenState.END_SCREEN_GENOCIDE_3 ||
               state == ScreenState.END_SCREEN_NEUTRAL_1 ||
               state == ScreenState.END_SCREEN_NEUTRAL_2 ||
               state == ScreenState.END_SCREEN_NEUTRAL_3 ||
               state == ScreenState.END_SCREEN_PACIFIST_1 ||
               state == ScreenState.END_SCREEN_PACIFIST_2 ||
               state == ScreenState.END_SCREEN_PACIFIST_3;
    }

    private static void handleMove(String[] parts) {
        if (Screen.getState() == ScreenState.COMBAT) {
            System.out.println("You can't escape from a combat");
            return;
        }
        Direction dir = parts.length > 1 ? parseDirection(parts[1]) : Direction.NONE;
        if (dir == Direction.NONE) {
            Screen.setState(ScreenState.MOVE_COMMANDS);
        } else {
            Game.getPlayer().move(dir);
        }
    }

    private static void handleNoise(String[] parts) {
        if (parts.length < 2) {
            System.out.println("Use: burp, scream.");
            return;
        }
        NoiseType noise = parseNoise(parts[1]);
        Screen.setState(noise == NoiseType.UNKNOWN ? ScreenState.NOISE_COMMANDS : ScreenState.BASE);
    }

    private static void handleAttack(String[] parts) {
        try {
            playSound("/audios/attack.wav");
            if (parts.length < 2) {
                System.out.println("Who do you want to attack? ej: attack A.");
                return;
            }
            Game.getPlayer().attackTarget(
                Game.getPlayer().getPositionX(),
                Game.getPlayer().getPositionY(),
                parts[1].toUpperCase().charAt(0)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleForgive(String[] parts) {
        try {
            playSound("/audios/forgive.wav");
            if (parts.length < 2) {
                System.out.println("Who do you want to forgive? ej: forgive A.");
                return;
            }
            Game.getPlayer().forgive(
                Game.getPlayer().getPositionX(),
                Game.getPlayer().getPositionY(),
                parts[1].toUpperCase().charAt(0)
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleRest() {
        try {
            playSound("/audios/rest.wav");
            Game.getPlayer().rest();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleBestiary() {
        try {
            playSound("/audios/bestiary.wav");
        } catch (Exception e) {
            e.printStackTrace();
        }
        com.mycompany.creepyatlas.Game.Data.Bestiary.printBestiary();
    }

    private static void playSound(String path) {
        try {
            AudioSource3D sound = new AudioSource3D(path, false, Game.getPlayer().getPositionX(), Game.getPlayer().getPositionY());
            sound.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Direction parseDirection(String word) {
        if (word == null || word.isEmpty()) return Direction.NONE;
        switch (Character.toLowerCase(word.charAt(0))) {
            case 'u': return Direction.UP;
            case 'd': return Direction.DOWN;
            case 'l': return Direction.LEFT;
            case 'r': return Direction.RIGHT;
            default: return Direction.NONE;
        }
    }

    private static NoiseType parseNoise(String word) {
        switch (word) {
            case "burp": return NoiseType.BURP;
            case "scream": return NoiseType.SCREAM;
            default: return NoiseType.UNKNOWN;
        }
    }
}
