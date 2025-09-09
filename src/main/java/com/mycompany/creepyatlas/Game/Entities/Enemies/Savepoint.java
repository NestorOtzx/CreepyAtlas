package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Savepoint extends Enemy {
    public Savepoint(int x, int y, int baseHealth,int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth,attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'S';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/savepoint.wav";
    }

}

