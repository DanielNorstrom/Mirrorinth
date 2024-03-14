package mirrorinth;


import javax.swing.*;

/**
 * Entities are stationary objects placed on top of tiles, inluding things such as cobwebs,
 * and also items (Teleporters and switches are tiles, not entities).
 */
public interface Entity
{
    int getX();
    int getY();
    ImageIcon getIcon();
    void onTouch();
}
