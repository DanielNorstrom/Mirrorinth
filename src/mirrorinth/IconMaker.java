package mirrorinth;

import javax.swing.*;
import java.awt.*;

/**
 * Creates icons for all objects and returns apropriate data to classes
 */

public final class IconMaker
{
    private IconMaker() {}

    private static ImageIcon characterIconMaker(String dir, String name){
       return new ImageIcon(
		    new ImageIcon(IconMaker.class.getResource("resources/" + name + "/" + name + "_" + dir + "_1.png")).getImage().getScaledInstance(Config.SPRITESIZE, Config.SPRITESIZE, Image.SCALE_SMOOTH));

        }


    public static ImageIcon[] getCharacterIcons(String name)
    {
	return new ImageIcon[]
		{
		characterIconMaker("up",name),
		characterIconMaker("right",name),
		characterIconMaker("down",name),
		characterIconMaker("left",name)};
	}

    public static ImageIcon entityIconMaker(String name)
    {
	return new ImageIcon(new ImageIcon(IconMaker.class.getResource("resources/Environment/" + name + ".png")).getImage()
				     .getScaledInstance(Config.SPRITESIZE, Config.SPRITESIZE, Image.SCALE_SMOOTH));
    }

    public static ImageIcon tileIconMaker(String name){
        return new ImageIcon(new ImageIcon(IconMaker.class.getResource("resources/Environment/" + name + ".png")).getImage()
						   .getScaledInstance(Config.TILESIZE, Config.TILESIZE, Image.SCALE_SMOOTH));
	   
    }
}
