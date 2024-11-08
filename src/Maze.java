
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
	
	public Cell getCell(int x, int y) {return cells[x][y];}
	
	public int getWidth() { return width; }
	public int getHeight() { return height; }

}
