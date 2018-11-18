/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementclient;

import Entity.EmployeeEntity;
import Entity.RoomEntity;
import Entity.RoomRateEntity;
import Entity.RoomTypeEntity;
import Stateful.hotelReservationsRemote;
import Stateless.EmployeeBeanRemote;
import Stateless.RoomBeanRemote;
import Stateless.RoomRateBeanRemote;
import Stateless.RoomTypeBeanRemote;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import util.RoomTypeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
class mainApp {

    Scanner sc = new Scanner(System.in);
    private boolean state = false;
    private boolean end = false;
    private EmployeeEntity loggedInEmployee;
    private Long EmployeeId;
    private List<EmployeeEntity> employeeList;
    private boolean ongoing = true;
    
    private final EmployeeBeanRemote employeebean = lookupEmployeeBeanRemote();
    private final RoomTypeBeanRemote roomtypebean = lookupRoomTypeBeanRemote();
    private final RoomBeanRemote roombean = lookupRoomBeanRemote();
    private final RoomRateBeanRemote roomratebean = lookupRoomRateBeanRemote();
    private final hotelReservationsRemote hotelReserve = lookuphotelReservationsRemote();

    public mainApp() {
    }

    public void run() throws InvalidLoginCredentialException, RoomTypeNotFoundException {
        while (end == false) {
            if (state == false) {
                System.out.print("Enter Username:");
                String u = sc.next();
                System.out.print("Enter Password:");
                String p = sc.next();
                if(u.equals("hi")||p.equals("bye")){
                    System.out.print("error");
                }
                loggedInEmployee = employeebean.staffLogin(u, p);
                EmployeeId = loggedInEmployee.getEmployeeId();
                state = true;
            } else if (state == true) {
                System.out.println();
                System.out.println("Choose Option");
                System.out.println("1) Create Employee");
                System.out.println("2) View All Employees");
                System.out.println("3) View Room Type Operations");
                System.out.println("4) View Room Operations");
                System.out.println("5) View Room Rate Operations");
                System.out.println("6) View Room Allocation Exception Report"); //to add
                System.out.println("7) Book a room");
                System.out.println("8) Check in a guest");
                System.out.println("9) Check out a guest");
                System.out.println("99) Do it");
                System.out.println("0) Log Out");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        createNewEmployee();
                        break;
                    case 2:
                        viewEmployees();
                        break;
                    case 3:
                        System.out.println();
                        System.out.println("1) Create a New Room Type");
                        System.out.println("2) View All Room Types");
                        System.out.println("3) View details of a Room Type");
                        System.out.println("4) Update details of a Room Type");
                        System.out.println("5) Delete or Disable a Room Type");
                        int choice2 = sc.nextInt();
                        switch (choice2) {
                            case 1:
                                createNewRoomType();
                                break;
                            case 2:
                                viewAllRoomTypes();
                                break;
                            case 3:
                                viewRoomTypeDetails();
                                break;
                            case 4:
                                updateRoomTypeDetails();
                                break;
                            case 5:
                                deleteRoomType();
                                break;
                        }
                        break;
                    case 4:
                        System.out.println();
                        System.out.println("1) Create a New Room");
                        System.out.println("2) View All Rooms");
                        System.out.println("3) Update details and status of a Room");
                        System.out.println("4) Delete a Room Record");
                        int choice3 = sc.nextInt();
                        switch (choice3) {
                            case 1:
                                createNewRoom();
                                break;
                            case 2:
                                viewAllRooms();
                                break;
                            case 3:
                                updateRoomDetails();
                                break;
                            case 4:
                                deleteRoom();
                                break;
                            case 5:
                                viewRoomBooked();
                                break;
                        }
                        break;
                    case 5:
                        System.out.println();
                        System.out.println("1) Create a New Room Rate Record");
                        System.out.println("2) View All Room Rate Records");
                        System.out.println("3) View the details of a Room Rate");
                        System.out.println("4) Update details of a Room Rate Record");
                        System.out.println("5) Delete a Room Rate Record");
                        int choice4 = sc.nextInt();
                        switch (choice4) {
                            case 1:
                                createNewRoomRate();
                                break;
                            case 2:
                                viewAllRoomRates();
                                break;
                            case 3: 
                                viewRoomRateDetails();
                                break;
                            case 4:
                                updateRoomRateDetails();
                                break;
                            case 5:
                                deleteRoomRate();
                                break;
                        }
                        break;
                    case 7:
                        bookRoom();
                        break;
                    case 8:
                        checkIn();
                        break;
                    case 9:
                        checkOut();
                        break;
                    case 99:
                        hotelReserve.doIt();
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
    }
    private void viewEmployees(){
        employeeList=employeebean.retrieveAllEmployeeEntity();
        for(EmployeeEntity i: employeeList){
            System.out.println("User "+i.getFirstName()+" "+i.getLastName()+", username: "+i.getUsername());
        }
    }

    private void createNewEmployee() {
        System.out.println("Enter first name");
        String f = sc.next();
        System.out.println("Enter last name");
        String l = sc.next();
        System.out.println("Enter username");
        String u = sc.next();
        System.out.println("Enter password");
        String p = sc.next();
        EmployeeEntity employee = new EmployeeEntity(f, l, u, p);
        employeebean.createEmployee(employee);
    }
    
    private void createNewRoomType() {
        System.out.println("Enter name of room type:(Deluxe Room, Premier Room, Family Room, Junior Suite, Grand Suite)");
        sc.nextLine();
        String g = sc.nextLine();
        System.out.println("Enter description of room type:");
        String b = sc.nextLine();
        System.out.println("Enter size of room:");
        String a = sc.next();
        System.out.println("Enter number of beds:");
        int m = sc.nextInt();
        System.out.println("Enter capacity of room:");
        int n = sc.nextInt();
        System.out.println("Enter number of each type of amenity in order:");
        System.out.println("0 - \"The World\" Brand Timepiece clock");
        System.out.println("1 - \"Sunlight Yellow\" Brand Hair Dryer");
        System.out.println("2 - \"Hermit Purple\" Provider Wifi");
        System.out.println("3 - \"Star Platinum\" Brand ORA(L) Care (Toothbrush and Toothpaste)");
        System.out.println("4 - \"Shining Diamond\" Brand Iron and Ironing Board");
        System.out.println("5 - \"Gold Experience\" Brand Slippers and Bathrobe");
        System.out.println("6 - \"Stone Free\" Brand Towels");
        System.out.println("7 - \"Tusk\" Brand Nail Clippers");
        System.out.println("8 - \"Soft and Wet\" Brand Soap and Shampoo");
        int[] amenities = new int[9];
        for(int i = 0; i < 9; i++){
            amenities[i] = sc.nextInt();
        }
        RoomTypeEntity roomtype = new RoomTypeEntity(g, b, a, m, n, amenities);
        roomtypebean.createRoomType(roomtype);
        
    }
    
    private void viewAllRoomTypes() {
        List<RoomTypeEntity> RoomTypesList = roomtypebean.viewAllRoomTypes();
        int count = 1;
        for(RoomTypeEntity r: RoomTypesList){
            System.out.println(count + ") "+ r.getTypeName());
            count++;
        }
    }
    
    private void viewRoomTypeDetails() throws RoomTypeNotFoundException {
        System.out.println("Enter the name of the Room Type: ");
        sc.nextLine();
        String typename = sc.nextLine();
        RoomTypeEntity r = roomtypebean.getRoomTypeDetails(typename);
        System.out.println("Room Type: " + r.getTypeName() + ", Room Description: " + r.getTypeDescription() + ", Room Size: " + r.getSize() + ", Number of Beds: " + r.getBedNumber() + ", Capacity of Room: " + r.getCapacity());
        System.out.println("Amenities: " + Arrays.toString(r.getAmenities())); //update for easier viewing
    }
    
    private void updateRoomTypeDetails() throws RoomTypeNotFoundException {
        Boolean ongoing = true;
        System.out.println("Enter the name of the Room Type to be Updated: ");
        sc.nextLine();
        String typename = sc.nextLine();
        RoomTypeEntity r = roomtypebean.getRoomTypeDetails(typename);
        while(ongoing == true){
            System.out.println("1) Room Description: " + r.getTypeDescription());
            System.out.println("2) Room Size: " + r.getSize());
            System.out.println("3) Number of Beds: " + r.getBedNumber());
            System.out.println("4) Capacity of Room: " + r.getCapacity());
            System.out.println("0) Save changes and exit updater");
            int response = sc.nextInt();
            sc.nextLine();
            if(response == 0){
                ongoing = false;
                System.out.println("Changes Saved");
            }
            else{
                switch (response){
                    case 1:
                        System.out.println("Enter new Room Description:");
                        String newdesc = sc.nextLine();
                        r.setTypeDescription(newdesc);
                        break;
                    case 2:
                        System.out.println("Enter new Room Size:");
                        String newsize = sc.nextLine();
                        r.setSize(newsize);
                        break;
                    case 3:
                        System.out.println("Enter new number of Beds:");
                        int newnum = sc.nextInt();
                        r.setBedNumber(newnum);
                        break;
                    case 4:
                        System.out.println("Enter new Capacity of Room:");
                        int newcap =  sc.nextInt();
                        r.setCapacity(newcap);
                        break;
                    case 5:
                        System.out.println("Enter new Amenities in order:");
                        int[] amenities = new int[9];
                        for(int i = 0; i < 9; i++){
                            amenities[i] = sc.nextInt();
                        }
                        r.setAmenities(amenities);
                        break; 
                }
                roomtypebean.updateRoomTypeDetails(r);
            }
        }
    }
    
    private void deleteRoomType() {
        sc.nextLine();
        System.out.println("Enter the name of the Room Type to be deleted:");
        String type = sc.nextLine();
        List<RoomEntity> roomlist = roombean.viewAllRooms();
        int delete = 0;
        for (RoomEntity r: roomlist) {
            if (r.getRoomType().equals(type)) {
                delete = 1;
            }
        }
        if (delete == 1) {
            roomtypebean.disableRoomType(type);
            System.out.println("Room Type \"" + type + "\" Disabled");
        }
        else {
            roomtypebean.deleteRoomType(type);
            System.out.println("Room Type \"" + type +"\" Deleted");
        }
    }
    
    private void createNewRoom() {
        sc.nextLine();
        System.out.println("Enter the Room Type of the new Room:");
        String t = sc.nextLine();
        Boolean disabled = roomtypebean.checkDisabled(t);
        if (disabled == false) {
            System.out.println("Enter the Floor Number of the new Room");
            int f = sc.nextInt();
            System.out.println("Enter the Room Number of the new Room");
            int r = sc.nextInt();
            RoomEntity room = new RoomEntity(t, f, r);
            roombean.createRoom(room);
        }
        else {
            System.out.println("Room Type is disabled, please choose another.");
        }
    }
    
    private void viewAllRooms() {
        List<RoomEntity> RoomList = roombean.viewAllRooms();
        int count = 1;
        for(RoomEntity r: RoomList){
            System.out.println(count + ") Room "+ r.getFinalNumber());
            count++;
        }
    }
    
    private void updateRoomDetails() {
        Boolean ongoing = true;
        System.out.println("Enter the Number of the Room to be edited: ");
        int roomnumber = sc.nextInt();
        RoomEntity rm = roombean.getRoomDetails(roomnumber);
        while(ongoing == true){
            System.out.println("1) Room Type: " + rm.getRoomType());
            System.out.println("2) Room Available: " + rm.isRoomAvailable());
            System.out.println("0) Save changes and exit updater");
            int response = sc.nextInt();
            sc.nextLine();
            if(response == 0){
                ongoing = false;
                System.out.println("Changes Saved");
            }
            else{
                switch (response){
                    case 1:
                        System.out.println("Enter new Room Type:");
                        String newtype = sc.nextLine();
                        rm.setRoomType(newtype);
                        break;
                    case 2:
                        System.out.println("Enter updated Room Availability:");
                        Boolean newavail = sc.nextBoolean();
                        rm.setRoomAvailable(newavail);
                        break;
                }
                roombean.updateRoomDetails(rm);
            }
        }
    }
    
    public void deleteRoom() {
        System.out.println("Enter the room number of the Room to be deleted: ");
        int roomnumber = sc.nextInt();
        Boolean disable = roombean.checkDisabled(roomnumber);
        if (disable == true) {
            roombean.disable(roomnumber);
            System.out.println("Room " + roomnumber + " Disabled");
        }
        else {
            roombean.deleteRoom(roomnumber);
            System.out.println("Room " + roomnumber + " Deleted");
        }
    }
    
    public void createNewRoomRate() {
        System.out.println("Enter name of room rate: ");
        sc.nextLine();
        String j = sc.nextLine();
        System.out.println("Enter the room type: (Deluxe Room, Premier Room, Family Room, Junior Suite, Grand Suite)");
        String k = sc.nextLine();
        System.out.println("Enter the type of rate: (Published Rate, Normal Rate, Peak Rate, Promotion Rate)");
        String o = sc.nextLine();
        System.out.println("Enter the rate per night:");
        BigDecimal y = sc.nextBigDecimal();
        RoomRateEntity roomrate = new RoomRateEntity(j, k, o, y);
        System.out.println("Enter the validity period? (Y/N)");
        String option = sc.next();
        if (option.equals("Y")) {
            roomrate.setValidity(true);
            System.out.println("Enter the Starting Date (YYYY MM DD)");
            int startyear = sc.nextInt();
            int startmonth = sc.nextInt();
            startmonth--;
            int startday = sc.nextInt();
            GregorianCalendar startdate = new GregorianCalendar(startyear, startmonth, startday);
            roomrate.setValidityStart(startdate);
            System.out.println("Enter the Ending Date (YYYY MM DD)");
            int endyear = sc.nextInt();
            int endmonth = sc.nextInt();
            endmonth--;
            int endday = sc.nextInt();
            GregorianCalendar enddate = new GregorianCalendar(endyear, endmonth, endday);
            roomrate.setValidityEnd(enddate);
            ArrayList <GregorianCalendar> rateperiod = new ArrayList<GregorianCalendar>();
            GregorianCalendar day = new GregorianCalendar(startyear, startmonth, startday);
            while (day.equals(enddate) == false) {
                rateperiod.add(day);
                day.add(GregorianCalendar.DAY_OF_WEEK, 1);
            }

            
            roomrate.setRateperiod(rateperiod);
        }

        roomratebean.createRoomRate(roomrate);
    }
    
    public void viewAllRoomRates() {
       List<RoomRateEntity> RoomRateList = roomratebean.viewAllRoomRates();
        int count = 1;
        for(RoomRateEntity rr: RoomRateList){
            System.out.println(count + ") ("+ rr.getRateType() + ") " + rr.getName());
            count++; 
        }
    }
    
    public void viewRoomRateDetails() {
        System.out.println("Enter the name of the Room Rate: ");
        sc.nextLine();
        String name = sc.nextLine();
        RoomRateEntity rate = roomratebean.getDetails(name);
        System.out.println("Name: " + rate.getName() + ", Room Type: " + rate.getRoomType() + ", Rate Type: " + rate.getRateType() + ", Rate per Night: " + rate.getRatePerNight());
        if (rate.getValidity() == true) {
            int startmonthcast = rate.getValidityStart().get(Calendar.MONTH);
            startmonthcast++;
            int endmonthcast = rate.getValidityEnd().get(Calendar.MONTH);
            endmonthcast++;
            System.out.println("Rate valid from: " + rate.getValidityStart().get(Calendar.YEAR) + "/" + startmonthcast + "/" + rate.getValidityStart().get(Calendar.DATE) + " to " + rate.getValidityEnd().get(Calendar.YEAR) + "/" + endmonthcast + "/" + rate.getValidityEnd().get(Calendar.DATE));
        }
        else {
            System.out.println("Prevailing Rate (Always Valid)");
        }
    }
    public void updateRoomRateDetails() {
        System.out.println("Enter the name of the Room Rate to be updated: ");
        sc.nextLine();
        String name = sc.nextLine();
        RoomRateEntity rate = roomratebean.getDetails(name);
        while(ongoing == true){
            System.out.println("1) Rate Name: " + rate.getName());
            System.out.println("2) Room Type: " + rate.getRoomType());
            System.out.println("3) Rate Type: " + rate.getRateType());
            System.out.println("4) Rate per night: " + rate.getRatePerNight());
            if (rate.getValidity() == true) {
                int startmonthcast = rate.getValidityStart().get(Calendar.MONTH);
                startmonthcast++;
                int endmonthcast = rate.getValidityEnd().get(Calendar.MONTH);
                endmonthcast++;
                System.out.println("5) Starting Date: " + rate.getValidityStart().get(Calendar.YEAR) + "/" + startmonthcast + "/" + rate.getValidityStart().get(Calendar.DATE));
                System.out.println("6) Ending Date: " + rate.getValidityEnd().get(Calendar.YEAR) + "/" + endmonthcast + "/" + rate.getValidityEnd().get(Calendar.DATE));
                System.out.println("7) Remove Validity Period");
            }
            else {
                System.out.println("5) Add a Starting Date ");
                System.out.println("6) Add an Ending Date ");
            }
            System.out.println("0) Save changes and exit updater");
            int response = sc.nextInt();
            sc.nextLine();
            if(response == 0){
                ongoing = false;
                System.out.println("Changes Saved");
            }
            else{
                switch (response){
                    case 1:
                        System.out.println("Enter new Rate Name: ");
                        String newname = sc.nextLine();
                        rate.setName(newname);
                        break;
                    case 2:
                        System.out.println("Enter updated Room Type: ");
                        String newtype = sc.nextLine();
                        rate.setRoomType(newtype);
                        break;
                    case 3:
                        System.out.println("Enter new Rate Type: (Published Rate, Normal Rate, Peak Rate, Promotion Rate)");
                        String newratetype = sc.nextLine();
                        rate.setRateType(newratetype);
                        break;
                    case 4:
                        System.out.println("Enter new Rate Per Night: ");
                        BigDecimal newrpn = sc.nextBigDecimal();
                        sc.nextLine();
                        rate.setRatePerNight(newrpn);
                        break;
                    case 5:
                        System.out.println("Enter new Starting Date: (YYYY MM DD)");
                        int newyear = sc.nextInt();
                        int newmonth = sc.nextInt();
                        newmonth--;
                        int newday = sc.nextInt();
                        sc.nextLine();
                        GregorianCalendar newstart = new GregorianCalendar(newyear, newmonth, newday);
                        rate.setValidityStart(newstart);
                        if (rate.getValidity() == false) {
                            rate.setValidity(true);
                        }
                        break;
                    case 6:
                        System.out.println("Enter new Ending Date: (YYYY MM DD)");
                        int neweryear = sc.nextInt();
                        int newermonth = sc.nextInt();
                        newermonth--;
                        int newerday = sc.nextInt();
                        sc.nextLine();
                        GregorianCalendar newend = new GregorianCalendar(neweryear, newermonth, newerday);
                        rate.setValidityEnd(newend);
                        if (rate.getValidity() == false) {
                            rate.setValidity(true);
                        }
                        break;
                    case 7:
                        System.out.println("Delete Validity Period? (Y/N)");
                        String answer = sc.nextLine();
                        if (answer.equals("Y")) {
                            rate.setValidity(false);
                        }
                        break;
                }
                roomratebean.updateRoomRateDetails(rate);
            }
        }
    }
    public void deleteRoomRate() {
        System.out.println("Enter the Name of the Room Rate to be deleted: ");
        sc.nextLine();
        String name = sc.nextLine();
        Boolean disable = roomratebean.checkDisabled(name);
        if (disable == true) {
            roomratebean.disable(name);
            System.out.println("Room Rate Record " + name + " Disabled");
        }
        else {
            roomratebean.deleteRoomRate(name);
            System.out.println("Room Rate Record " + name + " Deleted");
        }
    }

    private EmployeeBeanRemote lookupEmployeeBeanRemote() {
        try {
            Context c = new InitialContext();
            return (EmployeeBeanRemote) c.lookup("java:comp/env/EmployeeBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomTypeBeanRemote lookupRoomTypeBeanRemote() {
        try {
            Context c = new InitialContext();
            return (RoomTypeBeanRemote) c.lookup("java:comp/env/RoomTypeBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

    private RoomBeanRemote lookupRoomBeanRemote() {
        try {
            Context c = new InitialContext();
            return (RoomBeanRemote) c.lookup("java:comp/env/RoomBeanRemote");
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

    private void bookRoom() {
        /*System.out.println("Enter room type desired");
        sc.nextLine();
        String roomType=sc.nextLine();*/
        System.out.println("Enter check in date in this order: Year, Month, Day");
        int startYear=sc.nextInt();
        int startMonth=sc.nextInt();
        int startDay=sc.nextInt();
        GregorianCalendar d1 = new GregorianCalendar(startYear, startMonth-1, startDay);
        System.out.println("Enter check out date in this order: Year, Month, Day");
        int endYear=sc.nextInt();
        int endMonth=sc.nextInt();
        int endDay=sc.nextInt();
        GregorianCalendar d2 = new GregorianCalendar(endYear, endMonth-1, endDay);
        int duration =dayDiff(d1,d2);
        List<RoomEntity> rooms=roombean.searchRoom(d1, duration);
        for(int k=0; k<rooms.size(); k++){
            displayUnbookedRooms(d1,d2,rooms.get(k), duration);
        }
        System.out.println("Enter choice room number");
        int roomnumber=sc.nextInt();
        String roomType=roombean.getRoomDetails(roomnumber).getRoomType();
        sc.nextLine();
        hotelReserve.bookARoomEmployee(loggedInEmployee, roomType, startYear, startMonth, startDay, duration, roomnumber);
        
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

    private int dayDiff(GregorianCalendar d1, GregorianCalendar d2) {
        long starttime=d1.getTimeInMillis();
        long endtime =d2.getTimeInMillis();
        long durationInMillis=endtime-starttime;
        long durationInDays=durationInMillis/(1000*60*60*24);
        return Math.toIntExact(durationInDays);
    }
    
    private void viewRoomBooked(){
        List<RoomEntity> RoomList = roombean.viewAllRooms();
        int count = 1;
        for(RoomEntity r: RoomList){
            System.out.println(count + ") Room "+ r.getFinalNumber() + "booked?" + r.getDayBooked().toString());
            count++;
        }
    }

    private void displayUnbookedRooms(GregorianCalendar d1, GregorianCalendar d2, RoomEntity get, int duration) {
        String Roomtype = get.getRoomType();
        BigDecimal rateamt = roomratebean.getWalkInRate(Roomtype);
        BigDecimal finalprice = BigDecimal.ZERO;
        for (int g = 0; g < duration; g++) {
            finalprice = finalprice.add(rateamt);
        }
        System.out.println("ROOM "+get.getFinalNumber()+" of ROOMTYPE "+get.getRoomType() + " $ " + finalprice);
        
    }
    
    private void checkIn() {
        System.out.println("Enter the first name of the guest: ");
        sc.nextLine();
        String firstname = sc.nextLine();
        System.out.println("Enter the last name of the guest: ");
        String lastname = sc.nextLine();
        
        System.out.println(firstname + " " + lastname + "checked in.");
        
    }
    
    private void checkOut() {
        System.out.println("Enter the first name of the guest: ");
        sc.nextLine();
        String firstname = sc.nextLine();
        System.out.println("Enter the last name of the guest: ");
        String lastname = sc.nextLine();
        
        System.out.println(firstname + " " + lastname + "checked out.");
    }
    
    
}
