/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.BookingOrder;
import Entity.GuestEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.EmployeeNotFoundException;
import util.exception.InvalidLoginCredentialException;

/**
 *
 * @author Joshua
 */
@Stateless
public class GuestController implements GuestControllerRemote, GuestControllerLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public void register(GuestEntity guest){
        em.persist(guest);
        em.flush();
    }
        @Override
    public GuestEntity retrieveByUsername(String username) throws EmployeeNotFoundException
    {
        Query query = em.createQuery("SELECT g FROM GuestEntity g WHERE g.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (GuestEntity)query.getSingleResult();
        }
        catch(NoResultException|NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("User does not exist.");
        }


    }
    @Override
    public GuestEntity userLogin(String username, String password)throws InvalidLoginCredentialException{
    
       
        try{
            GuestEntity guestUser = retrieveByUsername(username);
            
            if(guestUser.getPassword().equals(password))
            {                
                return guestUser;
            }else{
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EmployeeNotFoundException ex){
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }

        
    }

    public void persist(Object object) {
        em.persist(object);
    }
    
    public List<BookingOrder> viewAllReservations(GuestEntity guest) {
        List<BookingOrder> reservationlist = em.createQuery("SELECT bo FROM BookingOrder bo").getResultList();
        for (BookingOrder bo: reservationlist) {
            if(bo.getGuest().equals(guest) == false) {
                reservationlist.remove(bo);
            }
        }
        return reservationlist;
    }
    
    public BookingOrder getReservationDetails(GuestEntity guest, int roomnumber) {
        List <BookingOrder> booking = em.createQuery("SELECT bo FROM BookingOrder bo WHERE bo.roomFinalNumber LIKE :number").setParameter("number", roomnumber).getResultList();
        BookingOrder roombooking = new BookingOrder();
        for (BookingOrder bo: booking) {
            if(bo.getGuest().equals(guest)) {
                roombooking = bo;
            }
        }
        return roombooking;
    }
}
