package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class Hydra extends Enemy {
    public Hydra(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'H';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/hydra_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "Hydra: ...Please."+
        "/n"+
        "Just don't hurt Flowby.";
    }
    @Override
    public String getForgiveMessage() {
        return "Hydra: I didn't need your forgiveness anyway!";
    }
}
