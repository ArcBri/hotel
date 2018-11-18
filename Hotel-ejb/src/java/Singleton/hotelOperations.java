/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Singleton;

import Entity.BookingOrder;
import Entity.RoomEntity;
import Entity.RoomRateEntity;
import Entity.RoomTypeEntity;
import Stateful.hotelReservationsLocal;
import Stateless.RoomBeanLocal;
import Stateless.RoomTypeBeanLocal;
import java.math.BigDecimal;
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
     
     public void init() {
         //RoomTypeEntity(String typeName, String typeDescription, String size, int bedNumber, int capacity, int[] amenities);
         int[] deluxeamen = {1, 1, 1, 1, 1, 1, 1, 1, 1};
         int[] premieramen = {1, 2, 2, 2, 2, 2, 2, 2, 2};
         int[] familyamen = {1, 3, 4, 3, 4, 3, 4, 3, 4};
         int[] junioramen = {2, 4, 4, 4, 4, 4, 4, 4, 4};
         int[] grandamen = {2, 5, 7, 5, 7, 5, 7, 5, 7};
         RoomTypeEntity deluxe = new RoomTypeEntity("Deluxe Room", "A very Deluxe Room", "Small", 2, 4, deluxeamen);
         RoomTypeEntity premier = new RoomTypeEntity("Premier Room", "A rare room made in commemoration for an event", "Medium", 3, 6, premieramen);
         RoomTypeEntity family = new RoomTypeEntity("Family Room", "A room to be booked by Families", "Medium", 3, 6, familyamen);
         RoomTypeEntity junior = new RoomTypeEntity("Junior Suite", "A kid sized suite", "Medium", 4, 8, junioramen);
         RoomTypeEntity grand = new RoomTypeEntity("Grand Suite", "A Grand Blue Room for Dreaming and Diving", "Large", 5, 10, grandamen);
         em.persist(deluxe);
         em.persist(premier);
         em.persist(family);
         em.persist(junior);
         em.persist(grand);
         
         //RoomRateEntity(String name, String roomType, String rateType, BigDecimal ratePerNight)
         RoomRateEntity pubdel = new RoomRateEntity("pubdeluxe", "Deluxe Room", "Published Rate", BigDecimal.valueOf(100));
         RoomRateEntity normdel = new RoomRateEntity("normdeluxe", "Deluxe Room", "Normal Rate", BigDecimal.valueOf(80));
         RoomRateEntity pubprem = new RoomRateEntity("pubpremier", "Premier Room", "Published Rate", BigDecimal.valueOf(120));
         RoomRateEntity normprem = new RoomRateEntity("normpremier", "Premier Room", "Normal Rate", BigDecimal.valueOf(100));
         RoomRateEntity pubfam = new RoomRateEntity("pubfamily", "Family Room", "Published Rate", BigDecimal.valueOf(150));
         RoomRateEntity normfam = new RoomRateEntity("normfamily", "Family Room", "Normal Rate", BigDecimal.valueOf(120));
         RoomRateEntity pubjun = new RoomRateEntity("pubjunior", "Junior Suite", "Published Rate", BigDecimal.valueOf(180));
         RoomRateEntity normjun = new RoomRateEntity("normjunior", "Junior Suite", "Normal Rate", BigDecimal.valueOf(150));
         RoomRateEntity pubgrand = new RoomRateEntity("pubgrand", "Grand Suite", "Published Rate", BigDecimal.valueOf(200));
         RoomRateEntity normgrand = new RoomRateEntity("normgrand", "Grand Suite", "Normal Rate", BigDecimal.valueOf(180));
         
         em.persist(pubdel);
         em.persist(normdel);
         em.persist(pubprem);
         em.persist(normprem);
         em.persist(pubfam);
         em.persist(normfam);
         em.persist(pubjun);
         em.persist(normjun);
         em.persist(pubgrand);
         em.persist(normgrand);
         
         
         em.flush();
         
         
     }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
