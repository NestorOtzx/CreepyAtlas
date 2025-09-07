package com.mycompany.creepyatlas.Game;

import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Utils.CommandReader;
import com.mycompany.creepyatlas.Utils.MapReader;

import java.util.*;

public class Game {
    private static char[][] baseMap;
    private static char[][] enemyLayer;
    private static char[][] playerLayer;
    private static char[][] fogLayer;
    private final List<char[][]> renderLayers;

    private static Player player;
    private static List<Entity> entities;
    private static List<Enemy> enemies;

    private static boolean inGame = true;


    public Game() {

        inGame = true;
        MapReader.MapData mapData = MapReader.loadLevel("levels/level1.txt");

        baseMap = mapData.getBaseMap();
        enemyLayer = new char[baseMap.length][baseMap[0].length];
        playerLayer = new char[baseMap.length][baseMap[0].length];
        fogLayer = new char[baseMap.length][baseMap[0].length];
        for (char[] row : fogLayer) {
            Arrays.fill(row, '.');
        }

        player = mapData.getPlayer();
        ClearFog(player.getX(), player.getY());

        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        entities.add(player);
        entities.addAll(mapData.getEnemies());
        enemies.addAll(mapData.getEnemies());

        renderLayers = new ArrayList<>();
        renderLayers.add(baseMap);
        renderLayers.add(enemyLayer);
        renderLayers.add(playerLayer);
        renderLayers.add(fogLayer);
    }

    private void refreshEnemyLayer() {
        for (char[] row : enemyLayer) {
            Arrays.fill(row, ' ');   
        }
        for (char[] row : playerLayer) {
            Arrays.fill(row, ' ');
        }
        


        for (Enemy enemy : enemies) {
            enemy.OnUpdateGame();
            int x = enemy.getX();
            int y = enemy.getY();
            if (y >= 0 && y < enemyLayer.length &&
                x >= 0 && x < enemyLayer[0].length) {
                    
                enemyLayer[y][x] = enemy.getSymbol();
            }
        }

        int playerx = player.getX();
        int playery = player.getY();
        playerLayer[playery][playerx] = player.getSymbol();
        if (Game.getEnemyLayer()[playery][playerx] != ' ' && Game.getEnemiesInCell(playerx, playery).size()>0)
        {
            Screen.setState(ScreenState.COMBAT);
        }else{
            Screen.setState(ScreenState.BASE);
        }
    }

    public static char[][] getEnemyLayer()
    {
        return enemyLayer;
    }

    public static List<Character> getEnemySymbolsInCell(int x, int y)
    {
        List<Character> ans = new ArrayList<>(); 
        for (int i = 0; i<enemies.size(); i++){
            if (enemies.get(i).getX() == x && enemies.get(i).getY() == y)
            {
                char symbol = enemies.get(i).getSymbol();
                if (symbol != 'X')
                {
                    ans.add(symbol);
                }
            }
        }
        return ans;
    }

    public static List<Enemy> getEnemiesInCell(int x, int y)
    {
        List<Enemy> ans = new ArrayList<>(); 
        for (int i = 0; i<enemies.size(); i++){
            if (enemies.get(i).getX() == x && enemies.get(i).getY() == y)
            {
                char symbol = enemies.get(i).getSymbol();
                if (symbol != 'X')
                {
                    ans.add(enemies.get(i));
                }
            }
        }
        return ans;
    }


    public static void EnemyAttacksPosition(Entity attacker, int x, int y, char target, int damage)
    {
        List<Enemy> enemies = getEnemiesInCell(x, y);
        
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == target)
            {
                enemies.get(i).RecieveAttack(player,damage);
            }
        }
        if (player.getSymbol() == target)
        {
            player.RecieveAttack(attacker, damage);
        }
    }

    public static void PlayerAttacksPosition(int x, int y, char target, int damage)
    {
        List<Enemy> enemies = getEnemiesInCell(x, y);
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == target)
            {
                enemies.get(i).RecieveAttack(player,damage);
            }
        }
    }

    public static Player getPlayer(){
        return player;
    }


    public void update() {
        while (inGame)
        {
            refreshEnemyLayer();
            CameraConsole.draw(
            player.getX(),
            player.getY(),
            renderLayers
            );
            ReadAction();
        }
    }

    private void ReadAction()
    {
        CommandReader.execCommand();
    }

    public void start() {
        update();
    }

    public static char[][] getBaseMap()
    {
        return baseMap;
    }

    public static void SetInGame(boolean _ingame)
    {
        inGame = _ingame;
    }

    public static void ClearFog(int x, int y)
    {
        if (y >= 0 && y < fogLayer.length)
        {
            if (x >= 0 && x < fogLayer.length)
            {
                fogLayer[y][x] = ' ';
            }
            if (x - 1 >= 0 && x-1 < fogLayer[y].length)
            {
                fogLayer[y][x-1] = ' ';
            }
            if (x + 1 >= 0 && x+1 < fogLayer[y].length)
            {
                fogLayer[y][x+1] = ' ';
            }
        }
        if (y+1 >= 0 && y+1 < fogLayer.length)
        {
            if (x >= 0 && x < fogLayer.length)
            {
                fogLayer[y+1][x] = ' ';
            }
            if (x - 1 >= 0 && x-1 < fogLayer[y+1].length)
            {
                fogLayer[y+1][x-1] = ' ';
            }
            if (x + 1 >= 0 && x+1 < fogLayer[y+1].length)
            {
                fogLayer[y+1][x+1] = ' ';
            }
        }
        if (y-1 >= 0 && y-1 < fogLayer.length)
        {
            if (x >= 0 && x < fogLayer.length)
            {
                fogLayer[y-1][x] = ' ';
            }
            if (x - 1 >= 0 && x-1 < fogLayer[y-1].length)
            {
                fogLayer[y-1][x-1] = ' ';
            }
            if (x + 1 >= 0 && x+1 < fogLayer[y-1].length)
            {
                fogLayer[y-1][x+1] = ' ';
            }
        }
    }
}
