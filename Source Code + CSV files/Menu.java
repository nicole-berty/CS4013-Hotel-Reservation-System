import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import Analytics.DataAnalysis;
import L4.Hotel;
import L4.*;
import L4.Room;
import People.Customer;
import People.Supervisor;
import Reservation.*;

import static Reservation.ReservationSystem.readReservation;
import static Reservation.ReservationSystem.checkCSV;

/**
 * @author: Sean Lynch, Michele Cavaliere, Marcin Sek, Nicole Berty
 */

class Menu {

	private Scanner scanner = new Scanner(System.in);
	private String[] hotels;
	private String hotelChosen;
	private LocalDate dateToday = LocalDate.now();
	private Hotel hotel = new Hotel();
	private ArrayList<Room> rooms = new ArrayList<>();
	private int numOfNights;
	private L4 chain = new L4();
	private String[] YesNo = new String[]{"Yes", "No"};

	/**
	 * Initialise the program by creating the 3 hotels in the L4 chain -  a 3, 4, and 5 star hotel.
	 * Write the details of these hotels to the L4 CSV file
	 */
	private void initialise() {
		chain.addHotel("Mighele's Paradise", "Tipperary", "5",
				new String[]{"Bachelor Suite,1,true", "Two to Tango Suite,2,true",
						"Bring the Family Suite,5,true"},new int[]{20, 30, 30});
		chain.addHotel("SeanVille Suites", "Limerick", "4",
				new String[]{"Single Room,1,true", "Double Room,2,false", "Family Room,5,true"}, new int[]{30, 40, 30});
		chain.addHotel("Marcin's Motel", "Clare", "3",
				new String[]{"Deluxe Room,3,true", "Single Room,1,false", "Double Room,2,false"}, new int[]{5, 10, 8});
		hotels = chain.hotelList();
		L4.writeHotelDetailsToCSV("L4.csv",chain.getL4());
	}

	/**
	 * Run the program
	 */
	void run() {
		initialise();
		boolean AP = true;
		boolean run = true;
		while (run) {
			checkCSV(dateToday,chain.getL4());
			System.out.println("Welcome to the L4 Hotel System!");
			System.out.println("Please choose the hotel you would like to access: ");
			hotelChosen = getOptions(hotels).toString();
			for (int i = 0; i < chain.getL4().size(); i++) {
				if (hotelChosen.equals(chain.getL4().get(i).getName())) {
					hotel = chain.getL4().get(i);
				}
			}
			System.out.println("Please choose a user: ");
			System.out.println("A) Customer B) Staff C) Quit");
			String choice = scanner.nextLine();
			choice = choice.toUpperCase();
			switch (choice) {
				case "A": {
					System.out.println("Please input: Name, Email, Phone");
					String info = scanner.nextLine();
					String[] split = info.split(",");
					Customer customer = new Customer(split[0], split[1], split[2]);
					System.out.println("Would you like to make a Reservation or a Cancellation?");
					Object[] options = new Object[]{"Reservation", "Cancellation"};
					Object option = getOptions(options);
					assert option != null;
					if (option.equals("Reservation")) {
						System.out.println("Would you like to make an advanced purchase booking for 5% off?");
						option = getOptions(YesNo);
						if (option.toString().equals("No")) {
							AP = false;
						}
						makeReservation(customer, AP);
						System.out.println("Would you like to use the system again?");
						option = getOptions(YesNo);
						if (option.equals("No")) {
							run = false;
						}
					} else {
						makeCancellation();
					}
					break;
				}
				case "B": {
					System.out.println("Please choose a staff member: ");
					Object[] options = {"Supervisor", "Administrator"};
					Object option = getOptions(options);
					switch (option.toString()) {
						case "Supervisor":
							System.out.println("What would you like to do?");
							options = new Object[]{"Make a reservation", "Make a cancellation", "Give a discount",
									"Request a data analysis", "Set weekly prices for room"};
							option = getOptions(options);
							switch (option.toString()) {
								case "Make a reservation":
									System.out.println("Please input customer's details as follows: Name, Email, Phone");
									String info = scanner.nextLine();
									String[] split = info.split(",");
									Customer customer = new Customer(split[0], split[1], split[2]);
									System.out.println("Would you like to make an advanced purchase booking for 5% off?");
									option = getOptions(YesNo);
									if (option.toString().equals("Yes")) AP = true;
									makeReservation(customer, AP);
									System.out.println("Would you like to use the system again?");
									option = getOptions(YesNo);
									if (option.equals("No")) {
										run = false;
									}
									break;
								case "Make a cancellation":
									makeCancellation();
									System.out.println("Would you like to use the system again?");
									option = getOptions(YesNo);
									if (option.equals("No")) {
										run = false;
									}
									break;
								case "Give a discount":
									Supervisor supervisor = new Supervisor();
									System.out.println("Enter reservation number: ");
									choice = scanner.nextLine();
									System.out.println("Enter discount amount: ");
									double discount = Double.parseDouble(scanner.nextLine());
									supervisor.giveDiscount(readReservation(choice, hotelChosen + "Reservations.csv"),
											discount,hotelChosen);
									System.out.printf("A %4.2f%% discount has been applied to reservation number %s\n\n",
											discount, choice);
									System.out.println("Would you like to use the system again?");
									option = getOptions(YesNo);
									if (option.equals("No")) {
										run = false;
									}
									break;
								case "Request a data analysis":
									System.out.println("Enter dates for analytics ");
									String dateFormat   = "dd-MM-yyyy";
									System.out.println("from dd-mm-yyyy");
									LocalDate from = LocalDate.parse(scanner.nextLine(),DateTimeFormatter.ofPattern(dateFormat));
									System.out.println("to dd-mm-yyyy");
									LocalDate to = LocalDate.parse(scanner.nextLine(),DateTimeFormatter.ofPattern(dateFormat));
									DataAnalysis.writeDataAnalyticsToCSV(from,to,hotel);
									System.out.println("Would you like to use the system again?");
									option = getOptions(YesNo);
									if (option.equals("No")) {
										run = false;
									}
									break;
								case "Set weekly prices for room":
									System.out.println("Choose a room to alter the price:");
									Room room = (Room) getOptions(hotel.getRoomTypes().toArray());
									System.out.println("Input prices from Monday to Sunday in the form: 43,234,234,243,243,324,54");
									String[] pricesString = scanner.nextLine().replaceAll(" ", "").split(",");
									double[] prices = new double[7];
									for(int i = 0; i < 7; i++) {
										prices[i] = Double.parseDouble(pricesString[i]);
									}
									Price p = new Price();
									hotel.setPrices(p.setWeeklyPricesForRoom(room, prices, hotel.getPrices(), hotel.getRoomTypes()));
									System.out.println("New prices have been set.\n");
									break;
							}
							break;
						case "Administrator":
							System.out.println("Would you like to make a Reservation or a Cancellation?");
							options = new Object[]{"Make a reservation", "Make a cancellation"};
							option = getOptions(options);
							switch (option.toString()) {
								case "Make a reservation":
									System.out.println("Please input customer's details as follows: Name, Email, Phone");
									String info = scanner.nextLine();
									String[] split = info.split(",");
									Customer customer = new Customer(split[0], split[1], split[2]);
									System.out.println("Would you like to make an advanced purchase booking for 5% off?");
									option = getOptions(YesNo);
									if (option.toString().equals("Yes")) AP = true;
									makeReservation(customer, AP);
									System.out.println("Would you like to use the system again?");
									option = getOptions(YesNo);
									if (option.equals("No")) {
										run = false;
									}
									break;
								case "Make a cancellation":
									makeCancellation();
									System.out.println("Would you like to use the system again?");
									option = getOptions(YesNo);
									if (option.equals("No")) {
										run = false;
									}
									break;
							}
							break;
					}
					break;
				}
				case "C":
					run = false;
					break;
				default:
					System.out.println("Choice not recognised, choose again!");
					break;
			}
		}
		L4.writeHotelDetailsToCSV("L4.csv",chain.getL4());
	}

	/**
	 * Create letters starting from A for the users of the system to choose as options
	 * @param options the possible options for the user to choose
	 * @return the letter chosen from the options
	 */
	private Object getOptions(Object[] options) {
		if (options.length == 0) {
			return null;
		}
		char c = 'A';
		for (Object option : options) {
			System.out.println(c + ") " + option);
			c++;
		}
		String p = scanner.nextLine();
		char x = p.toUpperCase().charAt(0);
		int h = x - 65;
		return options[h];
	}

	/**
	 * Make a reservation. Includes getting all reservation details and writing them to the reservations CSV file
	 * @param customer the customer making the reservation
	 * @param AP whether the booking is an advanced purchase booking or not
	 */
	private void makeReservation(Customer customer, boolean AP) {
		boolean done = false;
		Object option = null;
		rooms.removeAll(rooms);
		while (!done) {
			System.out.println("What type of room would you like?");
			int hotelInArray = 0;
			for (int i = 0; i < chain.getL4().size(); i++) {
				if (chain.getL4().get(i).getName().equals(hotelChosen)) {
					option = getOptions(chain.getL4().get(i).getRoomTypes().toArray());
					hotelInArray = i;
				}
			}
			for (int i = 0; i < chain.getL4().get(hotelInArray).getRoomTypes().size(); i++) {
				assert option != null;
				if (option.equals(chain.getL4().get(hotelInArray).getRoomTypes().get(i))){
					rooms.add(chain.getL4().get(hotelInArray).getRoomTypes().get(i));
				}
			}
			System.out.println("Would you like to add another room?");
			option = getOptions(YesNo);
			if (option.equals("No")) {
				done = true;
			}
		}
		System.out.println("When would you like to check in?(dd-mm-yyyy)");
		String dateFormat   = "dd-MM-yyyy";
		LocalDate checkIn = LocalDate.parse(scanner.nextLine(),DateTimeFormatter.ofPattern(dateFormat));
		System.out.println("How many nights are you staying?");
		numOfNights = Integer.parseInt(scanner.nextLine());
		customer.makeReservation(checkIn, numOfNights, rooms, AP, hotel.getRoomTypes(), hotel, hotelChosen);
	}

	/**
	 * Make a cancellation. Removes the reservation from the reservations CSV file and moves it to the Cancellations CSV file
	 */
	private void makeCancellation() {
		Object option;
		System.out.println("Enter Reservation Number: ");
		option = scanner.nextLine();
		if ((readReservation(option.toString(), hotelChosen + "Reservations.csv") != null)) {
			Reservation re = readReservation(option.toString(), hotelChosen + "Reservations.csv");

			ReservationSystem.deleteReservation(re,hotelChosen);
			System.out.println("Reservation has been cancelled.\n");
		}
	}
}
