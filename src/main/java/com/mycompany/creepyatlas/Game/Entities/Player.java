package com.mycompany.creepyatlas.Game.Entities;

import javax.swing.DebugGraphics;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Screen;

public class Player extends Entity {
    Direction faceDirection;
    int currentSavePoint;
    int timesPlayerMoved;
    int mentalDamage;

    public Player(int x, int y, int baseHealth,int mental_health, int attack_damage, int mental_damage) {
        super(x, y, baseHealth, mental_health, attack_damage);
        AudioListener3D.setPosition(x, y);
        faceDirection = Direction.DOWN;
        currentSavePoint = -1;
        timesPlayerMoved =0;
        mentalDamage = mental_damage;
    }

    @Override
    public char getSymbol() {
        return 'P';
    }

    public int getTimesPlayerMoved(){
        return timesPlayerMoved;
    }

    @Override
    public void move(Direction direction)
    {
        if (isDead) { return; }
        timesPlayerMoved++;
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case LEFT:  dx = -1; break;
            case RIGHT: dx =  1; break;
            case UP:    dy = -1; break;
            case DOWN:  dy =  1; break;
            default:    break;
        }

        int newX = this.positionX + dx;
        int newY = this.positionY + dy;

        if (newY < 0 || newY >= Game.getBaseMapLayer().length || newX < 0 || newX >= Game.getBaseMapLayer()[0].length) {
            System.out.println("You cannot move outside the map!");
            return;
        }

        char target = Game.getBaseMapLayer()[newY][newX];
        
        if (target == '|' || target == '-' || target == '#') {
            System.out.println("There is a wall in that direction!");
            try {
                AudioSource3D wallSound = new AudioSource3D("/audios/footsteps_and_wall.wav", false, this.positionX, this.positionY);
                wallSound.play();
                } catch (Exception e) {
                    System.out.println("error");
                }
            return;
        } else if (target == '$')
        {
            Game.endGame();
        }
        else{
            try {
                AudioSource3D stepSound = new AudioSource3D("/audios/footsteps.wav", false, this.positionX, this.positionY);
                stepSound.play();
                } catch (Exception e) {
                    System.out.println("error");
                }      
        }

        move(dx, dy);
        AudioListener3D.setPosition(positionX, positionY);

        Game.clearFog(positionX, positionY);
    }

    @Override
    public void translate(int x, int y) {
        super.translate(x, y);
        Game.clearFog(x, y);
    }

    @Override
    public String getBaseAudioPath()
    {
        return "";
    }

    @Override
    public void attackTarget(int x, int y, char target)
    {
        if (isDead) { return; }
        super.attackTarget(x, y, target);
        Game.playerAttacksPosition(x, y, target, this.attackDamage);
    }

    public void forgive(int x, int y, char target)
    {
        if (isDead) { return; }
        Game.playerForgivesPosition(x, y, target, this.mentalDamage);
    }

    @Override
    public void recieveAttack(Entity attacker, int damage)
    {
        if (isDead) { return; }
        super.recieveAttack(attacker, damage);
    }

    @Override
    protected void onDie(){
        System.out.println("Player die");
        if (isDead) { return; }
        Screen.setState(ScreenState.GAME_OVER);
        super.onDie();
        AudioListener3D.disabelAllAudioSources();
    }

    public Direction getFaceDirection()
    {
        return faceDirection;
    }

    public void setSavePoint(int index)
    {
        currentSavePoint = index;
    }

    public int getSavePoint(){
        return this.currentSavePoint;
    }

    public void rest()
    {
        if (isDead) { return; }
        health = Math.min(health+10, 100);
        timesPlayerMoved++;
        System.out.println("I will take a break and recover my health!");
    }

    public void eat()
    {
        if (isDead) { return; }
        health = Math.min(health+10, 200);
        timesPlayerMoved++;
        System.out.println("I will eat some magic beans!");
    }
}
