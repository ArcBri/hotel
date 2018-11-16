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
    public static void main(String[] args) {
      /*  Scanner sc = new Scanner(System.in);
        GregorianCalendar d1 = new GregorianCalendar(2018, 12, 12);
        GregorianCalendar d2 = new GregorianCalendar(2018, 12, 13);
        System.out.println("Enter the Room Type of the new Room:");
        String t = sc.nextLine();
        System.out.println("Enter the Floor Number of the new Room");
        int f = sc.nextInt();
        System.out.println("Enter the Room Number of the new Room");
        int r = sc.nextInt();
        RoomEntity room = new RoomEntity(t, f, r);
        ArrayList<GregorianCalendar> dayBooked=new ArrayList<>();
        room.setDayBooked(dayBooked);
        room.getDayBooked().add(d1);
        room.getDayBooked().add(d2);
        roomBean.createRoom(room);
        */// TODO code application logic here
        List<RoomEntity> rooms=roomBean.viewAllRooms();
        for(RoomEntity i : rooms){
            if(i.getDayBooked().isEmpty()){
                System.out.println("Empty" + i.getRoomNumber());
            }else{
               System.out.println("Booked"+i.getRoomNumber());
            }
        }
    }
    
}
