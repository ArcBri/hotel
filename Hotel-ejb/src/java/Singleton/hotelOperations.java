/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Singleton;

import Entity.BookingOrder;
import Entity.RoomEntity;
import Entity.RoomTypeEntity;
import Entity.dayTracker;
import Stateful.hotelReservationsLocal;
import Stateless.RoomTypeBeanLocal;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import util.RoomTypeNotFoundException;

/**
 *
 * @author Joshua
 */
@Singleton
public class hotelOperations implements hotelOperationsLocal {

    @EJB
    private hotelReservationsLocal hotelReservations;
@EJB
    private RoomTypeBeanLocal roomTypeBean;


    LinkedList<BookingOrder> roomsneeded=new LinkedList<BookingOrder>();
    Queue<Integer> successfulBookings =new LinkedList<Integer>();    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Schedule(hour="2")
    public void generateReport() throws RoomTypeNotFoundException{
        int size=roomsneeded.size();
        for(int i=0; i<size; i++){
            BookingOrder currentNeeded = roomsneeded.get(i);
            String typeNeeded =currentNeeded.getTypeName();
            List<GregorianCalendar> dayNeeded =currentNeeded.getDayBooked();
            GregorianCalendar lastDay=dayNeeded.get(dayNeeded.size()-1);
            RoomTypeEntity needed=roomTypeBean.getRoomTypeByName(typeNeeded);
            List<RoomEntity> room = needed.getRooms();
            for(RoomEntity j:room){
                List<dayTracker> daysBooked = j.getDayBooked();
                for(dayTracker k: daysBooked){
                    for(GregorianCalendar l : dayNeeded){
                        if(k.getDay().equals(l)){
                        break;
                    }else if (k.getDay().equals(l)==false&&l.equals(lastDay)){
                        hotelReservations.actuallyBookTheRoom(j, dayNeeded);
                        successfulBookings.add(i);
                        break;
                    }
                    }
                    
                }
            }
            
        }
        deleteSuccessfulFromQueue();
    }

     void deleteSuccessfulFromQueue() {
        while(successfulBookings.isEmpty()==false){
        roomsneeded.remove(successfulBookings.poll());
        }
    }
@Override
     public void queueBooking(BookingOrder a){
         roomsneeded.add(a);
     }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
