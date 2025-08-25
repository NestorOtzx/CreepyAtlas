package com.mycompany.creepyatlas;

public class App {
    public static void main(String[] args) throws Exception {
        AudioPlayer3D audio = new AudioPlayer3D();
        audio.init();

        audio.playSoundAt("/plankton_mono.wav", -10.0f, 0.0f, 0.0f);

        audio.playSoundAt("/plankton_mono.wav", 10.0f, 0.0f, 0.0f);

        audio.cleanup();
        System.out.println("Finished.");
    }
}
