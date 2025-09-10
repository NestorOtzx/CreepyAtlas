package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class PrayPrey extends Enemy {
    public PrayPrey(int positionX, int positionY, int baseHealth, int mentalHealth, int attack_damage) {
        super(positionX, positionY, baseHealth, mentalHealth,attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
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
