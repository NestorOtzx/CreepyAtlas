package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Acerco extends Enemy {
    public Acerco(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'Z';
    }
}
