package cinema;

import java.util.Arrays;
import java.util.Scanner;

/*
 *  This program simulate a cinema room of given size
 *  and allow to buy ticket or see stats about income.
 *  totalIncome represent possible total income based on room size.
 *  B represents booked seats.
 *  S represents empty seats.
 *
 *  @author   - Trapti Tiwari
 *  @email    - traptit1@yahoo.com
 *  @linkedin - https://www.linkedin.com/in/tiwari-trapti/
 */

public class Cinema {

    // Declare objects, constant and variables to be used throughout program.
    static final private Scanner readIp = new Scanner(System.in);
    final private static int firstPrice = 10;
    final private static int secondPrice = 8;
    private static int totalSeats;
    private static int totalSeatSold = 0;
    private static int seatsInFirstHalf = 0;    // Sold seats.
    private static int seatsInSecondHalf = 0;   // Sold seats.
    private static int totalIncome;
    private static int currentIncome;
    private static int row;
    private static int col;
    private static String[][] movieHall = new String[row][col];


    public static void main(String[] args) {
        //  Create room of given size.
        System.out.println("Enter the number of rows:");
        row = readIp.nextInt();
        System.out.println("Enter the number of seats in each row:");
        col = readIp.nextInt();
        movieHall = new String[row][col];       // Resize to given size.
        fillSeatingArrangement();               // Fill seats with "S" meaning available.

        // Options to choose.
        final String exit = "0. Exit";
        final String show = "1. Show the seats";
        final String buy = "2. Buy a ticket";
        final String stats = "3. Statistics";
        int command = -1;

        // While not selected exit ask for action to perform.
        while (command != 0) {
            System.out.printf("%s \n %s \n %s \n %s \n", show, buy, stats, exit);
            command = readIp.nextInt();

            switch (command) {
                case 1:
                    showSeatingArrangement();
                    break;
                case 2:
                    //  Buy a seat.
                    while (true) {
                        System.out.println("Enter a row number:");
                        int rPos = readIp.nextInt() - 1;
                        System.out.println("Enter a seat number in that row:");
                        int cPos = readIp.nextInt() - 1;

                        // Check if given positions are within range.
                        if (!isCorrectPosition(rPos,cPos)) {
                            System.out.println("\n Wrong input! \n");
                        } else {

                            // If seat is available then show the price else ask for different location.
                            if (placeInMovieHall(rPos, cPos)) {
                                int price = calculateTicketPrice(rPos);
                                // Keep count of sold seats.
                                if (price == firstPrice) {
                                    seatsInFirstHalf++;
                                } else {
                                    seatsInSecondHalf++;
                                }
                                System.out.println("Ticket price: $" + price);
                                System.out.println();
                                break;
                            } else {
                                System.out.println("\n That ticket has already been purchased! \n");
                            }
                        }
                    }
                    break;
                case 3:
                    showStats();
                    break;
                default:
                    break;
            }
        }
    }

    /*
        Show stats about income and ticket.
     */
    private static void showStats() {
        calculateIncome();
        System.out.println();
        System.out.println("Number of purchased tickets: " + totalSeatSold);
        String percentage = String.format("%.2f", (totalSeatSold * 100.0) / totalSeats);
        System.out.println("Percentage: " + percentage + "%");
        System.out.println("Current income: $" + currentIncome);
        System.out.println("Total income: $" + totalIncome);
        System.out.println();
    }

    /*
        Check if seat asked by user to book is within given row and seats
        @input  -   seats position asked by user to book
     */

    private static boolean isCorrectPosition(int rPos, int cPos) {
        return rPos >= 0 && rPos < row && cPos >= 0 && cPos < col;
    }

    /*
        Check if seat asked by user is empty or not
        if empty, book the seat and return true
        else return false
     */
    private static boolean placeInMovieHall(int rPos, int cPos) {
        // Occupy seat if available in hall.
        if (movieHall[rPos][cPos].equals("S")) {
            movieHall[rPos][cPos] = "B";
            totalSeatSold++;
            return true;
        }
        return false;
    }

    /*
        Return price of asked seat.
        if total seats are greater than 60 and asked row is in lower half price is 8$
        else 10$.
     */
    private static int calculateTicketPrice(int rPos) {
        // Calculate ticket price according to position.
        int ticketPrice = firstPrice;

        totalSeats = row * col;

        if (totalSeats > 60 && rPos >= row / 2){
            ticketPrice = secondPrice;
        }
        return ticketPrice;
    }

    /*
        Calculate stats about income based on room size and sold seats
     */
    private static void calculateIncome() {
        // Calculate current and total income.
        totalSeats = row * col;

        if (totalSeats <= 60) {
            totalIncome = totalSeats * firstPrice;
            currentIncome = totalSeatSold * firstPrice;
        } else {
            totalIncome = (row / 2) * col * firstPrice + (row - (row / 2)) * col * secondPrice;
            currentIncome = seatsInFirstHalf * firstPrice + seatsInSecondHalf * secondPrice;
        }
    }

    /*
        Print seat position and status (whether seat is booked or available);
        B  -   if seat is booked
        S   -   if seat is available
     */
    private static void showSeatingArrangement() {
        // Print seating arrangements in format.
        System.out.println();
        System.out.println("Cinema:");
        System.out.print(" ");
        for (int c = 1; c <= col; c++) {
            System.out.print(" " + c);
        }
        System.out.println();

        for (int r = 0; r < row; r++) {
            System.out.print(r + 1 + " ");
            for (int c = 0; c < col; c++) {
                System.out.print(movieHall[r][c] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
        Fill seating arrangement initially with empty seats.
     */
    private static void fillSeatingArrangement() {
        // Fill initial hall with available space.
        for (String[] strings : movieHall) {
            Arrays.fill(strings, "S");
        }
    }
}