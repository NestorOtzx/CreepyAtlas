package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Ana extends Enemy {
    public Ana(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        return 'N';

    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/ana_mono.wav";
    }
}
