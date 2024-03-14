package mirrorinth;

import javax.swing.*;

/**
 * Floor or wall tile, with funcionality to switch between the two.
 */
public class BasicTile extends Tile
{
    private static final ImageIcon FLOORICON = IconMaker.tileIconMaker("Floor_stone");
    private static final ImageIcon WALLICON = IconMaker.tileIconMaker("Wall_stone");

    protected BasicTile(final int x, final int y, boolean traversable, final String channel) {
	super(x, y, traversable, channel, null);
	setIcon();
    }

    private void setIcon()
    {
	if(this.traversable){
	    this.icon = FLOORICON;
	}
	else{
	    this.icon = WALLICON;
	}
    }


    @Override protected void onStep() {
        //Regular floor tiles do nothing when stepped on.
    }

    //If wall, become floor, vice versa.
    @Override protected void activate() {
	traversable = !traversable;
	setIcon();
    }

    @Override protected void link(final Tile t) {
        //Regular floor tiles can't activate other tiles, and thus do not need links to other tiles.
    }
}
