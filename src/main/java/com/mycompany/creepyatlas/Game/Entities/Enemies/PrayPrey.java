package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class PrayPrey extends Enemy {
    public PrayPrey(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'O';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/pray_prey_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "WIIIIIIIIIIITCH!!!";
    }
    @Override
    public String getForgiveMessage() {
        return "PrayPrey: Do you have a few minutes to talk about God?";
    }
}
