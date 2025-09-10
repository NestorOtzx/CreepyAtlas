package com.mycompany.creepyatlas.Game.Entities.Enemies;
import java.util.List;

import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Entities.Enemy;
import com.mycompany.creepyatlas.Utils.Dijkstra;

public class Atlas extends Enemy {

    public Atlas(int x, int y, int baseHealth, int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public void move(int dx, int dy)
    {
        super.move(dx, dy);
        Game.ClearFogSingle(this.x, this.y);
        int playerx = Game.getPlayer().getX();
        int playery = Game.getPlayer().getY();
        if (playerx == this.x && playery == this.y){
            Game.EnemyAttacksPosition(this, x, y, Game.getPlayer().getSymbol(), attack_damage);
        }
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
        if (Game.getPlayer().getIsDead()) { return; }
        
        int playerx = Game.getPlayer().getX();
        int playery = Game.getPlayer().getY();
        if (playerx == this.x && playery == this.y){
            Game.EnemyAttacksPosition(this, x, y, Game.getPlayer().getSymbol(), attack_damage);
        }
             

        int timesPlayerMoved = Game.getPlayer().GetTimesPlayerMoved();
        if (timesPlayerMoved % 3 != 0)
        {
            List<int[]> path = Dijkstra.findPath(Game.getBaseMap(), x, y, Game.getPlayer().getX(), Game.getPlayer().getY());
            
            if (path != null && path.size() >= 2 && path.size() < 5)
            {
                int directionX = path.get(1)[1]-x;
                int directionY = path.get(1)[0]-y;
                move(directionX, directionY);
            }
            TryTeleportToPlayer();
        }
    }

    private void TryTeleportToPlayer()
    {
        List<int[]> path = Dijkstra.findPath(Game.getBaseMap(), x, y, Game.getPlayer().getX(), Game.getPlayer().getY());
            
        if (path.size() >= 4)
        {
            translate(path.get(path.size()-4)[1], path.get(path.size()-4)[0]);
        }
    }


    @Override
    public String getDefeatMessage() {
        return "Atlas: Huh... Do you think you can defeat me?..."+
        "\n"+ 
        "..."+
        "\n"+
        "Uhu, it seems you were right.";
    }
    @Override
    public String getForgiveMessage() {
        return "Atlas: sniff* ";
    }

    @Override
    public void OnDie(){
        System.out.println("Atlas: Guau...");
    }

    @Override
    public void OnPlayerRespawn(){
        TryTeleportToPlayer();
    }
}
