package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class MashiTa extends Enemy {
    public MashiTa(int positionX, int positionY, int baseHealth, int mentalHealth, int attack_damage) {
        super(positionX, positionY, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
        {
            return 'T';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/mashi_ta_mono.wav";
    }
    @Override
    public String getDefeatMessage() {
        return "You scared it to death!";
    }
    @Override
    public String getForgiveMessage() {
        return "You scared it to death!";
    }
    

}
