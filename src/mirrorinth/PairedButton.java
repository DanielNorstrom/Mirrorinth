package mirrorinth;

import javax.swing.*;

/**
 * Tile that should come in pairs (shared channel). When both are pressed down,
 * linked tiles are activated, and the two buttons disapear.
 */
public class PairedButton extends Tile
{
    private Maze maze;
    private PairedButton connectedButton = null;
    private static final ImageIcon ICON = IconMaker.tileIconMaker("Magic_circle");


    public PairedButton(final int x, final int y, final String channel, final Maze maze) {
        super(x, y, true, channel, ICON);
	this.maze = maze;
    }

    @Override public void link(final Tile t) {
        if(!t.equals(this)) {
            if(t.getClass().equals(PairedButton.class)){
                this.connectedButton = (PairedButton) t;
	    }
	    linkedTiles.add(t);
	}
    }

    @Override public void onStep() {
	if(!maze.getCharactersAt(connectedButton.x,connectedButton.y).isEmpty())
	{
	    for(Tile tile: linkedTiles)
	    {
	        tile.activate();
	    }
	    connectedButton.activate();
	    this.activate();
	}
    }

    @Override public void activate(){
	    maze.setTile(x, y, new BasicTile(x, y, true, ""));
	}
    }

