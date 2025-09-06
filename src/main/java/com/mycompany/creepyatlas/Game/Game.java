package com.mycompany.creepyatlas.Game;

import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Utils.ConsoleCommand;
import com.mycompany.creepyatlas.Utils.CommandReader;
import com.mycompany.creepyatlas.Utils.MapReader;

import java.util.*;

public class Game {
    private static char[][] baseMap;
    private static char[][] enemyLayer;
    private static char[][] playerLayer;
    private final List<char[][]> renderLayers;

    private static Player player;
    private static List<Entity> entities;
    private static List<Enemy> enemies;

    private boolean inGame = true;


    public Game() {

        inGame = true;
        MapReader.MapData mapData = MapReader.loadLevel("levels/level1.txt");

        baseMap = mapData.getBaseMap();
        enemyLayer = new char[baseMap.length][baseMap[0].length];
        playerLayer = new char[baseMap.length][baseMap[0].length];

        player = mapData.getPlayer();
        entities = new ArrayList<>();
        enemies = new ArrayList<>();
        entities.add(player);
        entities.addAll(mapData.getEnemies());
        enemies.addAll(mapData.getEnemies());

        renderLayers = new ArrayList<>();
        renderLayers.add(baseMap);
        renderLayers.add(enemyLayer);
        renderLayers.add(playerLayer);
    }

    private void refreshEntityLayer() {
        for (char[] row : enemyLayer) {
            Arrays.fill(row, ' ');   
        }
        for (char[] row : playerLayer) {
            Arrays.fill(row, ' ');
            
        }


        for (Entity entity : entities) {
            int x = entity.getX();
            int y = entity.getY();
            if (y >= 0 && y < enemyLayer.length &&
                x >= 0 && x < enemyLayer[0].length) {
                enemyLayer[y][x] = entity.getSymbol();
            }
        }

        int playerx = player.getX();
        int playery = player.getY();
        playerLayer[playery][playerx] = player.getSymbol();
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
            refreshEntityLayer();
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
        ConsoleCommand command = CommandReader.readCommand();
        System.out.println(command);
        if (command.getType() == CommandType.QUIT){
            inGame = false;
            return;
        }
        if (player.getIsDead())
        {
            return;
        }
        switch (command.getType()) {
            case MOVE:
                if (command.getDirection() == Direction.NONE)
                {
                    Screen.setState(ScreenState.MOVE_COMMANDS);
                }else{
                    Screen.setState(ScreenState.BASE);
                    player.move(command.getDirection());
                }
                break;
            case NOISE:
                if (command.getNoise() == NoiseType.UNKNOWN)
                {
                    Screen.setState(ScreenState.NOISE_COMMANDS);
                }else{
                    Screen.setState(ScreenState.BASE);
                }
            case ATTACK:
                break;
            case EAT:
                break;
            default:
                break;
        }          
    }

    public void start() {
        update();
    }

    public static char[][] getBaseMap()
    {
        return baseMap;
    }
}
