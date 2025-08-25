package com.mycompany.creepyatlas.Game.Entities;

public class Enemy extends Entity {
    public Enemy(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'E';
    }
}
