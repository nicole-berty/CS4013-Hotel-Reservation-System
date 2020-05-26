package Reservation;

import L4.Room;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author: Sean Lynch
 */

public class Cancellation extends Reservation
{
    private LocalDate cancellationDate;

    /**
     * Default constructor
     */
    public Cancellation() {
    }

    /**
     * Create a cancellation object
     * @param reservationId the id of the reservation
     * @param reservationName the name on the reservation
     * @param number the phone number associated with the reservation
     * @param email the email associated with the reservation
     * @param checkIn the check in date for the reservation
     * @param cancellationDate the cancellation date for the cancellation
     * @param rooms the rooms included the reservation
     * @param totalCost the total cost of the reservation
     * @param advancedPurchase whether the booking was an advanced purchase reservation or not
     */
    public Cancellation(String reservationId, String reservationName, String number, String email, LocalDate checkIn,
                        LocalDate cancellationDate, ArrayList<Room> rooms, double totalCost, boolean advancedPurchase)
    {
        super(reservationId, reservationName, number, email, checkIn, 0, rooms, totalCost, advancedPurchase);
        this.cancellationDate = cancellationDate;
    }

    /**
     * Create a cancellation object
     * @param reservation a reservation object containing the reservation details
     * @param hotelChosen the name of the hotel to make the cancellation in
     */
    public Cancellation(Reservation reservation,String hotelChosen)
    {
        this.cancellationDate = LocalDate.now();
        ArrayList<Reservation> cancels = readFromCSV(hotelChosen+"Cancellations.csv");
		ArrayList<Reservation> reservations = readFromCSV(hotelChosen+"Reservations.csv");
		reservations.remove(reservation);
        cancels.add(reservation);
		writeToCSV(hotelChosen+"Cancellations.csv",cancels,true);
		writeToCSV(hotelChosen+"Reservations.csv", reservations, true);
		
    }

    /**
     * Get the cancellation date
     * @return the cancellation date
     */
    public LocalDate getCancellationDate()
    {
        return cancellationDate;
    }

    /**
     * A method converting the cancellation details to a suitable form for CSV files
     * @return all data fields in a format suitable for use in CSV files
     */
    public String toCSV()
    {
        return reservationId + "," + reservationName + "," + number + "," + email + "," + checkIn + "," + cancellationDate + "," + rooms.size() + "," + getRoomsAsString() + "," + totalCost + "," + deposit + "," + advancedPurchase + "\n";
    }
}
