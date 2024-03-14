package mirrorinth;

/**
 * Holds four constant directions and int values to move one step in said direction.
 */
public enum Direction
{
    UP(0, -1), RIGHT(1, 0), DOWN(0, 1), LEFT(-1, 0);

    private final int x;
    private final int y;

    Direction(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int getY() {
	return y;
    }

    public int getX() {
	return x;
    }


    public Direction opposite()
    {
        switch(this)
	{
	    case UP:
		return DOWN;
	    case RIGHT:
		return LEFT;
	    case DOWN:
		return UP;
	    case LEFT:
		return RIGHT;
	}
	return null;
    }
}
