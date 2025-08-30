package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class MaKinDa extends Enemy {
    public MaKinDa(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'M';
    }
}
