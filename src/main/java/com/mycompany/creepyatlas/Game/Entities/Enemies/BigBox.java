package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class BigBox extends Enemy {
    public BigBox(int x, int y, int baseHealth, int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'B';
        }
        return super.getSymbol();
    }
   @Override
    public String getBaseAudioPath(){
        return "/audios/big_boss_mono.wav";
    }


    @Override
    public String getDefeatMessage() {
        return "BigBox: ..."+
        "\n"+
        "..."+
        "\n"+
        "It was just a cardboard box";
    }
    @Override
    public String getForgiveMessage() {
        return "BigBox: ...";
    }
}
