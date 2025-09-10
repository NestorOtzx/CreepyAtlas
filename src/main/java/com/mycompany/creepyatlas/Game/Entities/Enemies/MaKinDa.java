package com.mycompany.creepyatlas.Game.Entities.Enemies;

import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Entities.Enemy;
import com.mycompany.creepyatlas.Game.Entities.Savepoint;

public class MaKinDa extends Enemy {
    final int PLAYER_REWARD_Y = 2;
    final int PLAYER_REWARD_X = 8;

    public MaKinDa(int positionX, int positionY, int baseHealth,int mentalHealth, int attack_damage) {
        super(positionX, positionY, baseHealth, mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead)
        {
            return 'M';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/makinda_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "has defeated a mountain..."+
        "\n"+
        "it looks like it fell on one. It must not be very important.";
    }
    @Override
    public String getForgiveMessage() {
        return "MaKinDa: Uh, thank you."+
        "\n" +
        "excellent, traveler!" + 
        "\n" + 
        "You made it through MaKinDa's favorite tunnel!";
    }

    @Override 
    public void onBeForgiven()
    {
        super.onBeForgiven();
        Game.getPlayer().translate(PLAYER_REWARD_X, PLAYER_REWARD_Y);
    }
}
