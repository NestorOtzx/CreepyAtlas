package com.mycompany.creepyatlas.Game;
import static com.mycompany.creepyatlas.Enums.Enums.*;

public class Screen {

    private static final int WIDTH = 90;
    private static final int HEIGHT = 20;
    private static ScreenState currentState = ScreenState.GAME; 

    private static char[][] buffer = new char[HEIGHT][WIDTH];

    private static void fillBackground() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                buffer[y][x] = '#';
            }
        }
    }

    public static void render() {
        fillBackground();

        switch (currentState) {
            case GAME:
                renderGame();
                break;
            case INVENTORY:
                renderInventory();
                break;
            default:
                break;
        }

        display();
    }

    private static void renderGame() {
        char[][] cam = CameraConsole.getLastFrame();
        if (cam == null) return;

        int startY = 1;
        int startX = 2;

        for (int y = 0; y < cam.length && y + startY < HEIGHT; y++) {
            for (int x = 0; x < cam[y].length && x + startX < WIDTH; x++) {
                buffer[startY + y][startX + x] = cam[y][x];
            }
        }
    }

    private static void renderInventory() {
        String title = "=== INVENTORY ===";
        String option1 = "1. Sword";
        String option2 = "2. Potion";
        String option3 = "3. Exit";

        int midX = WIDTH / 2;

        drawCenteredLine(5, title, midX);
        drawCenteredLine(7, option1, midX);
        drawCenteredLine(8, option2, midX);
        drawCenteredLine(9, option3, midX);
    }

    private static void drawCenteredLine(int row, String text, int midX) {
        int start = midX - text.length() / 2;
        if (row < 0 || row >= HEIGHT) return;

        for (int i = 0; i < text.length() && (start + i) < WIDTH; i++) {
            buffer[row][start + i] = text.charAt(i);
        }
    }

    // Print to console
    public static void display() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(buffer[y][x]);
            }
            System.out.println();
        }
    }

    // Switch state
    public static void setState(ScreenState state) {
        currentState = state;
    }

    public static ScreenState getState() {
        return currentState;
    }
}
