
public enum Direction {
	NORTH(0),
	SOUTH(1),
	EAST(2),
	WEST(3);

	
	private int value;
	Direction(final int i) {
		value= i;
	}
	
	public int getValue()
	{
		return value;
	}
}
