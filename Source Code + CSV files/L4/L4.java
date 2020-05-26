package L4;

import java.io.*;
import java.util.ArrayList;

/**
 * @author: Marcin Sek and Sean Lynch
 */

public class L4 {
    private ArrayList<Hotel> L4 = new ArrayList<>();

    /**
     * Add a hotel to the array list L4.
     * @param name the name of the hotel
     * @param location the location of the hotel
     * @param rating the hotel's rating, from 1-5 stars
     * @param roomDetails an array containing the room details
     * @param numOfRooms an array containing the number of rooms in the hotel
     */
    public void addHotel(String name, String location, String rating, String[] roomDetails, int[] numOfRooms) {
        Hotel h = new Hotel(name, location, rating, roomDetails, numOfRooms);
        L4.add(h);
    }

    /**
     * Removes a hotel from the array list L4.
     * @param index the number of the hotel in the L4 arraylist
     */
    public void removeHotel(int index) {
        L4.remove(index);
    }

    /**
     * Returns a list of hotels in the l4 chain.
     * @return array of hotels
     */
    public String[] hotelList() {
        String[] temp = new String[L4.size()];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = L4.get(i).getName();
        }
        return temp;
    }

    /**
     * @return L4 array list
     */
    public ArrayList<Hotel> getL4() {
        return L4;
    }

    /**
     * Writes the details of the specified hotel to a CSV file
     * @param filename the name of the file to write to
     * @param hotels an array list of the hotels in the chain
     */
    public static void writeHotelDetailsToCSV(String filename, ArrayList<Hotel> hotels) {
        try {
            File file = new File(filename);
            StringBuffer data = new StringBuffer();
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
            }catch (IOException e) {
                System.out.println("File creation error");
            }
            PrintWriter printWriter = new PrintWriter(file);
            data.append("Hotel Name, Hotel type, Room type, Number of Rooms, Occupancy-min, Occupancy-max, Rates\n");
            data.append(",,,,Adult-Child,Adult-Child,Monday,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday\n");
            for (Hotel hotel : hotels) {
                data.append(hotel.getName()).append(",").append(hotel.getRating()).append(",");
                for (int j = 0; j < hotel.prices.length; j++) {
                    if (j != 0) data.append(",,");
                    data.append(hotel.getRoomTypes().get(j)).append(",").append(hotel.noOfRooms[j])
                            .append(",1-0,").append(hotel.getRoomTypes().get(j).occupancy).append(",")
                            .append(hotel.prices[j][0]).append(",").append(hotel.prices[j][1]).append(",")
                            .append(hotel.prices[j][2]).append(",").append(hotel.prices[j][3]).append(",")
                            .append(hotel.prices[j][4]).append(",").append(hotel.prices[j][5]).append(",")
                            .append(hotel.prices[j][6]).append("\n");
                }
            }
            printWriter.write(data.toString());
            printWriter.close();
        }catch (IOException e) {
            System.out.println("Error in writing to csv");
        }
    }

}
