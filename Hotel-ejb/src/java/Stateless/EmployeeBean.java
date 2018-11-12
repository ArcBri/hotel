/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.EmployeeEntity;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
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
@Local(EmployeeBeanLocal.class)
@Remote(EmployeeBeanRemote.class)
public class EmployeeBean implements EmployeeBeanRemote, EmployeeBeanLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    private EmployeeEntity loggedInEntity;
    
    public void persist(Object object) {
        em.persist(object);
    }
     @Override
    public Long createEmployeeEntity(EmployeeEntity newEmployeeEntity){
        em.persist(newEmployeeEntity);
        em.flush(); //for identity primary key must flush before exit method
        
        return newEmployeeEntity.getEmployeeId(); //ctrl space auto complete
    }
    
    @Override
    public List<EmployeeEntity> retrieveAllEmployeeEntity(){
        Query query = em.createQuery("SELECT ee FROM EmployeeEntity ee");
        return query.getResultList();
    }
    @Override
    public EmployeeEntity retrieveStaffByUsername(String username) throws EmployeeNotFoundException
    {
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.username = :inUsername");
        query.setParameter("inUsername", username);
        
        try
        {
            return (EmployeeEntity)query.getSingleResult();
        }
        catch(NoResultException|NonUniqueResultException ex)
        {
            throw new EmployeeNotFoundException("Employee does not exist.");
        }


    }
    @Override
    public EmployeeEntity retrieveLinkedAccount(String name) {
        Query query = em.createQuery("SELECT e FROM EmployeeEntity e WHERE e.firstname = :inUsername");
        query.setParameter("inUsername", name);
        return (EmployeeEntity) query.getSingleResult();
    }
    @Override
    public EmployeeEntity staffLogin(String username, String password)throws InvalidLoginCredentialException{
    
       
        try{
            EmployeeEntity staffEntity = retrieveStaffByUsername(username);
            
            if(staffEntity.getPassword().equals(password))
            {                
                loggedInEntity=staffEntity;
                return staffEntity;
            }else{
                throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
            }
        }
        catch(EmployeeNotFoundException ex){
            throw new InvalidLoginCredentialException("Username does not exist or invalid password!");
        }

        
    }
        @Override
    public Long createEmployee(EmployeeEntity newEmployeeEntity){
        em.persist(newEmployeeEntity);
        em.flush(); //for identity primary key must flush before exit method
        
        return newEmployeeEntity.getEmployeeId(); //ctrl space auto complete
    }
    @Override
    public EmployeeEntity getReserver(){
        return loggedInEntity;
    }



    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
