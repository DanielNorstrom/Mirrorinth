package mirrorinth;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;


/**
 * Class to create visuals for the game. Displayed within MazeFrame.
 * Also handles keyboard input.
 */
public class MazeComponent extends JComponent
{
    private Maze maze;
    //Timer to update component regularly based on a frequency assigned in Config. stored as class global incase functions such as pause are implemented
    private Timer screenUpdateTimer;

    public MazeComponent(final Maze maze) {
	super();
	this.maze = maze;

	InputMap inMap = this.getInputMap();
	inMap.put(KeyStroke.getKeyStroke("UP"), "up");
	inMap.put(KeyStroke.getKeyStroke("RIGHT"), "right");
	inMap.put(KeyStroke.getKeyStroke("DOWN"), "down");
	inMap.put(KeyStroke.getKeyStroke("LEFT"), "left");
	inMap.put(KeyStroke.getKeyStroke("SPACE"), "space");

	ActionMap actMap = this.getActionMap();
	actMap.put("up", new UpKeyAction());
	actMap.put("right", new RightKeyAction());
	actMap.put("down", new DownKeyAction());
	actMap.put("left", new LeftKeyAction());
	actMap.put("space", new SpaceKeyAction());

	screenUpdateTimer = new Timer(Config.ANIMDELAY, new ActionListener()
	{
	    public void actionPerformed(ActionEvent e)
	    {
	        repaint();
	    }
	});
	screenUpdateTimer.setRepeats(true);
	screenUpdateTimer.start();
    }

    @Override public Dimension getPreferredSize()
    {
	return new Dimension(Config.RESX, Config.RESY);
    }

    @Override protected void paintComponent(Graphics g)
    {
	super.paintComponent(g);
	Graphics2D g2d = (Graphics2D) g;
	//draw tiles.
	int topOfMap = (Config.RESY - maze.getHeight() * Config.TILESIZE) / 2;
	int leftOfMap = (Config.RESX - maze.getWidth() * Config.TILESIZE) / 2;
	//These two integers make up the distance between the top left corner of the component
	//and the top left corner of the actual map.
	int spriteOffset = (Config.TILESIZE - Config.SPRITESIZE) / 2;
	//distance between the top left corner of a tile and the top left corner of a sprite,
	//neccessary because sprites are smaller than tiles.
	for (int y = 0; y < maze.getHeight(); y++) {
	    int offsetUp = y * Config.TILESIZE + topOfMap;
	    for (int x = 0; x < maze.getWidth(); x++) {
		int offsetLeft = x * Config.TILESIZE + leftOfMap;
		ImageIcon icon = maze.getTile(x, y).getIcon();
		icon.paintIcon(this, g2d, offsetLeft, offsetUp);
	    }
	}

	//draw character sprites.
	for (Character character : maze.getCharacters()) {
	    ImageIcon characterIcon = character.getIcon();
	    //Draw sprite in middle of appropriate tile.
	    characterIcon.paintIcon(this, g2d,
				    character.getX() * Config.TILESIZE +
				    leftOfMap + spriteOffset + character.getWalkOffsetX(),
				    character.getY() * Config.TILESIZE +
				    topOfMap + spriteOffset + character.getWalkOffsetY());
	}

	//draw entities
	for (int y = 0; y < maze.getHeight(); y++) {
	    for (int x = 0; x < maze.getWidth(); x++) {
		Entity entity = maze.getEntity(x, y);
		if(entity != null) {
		    ImageIcon icon = entity.getIcon();
		    icon.paintIcon(this, g2d,
				   entity.getX() * Config.TILESIZE + leftOfMap + spriteOffset,
				   entity.getY() * Config.TILESIZE + topOfMap + spriteOffset);
		}
	    }
	}

	//draw fog of war
	if(maze.isFogEnabled()) {
	    g2d.setColor(Color.BLACK);
	    Area field = new Area(new Rectangle(leftOfMap, topOfMap,
						maze.getWidth() * Config.TILESIZE,
						maze.getHeight() * Config.TILESIZE));

	    for (PlayerCharacter character : maze.getPlayerCharacters()) {
		field.subtract(new Area(new Ellipse2D.Double(
			character.getX() * Config.TILESIZE + leftOfMap +
			Config.TILESIZE / 2 - maze.getVisionRadius() / 2 + character.getWalkOffsetX(),
			character.getY() * Config.TILESIZE + topOfMap +
			Config.TILESIZE / 2 - maze.getVisionRadius() / 2 + character.getWalkOffsetY(),
			maze.getVisionRadius(), maze.getVisionRadius())));
	    }
	    g2d.fill(field);
	}

	//Add a slim border around the map. Reduces lag a bunch.
	Area field = new Area(new Rectangle(leftOfMap, topOfMap,
					    maze.getWidth() * Config.TILESIZE,
					    maze.getHeight() * Config.TILESIZE));

	field.subtract(new Area(new Rectangle(leftOfMap + 1, topOfMap + 1,
					      maze.getWidth() * Config.TILESIZE - 2,
					      maze.getHeight() * Config.TILESIZE - 2)));
	g2d.fill(field);
    }

    private class UpKeyAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    maze.moveCharacters(Direction.UP);
	}
    }

    private class RightKeyAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    maze.moveCharacters(Direction.RIGHT);
	}
    }

    private class DownKeyAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    maze.moveCharacters(Direction.DOWN);
	}
    }

    private class LeftKeyAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    maze.moveCharacters(Direction.LEFT);
	}
    }

    private class SpaceKeyAction extends AbstractAction {
	@Override public void actionPerformed(final ActionEvent e) {
	    maze.useItems();
	}
    }
}
