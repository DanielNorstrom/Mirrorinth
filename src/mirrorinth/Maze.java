package mirrorinth;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that holds the main data and funtionality of the game.
 */
public class Maze
{
    private int width, height;
    private int levelCounter;
    private Tile[][] tiles;
    private Iterable<Character> characters;
    private PlayerCharacter[] playerCharacters;
    private Iterable<NonPlayerCharacter> npcs;
    private Entity[][] entities;
    private Level level;
    private boolean inputEnabled;
    private boolean fogEnabled;
    private int visionRadius = 100;
    static final int MAXLEVEL = 7;
    //Timer to execute npc actions on an interval. stored as a class global incase functions such as pause are implemented
    private Timer mobTimer = new Timer(Config.MOBDELAY, new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            for(NonPlayerCharacter npc: npcs){
                npc.onTick();
	    }
	}
    });

    public Maze() {
        levelCounter = 1;
        level = fetchLevel();
        loadLevel(level);
        //Activate mob AI.
	mobTimer.setRepeats(true);
	mobTimer.start();
	inputEnabled = true;
    }


    private void nextLevel()
    {
	levelCounter++;
	level = fetchLevel();
	loadLevel(level);

    }

    public void resetLevel() {
	System.out.println("Restarting level");
	loadLevel(fetchLevel());

    }

    private void loadLevel(Level level)
    {
	if (level == null) {
	    //Out of levels.
	    gameEnd();
	}
	//If a level is loaded there will be a height.
	width = level.getWidth();
	height = level.getHeight();
	tiles = level.getTiles();
	characters = level.getCharacters();
	playerCharacters = new PlayerCharacter[] { level.getCie(), level.getVie() };
	entities = level.getEntities();
	npcs = level.getNpcs();
	fogEnabled = level.isFog();
	visionRadius = level.getVisionRadius();
    }

    private Level fetchLevel()
    {
        if(levelCounter > MAXLEVEL) {
            gameEnd();
	}

	int tries = 0;
	int maxTries = 2;
	while (true) {
	    try {
	        return new Level("maps/level_" + levelCounter + ".txt", this);
	    } catch (IOException ignore) {
		tries++;
	        if (tries >= maxTries) {
		    System.out.println("Level could not be read, trying next level");
	            levelCounter++;
	            return fetchLevel();
		    }
		}
	    }
    }

    public int getVisionRadius() {
	return visionRadius;
    }

    public Iterable<Character> getCharacters() {
	return characters;
    }

    public PlayerCharacter[] getPlayerCharacters() {
	return playerCharacters;
    }

    public List<Character> getCharactersAt(int x, int y)
    {
        List<Character> characterList = new ArrayList<>();
	for (Character character : characters) {
	    if (character.getX() == x && character.getY() == y) {
	        characterList.add(character);
	    }
	}
	return characterList;
	}

    public PlayerCharacter getPlayerCharacterAt(int x, int y)
    {
	for (PlayerCharacter character : playerCharacters) {
	    if (character.getX() == x && character.getY() == y) {
		return character;
	    }
	}
	return null;
    }

    protected int getWidth() {
	return width;
    }

    protected int getHeight() {
	return height;
    }

    public boolean isFogEnabled() {
	return fogEnabled;
    }

    public void setInputEnabled(final boolean inputEnabled) {
	this.inputEnabled = inputEnabled;
    }

    public void setTile(int x, int y, Tile tile) {
	tiles[x][y] = tile;
    }

    public Tile getTile(int x, int y) {
	return tiles[x][y];
    }

    public Entity getEntity(int x, int y)
    {
	return entities[x][y];
    }

    public void clearEntity(Entity entity)
    {
	entities[entity.getX()][entity.getY()] = null;
    }

    public void addEntity(Entity entity)
    {
	entities[entity.getX()][entity.getY()] = entity;
    }


    public void useItems()
    {
        if(inputEnabled) {
	    for (PlayerCharacter character : playerCharacters) {
		if (character.hasItem()) {
		    character.getItem().activate();
		}
	    }
	}
    }

    protected void moveCharacters(Direction dir)
    {
        if(inputEnabled) {
	    if (playerCharacters[0].tryMove(dir)) {
		//if Cie moved, also try to move Vie (tryMove() returns true if the character moved), and disable input while characters are moving.
		inputEnabled = false;
	        playerCharacters[1].tryMove(dir.opposite());
	    }
	}
    }

    public void gameEnd()
    {
	System.out.println("You win!!!");
	System.exit(0);
    }

    protected boolean inbounds(int x, int y)
    {
	return (x >= 0 && x < width && y >= 0 && y < height);
    }

    protected void win()
    {
	nextLevel();
    }
}
