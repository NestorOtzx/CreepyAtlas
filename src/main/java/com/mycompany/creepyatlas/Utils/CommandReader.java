package com.mycompany.creepyatlas.Utils;

import com.mycompany.creepyatlas.Enums.Enums.*;

import java.util.*;

public class CommandReader {
    private static final Scanner scanner = new Scanner(System.in);

    public static ConsoleCommand readCommand() {
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim().toLowerCase();

            input = input.replaceAll("\\s+", " ");

            String[] parts = input.split(" ");
            if (parts.length == 0) {
                System.out.println("Command not understood. Try again.");
                continue;
            }

            String main = parts[0];

            if (main.equals("move")) {
                Direction dir = Direction.NONE;
                if (parts.length > 1) {
                    dir = parseDirection(parts[1]);
                    if (dir == Direction.NONE) {
                        System.out.println("Unknown direction. Use: up, down, left, right.");
                        continue;
                    }
                }
                return new ConsoleCommand(CommandType.MOVE, dir, NoiseType.UNKNOWN);

            } else if (main.equals("noise")) {
                if (parts.length < 2) {
                    System.out.println("You must specify a noise (example: noise burp).");
                    continue;
                }
                NoiseType noise = parseNoise(parts[1]);
                if (noise == NoiseType.UNKNOWN) {
                    System.out.println("Unknown noise. Use: burp, scream, etc.");
                    continue;
                }
                return new ConsoleCommand(CommandType.NOISE, Direction.NONE, noise);

            } else {
                System.out.println("Unknown command. Try again.");
            }
        }
    }

    private static Direction parseDirection(String word) {
        switch (word) {
            case "up": return Direction.UP;
            case "down": return Direction.DOWN;
            case "left": return Direction.LEFT;
            case "right": return Direction.RIGHT;
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
