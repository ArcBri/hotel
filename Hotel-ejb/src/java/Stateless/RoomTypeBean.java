/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomTypeEntity;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Scanner;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.RoomTypeNotFoundException;

/**
 *
 * @author user
 */
@Stateless
public class RoomTypeBean implements RoomTypeBeanRemote, RoomTypeBeanLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;
    private String storage;
    Scanner sc = new Scanner(System.in);

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
    
    @Override
    public RoomTypeEntity getRoomTypeDetails(String typename) throws RoomTypeNotFoundException
    {
        try{
        return (RoomTypeEntity) em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.typeName LIKE :roomtypename").setParameter("roomtypename", typename).getSingleResult();
        }
        catch(NoResultException ex){
            throw new RoomTypeNotFoundException("Room Type does not Exist.");
        }
    }
    @Override
    public RoomTypeEntity getRoomTypeByName(String typename){
        return (RoomTypeEntity) em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.typeName LIKE :roomtypename").setParameter("roomtypename", typename).getSingleResult();
    }
    
    @Override
    public void updateRoomTypeDetails(RoomTypeEntity rt){
        
        em.merge(rt);
        em.flush();
    }
    
    @Override
    public void deleteRoomType(String typename) {
        RoomTypeEntity roomtype = (RoomTypeEntity) em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.typeName LIKE :roomtypename").setParameter("roomtypename", typename).getSingleResult();
        em.remove(roomtype);
        em.flush();
    }
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
