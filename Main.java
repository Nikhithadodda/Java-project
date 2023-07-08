import java.util.InputMismatchException;
import java.util.Scanner;

class Flight {
    private String flightNumber;
    private String source;
    private String destination;
    private String flightType; // Domestic or International
    private int availableSeats;

    public Flight(String flightNumber, String source, String destination, String flightType, int availableSeats) {
        this.flightNumber = flightNumber;
        this.source = source;
        this.destination = destination;
        this.flightType = flightType;
        this.availableSeats = availableSeats;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getFlightType() {
        return flightType;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeats(int numSeats) {
        if (availableSeats >= numSeats) {
            availableSeats -= numSeats;
        }
    }
}

class PaymentModule {
    private static final String DESIRED_UPI_ID = "ns@upi";
    private static final String DESIRED_USERNAME = "ap0916";
    private static final String DESIRED_PASSWORD = "161834";
    private static final long DESIRED_CARD_NUMBER = 8284895969161834L;
    private static final String DESIRED_EXPIRY_DATE = "11/25";

    public static boolean processPayment() {
        System.out.println("--------------------------------Payment Portal--------------------------------\n\n");
        System.out.println("1. Credit/Debit Card\n");
        System.out.println("2. UPI\n");
        System.out.println("3. Net Banking\n");
        System.out.println("4. Cancel Payment\n");
		
        Scanner scanner = new Scanner(System.in);
        int paymentOption = scanner.nextInt();

        switch (paymentOption) {
            case 1:
                System.out.println("CREDIT/DEBIT CARD\n");
                System.out.print("Enter the Card Number:\n ");
                long cardNumber = scanner.nextLong();
                System.out.print("Enter the Expiry date (MM/YY):\n ");
                String expiryDate = scanner.next();
                System.out.print("Enter the CVV:\n");
                int cvv = scanner.nextInt();

                // Check if the entered card number and expiry date are valid
                if (isValidCardDetails(cardNumber, expiryDate)) {
                    // Process the card payment
                    System.out.println("Payment Successful!\n");
                    return true;
                } else {
                    System.out.println("Invalid card number or expiry date. Payment Failed.\n");
                    return false;
                }
            case 2:
                System.out.println("UPI");
                System.out.print("Enter the UPI ID:\n ");
                String upiId = scanner.next();

                // Check if the entered UPI ID is valid
                if (isValidUpiId(upiId)) {
                    // Process the UPI payment
                    System.out.println("Payment Successful!\n");
                    return true;
                } else {
                    System.out.println("Invalid UPI ID. Payment Failed.\n");
                    return false;
                }
            case 3:
                System.out.println("NET BANKING");
                System.out.print("Enter the username:\n ");
                String username = scanner.next();
                System.out.print("Enter the password:\n ");
                String password = scanner.next();

                // Check if the entered username and password are valid
                if (isValidCredentials(username, password)) {
                    // Process the net banking payment
                    System.out.println("Payment Successful!\n");
                    return true;
                } else {
                    System.out.println("Invalid username or password. Payment Failed.\n");
                    return false;
                }
            case 4:
                System.out.println("Payment Cancelled.\n");
                return false;
            default:
                System.out.println("Invalid payment option. Payment Failed.\n");
                return false;
        }
    }

    private static boolean isValidCardDetails(long cardNumber, String expiryDate) {
        // Validate card number and expiry date against desired values
        return cardNumber == DESIRED_CARD_NUMBER && expiryDate.equals(DESIRED_EXPIRY_DATE);
    }

    private static boolean isValidUpiId(String upiId) {
        // Validate UPI ID against desired value
        return upiId.equals(DESIRED_UPI_ID);
    }

    private static boolean isValidCredentials(String username, String password) {
        // Validate username and password against desired values
        return username.equals(DESIRED_USERNAME) && password.equals(DESIRED_PASSWORD);
    }
}

class FlightBookingSystem {
    private Flight[] flights;
    public FlightBookingSystem() {
        flights = new Flight[10];
        flights[0] = new Flight("SN1618", "Delhi", "Mumbai", "Domestic", 50);
        flights[1] = new Flight("NS1816", "Delhi", "New York", "International", 100);
        flights[2] = new Flight("AP0916", "Delhi", "London", "International", 75);
        flights[3] = new Flight("PA1609", "Mumbai", "Kolkata", "Domestic", 80);
        flights[4] = new Flight("DLN113", "Mumbai", "Singapore", "International", 120);
        flights[5] = new Flight("DLN054", "Chennai", "Delhi", "Domestic", 60);
        flights[6] = new Flight("N34511", "Chennai", "Dubai", "International", 90);
        flights[7] = new Flight("S11543", "Kolkata", "Bangalore", "Domestic", 70);
        flights[8] = new Flight("NPM169", "Kolkata", "Sydney", "International", 110);
        flights[9] = new Flight("S18054", "Bangalore", "Hyderabad", "Domestic", 65);
    }

    public void viewAvailableFlights() {
        System.out.println("Available Flights:\n");
        System.out.println("Flight Number   Source          Destination     Type              Available Seats");
        System.out.println("---------------------------------------------------------------------------------");
        for (Flight flight : flights) {
            System.out.printf("%-15s %-15s %-15s %-20s %d\n", flight.getFlightNumber(), flight.getSource(),
                    flight.getDestination(), flight.getFlightType(), flight.getAvailableSeats());
        }
        System.out.println();
    }

    public void bookDomesticFlight(String flightNumber, int numSeats) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber) && flight.getFlightType().equals("Domestic")) {
                if (flight.getAvailableSeats() >= numSeats) {
                    boolean paymentSuccessful = PaymentModule.processPayment(); // Process payment and check if it is successful
                    if (paymentSuccessful) {
                        flight.bookSeats(numSeats); // Decrease available seats
                        System.out.println("Booking Successful! Enjoy your flight.\n");
                    } else {
                        System.out.println("Payment cancelled. Booking failed. Seats remain unchanged.\n");
                        return; // Return without decreasing the available seats
                    }
                } else {
                    System.out.println("Not enough seats available on the selected flight.\n");
                }
                return;
            }
        }
        System.out.println("Invalid flight number or flight type.\n");
    }

    public void bookInternationalFlight(String flightNumber, int numSeats) {
        for (Flight flight : flights) {
            if (flight.getFlightNumber().equals(flightNumber) && flight.getFlightType().equals("International")) {
                if (flight.getAvailableSeats() >= numSeats) {
                    boolean paymentSuccessful = PaymentModule.processPayment(); // Process payment and check if it is successful
                    if (paymentSuccessful) {
                        flight.bookSeats(numSeats); // Decrease available seats
                        System.out.println("Booking Successful! Enjoy your flight.\n");
                    } else {
                        System.out.println("Payment cancelled. Booking failed. Seats remain unchanged.\n");
                        return; // Return without decreasing the available seats
                    }
                } else {
                    System.out.println("Not enough seats available on the selected flight.\n");
                }
                return;
            }
        }
        System.out.println("Invalid flight number or flight type.\n");
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightBookingSystem bookingSystem = new FlightBookingSystem();
        System.out.println("-----------------------------------Welcome-----------------------------------\n");
        System.out.println("1. View Available Flights\n");
        System.out.println("2. Book Domestic Flight\n");
        System.out.println("3. Book International Flight\n");
        System.out.println("4. Exit\n");
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        bookingSystem.viewAvailableFlights();
                        break;
                    case 2:
                        System.out.print("Enter the flight number: ");
                        String domesticFlightNumber = scanner.next();
                        System.out.print("Enter the number of seats to book: ");
                        int numSeatsDomestic = scanner.nextInt();
                        bookingSystem.bookDomesticFlight(domesticFlightNumber, numSeatsDomestic);
                        break;
                    case 3:
                        System.out.print("Enter the flight number: ");
                        String internationalFlightNumber = scanner.next();
                        System.out.print("Enter the number of seats to book: ");
                        int numSeatsInternational = scanner.nextInt();
                        bookingSystem.bookInternationalFlight(internationalFlightNumber, numSeatsInternational);
                        break;
                    case 4:
                        System.out.println("Thank you for using the Flight Booking System. Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.\n");
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }
} 