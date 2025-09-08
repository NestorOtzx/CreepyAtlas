package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Chubby extends Enemy {
    public Chubby(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'C';
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/chubby_mono.wav";
    }
}
