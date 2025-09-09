package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class MashiTa extends Enemy {
    public MashiTa(int x, int y, int baseHealth, int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
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
    //Igual muere
    @Override
    public String getForgiveMessage() {
        return "You scared it to death!";
    }
    

}
