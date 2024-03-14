package mirrorinth;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Skeleton mob enemy that will wait until a player character enters its line of sight,
 * and will then walk to where the player character was seen, before walking back home.
 * If it sees a player character in several locations, it will walk to each of these in order before returning home.
 */
public class Skeleton extends NonPlayerCharacter
{
    private List<Tile> linkedTiles;
    private Tile home;
    private Direction initialDirection;
    private static ImageIcon[] icon = IconMaker.getCharacterIcons("Skeleton");


    public Skeleton(final int x, final int y, final Maze maze) {
	super(x, y, maze, icon);
	linkedTiles = new ArrayList<>();
	home = maze.getTile(x,y);
	initialDirection = Direction.DOWN;
    }


    @Override public void changePatrol() {
	if(!linkedTiles.isEmpty())
	{
	    linkedTiles.remove(0);
	}
	else
	{
	    turn(initialDirection);
	}
    }

    @Override public void onTick() {
        PlayerCharacter pc = findPlayer();
        if(pc != null)
	{
	    link(maze.getTile(pc.getX(), pc.getY()));
	}
	if(!canMove())
	{
	    stunDuration -= 1;
	}
	if(!linkedTiles.isEmpty())
	{
	    moveTo(linkedTiles.get(0));
	}
	else {
	    moveTo(home);
	}
    }

    //Looks straight forward for player characters. Stops if it sees a wall first.
    private PlayerCharacter findPlayer()
    {
	for (int i = 0; i < max(maze.getWidth(), maze.getHeight()); i++) {
	    int checkX =  x + i*direction.getX();
	    int checkY =  y + i*direction.getY();
	    if(!maze.inbounds(checkX, checkY) || !maze.getTile(checkX, checkY).isTraversable())
	    {
	        return null;
	    }
	    PlayerCharacter player = maze.getPlayerCharacterAt(checkX, checkY);
	    if(player != null)
	    {
	        return player;
	    }
	}
	return null;
    }

    private void link(Tile tile)
    {
        linkedTiles.add(tile);
    }
}
