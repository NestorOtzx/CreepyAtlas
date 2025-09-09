package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class MaKinDa extends Enemy {
    public MaKinDa(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
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
    public String getDefeatMessagge() {
        return "has defeated a mountain..."+
        "it looks like it fell on one. It must not be very important.";
    }
    @Override
    public String getForgiveMessagge() {
        return "MaKinDa: Uh, thank you."+
        "/n" +
        "excellent, traveler!" + 
        "/n" + 
        "You made it through MaKinDa's favorite tunnel!";
    }
}
