package com.mycompany.creepyatlas.Game.Entities;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Game.Game;

public class Savepoint extends Entity {
    int savePointIndex;

    public Savepoint(int x, int y, int baseHealth,int mentalHealth, int attack_damage, int index) {
        super(x, y, baseHealth, mentalHealth,attack_damage);
        savePointIndex = index;
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
            Game.getPlayer().SetSavePoint(savePointIndex);
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

