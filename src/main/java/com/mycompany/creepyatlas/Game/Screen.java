package com.mycompany.creepyatlas.Game;

import java.util.List;

import com.mycompany.creepyatlas.Enums.Enums.Direction;
import com.mycompany.creepyatlas.Enums.Enums.ScreenState;
import com.mycompany.creepyatlas.Game.Entities.Enemy;

public class Screen {
    private static final int WIDTH = 77;
    private static final int HEIGHT = 20;
    private static ScreenState currentState = ScreenState.SCENE_PROLOG_1; 

    private static char[][] screenBuffer = new char[HEIGHT][WIDTH];

    private static void fillBackground() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                screenBuffer[y][x] = '#';
            }
        }
    }

    public static void render() {
        fillBackground();

        switch (currentState) {
            case BASE:
                renderGame(1, 2);
                drawBoxWithText(13, 2, "                          Commands                         ");
                drawBoxWithText(8, 65, Game.getPlayer().getHealth()+" <3");
                drawArrow(1, 65, Game.getPlayer().getFaceDirection());
                drawHorizontalButtons(16, 4, 2, "Move", "Noise", "Stats", "Eat", "Rest", "Bestiary","Quit");
                break;

            case MOVE_COMMANDS:
                renderGame(1, 2);
                drawBoxWithText(13, 2, "                            Move + ...                       ");
                drawHorizontalButtons(16, 2, 2, "Left", "Right", "Up", "Down");
                drawBoxWithText(8, 65, Game.getPlayer().getHealth()+" <3");
                drawArrow(1, 65, Game.getPlayer().getFaceDirection());
                break;
            case NOISE_COMMANDS:
                renderGame(1, 2);
                drawBoxWithText(13, 2, "                            Noise + ...                       ");
                drawHorizontalButtons(16, 2, 2, "Burp", "Scream");
                drawBoxWithText(8, 65, Game.getPlayer().getHealth()+" <3");
                drawArrow(1, 65, Game.getPlayer().getFaceDirection());
                break;
            case COMBAT:
                renderGame(1, 2);
                int x = Game.getPlayer().getPositionX();
                int y = Game.getPlayer().getPositionY();

                List<Enemy> enemies = Game.getEnemiesInCell(x, y);

                StringBuilder enemyInfo = new StringBuilder();
                for (Enemy enemy : enemies) {
                    enemyInfo.append("{"+enemy.getSymbol())
                            .append(": ")
                            .append(enemy.getHealth()).append(" <3, ")
                            .append(enemy.getMentalHealth()).append(" !!")
                            .append("}"); 
                }

                drawBoxWithText(13, 2, "  Combat Mode! with: " + enemyInfo.toString() + "  ");
                drawHorizontalButtons(16, 4, 2, "Forgive", "Attack", "Stats", "Eat", "Bestiary");
                drawBoxWithText(8, 65, Game.getPlayer().getHealth() + " <3");
                drawBoxWithText(8, 65, Game.getPlayer().getHealth() + " <3");
                drawArrow(1, 65, Game.getPlayer().getFaceDirection());
                break;
            case GAME_OVER:
                drawBoxWithText(2, 2, "                            YOU ARE DEAD                       ");
                drawBoxWithText(8, 2, "                            GAME OVER...                      ");
                drawHorizontalButtons(12, 4, 2, "Continue", "Quit");
                break;
            case STATS:
                break;
            case SCENE_PROLOG_1:
                PrintAtlas();
                drawBoxWithText(1, 1, "You awaken in the underground of Celestia.");
                drawBoxWithText(4, 1, "From the shadows emerges Atlas, the guardian of this forsaken realm.");
                drawBoxWithText(7, 1, "With a roar, he tears the light from your eyes-");
                drawBoxWithText(10, 1, "LEAVING YOU IN ETERNAL DARKNESS!");

                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case SCENE_PROLOG_2:
                drawBoxWithText(1, 1, "But you are not broken.");
                drawBoxWithText(4, 1, "Your memory is sharper than any blade, and though you wander blind,");
                drawBoxWithText(7, 1, "you recall every path, every wall, every faint whisper of the dungeon.");
                drawBoxWithText(10, 1, "Sight is lost, but your mind sketches a map of what lies ahead.");

                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case SCENE_PROLOG_3:
                drawBoxWithText(1, 1, "Thus begins your descent - hunted by beasts, mocked by spirits,");
                drawBoxWithText(5, 1, "and forever haunted by the echo of Atla's laugh...");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_NEUTRAL_1:
                drawBoxWithText(1, 1, "You have escaped the cave!");
                drawBoxWithText(5, 1, "During your escape, some monsters fell, others survived.");
                drawBoxWithText(9, 1, "Atlas still waits in the depths... awaiting your return.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_NEUTRAL_2:
                drawBoxWithText(1, 1, "The light outside welcomes you.");
                drawBoxWithText(5, 1, "Your story in the caves will be told with fear and respect.");
                drawBoxWithText(9, 1, "Neither hero nor villain... just a survivor.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_NEUTRAL_3:
                drawBoxWithText(1, 1, "You left behind beasts...");
                drawBoxWithText(5, 1, "Some live to stalk other travelers.");
                drawBoxWithText(9, 1, "Your destiny is still marked by the shadow of Atlas.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_GENOCIDE_1:
                drawBoxWithText(1, 1, "You have slaughtered everything you found in the cave!");
                drawBoxWithText(5, 1, "Chubby's body lies lifeless, and BiYah curses your name.");
                drawBoxWithText(9, 1, "The walls echo with the sound of your violence.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_GENOCIDE_2:
                drawBoxWithText(1, 1, "You escaped, but the price was destruction.");
                drawBoxWithText(5, 1, "Neither the Hydra, nor even Ana could stop your fury.");
                drawBoxWithText(9, 1, "The air outside smells of ash and guilt.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_GENOCIDE_3:
                drawBoxWithText(1, 1, "You have survived... but at the cost of everything else.");
                drawBoxWithText(5, 1, "The caves of Celestia are stained with blood.");
                drawBoxWithText(9, 1, "Atlas roars in the distance, fueled by your brutality.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_PACIFIST_1:
                drawBoxWithText(1, 1, "You escaped the cave without spilling any blood!");
                drawBoxWithText(5, 1, "Chubby jumps happily, BiYah sings of your exploits.");
                drawBoxWithText(9, 1, "Even Ana smiles, grateful for your compassion.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_PACIFIST_2:
                drawBoxWithText(1, 1, "Daylight illuminates your exit.");
                drawBoxWithText(5, 1, "The beasts remember your kindness, and now you are a legend.");
                drawBoxWithText(9, 1, "Atlas himself doubts whether you are an enemy or a worthy ally.");
                drawBoxWithText(13, 1, "                          Commands                         ");
                drawHorizontalButtons(16, 4, 2, "Ok");
                break;
            case END_SCREEN_PACIFIST_3:
                drawBoxWithText(1, 1, "You have proven that compassion is also power.");
                drawBoxWithText(5, 1, "The creatures of Celestia unite in your honor.");
                drawBoxWithText(9, 1, "Your journey is just beginning, as protector of the kingdom.");
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
                screenBuffer[startY + y][startX + x] = cam[y][x];
            }
        }
    }

    public static void drawBoxWithText(int top, int left, String text) {
        int boxWidth = text.length();
        int boxHeight = 2;
        if (top < 0 || left < 0 || top + boxHeight >= HEIGHT || left + boxWidth >= WIDTH) {
            return;
        }
        screenBuffer[top][left] = '+';
        screenBuffer[top][left + boxWidth+1] = '+';
        screenBuffer[top + boxHeight][left] = '+';
        screenBuffer[top + boxHeight][left + boxWidth+1] = '+';

        for (int x = left + 1; x <= left + boxWidth; x++) {
            screenBuffer[top][x] = '-';
            screenBuffer[top + boxHeight][x] = '-';
        }

        for (int y = top + 1; y < top + boxHeight; y++) {
            screenBuffer[y][left] = '|';
            screenBuffer[y][left + boxWidth+1] = '|';
        }

        int textRow = top + boxHeight / 2;
        int textStart = left + 1;
        for (int i = 0; i < text.length() && textStart + i <= left + boxWidth; i++) {
            screenBuffer[textRow][textStart + i] = text.charAt(i);
        }
    }

    public static void drawHorizontalButtons(int top, int startLeft, int gap, String... texts) {
        int left = startLeft;
        for (String text : texts) {
            drawBoxWithText(top, left, text);
            left += text.length() + 2 + gap;
        }
    }

    public static void display() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(screenBuffer[y][x]);
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

        for (int y = 0; y < arrow.length; y++) {
            for (int x = 0; x < arrow[y].length(); x++) {
                char c = arrow[y].charAt(x);
                if (top + y < HEIGHT && left + x < WIDTH) {
                    screenBuffer[top + y][left + x] = c;
                }
            }
        }
}

}
