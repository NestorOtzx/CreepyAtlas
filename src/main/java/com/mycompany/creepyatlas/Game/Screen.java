package com.mycompany.creepyatlas.Game;

import java.util.List;

import com.mycompany.creepyatlas.Enums.Enums.Direction;
import com.mycompany.creepyatlas.Enums.Enums.ScreenState;

public class Screen {

    private static final int WIDTH = 77;
    private static final int HEIGHT = 20;
    private static ScreenState currentState = ScreenState.SCENE_PROLOG_1; 

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
                drawHorizontalButtons(16, 4, 2, "Move", "Noise", "Stats", "Eat", "Rest", "Bestiary","Quit");
                drawArrow(1, 65, Game.getPlayer().getFaceDirection());
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
            case COMBAT:
                renderGame(1, 2);
                int x = Game.getPlayer().getX();
                int y = Game.getPlayer().getY();

                List<Character> enemies = Game.getEnemySymbolsInCell(x, y);
                
                drawBoxWithText(13, 2, "  Combat Mode! with: "+enemies.toString() + "  ");
                drawHorizontalButtons(16, 4, 2, "Move", "Talk", "Attack", "Stats", "Eat", "Bestiary");
                break;
            case GAME_OVER:
                drawBoxWithText(2, 2, "                            YOU ARE DEAD                       ");
                drawBoxWithText(8, 2, "                            GAME OVER...                      ");
                drawHorizontalButtons(12, 4, 2, "Quit");
                break;
            case STATS:
                break;
            case SCENE_PROLOG_1:
                PrintAtlas();
                drawBoxWithText(1, 1, "You are a wizard looking for something magic on the caves, you find atlas");
                drawBoxWithText(5, 1, "and he stabs your eyes");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case SCENE_PROLOG_2:
                drawBoxWithText(1, 1, "You try to run away from him");
                drawBoxWithText(5, 1, "but he keeps following you");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case SCENE_PROLOG_3:
                drawBoxWithText(1, 1, "Now you have to scape from the cave");
                drawBoxWithText(5, 1, "and avoid the Creepy atlas catch you");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
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
        System.out.println("STATE: "+state);
        currentState = state;
    }

    public static ScreenState getState() {
        return currentState;
    }

    public static void PrintAtlas()
    {
        System.out.println("#############################################################################");
        System.out.println("@@@@@@@@@@@@@@@@%%%%%@@%@@#=*-.=-==-                                        #");
        System.out.println("@@@@@@@@@@@@@@@@%%%%%%%@@@#+*-.=-=-:                                        #");
        System.out.println("@@@@@@@@@@@@@@@@%%%%%%%%@@*+#::-==-:                                        #");
        System.out.println("@@@@@@@@@@@@@@@@%%%%%%%%%@*+#-:====.                                        #");
        System.out.println("@@@@@@@@@@@@@@@@%%%%%%@%%@*+*:............:.                                #");
        System.out.println("@@@@@@@@@@@@@@@@%%%%%%%%%%-........:..........::                            #");
        System.out.println("@@@@@@@@@@@@@@@@%%%%%@%#=................ ....:-::                          #");
        System.out.println("@@@@@@@@@@@@@@@@*%%%%%*:-..-.::.............:.:=+-::                        #");
        System.out.println("@@@@@@@@@@@@@@@@%%%%#=:-..:-:.::........:..-=---=--:.::.                    #");
        System.out.println("@@@@@@@@@@@@@@@@%%#..:==:+#*+:::...:....:-=+*%...:=-::--:                   #");
        System.out.println("@@@@@@@@@@@@@@@@#::.:-=..  -+--..:::-...:+==##@@@*=----=+#-                 #");
        System.out.println("@@@@@@@@@@@@@@@@*::.:==*@@@@++...........=*#@#@@@@#===+**==-. :             #");
        System.out.println("@@@@@@@@@@@%*+::::::-:#@@@@@+:-........::-:=@@@@@%*%**#@*-=*-..             #");
        System.out.println("@@@@@@@@@@@%@@#-----+#-*##*=+-:-.......-.:-+*=:-:+*#*#+=%**+=-              #");
        System.out.println("@@@@@@@@@@@@@@#--:=:++*:::-=:-:::.:.::--::==-=++#++*-++#+#=++-              #");
        System.out.println("@@@@@@@@@@@@@#**#=--+:#*+=:-=-..:::::--:-:=:+==***#*-==#@%*++=.             #");
        System.out.println("@@@@@@@@@@@@@@@#%%+==:-**=-=-=.:::....::::-=+===*#**=+*=%#=*=#=             #");
        System.out.println("@@@@@@@@@@@@@@@%@@#:+=-+==+=+=::.:=-+==..:--=+++-+=**+#+*=--=*+             #");
        System.out.println("@@@@@@@@@@@@@@@##*+:*=:::=+*-:..=***#**-:.::-+=*++-:+*##@::--%*             #");
        System.out.println("@@@@@@@@@@@@@@@#*+=:#*-:=++-...+**+#**+*=:...-=**-::=%%*+:+*#%*             #");
        System.out.println("@@@@@@@@@@@@@@@@@#=-##...+*:.*@%@#%###**%%*:-=+##=-::%@##=#%@@*.            #");
        System.out.println("@@@@@@@@@@@@@@@@@##+%@+*=+*:+@%%+ :-==%%@@@@:-+***%*#@@%@#%%%@*:            #");
        System.out.println("@@@@@@@@@@@@@@@@@@=-=#%@@*+:@@@@@@@@@@@@@@@@-=++*#*%@@@%@%@#*#+             #");
        System.out.println("@@@@@@@@@@@@@@@@%+++==#%@-=:@@@@@@@@@@@@@@@@==+++%@@@@@@@%@*#+=             #");
        System.out.println("@@@@@@@@@@@@@@@@*=-#+#+-%++=:@@@@@@@@@@@@@@@+=*++@@-@@@@@##**++             #");
        System.out.println("@@@@@@@@@@@@@@%%++*%**=*%*=#=+@@@@@@@@@@@@#*++++%-@*@@@@@@**++*.            #");
        System.out.println("@@@@@@@%@%%@@%%%#**###*%%@##**+=@@@@@@@@@%%#+*+%@#@@*%@@@@@#*%%=            #");
        System.out.println("@@@@%@%%%%%%%%%%%-**+=-++#+@%%%@@@@@@@@@@%**#*#@@@@@@@@@@@@@+  .            #");
        System.out.println("%%%%%%%%%%%%%%%##=+===*:==+%%@@#%@@@@@@@@%@@%@%@@@@@%@%%**%*+               #");
        System.out.println("%%%%%%%#########*-+++=-:-=++*#@@@@@@@@@@@@@@#@%@@@@@@%%#*-=+:.              #");
        System.out.println("%%%##%#########*::+--===:==*+=%@@@@@@@@@@@@@%%%@@@@@@@#%**+*.               #");
        System.out.println("###########*##*.::=--+*++:+++=+#@@@@@@@@@@@@%%%@@@@%%%@#==*++:              #");
        System.out.println("#######*##****..:--=+*%+:+++=*++*%@@@@@@@@@@%*%@@@@%%#%#++++=-.             #");
        System.out.println("######**#**#*=...:-=**=##-:=+**+**%@@@@@@@@@%%%@@@@%@#%*+***+=.             #");
        System.out.println("##***##******-:.:::-*+**+:-++**+**#%@@@%@@@%%@@@@@@%@#%%*+*#*+-             #");
        System.out.println("#**#********++::.:-=*****-:=+#***#%####%%###@@@@%%#@%%%%#*#**+-.            #");
        System.out.println("***#******++++-...::=+###*%--*#*##*#####***@@@@@@%%%%%%%#*#*+==:.       ..  #");
        System.out.println("#***********++-:....--+#%*#=++++####%*****@@@@@%%%@%%%%@%**++=-.        . ..#");
        System.out.println("******++***+==:--.::::-*#%**%--=+*@@@%%@@@@@%%%@@@@%%%%@@%*===:        .....#");
        System.out.println("***++++****+++-:=-.:.-==+*=%++%#*%%@@@%%@@@@@@%@@@@%%%%@%#+=-=      . ......#");
        System.out.println("********++=-#----::.:.+++*+*+++=#*#@@@%*@@@@@@@@@@%%#%@%#*=--+    ..........#");
        System.out.println("***++*++==:**+=-:-..:=-*=+*#=*=*=++%@@%#@@@@%@@@@@#%#%%#*++=*+ .     .....  #");
        System.out.println("+*++*++==-=#*===-:--=:+=++*%##+*--+@#@%%%@@@%@@@@@#%%%##*+*#*+  ..          #");
        System.out.println("++***+++=-#***+-=+*:-=--==+%%#*=-*%**@%%%@@@%@@@@%%%%##%##+*#-.     ...   ..#");
        System.out.println("**+*+++=--%##**=-=====--:=*%#%%*+@**%%%%@@%@%@@%@%##%###%##**:...:..::::::::#");
        System.out.println("++*+==-:::%#***#++-+++++-=+@#%%%#%##%**#%%@%@@%@%@@%%%#@##%#*. ...:::::-----#");
        System.out.println("+*+=--:..*%%***#===++++***#@##%###@@*==*##@%@%%%@%%@%%@%@%%#+.   .. .....   #");
        System.out.println("*+==-:...#%%#*#+*+*#++#+#*#@**##*%%*++=#*#%@%@@%@%%@@%@@%@%*+#@@=...   .....#");
        System.out.println("++-:::...%##**#+**#**#%*#+*%++##**+*+++**%%@%%%##%@@%%##%###-.::-==+*#%%%#..#");
        System.out.println("=-:::.::.+%*******#*%*#****#==+#%*++***#%%#@%%%%#%@%%%%%####=:.....::+%%#%*##");
    }

    public static void drawArrow(int top, int left, Direction dir) {

        // Definimos las flechas en ASCII (6x10)
        String[] arrow;

        switch (dir) {
            case UP:
                arrow = new String[]{
                    "    ^     ",
                    "   /|\\    ",
                    "  / | \\   ",
                    "    |     ",
                    "    |     ",
                    "    |     "
                };
                break;

            case DOWN:
                arrow = new String[]{
                    "    |     ",
                    "    |     ",
                    "    |     ",
                    "  \\ | /   ",
                    "   \\|/    ",
                    "    v     "
                };
                break;

            case LEFT:
                arrow = new String[]{
                    "          ",
                    "   /      ",
                    "  <----   ",
                    "   \\      ",
                    "          ",
                    "          "
                };
                break;

            case RIGHT:
                arrow = new String[]{
                    "          ",
                    "      \\   ",
                    "   ---->  ",
                    "      /   ",
                    "          ",
                    "          "
                };
                break;

            default:
                arrow = new String[]{
                    "          ",
                    "          ",
                    "   ???    ",
                    "          ",
                    "          ",
                    "          "
                };
                break;
        }

        // Dibuja la flecha en el buffer con top y left
        for (int y = 0; y < arrow.length; y++) {
            for (int x = 0; x < arrow[y].length(); x++) {
                char c = arrow[y].charAt(x);
                if (top + y < HEIGHT && left + x < WIDTH) {
                    buffer[top + y][left + x] = c;
                }
            }
        }
}

}
