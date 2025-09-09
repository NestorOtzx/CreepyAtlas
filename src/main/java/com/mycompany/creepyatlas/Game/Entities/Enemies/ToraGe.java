package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class ToraGe extends Enemy {
    public ToraGe(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'G';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/tora_ge_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "Oh my gosh, it's like a giant kitten." +
                "/n" + 
               "Scratch its belly a little for me.";
    }
    @Override
    public String getForgiveMessage() {
        return "Tora is very grateful." + 
        "/n" + 
        "He can finally go home with his tigress.";
    }
}

