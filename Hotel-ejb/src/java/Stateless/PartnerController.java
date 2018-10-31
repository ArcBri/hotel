/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.BookingOrder;
import Entity.EmployeeEntity;
import Entity.PartnerEntity;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.EmployeeNotFoundException;

/**
 *
 * @author Joshua
 */
@Stateless
public class PartnerController implements PartnerControllerRemote, PartnerControllerLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    @EJB
    private EmployeeBeanLocal employeeBean;
    
    EmployeeEntity linkedAccount;
    PartnerEntity partner;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public List<BookingOrder> viewPartnerRecords(String partnerName) throws EmployeeNotFoundException{
        linkedAccount = employeeBean.retrieveLinkedAccount(partnerName);
        return linkedAccount.getBookingOrder();
        
    }
    
    public Long createNewPartner(String partnerName, String partnerUser, String partnerPassword){
       EmployeeEntity account = new EmployeeEntity(partnerName, "company", partnerUser, partnerPassword);
       employeeBean.createEmployeeEntity(account);
       linkedAccount=account;
       PartnerEntity newPartner = new PartnerEntity(partnerName);
       persist(newPartner);
       partner=newPartner;
       return newPartner.getId();
       
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
