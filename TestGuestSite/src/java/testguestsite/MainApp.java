/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testguestsite;

import Entity.GuestEntity;
import Stateless.GuestControllerRemote;
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
    private final GuestControllerRemote guestControl= lookupGuestControllerRemote();

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
                System.out.println("Enter Username");
                String username=sc.next();
                System.out.println("Enter Password");
                String password=sc.next();
                guestControl.userLogin(username, password);
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

    private GuestControllerRemote lookupGuestControllerRemote() {
        try {
            Context c = new InitialContext();
            return (GuestControllerRemote) c.lookup("java:comp/env/GuestController");
        } catch (NamingException ne) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, "exception caught", ne);
            throw new RuntimeException(ne);
        }
    }
}
