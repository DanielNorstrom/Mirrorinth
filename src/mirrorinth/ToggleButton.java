package mirrorinth;

import javax.swing.*;

/**
 * Button to activate linked tiles. Can be pressed multiple times.
 */
public class ToggleButton extends Tile
{
    private static final ImageIcon ICON1 = IconMaker.tileIconMaker("Lever_1");
    private static final ImageIcon ICON2 = IconMaker.tileIconMaker("Lever_2");


    public ToggleButton(final int x, final int y, final String channel) {
        super(x, y, true, channel, ICON1);
    }

    @Override public void onStep() {
        if (linkedTiles != null) {
	    for (Tile tile : linkedTiles) {
		tile.activate();
	    }
	}
	setIcon();
    }

    private void setIcon()
    {
	if(this.icon.equals(ICON1)){
            this.icon = ICON2;
	}
	else{this.icon = ICON1;}
    }

    @Override public void activate() {
        //Isn't ever activated.
    }
}
