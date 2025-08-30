package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Savepoint extends Enemy {
    public Savepoint(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'S';
    }
}

