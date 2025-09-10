package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Game.*;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.Direction;

public abstract class Entity{
    protected int x;
    protected int y;
    protected int initial_x;
    protected int initial_y;
    protected AudioSource3D audiosource;
    protected int health = 100;
    protected int mental_health = 100;
    protected int attack_damage = 10;
    protected boolean is_dead = false;
    protected boolean is_forgiven = false;

    public Entity(int x, int y, int baseHealth, int mentalHealth, int attack_damage){
        this.x = x;
        this.y = y;
        InitAudio();
        this.is_dead = false;
        this.attack_damage = attack_damage;
        this.health = baseHealth;
        this.mental_health = mentalHealth;
        initial_x = x;
        initial_y = y;
    }

    
    protected void InitAudio(){
        try {
            this.audiosource = new AudioSource3D(this.getBaseAudioPath(), true, x, y);
            this.audiosource.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public int getInitialX()
    {
        return initial_x;
    }

    public int getInitialY()
    {
        return initial_y;
    }
    

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

    public void translate(int x, int y)
    {

        int newX = x;
        int newY = y;

        if (newY < 0 || newY >= Game.getBaseMap().length || newX < 0 || newX >= Game.getBaseMap()[0].length) {
            System.out.println(getSymbol()+": cannot translate outside the map!");
            return;
        }

        char target = Game.getBaseMap()[newY][newX];
        if (target == '|' || target == '-' || target == '#') {
            System.out.println(getSymbol()+": There is a wall in that place!");
            return;
        }
        

        if (this.audiosource != null)
        {
            this.audiosource.setPosition(x, y);
        }
        this.x = newX;
        this.y = newY;
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
        if (is_dead || is_forgiven) { return; }
        System.out.println(getSymbol() + " Attack "+x + ", "+ y+ " to: "+ target);
    }

    public void RecieveAttack(Entity attacker, int damage)
    {
        if (is_dead || is_forgiven) { return; }
        System.out.println(getSymbol() + " Recieve attack from " + attacker.getSymbol() + " amount:"+damage);
        health -= damage;
        if (health <= 0){
            OnDie();
        }
        //puedes agregar aqui un audio de recibir daño
    }

    public void RecieveForgiveness(Entity forgiver, int forgiveness)
    {
        if (is_dead || is_forgiven) { return; }
        mental_health -= forgiveness;
        if (mental_health <= 0)
        {
            OnBeForgiven();
        }
    }

    protected void OnDie(){
        if (is_dead || is_forgiven) { return; }
        health = 0;
        is_dead=true;
        if (audiosource != null)
        {
            audiosource.Disable();
        }
    }

    protected void OnBeForgiven(){
        if (is_dead || is_forgiven) { return; }
        mental_health = 0;
        is_forgiven = true;

    }

    public boolean getIsDead(){
        return is_dead;
    }

    public boolean getIsForgiven()
    {
        return is_forgiven;
    }



    public void OnUpdateGame()
    {

    }
    
}
