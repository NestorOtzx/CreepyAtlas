package com.mycompany.creepyatlas.Game.Entities.Enemies;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Entities.Enemy;
import com.mycompany.creepyatlas.Utils.Distance;

public class Savepoint extends Enemy {
    public Savepoint(int x, int y, int baseHealth,int mentalHealth, int attack_damage) {
        super(x, y, baseHealth, mentalHealth,attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!is_dead)
        {
            return 'S';
        }
        return super.getSymbol();
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/savepoint.wav";
    }

    @Override
    public void OnUpdateGame()
    {
        int playerx = Game.getPlayer().getX();
        int playery = Game.getPlayer().getY();
        System.out.println("player: "+playerx + playery+ " save: "+ x + y);
        if (playerx == this.x && playery == this.y)
        {
            audiosource.play();
        }
    }

    @Override
    protected void InitAudio()
    {
        try {
            this.audiosource = new AudioSource3D(this.getBaseAudioPath(), false, x, y);
            System.out.println("playing: "+ getBaseAudioPath() + " in: "+getSymbol());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

