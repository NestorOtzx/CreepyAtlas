package com.mycompany.creepyatlas.Game;

import static com.mycompany.creepyatlas.Enums.Enums.*;

public class Screen {

    private static final int WIDTH = 65;
    private static final int HEIGHT = 20;
    private static ScreenState currentState = ScreenState.BASE; 

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
            case BASE:
                renderGame(1, 2);
                drawBoxWithText(13, 2, "                          Commands                         ");
                drawHorizontalButtons(16, 2, 2, "Move", "Noise", "Stats", "Quit");
                break;

            case MOVE_COMMANDS:
                renderGame(1, 2);
                drawBoxWithText(13, 2, "                            Move + ...                       ");
                drawHorizontalButtons(16, 2, 2, "Left", "Right", "Up", "Down");
                break;
            case NOISE_COMMANDS:
                renderGame(1, 2);
                drawBoxWithText(13, 2, "                            Noise + ...                       ");
                drawHorizontalButtons(16, 2, 2, "Burp", "Scream");
                break;
            case STATS:
                break;

            default:
                break;
        }

        display();
    }

    private static void renderGame(int startY, int startX) {
        char[][] cam = CameraConsole.getLastFrame();
        if (cam == null) return;

        for (int y = 0; y < cam.length && y + startY < HEIGHT; y++) {
            for (int x = 0; x < cam[y].length && x + startX < WIDTH; x++) {
                buffer[startY + y][startX + x] = cam[y][x];
            }
        }
    }

    private static void drawCenteredLine(int row, String text, int midX) {
        int start = midX - text.length() / 2;
        if (row < 0 || row >= HEIGHT) return;

        for (int i = 0; i < text.length() && (start + i) < WIDTH; i++) {
            buffer[row][start + i] = text.charAt(i);
        }
    }

    public static void drawBoxWithText(int top, int left, String text) {
        int boxWidth = text.length();
        int boxHeight = 2;
        if (top < 0 || left < 0 || top + boxHeight >= HEIGHT || left + boxWidth >= WIDTH) {
            return;
        }
        buffer[top][left] = '+';
        buffer[top][left + boxWidth+1] = '+';
        buffer[top + boxHeight][left] = '+';
        buffer[top + boxHeight][left + boxWidth+1] = '+';

        for (int x = left + 1; x <= left + boxWidth; x++) {
            buffer[top][x] = '-';
            buffer[top + boxHeight][x] = '-';
        }

        for (int y = top + 1; y < top + boxHeight; y++) {
            buffer[y][left] = '|';
            buffer[y][left + boxWidth+1] = '|';
        }

        int textRow = top + boxHeight / 2;
        int textStart = left + 1;
        for (int i = 0; i < text.length() && textStart + i <= left + boxWidth; i++) {
            buffer[textRow][textStart + i] = text.charAt(i);
        }
    }

    /**
     * Dibuja una fila de botones automáticamente con separación (gap).
     */
    public static void drawHorizontalButtons(int top, int startLeft, int gap, String... texts) {
        int left = startLeft;
        for (String text : texts) {
            drawBoxWithText(top, left, text);
            left += text.length() + 2 /*bordes*/ + gap;
        }
    }

    public static void display() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(buffer[y][x]);
            }
            System.out.println();
        }
    }

    public static void setState(ScreenState state) {
        currentState = state;
    }

    public static ScreenState getState() {
        return currentState;
    }
}
