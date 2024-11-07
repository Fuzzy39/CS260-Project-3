
public class Cell 
{
	
	private boolean[] passableDirections;
	private boolean onPath;

	public Cell(boolean north, boolean south, boolean east, boolean west) {
		passableDirections = new boolean[]{north, south, east, west};
		onPath = false;
	}
	
	public boolean getOnPath() {return onPath;}
	public void setOnPath(boolean set) { onPath = set;}
	
	public boolean getPassable(Direction d)
	{
		return passableDirections[d.getValue()];
	}

}
