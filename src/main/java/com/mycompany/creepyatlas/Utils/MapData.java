package com.mycompany.creepyatlas.Utils;

import java.util.List;

import com.mycompany.creepyatlas.Game.Entities.Enemy;
import com.mycompany.creepyatlas.Game.Entities.Player;
import com.mycompany.creepyatlas.Game.Entities.Savepoint;

public class MapData {
    private final char[][] baseMap;
    private final Player player;
    private final List<Enemy> enemies;
    private final List<Savepoint> savePoints;

    public MapData(char[][] baseMap, Player player, List<Enemy> enemies, List<Savepoint> savePoints) {
        this.baseMap = baseMap;
        this.player = player;
        this.enemies = enemies;
        this.savePoints = savePoints;
    }

    public char[][] getBaseMap() { return baseMap; }
    public Player getPlayer() { return player; }
    public List<Enemy> getEnemies() { return enemies; }
    public List<Savepoint> getSavePoints() { return savePoints; }
}