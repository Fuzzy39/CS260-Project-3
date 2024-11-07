
public class Maze 
{
	private int width;
	private int height;
	private Cell[][] cells;
	
	
	public Maze(Cell[][] cells) 
	{
		width = cells.length;
		height = cells[0].length;
		this.cells = cells;
	}

}
