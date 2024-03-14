package mirrorinth;

import javax.swing.*;

/**
 * Class to handle the two player characters. Always two of these should be created.
 */
public class PlayerCharacter extends Character
{
    private Item item;

    public PlayerCharacter(int x, int y, final String name, Maze maze) {
        super(x,y,maze, loadCharacterImages(name));
	this.item = null;

    }

    private static ImageIcon[] loadCharacterImages(String name)
    {
        return IconMaker.getCharacterIcons(name);
    }

    public Item getItem() {
	return item;
    }

    public boolean hasItem()
    {
        return item != null;
    }

    public void removeItem()
    {
        item = null;
    }

    public void addItem(Item item)
    {
        this.item = item;
    }

    @Override protected void handleTouch(final Character character) {
	character.onTouch();
    }

    @Override public void onTouch() {
	maze.win();
    }

    @Override public void handleFire() {
	maze.resetLevel();
    }

    @Override protected void finishMove()
    {
    	x += direction.getX();
    	y += direction.getY();
    	walkOffsetX = 0;
    	walkOffsetY = 0;
    	handleCollisions();
    	maze.setInputEnabled(true);
    }
}


