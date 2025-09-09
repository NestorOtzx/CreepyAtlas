package com.mycompany.creepyatlas.Game.Entities.Enemies;

import com.mycompany.creepyatlas.Game.Game;
import com.mycompany.creepyatlas.Game.Entities.*;

public class Chubby extends Enemy {
    int initialX;
    int initialY;
    private int timesPlayerMoved;
    private int timesChubbyMoved;

    public Chubby(int x, int y, int baseHealth, int attack_damage) {
        super(x, y, baseHealth, attack_damage);
        initialX = x;
        initialY = y;
    }

    @Override
    public char getSymbol() {
        if (!is_dead) {
            return 'C';
        }
        return super.getSymbol();
    }

    @Override
    public void OnUpdateGame() {
        timesPlayerMoved++;
        char[][] map = Game.getBaseMap();

        // Chubby se mueve cada 2 turnos del jugador
        if (timesPlayerMoved % 2 == 0) {
            attemptMovePattern(map);
        }
    }

    /**
     * Intenta mover a Chubby siguiendo el patrón:
     * Derecha -> Origen -> Arriba -> Origen -> Izquierda -> Origen -> Abajo -> Origen
     */
    private void attemptMovePattern(char[][] map) {
        // Secuencia de direcciones principales (dx, dy)
        int[][] sequence = {
            {1, 0},   // derecha
            {0, -1},  // arriba
            {-1, 0},  // izquierda
            {0, 1}    // abajo
        };

        while (true) {
            int step = timesChubbyMoved % (sequence.length * 2);

            if (step % 2 == 0) {
                // Movimiento hacia una dirección
                int[] dir = sequence[step / 2];
                if (tryMove(map, dir[0], dir[1])) {
                    timesChubbyMoved++;
                    break;
                } else {
                    // No se puede -> intenta siguiente paso del ciclo
                    timesChubbyMoved++;
                }
            } else {
                // Movimiento de regreso al origen (paso a paso)
                int dx = Integer.compare(initialX, this.x);
                int dy = Integer.compare(initialY, this.y);

                if (dx == 0 && dy == 0) {
                    // Ya estamos en el origen -> pasa al siguiente estado
                    timesChubbyMoved++;
                } else if (tryMove(map, dx, dy)) {
                    timesChubbyMoved++;
                    break;
                } else {
                    // Si no puede regresar por algún obstáculo, salta
                    timesChubbyMoved++;
                }
            }
        }
    }

    /**
     * Intenta mover en (dx, dy) verificando que la celda sea transitable.
     */
    private boolean tryMove(char[][] map, int dx, int dy) {
        int newX = this.x + dx;
        int newY = this.y + dy;

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
        "What a kind adventurer you are. Chubby is very happy!";
    }
}
