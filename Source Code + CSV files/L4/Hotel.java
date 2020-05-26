package L4;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author: Michele Cavaliere and Nicole Berty
 */

public class Hotel extends L4 {

    private String name, location;
    private String rating;
    int[] noOfRooms;
    private ArrayList<Room> RoomTypes = new ArrayList<Room>();
    double[][] prices;

    public Hotel(){}

    /**
     * Creates a hotel object.
     * @param name the name of the hotel
     * @param location the location of the hotel
     * @param rating the hotel's rating, from 1-5 stars
     * @param roomDetails an array containing the room details
     * @param noOfRooms an array containing the number of rooms in the hotel
     */
    Hotel(String name, String location, String rating, String[] roomDetails, int[] noOfRooms){
        this.name = name;
        this.location = location;
        this.rating = rating;
        this.noOfRooms = noOfRooms;
        createRooms(roomDetails,noOfRooms);
        createFiles();
        prices = new double[noOfRooms.length][7];
        getPriceFromCSV();
    }

    /**
     * Creates the reservations, cancellations and stays CSV files for the system.
     */
    private void createFiles() {
        try {
            File reservations = new File(name + "Reservations.csv");
            reservations.createNewFile();
            File cancellations = new File(name + "Cancellations.csv");
            cancellations.createNewFile();
            File stays = new File(name + "Stays.csv");
            stays.createNewFile();
        }catch (IOException e) {
            System.out.println("File creation error");
        }
    }

    /**
     *
     * @return an array of prices
     */
    public double[][] getPrices() {
        return prices;
    }

    /**
     * Reads the prices from the CSV file.
     */
    private void getPriceFromCSV() {
        try {
            double[][] prices = this.prices;
            String temp;
            File file = new File("L4.csv");
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                temp = scanner.nextLine();
                if (temp.contains(name)) {
                    for (int i = 1; i < getRoomTypes().size(); i++) {
                        temp += scanner.nextLine();
                    }
                    String[] info = temp.split(",");
                    int x = 0;
                    String[] price = new String[7*getRoomTypes().size()];
                    for (String s : info) {
                        if (s.contains(".")) {
                            price[x] = s;
                            x++;
                        }
                    }
                    int counter = 0;
                    for (int i = 0; i < price.length/7; i++) {
                        for (int j = 0; j < 7; j++) {
                            prices[i][j] = Double.parseDouble(price[counter]);
                            counter++;
                        }
                    }
                }
            }
        }catch (IOException e) {
            System.out.println("Error in getting prices");
        }
    }

    /**
     * @return hotel rating
     */
    String getRating() {
        return this.rating;
    }

    /**
     * @return hotel name
     */
    public String getName(){
        return this.name;
    }

    /**
     * @return hotel location
     */
    String getLocation(){
        return this.location;
    }

    /**
     * Creates rooms in the hotel
     * @param RoomDetails the types of rooms
     * @param numOfRooms the number of rooms
     */
    private void createRooms(String[] RoomDetails, int[] numOfRooms) {
        for (int i = 0; i < numOfRooms.length; i++) {
            for (int j = 0; j < numOfRooms[i]; j++) {
                String[] dataFields = RoomDetails[i].split(",");
                Room room = new Room(dataFields[0], dataFields[1], Boolean.parseBoolean(dataFields[2]));
                if (j == 0) {
                    RoomTypes.add(room);
                }
            }
        }
    }

    /**
     * Get the types of rooms
     * @return room types
     */
    public ArrayList<Room> getRoomTypes() {
        return RoomTypes;
    }

    /**
     * Set the prices
     * @param prices a double array of the hotel prices
     */
    public void setPrices(double[][] prices) {
        this.prices = prices;
    }

    /**
     * Get the hotel details as a String
     * @return hotel's details in String form
     */
    @Override
    public String toString(){
        return "Name: " + getName() + "Location: " +
                getLocation() +"Rating: " + getRating() +
                "Number of Rooms: ";
    }
}
