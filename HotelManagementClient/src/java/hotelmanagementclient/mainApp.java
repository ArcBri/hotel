/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelmanagementclient;

import Entity.EmployeeEntity;
import Stateless.EmployeeBeanRemote;
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
class mainApp{
    Scanner sc =new Scanner(System.in);
    private boolean state = false;
    private EmployeeEntity loggedInEmployee;
    private Long EmployeeId;
    private EmployeeBeanRemote employeebean=lookupEmployeeBeanRemote();
    
    public mainApp() {
    }
    
    public void run(){
       createNewEmployee();
    }


    private void createNewEmployee() {
        System.out.println("Enter first name");
        String f=sc.nextLine();
        System.out.println("Enter last name");
        String l=sc.nextLine();
        System.out.println("Enter username");
        String u=sc.nextLine();
        System.out.println("Enter password");
        String p=sc.nextLine();
        EmployeeEntity employee= new EmployeeEntity(f,l,u,p);
        EmployeeId=employeebean.createEmployee(employee);
        
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

