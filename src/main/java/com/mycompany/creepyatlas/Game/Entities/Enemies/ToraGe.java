package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class ToraGe extends Enemy {
    public ToraGe(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'G';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/tora_ge_mono.wav";
    }
}

