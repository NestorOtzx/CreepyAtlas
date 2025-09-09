package com.mycompany.creepyatlas.Game.Entities;

import javax.swing.DebugGraphics;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Screen;

public class Player extends Entity {
    Direction faceDirection;

    public Player(int x, int y, int baseHealth,int mental_health, int attack_damage) {
        super(x, y, baseHealth, mental_health, attack_damage);
        AudioListener3D.setPosition(x, y);
        faceDirection = Direction.DOWN;
    }

    @Override
    public char getSymbol() {
        return 'P';
    }

    @Override
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

        int newX = this.x + dx;
        int newY = this.y + dy;

        if (newY < 0 || newY >= Game.getBaseMap().length || newX < 0 || newX >= Game.getBaseMap()[0].length) {
            System.out.println("You cannot move outside the map!");
            return;
        }

        char target = Game.getBaseMap()[newY][newX];
        
        if (target == '|' || target == '-' || target == '#') {
            System.out.println("There is a wall in that direction!");
            try {
                AudioSource3D wallSound = new AudioSource3D("/audios/footsteps_and_wall.wav", false, this.x, this.y);
                wallSound.play();
                } catch (Exception e) {
                    System.out.println("error");
                }
            return;
        } else if (target == '$')
        {
            Game.EndGame();
        }
        else{
            try {
                AudioSource3D stepSound = new AudioSource3D("/audios/footsteps.wav", false, this.x, this.y);
                stepSound.play();
                } catch (Exception e) {
                    System.out.println("error");
                }      
        }

        move(dx, dy);
        AudioListener3D.setPosition(x, y);
        System.out.println("character: "+ Game.getEnemyLayer()[y][x]);

        Game.ClearFog(x, y);
        

    }

    @Override
    public String getBaseAudioPath()
    {
        return "";
    }

    @Override
    public void Attack(int x, int y, char target)
    {
        if (is_dead) { return; }
        super.Attack(x, y, target);
        Game.PlayerAttacksPosition(x, y, target, this.attack_damage);
    }

    @Override
    public void RecieveAttack(Entity attacker, int damage)
    {
        if (is_dead) { return; }
        super.RecieveAttack(attacker, damage);
    }

    @Override
    protected void OnDie(){
        System.out.println("Player die");
        if (is_dead) { return; }
        Screen.setState(ScreenState.GAME_OVER);
        super.OnDie();
        AudioListener3D.DisabelAllAudios();
    }


    public Direction getFaceDirection()
    {
        return faceDirection;
    }
    
}
