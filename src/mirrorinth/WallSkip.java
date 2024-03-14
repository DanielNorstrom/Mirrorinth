package mirrorinth;

import javax.swing.*;

/**
 * Teleports character two tiles forward, can travel through usually impassable tiles.
 */
public class WallSkip extends Item
{
    private final static ImageIcon ICON = IconMaker.entityIconMaker("Orb_blue");

    public WallSkip(final int x, final int y, final Maze maze) {
	super(x, y, maze, ICON);
    }

    @Override public void activate() {
        int x = holder.getX() + 2*holder.getDirection().getX();
	int y = holder.getY() + 2*holder.getDirection().getY();
	if(maze.inbounds(x, y) && maze.getTile(x, y).isTraversable()) {
	    holder.setX(x);
	    holder.setY(y);
	}
	holder.handleCollisions();
	holder.removeItem();
	reset();
    }
}
