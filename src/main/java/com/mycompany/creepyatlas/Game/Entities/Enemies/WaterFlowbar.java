package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class WaterFlowbar extends Enemy {
    public WaterFlowbar(int positionX, int positionY, int baseHealth, int mentalHealth, int attack_damage) {
        super(positionX, positionY, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
        {
            return 'F';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/water_flowbar_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "WaterFlowbar: glup...";
    }
    @Override
    public String getForgiveMessage() {
        return "WaterFlowbar: glup, glup! ^u^";
    }
}

