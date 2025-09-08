package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class BigBox extends Enemy {
    public BigBox(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'B';
        }
        return super.getSymbol();
    }
   @Override
    public String getBaseAudioPath(){
        return "/audios/big_boss_mono.wav";
    }
}
