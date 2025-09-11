package com.mycompany.creepyatlas.Game.Entities;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.AudioEffectType;
import com.mycompany.creepyatlas.Game.Game;

public class Savepoint extends Entity {
    int savePointIndex;
    boolean isTakenByPlayer = false;

    public Savepoint(int x, int y, int baseHealth,int mentalHealth, int attack_damage, int index) {
        super(x, y, baseHealth, mentalHealth,attack_damage);
        savePointIndex = index;
        isTakenByPlayer = false;
    }

    @Override
    public char getSymbol() {
        if (!isDead && !isTakenByPlayer)
        {
            return 'S';
        }else if (isTakenByPlayer)
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
    public void onUpdateGame()
    {
        int playerx = Game.getPlayer().getPositionX();
        int playery = Game.getPlayer().getPositionY();
        if (playerx == this.positionX && playery == this.positionY)
        {
            Game.getPlayer().setSavePoint(savePointIndex);
            audioSource.play();
            isTakenByPlayer = true;
        }
    }

    @Override
    protected void initAudio()
    {
        try {
            this.audioSource = new AudioSource3D(this.getBaseAudioPath(), false, positionX, positionY,AudioEffectType.REVERB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

