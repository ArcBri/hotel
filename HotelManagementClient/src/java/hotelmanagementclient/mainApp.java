/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementclient;

import Entity.EmployeeEntity;
import Stateless.EmployeeBeanRemote;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

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
    private EmployeeBeanRemote employeebean = lookupEmployeeBeanRemote();

    public mainApp() {
    }

    public void run() {
        while (end == false) {
            if (state == false) {
                System.out.print("Enter Username:");
                String u = sc.next();
                System.out.print("Enter Password:");
                String p = sc.next();
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

    private EmployeeBeanRemote lookupEmployeeBeanRemote() {
        try {
            Context c = new InitialContext();
            return (EmployeeBeanRemote) c.lookup("java:comp/env/EmployeeBeanRemote");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }

}
