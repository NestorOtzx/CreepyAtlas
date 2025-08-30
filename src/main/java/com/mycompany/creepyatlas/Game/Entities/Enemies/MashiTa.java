package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class MashiTa extends Enemy {
    public MashiTa(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'T';
    }
}
