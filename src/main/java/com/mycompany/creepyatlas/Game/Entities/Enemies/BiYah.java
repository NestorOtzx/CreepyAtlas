package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class BiYah extends Enemy {
    public BiYah(int x, int y, int baseHealth,int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
        {
            return 'Y';
        }
        return super.getSymbol();
    }
    @Override
    public String getBaseAudioPath(){
        return "/audios/bi_yah_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "BiYah couldn't handle the pressure and just blew himself up.";
    }
    @Override
    public String getForgiveMessage() {
        return "One of BiYah's wives appears and drags her husband away by his ears.";
    }
}
