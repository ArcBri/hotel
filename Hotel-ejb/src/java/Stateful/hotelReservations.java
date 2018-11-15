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
import Singleton.hotelOperationsLocal;
import Stateless.EmployeeBeanLocal;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.RoomTypeNotFoundException;

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
    public void bookARoomEmployee(EmployeeEntity reserver, String roomType, int startYear, int startMonth, int startDay, int duration){
        BookingOrder newBooking = new BookingOrder(roomType, reserver);
        GregorianCalendar start = new GregorianCalendar(startYear, startMonth-1, startDay);
        newBooking.setDayBooked(start);
        newBooking.setDuration(duration);
        em.persist(newBooking);
        hotelOperations.queueBooking(newBooking);
        
    }
    @Override
        public void bookARoomGuest(GuestEntity reserver, String roomType, int startYear, int startMonth, int startDay, int duration){
        BookingOrder newBooking = new BookingOrder(roomType, reserver);
        GregorianCalendar start = new GregorianCalendar(startYear, startMonth-1, startDay);
        newBooking.setDayBooked(start);
        newBooking.setDuration(duration);
        em.persist(newBooking);
        hotelOperations.queueBooking(newBooking);
        
    }
    @Override
    public void actuallyBookTheRoom(RoomEntity roomToBook, List<GregorianCalendar> daysNeeded){
        for(GregorianCalendar j: daysNeeded){
            roomToBook.getDayBooked().add(j);
        }
        em.merge(roomToBook);
        em.flush();
    }
    
    @Override
    public void doIt(){
        try {
            hotelOperations.generateReport();
        } catch (RoomTypeNotFoundException ex) {
            Logger.getLogger(hotelReservations.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
