package mirrorinth;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Snake mob enemy that patrols an area in a predefined pattern assigned through channels in the map text file.
 */
public class Snake extends NonPlayerCharacter
{
    private List<Tile> tiles;
    private String channel;
    //tilecounter determines which tile the snake will move to next.
    private int tileCounter;
    private static ImageIcon[] icon = IconMaker.getCharacterIcons("Snake");


    public Snake(final int x, final int y, String channel, final Maze maze) {
	super(x, y, maze, icon);
	this.channel = channel;
	this.tileCounter = 0;
	tiles = new ArrayList<>();
    }

    @Override public void onTick() {
        moveTo(tiles.get(tileCounter));
    }

    //Increases tileCounter by one. If TileCounter becomes too large, reset it.
    @Override public void changePatrol()
    {
	tileCounter++;
	if(tileCounter >= tiles.size())
	{
	    tileCounter = 0;
	}
    }

    protected String getChannel()
    {
	return channel;
    }

    protected void link(Tile tile)
    {
        tiles.add(tile);
    }
}
