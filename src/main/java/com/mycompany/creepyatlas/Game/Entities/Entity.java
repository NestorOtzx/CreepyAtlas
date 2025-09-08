package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Game.*;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.Direction;

public abstract class Entity{
    protected int x;
    protected int y;
    protected AudioSource3D audiosource;
    protected int health = 100;
    protected int attack_damage = 10;
    protected boolean is_dead = false;

    public Entity(int x, int y, int baseHealth, int attack_damage){
        this.x = x;
        this.y = y;
        try {
            this.audiosource = new AudioSource3D(this.getBaseAudioPath(), true, x, y);
            this.audiosource.play();
            System.out.println("playing: "+ getBaseAudioPath() + " in: "+getSymbol());
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.is_dead = false;
        this.attack_damage = attack_damage;
        this.health = baseHealth;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    

    public void move(int dx, int dy) {
        if (is_dead) { return; }
        

        int newX = this.x + dx;
        int newY = this.y + dy;

        if (newY < 0 || newY >= Game.getBaseMap().length || newX < 0 || newX >= Game.getBaseMap()[0].length) {
            System.out.println(getSymbol()+": cannot move outside the map!");
            return;
        }

        char target = Game.getBaseMap()[newY][newX];
        if (target == '|' || target == '-' || target == '#') {
            System.out.println(getSymbol()+": There is a wall in that direction!");
            return;
        }
        
        x += dx;
        y += dy;

        if (this.audiosource != null)
        {
            this.audiosource.setPosition(x, y);
        }
    }

    public void move(Direction direction)
    {
        if (is_dead) { return; }
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case LEFT:  dx = -1; break;
            case RIGHT: dx =  1; break;
            case UP:    dy = -1; break;
            case DOWN:  dy =  1; break;
            default:    break;
        }
        move(dx, dy);
    }

    public char getSymbol()
    {
        if (is_dead)
        {
            return 'X';
        }else{
            return '.';
        }
    }

    public String getBaseAudioPath(){
        return "";
    }

    public float getHealth(){
        return health;
    }

    public void Attack(int x, int y, char target)
    {
        if (is_dead) { return; }
        System.out.println("I " + getSymbol() + " Attack "+x + ", "+ y+ " to: "+ target);
    }

    public void RecieveAttack(Entity attacker, int damage)
    {
        if (is_dead) { return; }
        System.out.println("I" + getSymbol() + " Recieve attack from " + attacker.getSymbol() + " amount:"+damage);
        health -= damage;
        if (health <= 0){
            OnDie();
        }
        //puedes agregar aqui un audio de recibir daño
    }

    protected void OnDie(){
        if (is_dead) { return; }
        health = 0;
        is_dead=true;
        if (audiosource != null)
        {
            audiosource.Disable();
        }
    }

    public boolean getIsDead(){
        return is_dead;
    }

    public void OnUpdateGame()
    {

    }
    
}
