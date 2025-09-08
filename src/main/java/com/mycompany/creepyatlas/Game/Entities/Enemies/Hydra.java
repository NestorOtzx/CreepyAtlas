package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Hydra extends Enemy {
    public Hydra(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'H';
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/hydra_mono.wav";
    }
}
