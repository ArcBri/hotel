/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomRateEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author user
 */
@Stateless
public class RoomRateBean implements RoomRateBeanRemote, RoomRateBeanLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    
    @Override
    public Long createRoomRate(RoomRateEntity newRoomRateEntity){
        em.persist(newRoomRateEntity);
        em.flush();
        return newRoomRateEntity.getRoomRateId();
    }
    
    @Override
    public List <RoomRateEntity> viewAllRoomRates() {
        return em.createQuery("SELECT rr FROM RoomRateEntity rr").getResultList();
    }
    
    @Override
    public RoomRateEntity getDetails(String name) {
        return (RoomRateEntity) em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name LIKE :ratename").setParameter("ratename", name).getSingleResult();
    }
    
    @Override
    public void updateRoomRateDetails(RoomRateEntity rr) {
        em.merge(rr);
        em.flush();
    }
    
    @Override
    public void deleteRoomRate(String name) {
        RoomRateEntity roomrate = (RoomRateEntity) em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name LIKE :ratename").setParameter("ratename", name).getSingleResult();
        em.remove(roomrate);
        em.flush();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
