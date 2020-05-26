package L4;

/**
 * @author: Nicole Berty
 */

public class Room extends Hotel {

	public String type;
	String occupancy;
	private boolean breakfastIncluded;

	/**
	 * Default constructor
	 */
	Room(){}

	/**
	 * Creates a room object
	 * @param type the type of room
	 * @param occupancy the occupancy of the room
	 * @param breakfastIncluded whether breakfast is included in the room or not
	 */
   public Room (String type, String occupancy, boolean breakfastIncluded) {
		this.type = type;
		this.occupancy = occupancy;
		this.breakfastIncluded = breakfastIncluded;
	}

	/**
	 * Sets the type of room
	 * @param type the type of room, eg. single, double, etc.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the room type
	 */
	private String getType() {
		return type;
	}

	/**
	 * Sets the occupancy of a room
	 * @param occupancy how many people can sleep in a room
	 */
	void setOccupancy(String occupancy) {
		this.occupancy = occupancy;
	}

	/**
	 * Gets the occupancy of a room
	 * @return room occupancy
	 */
	private String getOccupancy() {
		return occupancy;
	}

	/**
	 * Set whether breakfast is included in a room.
	 * @param breakfastIncluded whether breakfast is included or not with the room
	 */
	void setBreakfastIncluded(boolean breakfastIncluded) {
		this.breakfastIncluded = breakfastIncluded;
	}

	/**
	 * @return true or false based on whether breakfast is included with a room
	 */
	private boolean isBreakfastIncluded() {
		return breakfastIncluded;
	}

	/**
	 * @return room details formatted for CSV file
	 */
	public String toCSV()
	{
		return getType() + "_" + getOccupancy() + "_" + isBreakfastIncluded();
	}

	/**
	 * A method converting room details to a String
	 * @return room details as a String
	 */
	@Override
	public String toString()
	{
		String breakfast = "/ Breakfast not included";
		if (isBreakfastIncluded())
		{
			breakfast = "/ Breakfast included";
		}
		return getType() + " " + breakfast;
	}
}
