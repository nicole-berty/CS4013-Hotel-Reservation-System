package People;

/**
 * @author: Michele Cavaliere
 */

public class Customer extends Person {

    /**
     * Default constructor
     */
    public Customer() {}

    /**
     * Create a customer object
     * @param name the name of the customer
     * @param email the email of the customer
     * @param phone the phone number of the customer
     */
    public Customer(String name, String email, String phone) {
        super.name = name;
        super.email = email;
        super.phone = phone;
    }

    /**
     * A method converting customer details to a String
     * @return customer details as a String
     */
    @Override
    public String toString(){
        return "Name: " + getName() + "Email: " + getEmail() + "Phone: " + getPhone();
    }
}