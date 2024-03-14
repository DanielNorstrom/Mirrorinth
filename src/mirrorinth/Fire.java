package mirrorinth;

import javax.swing.*;

/**
 * Item to burn the space in front of the character who holds it.
 * Destroys cobwebs, stunns enemies, and kills player characters.
 */
public class Fire extends Item
{
    private static final ImageIcon ICON = IconMaker.entityIconMaker("Orb_red");

    public Fire(final int x, final int y, final Maze maze) {
	super(x, y, maze, ICON);
    }

    @Override public void activate() {
        int x = holder.getX() + holder.getDirection().getX();
	int y = holder.getY() + holder.getDirection().getY();
	Entity entity = maze.getEntity(x, y);
	if(entity != null && entity.getClass().equals(Cobweb.class))
	{
	    maze.clearEntity(entity);
	}
	Iterable<Character> characters = maze.getCharactersAt(x, y);
	for(Character character: characters)
	{
	    character.handleFire();
	}
	holder.removeItem();
	reset();
    }
}
