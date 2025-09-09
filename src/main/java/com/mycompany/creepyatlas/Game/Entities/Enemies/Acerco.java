package com.mycompany.creepyatlas.Game.Entities.Enemies;
import java.security.PublicKey;

import com.mycompany.creepyatlas.Game.Entities.*;

public class Acerco extends Enemy {
    public Acerco(int x, int y, int baseHealth, int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        return 'Z';
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/acerco_mono.wav";
    }
    @Override
    public String getDefeatMessage() {
        return "Acerco: Dude, I couldn't believe it... she was like..." +
            "\n" + 
            "Acerco: And I couldn't... Parkour! Bro, it was crazy [...]";
    }
    @Override
    public String getForgiveMessage() {
        return "Acerco: Hey, buddy. Where are you going?";
    }

}

