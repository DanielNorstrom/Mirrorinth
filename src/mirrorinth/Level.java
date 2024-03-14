package mirrorinth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to load level data from a text file.
 * The first row of a text file should be either a blank row, or a single integer;
 * the integer determines vision radius and enables "fog of war", whereas leaving it blank disables the fog.
 *
 * The rest of the text file should be a 2d grid of integers, sometimes alongside letters.
 * The letters represent channels, used to link tiles together,
 * whereas the integers determine what type of tile, entity, or character should be placed there.
 *
 * 0: BasicTile floor.
 * 1: BasicTile wall.
 * 2: Floor + Cie.
 * 3: Floor + Vie.
 * 4: ToggleButton.
 * 5: PairedButton.
 * 6: Lava.
 * 7: Teleporter.
 * 8: Floor + Cobweb.
 * 9: Floor + WallSkip
 * 10: Floor + Fire.
 * 11: Floor + Snake.
 * 12: Floor + Skeleton.
 */
public class Level
{
    private int width;
    private int height;
    private PlayerCharacter cie = null, vie = null;
    private List<Character> characters;
    private List<NonPlayerCharacter> npcs;
    private Entity[][] entities;
    private int[][] tileCodes;
    private Maze maze;
    private Tile[][] tiles;
    private boolean fog;
    private int visionRadius;

    public Level(String levelFilePath, Maze maze) throws IOException
    {
	characters = new ArrayList<>();
	npcs =  new ArrayList<>();
        this.maze = maze;
	List<ArrayList<String>> channels = new ArrayList<>();
	//Read the selected map file.
	List<ArrayList<Integer>> rows;
	try (BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(levelFilePath))))
	{
	    String line = br.readLine();
	    if (!line.isEmpty())
	    {
		fog = true;
		visionRadius = Integer.valueOf(line);
	    } else
	    {
		fog = false;
	    }
	    line = br.readLine();
	    int y = 0;
	    rows = new ArrayList<>();
	    //read one line after the other until out of lines.
	    while (line != null)
	    {
		String[] stringRow = line.split(",");
		width = stringRow.length;
		ArrayList<Integer> row = new ArrayList<>();
		ArrayList<String> rowChannels = new ArrayList<>();
		//Loop through the row's worth of strings, separating channels (letters) from tiles/entities/characters (numbers).
		for (String stringTile : stringRow)
		{
		    //If channel present.
		    if (stringTile.matches(".*[a-z]"))
		    {
			rowChannels.add(stringTile.substring(stringTile.length() - 1, stringTile.length()));
			stringTile = stringTile.substring(0, stringTile.length() - 1);
		    } else
		    {
			rowChannels.add("");
		    }
		    row.add(Integer.valueOf(stringTile));
		}
		rows.add(row);
		channels.add(rowChannels);
		line = br.readLine();
		y++;
	    }
	    height = y;
	}
	//Convert 2d ArryList of Integer to 2d array of int. Code taken from StackOverflow user Pimp Trizkit.
	//https://stackoverflow.com/questions/10043209/convert-arraylist-into-2d-array-containing-varying-lengths-of-arrays
	tileCodes = rows.stream().map(u -> u.stream().mapToInt(i -> i).toArray()).toArray(int[][]::new);

	//Create a 2d array of proper tiles.
	tiles = new Tile[width][height];
	entities = new Entity[width][height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
		tiles[x][y] = decode(x, y, channels.get(y).get(x));
            }
        }
        //Link together tiles that share channels.
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		if(!tiles[x][y].getChannel().isEmpty())
		{
		    for (int yy = 0; yy < height; yy++) {
			for (int xx = 0; xx < width; xx++) {
			    if(tiles[xx][yy].getChannel().equals(tiles[x][y].getChannel()))
			    {
				tiles[x][y].link(tiles[xx][yy]);
			    }
			}
		    }
		    for(NonPlayerCharacter npc: npcs)
		    {
		        if(npc.getClass().equals(Snake.class))
			{
			    Snake npcSnake = (Snake)npc;
			    if(tiles[x][y].getChannel().equals( npcSnake.getChannel())) {
			        npcSnake.link(tiles[x][y]);
			    }
			}
		    }
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

    public int getWidth() {
        return width;
    }

    public boolean isFog() {
	return fog;
    }

    public Iterable<NonPlayerCharacter> getNpcs()
    {
        return npcs;
    }

    public int getHeight() {
        return height;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Entity[][] getEntities() {
	return entities;
    }

    private Tile decode(int x, int y, String channel)
    {
        switch(tileCodes[y][x])
	{
	    case 0:
	        return new BasicTile(x, y,true, channel);
	    case 1:
	        return new BasicTile(x, y,false, channel);
	    case 2:
		cie = new PlayerCharacter(x, y, "Cie", maze);
		characters.add(cie);
		return new BasicTile(x, y,true, channel);
	    case 3:
		vie = new PlayerCharacter(x, y, "Vie", maze);
		characters.add(vie);
	        return new BasicTile(x, y,true, channel);
	    case 4:
	        return new ToggleButton(x, y, channel);
	    case 5:
	        return new PairedButton(x,y,channel,maze);
	    case 6:
	        return new Lava(x,y,channel,maze);
	    case 7:
	        return new Teleporter(x,y,channel,maze);
	    case 8:
	        entities[x][y] = new Cobweb(x,y, maze);
	        return new BasicTile(x,y,true,channel);
	    case 9:
	        entities[x][y] = new WallSkip(x,y, maze);
	        return new BasicTile(x,y,true,channel);
	    case 10:
	        entities[x][y] = new Fire(x,y, maze);
	        return new BasicTile(x,y,true,channel);
	    case 11:
	        Snake snake =new Snake(x, y, channel, maze);
	        characters.add(snake);
	        npcs.add(snake);
	        return new BasicTile(x,y,true,channel);
	    case 12:
	        Skeleton skeleton = new Skeleton(x, y, maze);
	        characters.add(skeleton);
	        npcs.add(skeleton);
	        return new BasicTile(x,y,true,channel);
	    default:
	        throw new IndexOutOfBoundsException("custom");
	}
    }
    public PlayerCharacter getCie() {
    	return cie;
        }
    public PlayerCharacter getVie() {
    	return vie;
        }
}