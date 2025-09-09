package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class AKa extends Enemy {
    public AKa(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        return 'K';
    }
    @Override
    public String getBaseAudioPath(){
        return "/audios/a_ka_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "AKa: Bzzz..."+
                "/n"+
                "Mmmm, looks like someone won't be able to sleep well tonight."
                ;
    }
    @Override
    public String getForgiveMessage() {
        return "AKa: BZZZ!" +
                "/n" + 
                "WHAT AN UNGRATEFUL CREATURE!";
    }

    


}

