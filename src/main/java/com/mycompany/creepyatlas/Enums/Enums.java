package com.mycompany.creepyatlas.Enums;

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
        GAME_OVER,
        SCENE_PROLOG_1,
        SCENE_PROLOG_2,
        SCENE_PROLOG_3,
        END_SCREEN_NEUTRAL_1,
        END_SCREEN_NEUTRAL_2,
        END_SCREEN_NEUTRAL_3,
        END_SCREEN_GENOCIDE_1,
        END_SCREEN_GENOCIDE_2,
        END_SCREEN_GENOCIDE_3,
        END_SCREEN_PACIFIST_1,
        END_SCREEN_PACIFIST_2,
        END_SCREEN_PACIFIST_3,
    }

}