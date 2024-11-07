
/**
 * A Maze Representation is the string representation of a maze, and facilitates in
 * translating a maze to and from its representation.
 * A MazeRepresentation is immutable.
 */
public class MazeRepresentation 
{
	
	private int width;
	private int height;
	
	// Size should be width*2, height+1
	private char[][] representation;

	/**
	 * 
	 * @param width The width, in cells, of the Maze
	 * @param height the height, in cells, of the Maze.
	 * @param maze
	 */
	public MazeRepresentation(int width, int height, String maze) 
	{
		this.width = width;
		this.height = height;
		
		String[] rows = maze.split("\n");
		if(rows.length!= height+1)
		{
			throw new IllegalArgumentException("Wrong height for maze");
		}
		
		representation = new char[width*2][height+1];
		
		int i = 0;
		for(String s: rows)
		{
			char[] row = s.toCharArray();
			if(row.length!=width*2) 
			{
				throw new IllegalArgumentException("Malformed or wrong width for maze.");
			}
			representation[i] = row;
			i++;
		}
	}
	
	public MazeRepresentation(Maze m)
	{
		
	}
	
	private Cell getCell(int x, int y)
	{
		// the X of the center character of a cell is 2x+1
		int charx = 2*x +1;
		int chary = y+1;
		boolean north = representation[charx][chary-1] == ' ';
		boolean south = representation[charx][chary] == ' ';
		boolean east = representation[charx+1][chary] == ' ';
		boolean west = representation[charx-1][chary] == ' ';
		return new Cell(north, south, east, west);
	}
	
	private Cell[][] getCells()
	{
		Cell[][] toReturn = new Cell[width][height];
		for(int x = 0; x<width; x++)
		{
			for(int y = 0; y<height; y++)
			{
				toReturn[x][y] = getCell(x, y);
			}
		}
		
		return toReturn;
	}
	
	public Maze toMaze()
	{
		return new Maze(getCells());
	}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }
	
}
