package Reservation;

import L4.Room;
import L4.Hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author: Marcin Sek
 */

public class ReservationSystem
{
	/**
	 * Writes data into CSV files. This will create the file and add a header if it doesn't exist or if it is being overwritten.
	 * @param fileName the name of the file being written into
	 * @param reservations list of reservations that will be saved into the file
	 * @param overwrite option to overwrite the file instead of appending to it
	 */
	public static void writeToCSV(String fileName, ArrayList<Reservation> reservations, boolean overwrite)
	{
		try
		{
			File file = new File(fileName);
			StringBuffer data = new StringBuffer();
			PrintWriter printWriter;
			if (file.exists() && !overwrite)
			{
				printWriter = new PrintWriter(new FileOutputStream(file, true));
			}
			else
			{
				printWriter = new PrintWriter(file);
				if(fileName.contains("Cancellations"))
				{
					data.append("ID,Name,Number,Email,Check in Date,Cancellation Date,No. of Rooms, Room Types, Total Cost,Deposit,Advanced\n");
				}
				else
				{
					data.append("ID,Name,Number,Email,Check in Date,Stay Duration,No. of Rooms, Room Types,Total Cost,Deposit,Advanced\n");
				}
			}
			for (Reservation reservation : reservations)
			{
				data.append(reservation);
			}
			printWriter.write(data.toString());
			printWriter.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Reads data from CSV file
	 * @param filename name of the file being read
	 * @return list of reservations read from the file
	 */
	public static ArrayList<Reservation> readFromCSV(String filename)
	{
		ArrayList<Reservation> details = new ArrayList<>();
		try
		{
			File File = new File(filename);
			Scanner input = new Scanner(File);
			if (input.hasNextLine())
			input.nextLine();
			while (input.hasNext())
			{
				String[] fields = input.nextLine().split(",", 11);
				String[] roomDetails = fields[7].split("/", 100);
				ArrayList<Room> rooms = new ArrayList<>();
				for (int i = 0; i < roomDetails.length - 1; i++)
				{
					String[] data = roomDetails[i].split("_", 3);
					rooms.add(new Room(data[0], data[1], Boolean.getBoolean(data[2])));
				}

				if (filename.contains("Cancellation"))
				{
					Cancellation cancellation = new Cancellation(fields[0], fields[1], fields[2], fields[3], LocalDate.parse(fields[4]), LocalDate.now(), rooms, Double.parseDouble(fields[8]), Boolean.parseBoolean(fields[10]));
					details.add(cancellation);
				}
				else
				{
					Reservation reservations = new Reservation(fields[0], fields[1], fields[2], fields[3], LocalDate.parse(fields[4]), Integer.parseInt(fields[5]), rooms, Double.parseDouble(fields[8]), Boolean.parseBoolean(fields[10]));
					details.add(reservations);
				}
			}
			input.close();
		}
		catch (IOException error)
		{
			System.out.println("Error in reading from csv");
		}
		return details;
	}

	/**
	 * Reads data from CSV in search for a specific reservation
	 * @param reservationNum the reservation number by which the correct reservation is identified
	 * @param filename the name of the file being read by the method
	 * @return Reservation with the appropriate reservation number
	 */
	public static Reservation readReservation(String reservationNum, String filename)
	{
		Reservation reservation = null;
		try
		{
			File file = new File(filename);
			Scanner input = new Scanner(file);
			if (input.hasNextLine())
			{
				input.nextLine();
			}

			while(input.hasNextLine())
			{
				String line = input.nextLine();
				String[] fields = line.split(",", 11);
				if (fields[0].equals(reservationNum))
				{
					String[] roomDetails = fields[7].split("/", 100);
					ArrayList<Room> rooms = new ArrayList<>();
					for (int i = 0; i < roomDetails.length - 1; i++)
					{
						String[] data = roomDetails[i].split("_", 3);
						rooms.add(new Room(data[0], data[1], Boolean.getBoolean(data[2])));
					}
					reservation = new Reservation(fields[0], fields[1], fields[2], fields[3], LocalDate.parse(fields[4]),
							Integer.parseInt(fields[5]), rooms, Double.parseDouble(fields[8]), Boolean.parseBoolean(fields[10]));
					break;
				}
			}
			input.close();
		}
		catch(IOException error)
		{
			System.out.println(error.toString());
		}
		return reservation;
	}

	/**
	 * Deletes a single reservation from CSV
	 * @param reservation the reservation being deleted
	 * @param hotelName name of the hotel in which the reservation was made
	 */
	public static void deleteReservation(Reservation reservation, String hotelName) {
		Cancellation cancellation = new Cancellation(reservation,hotelName);
		ArrayList<Reservation> list = readFromCSV(hotelName + "Reservations.csv");
		for (int i = 0; i < list.size(); i++) {
			if (reservation.getReservationId().equals(list.get(i).reservationId)) {
				list.remove(i);
				i--;
			}
		}
		writeToCSV(hotelName + "Reservations.csv", list, true);
	}

	/**
	 * Makes sure that all CSV files are up to date. Deletes stays from 7+ years ago.
	 * Deletes cancellations from 30+ days ago. Moves reservations to stays after 30 days.
	 * @param today today's date used to compare check in, out and cancellation dates
	 * @param hotels list of hotels the check is happening for
	 */
	public static void checkCSV(LocalDate today, ArrayList<Hotel> hotels) {
		for (Hotel hotel : hotels)
		{
			ArrayList<Reservation> reservations = readFromCSV(hotel.getName() + "Reservations.csv");
			ArrayList<Reservation> stays = readFromCSV(hotel.getName() + "Stays.csv");
			ArrayList<Reservation> cancellations = readFromCSV(hotel.getName() + "Cancellations.csv");

			for (int i = 0; i < stays.size(); i++)
			{
				Reservation stay = stays.get(i);
				if (stay.getCheckOutDate().plusYears(7).compareTo(today) <= 0)
				{
					stays.remove(i);
					i--;
				}
			}

			for (int i = 0; i < cancellations.size(); i++)
			{
				Cancellation cancellation = (Cancellation) cancellations.get(i);
				if (cancellation.getCancellationDate().plusDays(30).compareTo(today) <= 0)
				{
					cancellations.remove(i);
					i--;
				}
			}

			for (int i = 0; i < reservations.size(); i++)
			{
				Reservation reservation = reservations.get(i);
				if (reservation.getCheckInDate().compareTo(today) <= 0)
				{
					reservations.remove(i);
					stays.add(reservation);
					i--;
				}
			}

			writeToCSV(hotel.getName() + "Reservations.csv", reservations, true);
			writeToCSV(hotel.getName() + "Stays.csv", stays, true);
			writeToCSV(hotel.getName() + "Cancellations.csv", cancellations, true);
		}
	}
}
