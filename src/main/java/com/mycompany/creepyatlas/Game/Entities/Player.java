package com.mycompany.creepyatlas.Game.Entities;

import javax.swing.DebugGraphics;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Screen;

public class Player extends Entity {
    public Player(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
        AudioListener3D.setPosition(x, y);
    }

    @Override
    public char getSymbol() {
        return 'P';
    }

    @Override
    public void move(Direction direction)
    {
        if (is_dead) { return; }
        super.move(direction);
        AudioListener3D.setPosition(x, y);
        System.out.println("character: "+ Game.getEnemyLayer()[y][x]);
        if (Game.getEnemyLayer()[y][x] != ' ' && Game.getEnemyLayer()[y][x] != '|' && Game.getEnemyLayer()[y][x] != '-' && Game.getEnemyLayer()[y][x] != '#')
        {
            Screen.setState(ScreenState.COMBAT);
        }
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

    
}
