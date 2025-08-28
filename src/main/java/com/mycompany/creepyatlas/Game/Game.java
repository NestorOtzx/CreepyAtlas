package com.mycompany.creepyatlas.Game;

import com.mycompany.creepyatlas.Enums.Enums.*;
import com.mycompany.creepyatlas.Game.Entities.*;
import com.mycompany.creepyatlas.Utils.ConsoleCommand;
import com.mycompany.creepyatlas.Utils.CommandReader;
import com.mycompany.creepyatlas.Utils.MapReader;

import java.util.*;

public class Game {
    private static char[][] baseMap;
    private static char[][] entityLayer;
    private final List<char[][]> renderLayers;

    private static Player player;
    private final List<Entity> entities;

    private boolean inGame = true;

    public Game() {

        inGame = true;
        MapReader.MapData mapData = MapReader.loadLevel("levels/level1.txt");

        baseMap = mapData.getBaseMap();
        entityLayer = new char[baseMap.length][baseMap[0].length];

        player = mapData.getPlayer();
        entities = new ArrayList<>();
        entities.add(player);
        entities.addAll(mapData.getEnemies());

        renderLayers = new ArrayList<>();
        renderLayers.add(baseMap);
        renderLayers.add(entityLayer);
    }

    private void refreshEntityLayer() {
        for (char[] row : entityLayer) {
            Arrays.fill(row, ' ');
        }

        for (Entity entity : entities) {
            int x = entity.getX();
            int y = entity.getY();
            if (y >= 0 && y < entityLayer.length &&
                x >= 0 && x < entityLayer[0].length) {
                entityLayer[y][x] = entity.getSymbol();
            }
        }

        int playerx = player.getX();
        int playery = player.getY();
        entityLayer[playery][playerx] = player.getSymbol();
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
