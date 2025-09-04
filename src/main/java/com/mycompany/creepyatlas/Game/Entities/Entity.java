package com.mycompany.creepyatlas.Game.Entities;

import com.mycompany.creepyatlas.Game.*;
import com.mycompany.creepyatlas.Audio.AudioSource3D;
import com.mycompany.creepyatlas.Enums.Enums.Direction;

public abstract class Entity{
    protected int x;
    protected int y;
    protected AudioSource3D audiosource;
    

    public Entity(int x, int y) {
        this.x = x;
        this.y = y;
        try {
            this.audiosource = new AudioSource3D(this.getBaseAudioPath(), true);
            this.audiosource.setPosition(x, y);
            this.audiosource.play();
            System.out.println("playing: "+ getBaseAudioPath() + " in: "+getSymbol());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
        if (this.audiosource != null)
        {
            this.audiosource.setPosition(x, y);
        }
    }

    public void move(Direction direction)
    {
        int dx = 0;
        int dy = 0;

        switch (direction) {
            case LEFT:  dx = -1; break;
            case RIGHT: dx =  1; break;
            case UP:    dy = -1; break;
            case DOWN:  dy =  1; break;
            default:    break;
        }

        int newX = this.x + dx;
        int newY = this.y + dy;

        if (newY < 0 || newY >= Game.getBaseMap().length || newX < 0 || newX >= Game.getBaseMap()[0].length) {
            System.out.println("You cannot move outside the map!");
            return;
        }

        char target = Game.getBaseMap()[newY][newX];
        if (target == '|' || target == '-' || target == '#') {
            System.out.println("There is a wall in that direction!");
            try {
                AudioSource3D wallSound = new AudioSource3D("/audios/footsteps_and_wall.wav", false);
                wallSound.setPosition(this.x, this.y);
                wallSound.play();
                } catch (Exception e) {
                    System.out.println("error");
                }
            return;
        }
        else{
            try {
                AudioSource3D stepSound = new AudioSource3D("/audios/footsteps.wav", false);
                stepSound.setPosition(this.x, this.y);
                stepSound.play();
                } catch (Exception e) {
                    System.out.println("error");
                }            
        }

        move(dx, dy);
    }

    public abstract char getSymbol();

    public String getBaseAudioPath(){
        return "";
    }
}
