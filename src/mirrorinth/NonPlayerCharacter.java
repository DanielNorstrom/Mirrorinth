package mirrorinth;

import javax.swing.*;

/**
 * Abstract superclass to handle mob enemies.
 */
public abstract class NonPlayerCharacter extends Character
{


    protected NonPlayerCharacter(final int x, final int y, final Maze maze, final ImageIcon[] images) {
        super(x, y, maze, images);
    }

    @Override public void onTouch() {
	maze.resetLevel();
    }

    @Override protected void handleTouch(final Character character) {
    	if(character.getClass().equals(PlayerCharacter.class))
	{
	    this.onTouch();
	}
    }

    //Very simple pathfinding.
    protected void moveTo(Tile tile)
    {
	if(x < tile.getX()) {
	    tryMove(Direction.RIGHT);
	}
	else if(x > tile.getX()){
	    tryMove(Direction.LEFT);
	}
	else if(y < tile.getY()){
	    tryMove(Direction.DOWN);
	}
	else if(y > tile.getY()){
	    tryMove(Direction.UP);
	}
	else{
	    changePatrol();
	}
    }

    @Override public void handleFire()
    {
        this.applyStun();
    }

    @Override public void applyStun()
    {
	stunDuration = 10;
    }

    //Is called when the character reaches its destination.
    public abstract void changePatrol();

    public abstract void onTick();
}
