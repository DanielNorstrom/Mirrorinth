package mirrorinth;

import javax.swing.*;

/**
 * All nonmoving objects extends this
 */

public abstract class GameObject
{
    protected int x,y;
    protected ImageIcon icon;

    protected GameObject(final int x, final int y, final ImageIcon icon) {
	this.x = x;
	this.y = y;
	this.icon = icon;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

    public ImageIcon getIcon() {
	return icon;
    }
}
