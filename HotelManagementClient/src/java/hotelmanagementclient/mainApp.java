/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementclient;

import Entity.EmployeeEntity;
import Entity.RoomTypeEntity;
import Stateless.EmployeeBeanRemote;
import Stateless.RoomTypeBeanRemote;
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
                System.out.println("3) Create a New Room Type");
                System.out.println("4) View All Room Types");
                System.out.println("5) View a Room Type Description");
                System.out.println("6) Update details of a Room Type");
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
                        createNewRoomType();
                        break;
                    case 4:
                        viewAllRoomTypes();
                        break;
                        //edit a type A
                    case 5:
                        viewRoomTypeDetails();
                        break;
                    case 6:
                        updateRoomTypeDetails();
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
        System.out.println("Enter name of room type:(Deluxe Room, Premier Room, Family Room, Junior Suite, Grand Suite");
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
        //add amenities later
        RoomTypeEntity roomtype = new RoomTypeEntity(g, b, a, m, n);
        roomtypebean.createRoomType(roomtype);
    }
    
    private void viewAllRoomTypes() {
        List<RoomTypeEntity> RoomTypesList = roomtypebean.viewAllRoomTypes();
        int count = 1;
        for(RoomTypeEntity r: RoomTypesList){
            System.out.println(count + ") "+ r.getTypeName());
            count++;
        }
        //edit a type B
    }
    
    private void viewRoomTypeDetails() throws RoomTypeNotFoundException {
        System.out.println("Enter the name of the Room Type: ");
        sc.nextLine();
        String typename = sc.nextLine();
        RoomTypeEntity r = roomtypebean.getRoomTypeDetails(typename);
        System.out.println("Room Type: " + r.getTypeName() + ", Room Description: " + r.getTypeDescription() + ", Room Size: " + r.getSize() + ", Number of Beds: " + r.getBedNumber() + ", Capacity of Room: " + r.getCapacity());
    }
    
    private void updateRoomTypeDetails() throws RoomTypeNotFoundException {
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
            }
                roomtypebean.updateRoomTypeDetails(r);
            }
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



}
