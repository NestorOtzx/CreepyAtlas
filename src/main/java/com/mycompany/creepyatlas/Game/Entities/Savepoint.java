package com.mycompany.creepyatlas.Game.Entities;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Game.Game;

public class Savepoint extends Entity {
    int savePointIndex;
    boolean taken = false;

    public Savepoint(int x, int y, int baseHealth,int mentalHealth, int attack_damage, int index) {
        super(x, y, baseHealth, mentalHealth,attack_damage);
        savePointIndex = index;
        taken = false;
    }

    @Override
    public char getSymbol() {
        if (!is_dead && !taken)
        {
            return 'S';
        }else if (taken)
        {
            return ' ';
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
        if (playerx == this.x && playery == this.y)
        {
            Game.getPlayer().SetSavePoint(savePointIndex);
            audiosource.play();
            taken = true;
        }
    }

    @Override
    protected void InitAudio()
    {
        try {
            this.audiosource = new AudioSource3D(this.getBaseAudioPath(), false, x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

