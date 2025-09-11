package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Game.*;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.Direction;

public abstract class Entity{
    protected int positionX;
    protected int positionY;
    protected int initialPositionX;
    protected int initialPositionY;
    protected AudioSource3D audioSource;
    protected int health = 100;
    protected int mentalHealth = 100;
    protected int attackDamage = 10;
    protected boolean isDead = false;
    protected boolean isForgiven = false;

    public Entity(int positionX, int positionY, int baseHealth, int mentalHealth, int attackDamage){
        this.positionX = positionX;
        this.positionY = positionY;
        initAudio();
        this.isDead = false;
        this.attackDamage = attackDamage;
        this.health = baseHealth;
        this.mentalHealth = mentalHealth;
        this.initialPositionX = positionX;
        this.initialPositionY = positionY;
    }

    
    protected void initAudio(){
        try {
            this.audioSource = new AudioSource3D(this.getBaseAudioPath(), true, positionX, positionY);
            this.audioSource.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPositionX() { return positionX; }
    public int getPositionY() { return positionY; }

    public int getInitialX()
    {
        return initialPositionX;
    }

    public int getInitialY()
    {
        return initialPositionY;
    }
    

    public void move(int directionX, int directionY) {
        if (isDead) { return; }

        int newX = this.positionX + directionX;
        int newY = this.positionY + directionY;

        if (newY < 0 || newY >= Game.getBaseMapLayer().length || newX < 0 || newX >= Game.getBaseMapLayer()[0].length) {
            System.out.println(getSymbol()+": cannot move outside the map!");
            return;
        }

        char target = Game.getBaseMapLayer()[newY][newX];
        if (target == '|' || target == '-' || target == '#') {
            System.out.println(getSymbol()+": There is a wall in that direction!");
            return;
        }
        
        positionX += directionX;
        positionY += directionY;

        if (this.audioSource != null)
        {
            this.audioSource.setPosition(positionX, positionY);
        }
    }

    public void translate(int positionX, int positionY)
    {
        int newX = positionX;
        int newY = positionY;

        if (newY < 0 || newY >= Game.getBaseMapLayer().length || newX < 0 || newX >= Game.getBaseMapLayer()[0].length) {
            System.out.println(getSymbol()+": cannot translate outside the map!");
            return;
        }

        char target = Game.getBaseMapLayer()[newY][newX];
        if (target == '|' || target == '-' || target == '#') {
            System.out.println(getSymbol()+": There is a wall in that place!");
            return;
        }

        if (this.audioSource != null)
        {
            this.audioSource.setPosition(positionX, positionY);
        }
        this.positionX = newX;
        this.positionY = newY;
    }

    public void move(Direction direction)
    {
        if (isDead) { return; }
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
        if (isDead)
        {
            return 'X';
        }else{
            return '.';
        }
    }

    public String getBaseAudioPath(){
        return "";
    }

    public int getHealth(){
        return health;
    }

    public int getMentalHealth(){
        return mentalHealth;
    }

    public void attackTarget(int x, int y, char target)
    {
        if (isDead || isForgiven) { return; }
        System.out.println(getSymbol() + " Attack "+x + ", "+ y+ " to: "+ target);
    }

    public void recieveAttack(Entity attacker, int damage)
    {
        if (isDead || isForgiven) { return; }
        System.out.println(getSymbol() + " Recieve attack from " + attacker.getSymbol() + " amount:"+damage);
        health -= damage;
        if (health <= 0){
            onDie();
        }
    }

    public void recieveForgiveness(Entity forgiver, int forgiveness)
    {
        if (isDead || isForgiven) { return; }
        mentalHealth -= forgiveness;
        if (mentalHealth <= 0)
        {
            onBeForgiven();
        }
    }

    protected void onDie(){
        if (isDead || isForgiven) { return; }
        health = 0;
        isDead=true;
        if (audioSource != null)
        {
            audioSource.disable();
            audioSource = null;
        }
    }

    protected void onBeForgiven(){
        if (isDead || isForgiven) { return; }
        mentalHealth = 0;
        isForgiven = true;

    }

    public boolean getIsDead(){
        return isDead;
    }

    public boolean getIsForgiven()
    {
        return isForgiven;
    }

    public void onUpdateGame()
    {

    }
}
