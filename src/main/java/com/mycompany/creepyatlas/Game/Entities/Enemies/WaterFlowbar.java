package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class WaterFlowbar extends Enemy {
    public WaterFlowbar(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
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
    public String getDefeatMessagge() {
        return "WaterFlowbar: glup...";
    }
    @Override
    public String getForgiveMessagge() {
        return "WaterFlowbar: glup, glup! ^u^";
    }
}

