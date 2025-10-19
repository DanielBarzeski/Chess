package App;

public class Game {
    private static boolean FINISHED = true, PAUSED = true, WHITE = true, RESTARTING, VISIBLE;

    public static void START() {
        PAUSED = false;
        FINISHED = false;
        System.out.println("THE GAME HAS STARTED!");
    }

    public static void END() {
        PAUSED = true;
        FINISHED = true;
        System.out.println("THE GAME IS FINISHED!");
    }

    public static boolean isFINISHED() {
        return FINISHED;
    }

    public static boolean isPAUSED() {
        return PAUSED;
    }

    public static void setPAUSED(boolean PAUSED) {
        Game.PAUSED = PAUSED;
    }

    public static boolean isRESTARTING() {
        return RESTARTING;
    }

    public static void setRESTARTING(boolean RESTARTING) {
        Game.RESTARTING = RESTARTING;
    }

    public static boolean isVISIBLE() {
        return Game.VISIBLE;
    }

    public static void setVISIBLE(boolean VISIBLE) {
        Game.VISIBLE = VISIBLE;
    }


    public static boolean isPLAYER_WHITE() {
        return WHITE;
    }

    public static void setWHITE_PLAYER(boolean WHITE) {
        Game.WHITE = WHITE;
    }
}