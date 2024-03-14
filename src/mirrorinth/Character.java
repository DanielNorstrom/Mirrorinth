package mirrorinth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Abstract superclass to hold the basic character data and functionality, such as position, or the ability to walk one step.
 */
public abstract class Character
{
    protected int x;
    protected int y;
    protected int walkOffsetX;
    protected int walkOffsetY;
    protected Direction direction;
    protected ImageIcon[] images;
    protected Maze maze;
    protected int stunDuration;
    private Timer timer;

    protected Character(int x, int y, Maze maze, ImageIcon[] images) {
	this.x = x;
	this.y = y;
	this.maze = maze;
	this.direction = Direction.DOWN;
	this.images = images;
	//The walk offset values are pixel values used to smoothly move characters between tiles.
	walkOffsetX = 0;
	walkOffsetX = 0;
	stunDuration = 0;
	//Timer to handle walk animations (currently gliding across the floor).
	timer = new Timer(Config.ANIMDELAY, new ActionListener() {
					    public void actionPerformed(ActionEvent e) {
					    walkOffsetX += direction.getX();
					    walkOffsetY += direction.getY();
					    if(walkOffsetX >= Config.TILESIZE ||walkOffsetX <= -Config.TILESIZE ||walkOffsetY >= Config.TILESIZE ||walkOffsetY <= -Config.TILESIZE)
					    {
						finishMove();
						timer.stop();
					    }
					    }});
	timer.setRepeats(true);
    }

    protected void handleCollisions()
    {
        maze.getTile(x, y).onStep();
	if(maze.getEntity(x,y) != null){
	    maze.getEntity(x,y).onTouch();
	}
	if(maze.getCharactersAt(x,y).size()>1){
	    for(Character character : maze.getCharactersAt(x,y)) {
		if (!character.equals(this)) {
		    handleTouch(character);

		}
	    }
	}
    }

    protected boolean tryMove(Direction dir)
    {
	direction = dir;
        if(!canMove())
	{
	    stunDuration -= 1;
	    return false;
	}
        else if(maze.inbounds(x + dir.getX(), y + dir.getY()) && maze.getTile(x + dir.getX(), y + dir.getY()).isTraversable())
	{
	    timer.start();
	    return true;
	}
	else
	{
	    return false;
	}
    }

    protected void finishMove()
    {
	x += direction.getX();
	y += direction.getY();
	walkOffsetX = 0;
	walkOffsetY = 0;
	handleCollisions();
    }



    protected abstract void handleTouch(Character character);



    protected void turn(Direction dir) {
	direction = dir;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public Direction getDirection() {
	return direction;
    }

    public boolean canMove(){
        return !(stunDuration > 0);
    }

    public void setX(final int x) {
	this.x = x;
    }

    public void setY(final int y) {
	this.y = y;
    }

    public int getWalkOffsetX()
    {
        return walkOffsetX;
    }

    public int getWalkOffsetY()
    {
        return walkOffsetY;
    }

    public ImageIcon getIcon()
    {
	switch(direction)
	{
	    case UP:
		return images[0];
	    case RIGHT:
		return images[1];
	    case DOWN:
		return images[2];
	    case LEFT:
		return images[3];
	}
	return null;
    }

    public void applyStun() {
        if(canMove()) {
	    stunDuration = 5;
	}
    }

    public abstract void onTouch();

    public abstract void handleFire();
}


