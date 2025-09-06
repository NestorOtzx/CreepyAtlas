package com.mycompany.creepyatlas.Game.Entities;

public class Enemy extends Entity {
    public Enemy(int x, int y, int baseHealth, int attack_damage ) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'E';
        }
        return super.getSymbol();
    }
}
