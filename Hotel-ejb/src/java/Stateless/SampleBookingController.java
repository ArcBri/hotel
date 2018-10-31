/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.BookingOrder;
import Entity.EmployeeEntity;
import Entity.RoomEntity;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.EmployeeNotFoundException;

/**
 *
 * @author Joshua
 */
@Stateless
public class SampleBookingController implements SampleBookingControllerRemote, SampleBookingControllerLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    @Override
    public void addTransaction(EmployeeEntity employee, BookingOrder e){
        em.persist(e);
        em.flush();
        updateEmployeetransactions(employee, e);
        System.out.println("Success1");
    }
    @Override
    public Long makeRoom(RoomEntity room){
        em.persist(room);
        return room.getRoomId();
    }
    @Override
    public void bookFirstRoom(RoomEntity room, BookingOrder book, EmployeeEntity e){
       book.setRoomBooking(new ArrayList<RoomEntity>());
       room.setBooking(new ArrayList<BookingOrder>());
       room.getBooking().add(book);
       book.getRoomBooking().add(room);
       em.persist(book);
       em.flush();
       updateEmployeetransactions(e, book);
    }
    @Override
    public void bookRoom(RoomEntity room, BookingOrder book){
        book.getRoomBooking().add(room);
        em.merge(book);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public void updateEmployeetransactions(EmployeeEntity employee, BookingOrder e){
        e.setEmployee(employee);
        employee.setBookingOrder(new ArrayList<BookingOrder>());
        employee.getBookingOrder().add(e);
        em.merge(employee);
        em.flush();
        System.out.println("success");
    }
    @Override
    public List<BookingOrder> retrieveBookingOrders(String username) throws EmployeeNotFoundException{
        Query query=em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);
        
         try
        {
            EmployeeEntity e=(EmployeeEntity)query.getSingleResult();
            return e.getBookingOrder();
        }
        catch(NoResultException|NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("Employee does not exist.");
        }
        
    }
    
}
