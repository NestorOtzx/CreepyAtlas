package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class WaterFlowbar extends Enemy {
    public WaterFlowbar(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'F';
    }
}

