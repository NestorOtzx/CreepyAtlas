package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Ana extends Enemy {
    public Ana(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'N';
    }
}
