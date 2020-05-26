package L4;

import java.util.ArrayList;

/**
 * @author: Sean Lynch
 */

public class Price extends Hotel {

    private double[][] Prices;

    /**
     * Set the prices for different types of room for each day of the week
     * @param room the type of room you want to set the price for
     * @param NewPrices an array of the new prices for the week
     * @param OldPrices an array of the old prices for the week
     * @param RoomTypes an array list of the types of rooms in the hotel
     * @return old room prices
     */
    public double[][] setWeeklyPricesForRoom(Room room, double[] NewPrices,
                                      double[][] OldPrices, ArrayList<Room> RoomTypes) {
        int row = 0;
        for (int i = 0; i < RoomTypes.size(); i++) {
            if (room.toString().equals(RoomTypes.get(i).toString())) {
                row = i;
            }
        }
        System.arraycopy(NewPrices, 0, OldPrices[row], 0, NewPrices.length);
        this.Prices = OldPrices;
        return OldPrices;
    }

    /**
     * Get current hotel prices
     * @return current hotel prices
     */
    public double[][] getPrices() {
        return Prices;
    }
}