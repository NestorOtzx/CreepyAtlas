package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class BiYah extends Enemy {
    public BiYah(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'Y';
    }
    @Override
    public String getBaseAudioPath(){
        return "/audios/bi_yah_mono.wav";
    }
}
