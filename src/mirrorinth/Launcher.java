package mirrorinth;

/**
 * Initializes and runs the game.
 */
public final class Launcher
{
    private Launcher() {}

    public static void main(String[] args) {
        new MazeFrame(new Maze());
    }
}