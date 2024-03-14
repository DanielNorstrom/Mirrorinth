package mirrorinth;

import javax.swing.*;

/**
 * Tile that will teleport any character that touches is to another linked teleporter.
 * Should come in pairs, linked through channels.
 */
public class Teleporter extends Tile
{
    private Maze maze;
    private Teleporter exit = null;
    private static final ImageIcon ICON = IconMaker.tileIconMaker("Teleporter");

    public Teleporter(final int x, final int y, final String channel, final Maze maze) {
        super(x, y, true, channel, ICON);
	this.maze = maze;
    }

    @Override public void link(final Tile t) {
        //Doesn't affect linkedTiles because this tiles doesn't activate others.
	if (!t.equals(this) && t.getClass().equals(Teleporter.class)) {
	    this.exit = (Teleporter) t;
	}
    }

    @Override public void onStep() {
	Character character = maze.getPlayerCharacterAt(x, y);
	character.setX(exit.getX());
	character.setY(exit.getY());
    }

    @Override public void activate() {
        //Does not activate other tiles.
    }
}
