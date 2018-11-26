/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hotelreservationclient;

import Entity.RoomEntity;
import Stateful.hotelReservationsRemote;
import Stateless.RoomBeanRemote;
import Stateless.RoomTypeBeanRemote;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import javax.ejb.EJB;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
public class Main {

    @EJB
    private static RoomTypeBeanRemote roomTypeBean;

    @EJB
    private static hotelReservationsRemote hotelReservations;

    @EJB
    private static RoomBeanRemote roomBean;

    /**
     * @param args the command line arguments
     */
        public static void main(String[] args) throws InvalidLoginCredentialException {
        // TODO code application logic here
        MainApp mainApp = new MainApp();
        mainApp.run();
    }
    
}
