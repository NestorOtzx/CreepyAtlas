package com.mycompany.creepyatlas;

public class App {
    public static void main(String[] args) throws Exception {
        AudioListener3D.initOpenAL();

        AudioSource3D sound = new AudioSource3D("/plankton_mono.wav");
        sound.setPosition(5, 0, 0);
        sound.play();

        for (int i = 0; i < 50 && sound.isPlaying(); i++) {
            AudioListener3D.setPosition(i * 0.1f, 0, 0);
            Thread.sleep(100);
        }

        sound.cleanup();
        AudioListener3D.cleanupOpenAL();
    }
}
