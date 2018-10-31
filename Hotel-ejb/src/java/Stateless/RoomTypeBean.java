/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class RoomTypeBean implements RoomTypeBeanRemote, RoomTypeBeanLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    @Override
    public Long createRoomType(RoomTypeEntity newRoomTypeEntity){
        em.persist(newRoomTypeEntity);
        em.flush();
        return newRoomTypeEntity.getRoomTypeId();
    }
    @Override
    public List<RoomTypeEntity> viewAllRoomTypes(){
        Query query = em.createQuery("SELECT rt FROM RoomTypeEntity rt");
        return query.getResultList();
    }
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
