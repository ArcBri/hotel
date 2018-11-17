/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.PartnerEntity;
import java.util.ArrayList;
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
public class PartnerController implements PartnerControllerRemote, PartnerControllerLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
       @Override
    public PartnerEntity partnerLogin(String username, String password)throws InvalidLoginCredentialException{
    
       
        try{
            PartnerEntity partner = retrievePartnerByUsername(username);
            
            if(partner.getPassword().equals(password))
            {                
                partner.setBookingOrder(new ArrayList<>());
                return partner;
            }else{
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EmployeeNotFoundException ex){
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }

        
    }
    public PartnerEntity retrievePartnerByUsername(String username) throws EmployeeNotFoundException
        {
        Query query = em.createQuery("SELECT p FROM PartnerEntity p WHERE p.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (PartnerEntity)query.getSingleResult();
        }
        catch(NoResultException|NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("Employee does not exist.");
        }


    }

    public void persist(Object object) {
        em.persist(object);
    }
}
