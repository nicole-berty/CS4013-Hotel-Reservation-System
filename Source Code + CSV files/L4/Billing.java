package L4;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author: Sean Lynch
 */


public class Billing extends Price {

    /**
     * Calculate the price of the hotel stay
     * @param hotel specified hotel to calculate price from
     * @param rooms array list of rooms included in the booking
     * @param stayDuration the duration of the stay
     * @param checkIn the check in date for the reservation
     * @param roomTypes the types of room included in the booking
     * @param AP whether it is an advanced purchase reservation or not
     * @return price of the stay
     */
    public double calculatePrice(Hotel hotel,
                                 ArrayList<Room> rooms,
                                 int stayDuration,
                                 LocalDate checkIn, ArrayList<Room> roomTypes, boolean AP) {
        double price = 0;
        int day = checkIn.getDayOfWeek().getValue() - 1;
        double[][] prices = hotel.getPrices();
        int row = 0;
        for (Room room : rooms) {
            for (int i = 0; i < roomTypes.size(); i++) {
                if (room.equals(roomTypes.get(i))) {
                    row = i;
                }
            }
            for (int j = 0; j < stayDuration; j++) {
                if (day > 6) {
                    day = 0;
                }
                price += prices[row][day];
                day++;
            }
        }
        if (AP) {
            price *= .95;
        }
        return price;
    }
}