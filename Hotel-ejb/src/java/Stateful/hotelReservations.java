/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateful;

import Entity.BookingOrder;
import Entity.EmployeeEntity;
import Entity.GuestEntity;
import Entity.RoomEntity;
import Entity.dayTracker;
import Singleton.hotelOperationsLocal;
import Stateless.EmployeeBeanLocal;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Joshua
 */
@Stateful
public class hotelReservations implements hotelReservationsRemote, hotelReservationsLocal {

    @EJB
    private hotelOperationsLocal hotelOperations;

    @EJB
    private EmployeeBeanLocal employeeBean;

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;
    
    private EmployeeEntity loggedInEmployee;
    private GuestEntity loggedInGuest;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public void bookARoomEmployee(EmployeeEntity reserver, String roomType, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        BookingOrder newBooking = new BookingOrder(roomType, reserver);
        List<GregorianCalendar> daysBooked=new ArrayList<GregorianCalendar>();
        GregorianCalendar start = new GregorianCalendar(startYear, startMonth-1, startDay);
        GregorianCalendar end = new GregorianCalendar(endYear, endMonth-1, endDay);
        daysBooked.add(start);
        while(start.equals(end)==false){
            int i=1;
            GregorianCalendar targetDay = new GregorianCalendar(startYear, startMonth, startDay+i);
            start=targetDay;
            daysBooked.add(start);
        }
        newBooking.setDayBooked(daysBooked);
        em.persist(newBooking);
        hotelOperations.queueBooking(newBooking);
        
    }
    @Override
        public void bookARoomGuest(GuestEntity reserver, String roomType, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        BookingOrder newBooking = new BookingOrder(roomType, reserver);
        List<GregorianCalendar> daysBooked=new ArrayList<GregorianCalendar>();
        GregorianCalendar start = new GregorianCalendar(startYear, startMonth-1, startDay);
        GregorianCalendar end = new GregorianCalendar(endYear, endMonth-1, endDay);
        daysBooked.add(start);
        while(start.equals(end)==false){
            int i=1;
            GregorianCalendar targetDay = new GregorianCalendar(startYear, startMonth, startDay+i);
            start=targetDay;
            daysBooked.add(start);
        }
        newBooking.setDayBooked(daysBooked);
        em.persist(newBooking);
        hotelOperations.queueBooking(newBooking);
        
    }
    @Override
    public void actuallyBookTheRoom(RoomEntity roomToBook, List<GregorianCalendar> daysNeeded){
        for(GregorianCalendar g : daysNeeded){
            dayTracker bookedDay=new dayTracker(g);
            roomToBook.getDayBooked().add(bookedDay);
        }
        em.persist(roomToBook);
        em.flush();
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
