package com.mycompany.creepyatlas.Game;

import com.mycompany.creepyatlas.Audio.AudioListener3D;
import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Utils.CommandReader;
import com.mycompany.creepyatlas.Utils.MapData;
import com.mycompany.creepyatlas.Utils.MapReader;

import java.util.*;

public class Game {
    private static char[][] baseMapLayer;
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
        MapData mapData = MapReader.loadLevel("levels/level1.txt");

        baseMapLayer = mapData.getBaseMap();
        savePoints = mapData.getSavePoints();
        

        enemyLayer = new char[baseMapLayer.length][baseMapLayer[0].length];
        playerLayer = new char[baseMapLayer.length][baseMapLayer[0].length];
        savePointsLayer = new char[baseMapLayer.length][baseMapLayer[0].length];
        fogLayer = new char[baseMapLayer.length][baseMapLayer[0].length];
        for (char[] row : savePointsLayer) {
            Arrays.fill(row, ' ');
        }
        for (char[] row : fogLayer) {
            Arrays.fill(row, '.');
        }
        for (int i = 0; i<savePoints.size(); i++)
        {
            savePointsLayer[savePoints.get(i).getPositionY()][savePoints.get(i).getPositionX()] = savePoints.get(i).getSymbol();    
        }

        player = mapData.getPlayer();
        clearFog(player.getPositionX(), player.getPositionY());
        
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        entities.add(player);
        entities.addAll(mapData.getEnemies());
        enemies.addAll(mapData.getEnemies());
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == 'A')
            {
                clearFog(enemies.get(i).getPositionX(), enemies.get(i).getPositionY());
            }
        }

        renderLayers = new ArrayList<>();
        renderLayers.add(baseMapLayer);
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
            enemy.onUpdateGame();
            int x = enemy.getPositionX();
            int y = enemy.getPositionY();
            enemyLayer[y][x] = enemy.getSymbol();
        }
        for (Savepoint savepoint : savePoints)
        {
            savepoint.onUpdateGame();
            savePointsLayer[savepoint.getPositionY()][savepoint.getPositionX()] = savepoint.getSymbol();    
        }
        

        int playerx = player.getPositionX();
        int playery = player.getPositionY();
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
            if (enemies.get(i).getPositionX() == x && enemies.get(i).getPositionY() == y)
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
            if (enemies.get(i).getPositionX() == x && enemies.get(i).getPositionY() == y)
            {
                if (!enemies.get(i).getIsDead() && !enemies.get(i).getIsForgiven())
                {
                    ans.add(enemies.get(i));
                }
            }
        }
        return ans;
    }


    public static void enemyAttacksPosition(Entity attacker, int x, int y, char target, int damage)
    {
        List<Enemy> enemies = getEnemiesInCell(x, y);
        
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == target)
            {
                enemies.get(i).recieveAttack(player,damage);
            }
        }
        if (player.getSymbol() == target)
        {
            player.recieveAttack(attacker, damage);
        }
    }

    public static void playerAttacksPosition(int x, int y, char target, int damage)
    {
        List<Enemy> enemies = getEnemiesInCell(x, y);
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == target)
            {
                enemies.get(i).recieveAttack(player,damage);
            }
        }
    }

    public static void playerForgivesPosition(int x, int y, char target, int forgiveness)
    {
        List<Enemy> enemies = getEnemiesInCell(x, y);
        for (int i = 0; i<enemies.size(); i++)
        {
            if (enemies.get(i).getSymbol() == target)
            {
                enemies.get(i).recieveForgiveness(player, forgiveness);
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
            player.getPositionX(),
            player.getPositionY(),
            renderLayers
            );
            Screen.render();
            readAction();
        }
    }

    private void readAction()
    {
        CommandReader.execCommand();
    }

    
    public static char[][] getBaseMapLayer()
    {
        return baseMapLayer;
    }

    public static void setInGame(boolean _ingame)
    {
        inGame = _ingame;
    }

    public static void endGame()
    {
        boolean all_dead = true;
        boolean all_alive = true;
        for (int i = 0; i<enemies.size(); i++)
        {
            if (!enemies.get(i).getIsDead() && enemies.get(i).getSymbol() != 'A'){
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

    public static Savepoint getCurrentSavePoint()
    {
        if (player.getSavePoint() < 0)
        {
            return null;
        }
        return savePoints.get(player.getSavePoint());
    }

    public static void RevivePlayer()
    {
        
        Savepoint currentSave = getCurrentSavePoint();
        int reviveX = player.getInitialX();
        int reviveY = player.getInitialY();
        
        if (currentSave != null){
            reviveX = currentSave.getPositionX();
            reviveY = currentSave.getPositionY();
        }
        player = new Player(reviveX, reviveY, 100, 100, 50, 50);
        AudioListener3D.enableAllAudioSources(); 
        for (int i = 0; i<enemies.size(); i++){
            enemies.get(i).translate(enemies.get(i).getInitialX(), enemies.get(i).getInitialY());            
            enemies.get(i).onPlayerRespawn();
        }
        Screen.setState(ScreenState.BASE);
        
    }

    public static void clearFogSingle(int x, int y)
    {
        fogLayer[y][x] = ' ';
    }

    public static void clearFog(int x, int y)
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
