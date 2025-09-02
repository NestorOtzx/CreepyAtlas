package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Atlas extends Enemy {
    public Atlas(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'A';
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/atlas_mono.wav";
    }
}
