package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class Hydra extends Enemy {
    public Hydra(int positionX, int positionY, int baseHealth, int mentalHealth, int attack_damage) {
        super(positionX, positionY, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
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
        "\n"+
        "Just don't hurt Flowby.";
    }
    @Override
    public String getForgiveMessage() {
        return "Hydra: I didn't need your forgiveness anyway!";
    }
}
