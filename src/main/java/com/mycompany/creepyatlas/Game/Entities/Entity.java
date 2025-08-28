package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Game.*;

import com.mycompany.creepyatlas.Enums.Enums.Direction;

public abstract class Entity {
    protected int x;
    protected int y;

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    public void move(Direction direction)
    {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case LEFT:  dx = -1; break;
            case RIGHT: dx =  1; break;
            case UP:    dy = -1; break;
            case DOWN:  dy =  1; break;
            default:    break;
        }

        int newX = this.x + dx;
        int newY = this.y + dy;

        if (newY < 0 || newY >= Game.getBaseMap().length || newX < 0 || newX >= Game.getBaseMap()[0].length) {
            System.out.println("You cannot move outside the map!");
            return;
        }

        char target = Game.getBaseMap()[newY][newX];
        if (target == '|' || target == '-' || target == '#') {
            System.out.println("There is a wall in that direction!");
            return;
        }

        move(dx, dy);
    }

    public abstract char getSymbol();
}
