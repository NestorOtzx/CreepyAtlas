package com.mycompany.creepyatlas.Enums;

import com.mycompany.creepyatlas.Game.Data.Bestiary;

public class Enums {

    public enum CommandType {
        MOVE,
        NOISE,
        ATTACK,
        EAT,
        REST,
        BESTIARY,
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
        COMBAT,
    }

}
