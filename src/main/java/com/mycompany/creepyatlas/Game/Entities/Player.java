package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Screen;

public class Player extends Entity {
    public Player(int x, int y) {
        super(x, y);
        AudioListener3D.setPosition(x, y);
    }

    @Override
    public char getSymbol() {
        return 'P';
    }

    @Override
    public void move(Direction direction)
    {
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
}
