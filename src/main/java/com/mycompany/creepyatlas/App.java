package com.mycompany.creepyatlas;

public class App {
    public static void main(String[] args) throws Exception {
        AudioListener3D.initOpenAL();

        Game game = new Game();
        game.start();

        AudioListener3D.cleanupOpenAL();
    }
}
