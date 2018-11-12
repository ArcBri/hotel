/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateful;

import Entity.BookingOrder;
import Entity.EmployeeEntity;
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

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    public void bookARoomEmployee(String roomType, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay){
        EmployeeEntity reserver = employeeBean.getReserver();
        BookingOrder newBooking = new BookingOrder(roomType, reserver);
        List<GregorianCalendar> daysBooked=new ArrayList<GregorianCalendar>();
        GregorianCalendar start = new GregorianCalendar(startYear, startMonth, startDay);
        GregorianCalendar end = new GregorianCalendar(endYear, endMonth, endDay);
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

    public void persist(Object object) {
        em.persist(object);
    }
}
