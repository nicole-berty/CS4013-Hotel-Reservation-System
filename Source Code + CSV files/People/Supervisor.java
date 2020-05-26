package People;

import Reservation.*;

import java.util.ArrayList;

/**
 * @author: Marcin Sek
 */

public class Supervisor extends Person {
    private double wages;

    /**
     * Default constructor
     */
    public Supervisor(){}

    /**
     * Create a supervisor object
     * @param name the name of the supervisor
     * @param email the email of the supervisor
     * @param phone the phone number of the supervisor
     * @param wages the wages of the supervisor
     */
    Supervisor(String name, String email, String phone, double wages) {
        super.name = name;
        super.email = email;
        super.phone = phone;
        this.wages = wages;
    }

    /**
     * Set the wages of the supervisor
     * @param wages the supervisor's wages
     */
    private void setWages(double wages) {
        this.wages = wages;
    }

    /**
     * Get the wages of the supervisor
     * @return wages
     */
    private double getWages() {
        return this.wages;
    }

    /**
     * Give a discount on a specified reservation in a specified hotel
     * @param reservation a Reservation object
     * @param discount the discount amount to be applied
     * @param hotel the name of the hotel
     */
    public void giveDiscount(Reservation reservation, double discount, String hotel) {
        double cost = reservation.getTotalCost();
        double newPrice = cost * (1 - (discount / 100.0));
        ArrayList<Reservation> r1 = ReservationSystem.readFromCSV(hotel+"Reservations.csv");
        for (Reservation value : r1) {
            if (reservation.getReservationId().equals(value.getReservationId())) {
                value.setTotalCost(newPrice);
            }
        }
        Reservation.writeToCSV(hotel + "Reservations.csv",r1,true);
    }

    /**
     * A method converting supervisor details to a String
     * @return supervisor details as a String
     */
    @Override
    public String toString(){
        return "Name: " + getName() + "Phone: " + getPhone() + "Email: " + getEmail() + "Wage: " + this.wages;
    }
}
