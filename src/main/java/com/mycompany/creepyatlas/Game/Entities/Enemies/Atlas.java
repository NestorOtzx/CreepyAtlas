package com.mycompany.creepyatlas.Game.Entities.Enemies;
import java.util.List;

import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Utils.Dijkstra;

public class Atlas extends Enemy {
    private int timesPlayerMoved;

    public Atlas(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'A';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/atlas_mono.wav";
    }

    @Override
    public void OnUpdateGame()
    {
        timesPlayerMoved++;
        if (timesPlayerMoved % 2 == 0)
        {
            List<int[]> path = Dijkstra.findPath(Game.getBaseMap(), x, y, Game.getPlayer().getX(), Game.getPlayer().getY());
            if (path != null) {
                for (int[] pos : path) {
                    System.out.println("Y=" + pos[0] + ", X=" + pos[1]);
                }
            }
            
            if (path.size() > 2)
            {
                int directionX = path.get(1)[1]-x;
                int directionY = path.get(1)[0]-y;
                System.err.println("dirx: "+ directionX + "dirY: "+directionY);
                move(directionX, directionY);
            }
        }

    }
}
