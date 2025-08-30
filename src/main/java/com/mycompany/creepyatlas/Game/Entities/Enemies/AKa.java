package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class AKa extends Enemy {
    public AKa(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'K';
    }
}

