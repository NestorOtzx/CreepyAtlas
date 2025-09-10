package com.mycompany.creepyatlas.Game;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Utils.CommandReader;
import com.mycompany.creepyatlas.Utils.MapReader;

import java.util.*;

public class Game {
    private static char[][] baseMap;
    private static char[][] enemyLayer;
    private static char[][] savePointsLayer;
    private static char[][] playerLayer;
    private static char[][] fogLayer;
    private static List<char[][]> renderLayers;

    private static Player player;
    private static List<Entity> entities;
    private static List<Enemy> enemies;
    private static List<Savepoint> savePoints;

    private static boolean inGame = true;

    public Game() {
        inGame = true;
        MapReader.MapData mapData = MapReader.loadLevel("levels/level1.txt");

        baseMap = mapData.getBaseMap();
        savePoints = mapData.getSavePoints();
        

        enemyLayer = new char[baseMap.length][baseMap[0].length];
        playerLayer = new char[baseMap.length][baseMap[0].length];
        savePointsLayer = new char[baseMap.length][baseMap[0].length];
        fogLayer = new char[baseMap.length][baseMap[0].length];
        for (char[] row : savePointsLayer) {
            Arrays.fill(row, ' ');
        }
        for (char[] row : fogLayer) {
            Arrays.fill(row, '.');
        }
        for (int i = 0; i<savePoints.size(); i++)
        {
            savePointsLayer[savePoints.get(i).getY()][savePoints.get(i).getX()] = savePoints.get(i).getSymbol();    
        }

        player = mapData.getPlayer();
        ClearFog(player.getX(), player.getY());
        
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        entities.add(player);
        entities.addAll(mapData.getEnemies());
        enemies.addAll(mapData.getEnemies());
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == 'A')
            {
                ClearFog(enemies.get(i).getX(), enemies.get(i).getY());
            }
        }

        renderLayers = new ArrayList<>();
        renderLayers.add(baseMap);
        renderLayers.add(enemyLayer);
        renderLayers.add(savePointsLayer);
        renderLayers.add(playerLayer);
        renderLayers.add(fogLayer);
    }

    private static void refreshEnemyLayer() {
        for (char[] row : enemyLayer) {
            Arrays.fill(row, ' ');   
        }
        for (char[] row : playerLayer) {
            Arrays.fill(row, ' ');
        }
        for (char[] row : savePointsLayer)
        {
            Arrays.fill(row, ' ');
        }
        for (Enemy enemy : enemies) {
            enemy.OnUpdateGame();
            int x = enemy.getX();
            int y = enemy.getY();
            enemyLayer[y][x] = enemy.getSymbol();
        }
        for (Savepoint savepoint : savePoints)
        {
            savepoint.OnUpdateGame();
            savePointsLayer[savepoint.getY()][savepoint.getX()] = savepoint.getSymbol();    
        }
        

        int playerx = player.getX();
        int playery = player.getY();
        playerLayer[playery][playerx] = player.getSymbol();

        if (!player.getIsDead())
        {
            if (Game.getEnemyLayer()[playery][playerx] != ' ' && Game.getEnemiesInCell(playerx, playery).size()>0)
            {
                Screen.setState(ScreenState.COMBAT);
            }else{
                Screen.setState(ScreenState.BASE);
            }
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
                if (!enemies.get(i).getIsDead() && !enemies.get(i).getIsForgiven())
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

    public static void PlayerForgivesPosition(int x, int y, char target, int forgiveness)
    {
        List<Enemy> enemies = getEnemiesInCell(x, y);
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == target)
            {
                enemies.get(i).RecieveForgiveness(player, forgiveness);
            }
        }
    }

    public static Player getPlayer(){
        return player;
    }

    public void start() {
        Screen.setState(ScreenState.SCENE_PROLOG_1);
        Screen.render();
        CommandReader.execCommand();
        Screen.setState(ScreenState.SCENE_PROLOG_2);
        Screen.render();
        CommandReader.execCommand();
        Screen.setState(ScreenState.SCENE_PROLOG_3);
        Screen.render();
        CommandReader.execCommand();
        update();
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
            Screen.render();
            ReadAction();
        }
    }

    private void ReadAction()
    {
        CommandReader.execCommand();
    }

    
    public static char[][] getBaseMap()
    {
        return baseMap;
    }

    public static void SetInGame(boolean _ingame)
    {
        inGame = _ingame;
    }

    public static void EndGame()
    {
        boolean all_dead = true;
        boolean all_alive = true;
        for (int i = 0; i<enemies.size(); i++)
        {
            if (!enemies.get(i).getIsDead() && enemies.get(i).getSymbol() == 'A'){
                all_dead = false;
            }
            if (enemies.get(i).getIsDead()){
                all_alive = false;
            }
        }
        if (all_dead)
        {
            Screen.setState(ScreenState.END_SCREEN_GENOCIDE_1);
            Screen.render();
            CommandReader.execCommand();
            Screen.setState(ScreenState.END_SCREEN_GENOCIDE_2);
            Screen.render();
            CommandReader.execCommand();
            Screen.setState(ScreenState.END_SCREEN_GENOCIDE_3);
            Screen.render();
            CommandReader.execCommand();
        }else if (all_alive){
            Screen.setState(ScreenState.END_SCREEN_PACIFIST_1);
            Screen.render();
            CommandReader.execCommand();
            Screen.setState(ScreenState.END_SCREEN_PACIFIST_2);
            Screen.render();
            CommandReader.execCommand();
            Screen.setState(ScreenState.END_SCREEN_PACIFIST_3);
            Screen.render();
            CommandReader.execCommand();
        }else{
            Screen.setState(ScreenState.END_SCREEN_NEUTRAL_1);
            Screen.render();
            CommandReader.execCommand();
            Screen.setState(ScreenState.END_SCREEN_NEUTRAL_2);
            Screen.render();
            CommandReader.execCommand();
            Screen.setState(ScreenState.END_SCREEN_NEUTRAL_3);
            Screen.render();
            CommandReader.execCommand();
        }
        inGame = false;
    }

    public static Savepoint GetCurrentSavePoint()
    {
        if (player.GetSavePoint() < 0)
        {
            return null;
        }
        return savePoints.get(player.GetSavePoint());
    }

    public static void RevivePlayer()
    {
        
        Savepoint currentSave = GetCurrentSavePoint();
        int reviveX = player.getInitialX();
        int reviveY = player.getInitialY();
        
        if (currentSave != null){
            reviveX = currentSave.getX();
            reviveY = currentSave.getY();
        }
        player = new Player(reviveX, reviveY, 100, 100, 50, 50);
        for (int i = 0; i<enemies.size(); i++){
            enemies.get(i).translate(enemies.get(i).getInitialX(), enemies.get(i).getInitialY());            
            enemies.get(i).OnPlayerRespawn();
        }
        AudioListener3D.EnableAllAudioSources(); 
        Screen.setState(ScreenState.BASE);
        
    }

    public static void ClearFogSingle(int x, int y)
    {
        fogLayer[y][x] = ' ';
    }

    public static void ClearFog(int x, int y)
    {
        if (y >= 0 && y < fogLayer.length)
        {
            if (x >= 0 && x < fogLayer[y].length)
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
            if (x >= 0 && x < fogLayer[y+1].length)
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
            if (x >= 0 && x < fogLayer[y-1].length)
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
