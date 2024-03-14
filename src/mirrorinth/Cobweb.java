package mirrorinth;

import javax.swing.*;

/**
 * Applies a stun effect to characters who walk on top of it.
 * Can be destroyed by fire.
 */
public class Cobweb extends GameObject implements Entity
{
    private Maze maze;
    private static final ImageIcon ICON = IconMaker.entityIconMaker("Cobweb");

    public Cobweb(final int x, final int y, Maze maze) {
	super(x,y,ICON);
	this.maze = maze;
    }

    @Override public void onTouch() {
	for (Character character : maze.getCharacters())
	    if (character.getX() == x && character.getY() == y) {
	    	character.applyStun();
	    }
    }
}
