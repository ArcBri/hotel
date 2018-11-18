/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testguestsite;

import Entity.BookingOrder;
import Entity.GuestEntity;
import Entity.RoomEntity;
import Entity.RoomRateEntity;
import Stateful.hotelReservationsRemote;
import Stateless.GuestControllerRemote;
import Stateless.RoomBeanRemote;
import Stateless.RoomRateBeanRemote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
class MainApp {
    Scanner sc =new Scanner(System.in);
    private boolean state = false;
    private boolean end = false;
    private GuestEntity loggedInGuest;
    private long guestId;
    private final GuestControllerRemote guestControl= lookupGuestControllerRemote();
    private final RoomBeanRemote roombean = lookupRoomBeanRemote();
    private final RoomRateBeanRemote roomratebean = lookupRoomRateBeanRemote();
    private final hotelReservationsRemote hotelReserve = lookuphotelReservationsRemote();

    public MainApp() {
    }
    public void run() throws InvalidLoginCredentialException{
       
        System.out.println("1)Register");
        System.out.println("2)Log In");
        int choice1=sc.nextInt();
        switch(choice1){
            case 1:
                createNewuser();
                break;
            case 2:
                while (end == false) {
                    if (state == false) {
                        System.out.println("Enter Username");
                        String username=sc.next();
                        System.out.println("Enter Password");
                        String password=sc.next();
                        loggedInGuest = guestControl.userLogin(username, password);
                        guestId = loggedInGuest.getId();
                        state = true;
                    }
                    else if (state == true) {
                        System.out.println();
                        System.out.println("Choose Option");
                        System.out.println("1) Search and Book Hotel Room");
                        System.out.println("2) View All My Reservations");
                        System.out.println("3) View My Reservation Details");
                        System.out.println("0) Log Out");
                        int choice = sc.nextInt();
                        switch(choice) {
                            case 1:
                                searchRoom();
                                break;
                            case 2:
                                viewAllReservations();
                                break;
                            case 3: 
                                viewReservationDetails();
                                break;
                            case 0:
                                state = false;
                            System.out.println("End Application? Y/N");
                            String response=sc.next();
                            if (response.equals("Y")) {
                                end = true;
                            }
                        }
                    }
                }
                
                break;
                
        }
            
    }

    private void createNewuser() {
        System.out.println("Enter first name");
        String f = sc.next();
        System.out.println("Enter last name");
        String l = sc.next();
        System.out.println("Enter username");
        String u = sc.next();
        System.out.println("Enter password");
        String p = sc.next();
        GuestEntity guest = new GuestEntity(f, l, u, p);
        guestControl.register(guest);
    }
    
    private void searchRoom() {
        System.out.println("Enter check-in date (YYYY MM DD): ");
        int inYear = sc.nextInt();
        int inMonth = sc.nextInt();
        int inDay = sc.nextInt();
        inMonth--;
        GregorianCalendar inDate = new GregorianCalendar(inYear, inMonth, inDay);
        System.out.println("Enter check-out date (YYYY MM DD): ");
        int outYear = sc.nextInt();
        int outMonth = sc.nextInt();
        int outDay = sc.nextInt();
        outMonth--;
        GregorianCalendar outDate = new GregorianCalendar(outYear, outMonth, outDay);
        int duration = dayDiff(inDate, outDate);
        List<RoomEntity> candidates = roombean.searchRoom(inDate, duration);
        for (RoomEntity rm: candidates) {
            BigDecimal onlinerate = roomratebean.getOnlineRate(rm.getRoomType(), inDate, duration);
            System.out.println("Room " + rm.getFinalNumber() + ": $" + onlinerate);
        }
        boolean booking = true;
        while (booking == true) {
            System.out.println("Book a Room? (Y/N)");
            String bookchoice = sc.next();
            if (bookchoice.equals("Y")) {
                bookRoom(inYear, inMonth, inDay, duration);
            }
            else {
                System.out.println("Booking(s) Saved");
                booking = false;
            }
        }
        
    }
    
    private void bookRoom(int startYear, int startMonth, int startDay, int duration) {
        System.out.println("Enter the room number of the room to be booked: ");
        int roomnumber = sc.nextInt();
        String roomType=roombean.getRoomDetails(roomnumber).getRoomType();
        hotelReserve.bookARoomGuest(loggedInGuest, roomType, startYear, startMonth, startDay, duration, roomnumber);
    }
    
    private void viewAllReservations() {
        List<BookingOrder> reservationlist = guestControl.viewAllReservations(loggedInGuest);
        int count = 1;
        for (BookingOrder bo: reservationlist) {
            System.out.println(count + ") Room " + bo.getRoomFinalNumber());
            count++;
        }
    }
    
    private void viewReservationDetails() {
        System.out.println("Enter the room number of the booking");
        int finalnumber = sc.nextInt();
        BookingOrder booking = guestControl.getReservationDetails(loggedInGuest, finalnumber);
        GregorianCalendar startdate = booking.getDayBooked();
        int year = startdate.get(Calendar.YEAR);
        int month = startdate.get(Calendar.MONTH) + 1;
        int day = startdate.get(Calendar.DAY_OF_MONTH);
        int duration = booking.getDuration();
        System.out.println("Room " + finalnumber + " booked from " + year + "/" + month + "/" + day + " for " + duration + "days.");
    }

    private GuestControllerRemote lookupGuestControllerRemote() {
        try {
            Context c = new InitialContext();
            return (GuestControllerRemote) c.lookup("java:comp/env/GuestController");
         } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

   private RoomBeanRemote lookupRoomBeanRemote() {
        try {
            Context c = new InitialContext();
            return (RoomBeanRemote) c.lookup("java:comp/env/RoomBean");
       } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomRateBeanRemote lookupRoomRateBeanRemote() {
        try {
            Context c = new InitialContext();
            return (RoomRateBeanRemote) c.lookup("java:comp/env/RoomRateBean");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    private int dayDiff(GregorianCalendar d1, GregorianCalendar d2) {
        long starttime=d1.getTimeInMillis();
        long endtime =d2.getTimeInMillis();
        long durationInMillis=endtime-starttime;
        long durationInDays=durationInMillis/(1000*60*60*24);
        return Math.toIntExact(durationInDays);
    }

    private hotelReservationsRemote lookuphotelReservationsRemote() {
        try {
            Context c = new InitialContext();
            return (hotelReservationsRemote) c.lookup("java:comp/env/hotelReservations");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
    
    
   
}
