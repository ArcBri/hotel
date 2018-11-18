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
import Stateless.RoomBeanLocal;
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
    private RoomBeanLocal roomBean;

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
            int roomNumber=currentNeeded.getRoomFinalNumber();
            int durationofstay = currentNeeded.getDuration();
            List<GregorianCalendar> daysRoomBooked = new ArrayList<>();
            
            for(int day=0; day<durationofstay; day++){
                dayNeeded.add(GregorianCalendar.DAY_OF_MONTH, day);
                daysRoomBooked.add(dayNeeded);
            }
            RoomEntity roomToBook=roomBean.getRoomByFinalNumber(roomNumber);
            hotelReservations.actuallyBookTheRoom(roomToBook.getRoomId(), daysRoomBooked);
            successfulBookings.add(i);
            /*RoomTypeEntity needed=roomTypeBean.getRoomTypeByName(typeNeeded);
            List<RoomEntity> room = needed.getRooms();
            for(int k=0; k<room.size();k++){//looking through each room
                RoomEntity j = room.get(k);
                if(j.getDayBooked().isEmpty()==false){
                 ArrayList<GregorianCalendar> daysBooked = j.getDayBooked();
                 int sizeOf = daysBooked.size();
               if(sizeOf!=durationofstay){
                 for(int m=0; m<sizeOf;m++){//looking at the room's days 1 by 1
                     GregorianCalendar dayBooked=daysBooked.get(m);
                     for(int day=0; day<durationofstay; day++){//checking the order's required rooms
                        daysNeeded.add(GregorianCalendar.DAY_OF_MONTH, day);
                        if(dayBooked.compareTo(daysNeeded)==0){
                        break;
                        }else if (dayBooked.compareTo(daysRoomBooked.get(daysRoomBooked.size()-1))==0){
                        daysRoomBooked.add(daysNeeded);
                        hotelReservations.actuallyBookTheRoom(j.getRoomId(), daysRoomBooked);
                        successfulBookings.add(i); 
                        break;
                    }else{
                        daysRoomBooked.add(daysNeeded);
                    }
                    }
                    
                }
                }else if(j.getDayBooked().isEmpty()||j.getDayBooked()==null){
                    j.setDayBooked(new ArrayList<>());
                    hotelReservations.actuallyBookTheRoom(j.getRoomId(), daysRoomBooked);
                    successfulBookings.add(i);
                }
            }
            
        }
       */ 
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
