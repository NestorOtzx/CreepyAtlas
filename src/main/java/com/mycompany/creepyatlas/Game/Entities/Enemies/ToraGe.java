package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class ToraGe extends Enemy {
    public ToraGe(int positionX, int positionY, int baseHealth, int mentalHealth, int attack_damage) {
        super(positionX, positionY, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
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
                "\n" + 
               "Scratch its belly a little for me.";
    }
    @Override
    public String getForgiveMessage() {
        return "Tora is very grateful." + 
        "\n" + 
        "He can finally go home with his tigress.";
    }
}

