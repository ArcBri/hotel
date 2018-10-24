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
    
    private final EmployeeBeanRemote employeebean = lookupEmployeeBeanRemote();
    private final RoomTypeBeanRemote roomtypebean = lookupRoomTypeBeanRemote();

    public mainApp() {
    }

    public void run() throws InvalidLoginCredentialException {
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
                System.out.println("Choose Option");
                System.out.println("1) Create Employee");
                System.out.println("2)View All Employees");
                System.out.println("0)Log Out");
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
        System.out.println("Enter name of room type");
        int g = sc.nextInt(); //settle the conversion of int to type and add a menu here for int pressing
        sc.nextLine();
        System.out.println("Enter description of room type");
        String b = sc.nextLine();
        System.out.println("Enter size of room");
        String a = sc.next();
        System.out.println("Enter number of beds");
        int m = sc.nextInt();
        System.out.println("Enter capacity of room");
        int n = sc.nextInt();
        //add amenities later
        RoomTypeEntity roomtype = new RoomTypeEntity(g, b, a, m, n);
        roomtypebean.createRoomType(roomtype);
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
