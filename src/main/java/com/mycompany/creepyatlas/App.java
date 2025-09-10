package com.mycompany.creepyatlas;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Game.Game;

public class App {
    public static void main(String[] args) throws Exception {
        AudioListener3D.initOpenAL();

        Game game = new Game();
        game.start();

        AudioListener3D.cleanupOpenAL();
    }
}
