package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.*;

public class PrayPrey extends Enemy {
    public PrayPrey(int x, int y) {
        super(x, y);
    }

    @Override
    public char getSymbol() {
        return 'O';
    }
}
