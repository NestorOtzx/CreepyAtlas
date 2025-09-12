package com.mycompany.creepyatlas.Utils;

import com.mycompany.creepyatlas.Enums.Enums.Direction;

public class DirectionUtils {
    public static int[] getDelta(Direction direction) {
        switch (direction) {
            case LEFT:  return new int[]{-1, 0};
            case RIGHT: return new int[]{1, 0};
            case UP:    return new int[]{0, -1};
            case DOWN:  return new int[]{0, 1};
            default:    return new int[]{0, 0};
        }
    }
}
