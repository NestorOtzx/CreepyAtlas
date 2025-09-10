package com.mycompany.creepyatlas.Game.Entities.Enemies;

import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Chubby extends Enemy {
    private int timesPlayerMoved;
    private int timesChubbyMoved;

    public Chubby(int x, int y, int baseHealth,int mentalHealth, int attack_damage) {
        super(x, y, baseHealth,mentalHealth, attack_damage);
    }

    @Override
    public char getSymbol() {
        if (!isDead) {
            return 'C';
        }
        return super.getSymbol();
    }

    @Override
    public void onUpdateGame() {
        timesPlayerMoved++;
        char[][] map = Game.getBaseMapLayer();

        if (timesPlayerMoved % 2 == 0) {
            attemptMovePattern(map);
        }
    }

    private void attemptMovePattern(char[][] map) {
        int[][] sequence = {
            {1, 0}, 
            {0, -1},
            {-1, 0},
            {0, 1}  
        };

        while (true) {
            int step = timesChubbyMoved % (sequence.length * 2);

            if (step % 2 == 0) {
                int[] dir = sequence[step / 2];
                if (tryMove(map, dir[0], dir[1])) {
                    timesChubbyMoved++;
                    break;
                } else {
                    timesChubbyMoved++;
                }
            } else {
                int dx = Integer.compare(initialPositionX, this.positionX);
                int dy = Integer.compare(initialPositionY, this.positionY);

                if (dx == 0 && dy == 0) {
                    timesChubbyMoved++;
                } else if (tryMove(map, dx, dy)) {
                    timesChubbyMoved++;
                    break;
                } else {
                    timesChubbyMoved++;
                }
            }
        }
    }

    private boolean tryMove(char[][] map, int dx, int dy) {
        int newX = this.positionX + dx;
        int newY = this.positionY + dy;

        if (newY >= 0 && newY < map.length &&
            newX >= 0 && newX < map[0].length &&
            map[newY][newX] == ' ') {

            move(dx, dy);
            return true;
        }
        return false;
    }

    @Override
    public String getBaseAudioPath(){
        return "/audios/chubby_mono.wav";
    }

    @Override
    public String getDefeatMessage() {
        return "EWWW, NOW YOU'RE COVERED IN THICK SNOOT";
    }
    @Override
    public String getForgiveMessage() {
        return "Chubby: ribbit!" +
        "\n"+
        "What a kind adventurer you are. Chubby is very happy!";
    }
}
