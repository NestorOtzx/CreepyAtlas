package com.mycompany.creepyatlas.Utils;
import com.mycompany.creepyatlas.Enums.Enums.*;

public class ConsoleCommand {
    private final CommandType type;
    private final Direction direction;
    private final NoiseType noise;

    public ConsoleCommand(CommandType type, Direction direction, NoiseType noise) {
        this.type = type;
        this.direction = direction;
        this.noise = noise;
    }

    public CommandType getType() { return type; }
    public Direction getDirection() { return direction; }
    public NoiseType getNoise() { return noise; }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", direction=" + direction +
                ", noise=" + noise +
                '}';
    }
}