package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class Ana extends Enemy {
    public Ana(int x, int y, int baseHealth, int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        return 'N';

    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/ana_mono.wav";
    }


    @Override
    public String getDefeatMessage() {
        return "Oh, for Celestia's sake. You're so mean, you don't deserve to keep playing."
                ;
    //PIERDE
    }
    @Override
    public String getForgiveMessage() {
        return "Ana: I was so scared!"+
                "/n" + 
                "Ana: Mmmm... I think you're kinda cute.";
    }
}
