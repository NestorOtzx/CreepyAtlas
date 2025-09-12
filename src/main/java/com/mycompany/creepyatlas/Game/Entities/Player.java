package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Screen;
import com.mycompany.creepyatlas.Utils.DirectionUtils;

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
        int [] dirs = DirectionUtils.getDelta(direction);

        int newX = this.positionX + dirs[0];
        int newY = this.positionY + dirs[1];

        if (newY < 0 || newY >= Game.getBaseMapLayer().length || newX < 0 || newX >= Game.getBaseMapLayer()[0].length) {
            System.out.println("You cannot move outside the map!");
            return;
        }

        char target = Game.getBaseMapLayer()[newY][newX];
        
        if (target == '|' || target == '-' || target == '#') {
            System.out.println("There is a wall in that direction!");
            try {
                AudioSource3D wallSound = new AudioSource3D("/audios/footsteps_and_wall.wav", false, this.positionX, this.positionY, AudioEffectType.REVERB);
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
                AudioSource3D stepSound = new AudioSource3D("/audios/footsteps.wav", false, this.positionX, this.positionY, AudioEffectType.REVERB);
                stepSound.play();
            } catch (Exception e) {
                System.out.println("error");
            }      
        }

        move(dirs[0], dirs[1]);
        AudioListener3D.setPosition(positionX, positionY);

        Game.clearFog(positionX, positionY);
    }

    @Override
    public void translate(int x, int y) {
        super.translate(x, y);
        Game.clearFog(x, y);
        AudioListener3D.setPosition(positionX, positionY);
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
