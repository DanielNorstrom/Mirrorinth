package mirrorinth;

import javax.swing.*;

/**
 * Items are entities that can be picked up, used once, and then reapear on the map where the player picked it up.
 */
public abstract class Item extends GameObject implements Entity
{

    protected Maze maze;
    protected PlayerCharacter holder;

    protected Item(final int x, final int y, final Maze maze, final ImageIcon icon) {
	super(x,y,icon);
	this.maze = maze;
	holder = null;
    }

    @Override public void onTouch()
    {
        PlayerCharacter character = maze.getPlayerCharacterAt(x, y);
        if(!character.hasItem())
	{
	    holder = character;
	    holder.addItem(this);
	    maze.clearEntity(this);
	}
    }

    //Occurs when the player hits spacebar..
    public abstract void activate();

    //After an item is activated, it disapears from the player's inventory and reappears in the maze.
    public void reset()
    {
        maze.addEntity(this);
    }
}
