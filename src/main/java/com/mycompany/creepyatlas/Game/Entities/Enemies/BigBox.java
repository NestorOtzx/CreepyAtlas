package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class BigBox extends Enemy {
    public BigBox(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'B';
    }
}
