package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class ToraGe extends Enemy {
    public ToraGe(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'G';
    }
}

