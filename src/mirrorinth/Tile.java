package mirrorinth;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Superclass for tiles.
 */
public abstract class Tile extends GameObject
{
    protected boolean traversable;
    protected String channel;
    protected List<Tile> linkedTiles;

    protected Tile(final int x, final int y, final boolean traversable, final String channel, final ImageIcon icon)
    {
	super(x,y,icon);
	this.traversable = traversable;
	this.channel = channel;
	linkedTiles = new LinkedList<>();
    }

    public boolean isTraversable()
    {
	return traversable;
    }

    public String getChannel()
    {
	return channel;
    }

    protected void link(Tile t)
    {
        if(!t.equals(this))
	{
	    linkedTiles.add(t);
	}
    }

    abstract void onStep();
    abstract void activate();
}
