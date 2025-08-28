package com.mycompany.creepyatlas.Enums;

public class Enums {

    public enum CommandType {
        MOVE,
        NOISE,
        QUIT,
        UNKNOWN
    }

    public enum Direction {
        UP, DOWN, LEFT, RIGHT, NONE
    }

    public enum NoiseType {
        BURP, SCREAM, UNKNOWN
    }

    public enum ScreenState {
        BASE,
        STATS,
        MOVE_COMMANDS,
        NOISE_COMMANDS,
    }

}
