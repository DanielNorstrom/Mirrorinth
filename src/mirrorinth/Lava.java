package mirrorinth;

import javax.swing.*;

/**
 * A dangerous tile that kills any characters who step onto it.
 */
public class Lava extends Tile
{
    private Maze maze;
    private static final ImageIcon ICON = IconMaker.tileIconMaker("Lava");

    public Lava(final int x, final int y, final String channel, final Maze maze) {
	super(x, y, true, channel, ICON);
	this.maze = maze;
    }

    @Override public void link(final Tile t) {
	//Lava does not activate other tiles and therefor doesn't need a link to any.
    }

    @Override public void onStep() {
	maze.resetLevel();
    }

    @Override public void activate() {
	//Never used
    }
}
