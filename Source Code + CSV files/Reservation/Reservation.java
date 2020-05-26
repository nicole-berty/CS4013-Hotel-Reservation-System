package Reservation;

import L4.Room;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author: Michele Cavaliere
 */


public class Reservation extends ReservationSystem {

	String reservationName, number, email, reservationId;
	private int duration;
	LocalDate checkIn, checkOut;
	double totalCost, deposit;
	boolean advancedPurchase;
	ArrayList<Room> rooms = new ArrayList<>();

	/**
	 * Default constructor
	 */
	public Reservation() {}

	/**
	 * Create a reservation object
	 * @param reservationId the id of the reservation
	 * @param reservationName the name on the reservation
	 * @param number the phone number associated with the reservation
	 * @param email the email associated with the reservation
	 * @param checkIn the check in date for the reservation
	 * @param duration the duration of the stay
	 * @param rooms the rooms included the reservation
	 * @param totalCost the total cost of the reservation
	 * @param advancedPurchase whether the booking was an advanced purchase reservation or not
	 */
	public Reservation(String reservationId, String reservationName, String number, String email,
						LocalDate checkIn, int duration, ArrayList<Room> rooms,
						double totalCost, boolean advancedPurchase) {
		this.reservationName = reservationName;
		this.reservationId = reservationId;
		this.number = number;
		this.email = email;
		this.duration = duration;
		this.advancedPurchase = advancedPurchase;
		this.checkIn = checkIn;
		this.rooms = rooms;
		this.totalCost = totalCost;
		this.deposit = totalCost * .15;
	}

	/**
	 * Get the reservation id
	 * @return reservation id
	 */
	public String getReservationId() {
		return reservationId;
	}

	/**
	 * Get reservation name
	 * @return reservation name
	 */
	String getReservationName() {
		return reservationName;
	}

	/**
	 * Get the phone number for the reservation
	 * @return phone number
	 */
	String getPhoneNumber()
	{
		return number;
	}

	/**
	 * Calculate reservation ID by dividing the current time in milliseconds by 1000
	 * @return reservation id
	 */
	public String getNextReservationId() {
		return "" + System.currentTimeMillis() / 1000;
	}

	/**
	 * Set check in date
	 * @param checkIn date of check in for the reservation
	 */
	public void setCheckIn(LocalDate checkIn){
		this.checkIn = checkIn;
	}

	/**
	 * Set the check out date
	 * @param checkOut date of check out for the reservation
	 */
	public void setCheckOut(LocalDate checkOut){
		this.checkOut = checkOut;
	}

	/**
	 * Get the reservation email
	 * @return email
	 */
	String getReservationEmail()
	{
		return this.email;
	}

	/**
	 * Get the list of rooms
	 * @return rooms
	 */
	public ArrayList<Room> getRooms() { return rooms; }

	/**
	 * Get the duration of the stay
	 * @return duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * Check whether it is an advanced purchase booking or not
	 * @return true or false indicating whether or not it is an advanced purchase booking
	 */
	boolean getAdvancedPurchase(){
		return this.advancedPurchase;
	}

	/**
	 * Set whether it is an advanced purchase booking or not
	 * @param set boolean indicating if it's advance purchased or not
	 */
	void setAdvancedPurchase(boolean set)
	{
		this.advancedPurchase = set;
	}

	/**
	 * Get the check in date for the reservation
	 * @return the check in date
	 */
	public LocalDate getCheckInDate()
	{
		return checkIn;
	}

	/**
	 * Get the check out date for the reservation
	 * @return the check out date
	 */
	LocalDate getCheckOutDate()
	{
		return checkIn.plusDays(duration);
	}

	/**
	 * Get the total cost for the reservation
	 * @return the total cost
	 */
	public double getTotalCost()
	{
		return totalCost;
	}

	/**
	 * set the total cost of the reservation
	 * @param cost the totsl cost of the stay
	 */
	public void setTotalCost(double cost)
	{
		totalCost = cost;
	}

	/**
	 * Get the deposit amount for the booking
	 * @return the deposit amount
	 */
	double getDeposit()
	{
		return deposit;
	}

	/**
	 * Get the rooms included in the reservation listed as a string
	 * @return rooms as a string
	 */
	String getRoomsAsString()
	{
		String temp = "";
		for (Room room : rooms)
		{
			temp += room.toCSV() + "/";
		}
		return temp;
	}

	/**
	 * The reservation details converted to a String
	 * @return reservation details in String form
	 */
	@Override
	public String toString() {
		return reservationId + "," + reservationName + "," + number + "," + email + "," + checkIn + "," + duration + "," + rooms.size() + "," + getRoomsAsString() + "," + totalCost + "," + deposit + "," + advancedPurchase + "\n";
	}
}