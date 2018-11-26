/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Singleton;

import Entity.BookingOrder;
import Entity.EmployeeEntity;
import Entity.GuestEntity;
import Entity.PartnerEntity;
import Entity.RoomEntity;
import Entity.RoomRateEntity;
import Entity.RoomTypeEntity;
import Stateful.hotelReservationsLocal;
import Stateless.RoomBeanLocal;
import Stateless.RoomTypeBeanLocal;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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



    Queue<Integer> successfulBookings =new LinkedList<Integer>(); 
    LinkedList<BookingOrder> roomsneeded=new LinkedList<BookingOrder>();
    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;
    
    
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
        actuallygenreport();
        }
    }
@Override
     public void queueBooking(BookingOrder a){
         roomsneeded.add(a);
     }
     
     @PostConstruct
     public void init() {
         
         EmployeeEntity manager = new EmployeeEntity("man","ager","username","password");
         em.persist(manager);
         em.flush();
         GuestEntity guest=new GuestEntity("cust","omer","customer","password");
         em.persist(guest);
         em.flush();
       /*  PartnerEntity partner= new PartnerEntity("partner","password");
         em.persist(partner);
         em.flush();*/
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
         em.flush();
         em.persist(premier);
         em.flush();
         em.persist(family);
         em.flush();
         em.persist(junior);
         em.flush();
         em.persist(grand);
         em.flush();
         
         //RoomEntity(string roomtype, floornumber,roomnumber)
         RoomEntity a = new RoomEntity("Deluxe Room", 01,01);
         roomBean.createRoomLocal(a);
         RoomEntity b = new RoomEntity("Deluxe Room", 01,02);
         roomBean.createRoomLocal(b);
         RoomEntity c = new RoomEntity("Deluxe Room", 01,03);
         roomBean.createRoomLocal(c);
         RoomEntity d = new RoomEntity("Deluxe Room", 01,04);
         roomBean.createRoomLocal(d);
         RoomEntity e = new RoomEntity("Deluxe Room", 01,05);
         roomBean.createRoomLocal(e);
         RoomEntity a1 = new RoomEntity("Premier Room", 06, 01);
         roomBean.createRoomLocal(a1);
         RoomEntity b1 = new RoomEntity("Premier Room", 06, 02);
         roomBean.createRoomLocal(b1);
         RoomEntity c1 = new RoomEntity("Premier Room", 06, 03);
         roomBean.createRoomLocal(c1);
         RoomEntity d1 = new RoomEntity("Premier Room", 06, 04);
         roomBean.createRoomLocal(d1);
         RoomEntity e1 = new RoomEntity("Premier Room", 06, 05);
         roomBean.createRoomLocal(e1);
         RoomEntity a2 = new RoomEntity("Family Room", 11,01);
         roomBean.createRoomLocal(a2);
         RoomEntity b2 = new RoomEntity("Family Room", 11,02);
         roomBean.createRoomLocal(b2);
         RoomEntity c2 = new RoomEntity("Family Room", 11,03);
         roomBean.createRoomLocal(c2);
         RoomEntity d2 = new RoomEntity("Family Room", 11,04);
         roomBean.createRoomLocal(d2);
         RoomEntity e2 = new RoomEntity("Family Room", 11,05);
         roomBean.createRoomLocal(e2);
         RoomEntity a3= new RoomEntity("Junior Suite", 16,01);
         roomBean.createRoomLocal(a3);
         RoomEntity b3= new RoomEntity("Junior Suite", 16,02);
         roomBean.createRoomLocal(b3);
         RoomEntity c3= new RoomEntity("Junior Suite", 16,03);
         roomBean.createRoomLocal(c3);
         RoomEntity d3= new RoomEntity("Junior Suite", 16,04);
         roomBean.createRoomLocal(d3);
         RoomEntity e3= new RoomEntity("Junior Suite", 16,05);
         roomBean.createRoomLocal(e3);
         RoomEntity a4 = new RoomEntity("Grand Suite", 21, 01);
         roomBean.createRoomLocal(a4);
         RoomEntity b4 = new RoomEntity("Grand Suite", 21, 02);
         roomBean.createRoomLocal(b4);
         RoomEntity c4 = new RoomEntity("Grand Suite", 21, 03);
         roomBean.createRoomLocal(c4);
         RoomEntity d4 = new RoomEntity("Grand Suite", 21, 04);
         roomBean.createRoomLocal(d4);
         RoomEntity e4 = new RoomEntity("Grand Suite", 21, 05);
         roomBean.createRoomLocal(e4);
         
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
         em.flush();
         em.persist(normdel);
         em.flush();
         em.persist(pubprem);
         em.flush();
         em.persist(normprem);
         em.flush();
         em.persist(pubfam);
         em.flush();
         em.persist(normfam);
         em.flush();
         em.persist(pubjun);
         em.flush();
         em.persist(normjun);
         em.flush();
         em.persist(pubgrand);
         em.flush();
         em.persist(normgrand);         
         em.flush();
         
         
     }

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    public void persist(Object object) {
        em.persist(object);
    }

    private void actuallygenreport() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
