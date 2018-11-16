/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Singleton;

import Entity.BookingOrder;
import Entity.RoomEntity;
import Entity.RoomTypeEntity;
import Stateful.hotelReservationsLocal;
import Stateless.RoomTypeBeanLocal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import util.RoomTypeNotFoundException;

/**
 *
 * @author Joshua
 */
@Singleton
@Startup
public class hotelOperations implements hotelOperationsLocal {

    @EJB
    private hotelReservationsLocal hotelReservations;
@EJB
    private RoomTypeBeanLocal roomTypeBean;



    LinkedList<BookingOrder> roomsneeded=new LinkedList<BookingOrder>();
    Queue<Integer> successfulBookings =new LinkedList<Integer>(); 
        // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    @Schedule(hour="2")
    public void generateReport() throws RoomTypeNotFoundException{
        int size=roomsneeded.size();
        for(int i=0; i<size; i++){
            BookingOrder currentNeeded = roomsneeded.get(i);
            String typeNeeded =currentNeeded.getTypeName();
            GregorianCalendar dayNeeded =currentNeeded.getDayBooked();
            int durationofstay = currentNeeded.getDuration();
            List<GregorianCalendar> daysRoomBooked = new ArrayList<>();
            
            for(int day=0; day<durationofstay; day++){
                dayNeeded.add(GregorianCalendar.DAY_OF_MONTH, day);
                daysRoomBooked.add(dayNeeded);
            }
            
            RoomTypeEntity needed=roomTypeBean.getRoomTypeByName(typeNeeded);
            List<RoomEntity> room = needed.getRooms();
            for(RoomEntity j:room){//looking through each room
                if(j.getDayBooked().isEmpty()==false){
                ArrayList<GregorianCalendar> daysBooked = j.getDayBooked();
                int sizeOf = daysBooked.size();
                if(sizeOf!=durationofstay){
               /* for(GregorianCalendar k: daysBooked){//looking at the room's days 1 by 1
                    for(int day=0; day<durationofstay; day++){//checking the order's required rooms
                        dayNeeded.add(GregorianCalendar.DAY_OF_MONTH, day);
                        if(k.compareTo(dayNeeded)==0){
                        break;
                    }else if (day==durationofstay-1){*/
                        daysRoomBooked.add(dayNeeded);
                        hotelReservations.actuallyBookTheRoom(j.getRoomId(), daysRoomBooked);
                        successfulBookings.add(i); /*
                        break;
                    }else{
                        daysRoomBooked.add(dayNeeded);
                    }
                    }
                    
                }*/
                }else{
                    j.setDayBooked(new ArrayList<>());
                    hotelReservations.actuallyBookTheRoom(j.getRoomId(), daysRoomBooked);
                    successfulBookings.add(i);
                }
            }
            
        }
        }
        deleteSuccessfulFromQueue();//after this, anything remaining in roomsneeded means not enough space for that day
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
