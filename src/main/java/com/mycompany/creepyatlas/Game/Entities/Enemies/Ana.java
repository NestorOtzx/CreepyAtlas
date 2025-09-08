package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Ana extends Enemy {
    public Ana(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
<<<<<<< HEAD
        return 'N';

    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/ana_mono.wav";
=======
        if (!is_dead)
        {
            return 'N';
        }
        return super.getSymbol();
>>>>>>> 072dec4c57f41a085881d05e3a99d84897114337
    }
}
