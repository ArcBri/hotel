/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testguestsite;

import Entity.GuestEntity;
import Entity.RoomEntity;
import Entity.RoomRateEntity;
import Stateless.GuestControllerRemote;
import Stateless.RoomBeanRemote;
import Stateless.RoomRateBeanRemote;
import java.math.BigDecimal;
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
                        System.out.println("1) Search Hotel Room");
                        System.out.println("2) Reserve Hotel Room");
                        System.out.println("3) View All My Reservations");
                        System.out.println("4) View My Reservation Details");
                        System.out.println("0) Log Out");
                        int choice = sc.nextInt();
                        switch(choice) {
                            case 1:
                                searchRoom();
                                break;
                            case 2:
                                bookRoom();
                                break;
                            case 3:
                                viewAllReservations();
                                break;
                            case 4: 
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
        /*boolean booking = true;
        while (booking == true) {
            bookroom();
            System.out.println("Book a Room? (Y/N)");
            String bookchoice = sc.next();
            if (bookchoice == "N") {
                System.out.println("Booking(s) Saved");
                booking = false;
            }
        }*/
        
    }
    
    private void bookRoom() {
        System.out.println("Enter the room number of the room to be booked: ");
        int roomnumber = sc.nextInt();
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
    
   
}
