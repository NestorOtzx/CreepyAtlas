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
            String input = scanner.nextLine().trim().toLowerCase();

            input = input.replaceAll("\\s+", " ");

            String[] parts = input.split(" ");
            if (parts.length == 0) {
                System.out.println("Command not understood. Try again.");
                continue;
            }

            String main = parts[0];
            if (main.equals("quit")){
                Game.SetInGame(false);
                System.out.println("Good bye.");
                break;
            }
            else if (Game.getPlayer().getIsDead()){
                if (main.equals("ok") || main.equals("continue"))
                {
                    System.out.println("...");
                    Game.RevivePlayer();
                    break;
                }else{
                    System.out.println("You are dead, write 'continue' to continue.");
                }
            }
            else {
                
                if (main.equals("move")) {
                    Direction dir = Direction.NONE;
                    if (parts.length > 1) {
                        dir = parseDirection(parts[1]);
                    }

                    if (dir == Direction.NONE)
                    {
                        Screen.setState(ScreenState.MOVE_COMMANDS);
                    }else{
                        Game.getPlayer().move(dir);
                    }
                    
                } else if (main.equals("ok"))
                {
                    System.out.println("...");
                } else if (main.equals("noise")) {
                    if (parts.length < 2) {
                        System.out.println("Use: burp, scream.");
                        continue;
                    }
                    NoiseType noise = parseNoise(parts[1]);
                    if (noise == NoiseType.UNKNOWN)
                    {
                        Screen.setState(ScreenState.NOISE_COMMANDS);
                    }else{
                        Screen.setState(ScreenState.BASE);
                    }
                } else if (main.equals("attack")){
                    try {
                        int x = Game.getPlayer().getX();
                        int y = Game.getPlayer().getY();
                        AudioSource3D attackSound = new AudioSource3D("/audios/attack.wav", false, x, y);
                        attackSound.play();
                        if (parts.length < 2) {
                            System.out.println("Who do you want to attack? ej: attack A.");
                            continue;
                        }
                        Game.getPlayer().Attack(x, y, parts[1].toUpperCase().toCharArray()[0]);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (main.equals("eat")){
                    try {
                    AudioSource3D eatSound = new AudioSource3D("/audios/eat.wav", false, Game.getPlayer().getX(), Game.getPlayer().getY());
                    eatSound.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
    
                } else if (main.equals("rest")){
                    try {
                    AudioSource3D restSound = new AudioSource3D("/audios/rest.wav", false, Game.getPlayer().getX(), Game.getPlayer().getY());
                    restSound.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (main.equals("bestiary")){
                    try {
                        AudioSource3D bestiarySound = new AudioSource3D("/audios/bestiary.wav", false, Game.getPlayer().getX(), Game.getPlayer().getY());
                        bestiarySound.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    com.mycompany.creepyatlas.Game.Data.Bestiary.printBestiary();
                }
                else{
                    System.out.println("Unknown command. Try again.");
                }
                break;
            }
        }
    }

    private static Direction parseDirection(String word) {
        if (word == null || word.isEmpty()) {
            return Direction.NONE;
        }

        switch (Character.toLowerCase(word.charAt(0))) {
            case 'u': return Direction.UP;
            case 'd': return Direction.DOWN;
            case 'l': return Direction.LEFT;
            case 'r': return Direction.RIGHT;
            default:  return Direction.NONE;
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
