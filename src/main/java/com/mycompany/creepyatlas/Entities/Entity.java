package com.mycompany.creepyatlas.Entities;

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

    public abstract char getSymbol();
}
