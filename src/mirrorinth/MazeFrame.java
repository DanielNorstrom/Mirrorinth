package mirrorinth;

import javax.swing.*;
import java.awt.*;

/**
 * Class to create a window and display MazeComponent.
 */
public class MazeFrame extends JFrame
{

    public MazeFrame(final Maze maze) throws HeadlessException {
	super("Mirrorinth");
	MazeComponent mazeComponent = new MazeComponent(maze);
	setLayout(new BorderLayout());
	add(mazeComponent, BorderLayout.CENTER);
	pack();
	setVisible(true);
    }
}
