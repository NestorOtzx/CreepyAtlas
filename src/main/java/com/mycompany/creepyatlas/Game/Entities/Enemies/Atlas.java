package com.mycompany.creepyatlas.Game.Entities.Enemies;
import java.util.List;

import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Entities.Enemy;
import com.mycompany.creepyatlas.Utils.Dijkstra;

public class Atlas extends Enemy {

    public Atlas(int positionX, int positionY, int baseHealth, int mentalHealth, int attack_damage) {
        super(positionX, positionY, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public void move(int dx, int dy)
    {
        super.move(dx, dy);
        Game.clearFog(this.positionX, this.positionY);
        int playerx = Game.getPlayer().getPositionX();
        int playery = Game.getPlayer().getPositionY();
        if (playerx == this.positionX && playery == this.positionY){
            Game.enemyAttacksPosition(this, positionX, positionY, Game.getPlayer().getSymbol(), attackDamage);
        }
    }

    @Override
    public void translate(int x, int y)
    {
        super.translate(x, y);
        Game.clearFog(this.positionX, this.positionY);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
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
    public void onUpdateGame()
    {
        if (Game.getPlayer().getIsDead()) { return; }
        
        int playerx = Game.getPlayer().getPositionX();
        int playery = Game.getPlayer().getPositionY();
        if (playerx == this.positionX && playery == this.positionY){
            Game.enemyAttacksPosition(this, positionX, positionY, Game.getPlayer().getSymbol(), attackDamage);
        }
             

        int timesPlayerMoved = Game.getPlayer().getTimesPlayerMoved();
        if (timesPlayerMoved % 2 != 0)
        {
            List<int[]> path = Dijkstra.findPath(Game.getBaseMapLayer(), positionX, positionY, Game.getPlayer().getPositionX(), Game.getPlayer().getPositionY());
            
            if (path != null && path.size() >= 2 && path.size() < 5)
            {
                int directionX = path.get(1)[1]-positionX;
                int directionY = path.get(1)[0]-positionY;
                move(directionX, directionY);
            }
            tryTeleportToPlayer();
        }
    }

    private void tryTeleportToPlayer()
    {
        List<int[]> path = Dijkstra.findPath(Game.getBaseMapLayer(), positionX, positionY, Game.getPlayer().getPositionX(), Game.getPlayer().getPositionY());
            
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
    public void onDie(){
        System.out.println("Atlas: Guau...");
    }

    @Override
    public void onPlayerRespawn(){
        tryTeleportToPlayer();
    }
}
