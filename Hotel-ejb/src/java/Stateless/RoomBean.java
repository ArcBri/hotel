/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomEntity;
import Entity.RoomTypeEntity;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class RoomBean implements RoomBeanRemote, RoomBeanLocal {

    @PersistenceContext(unitName = "Hotel-ejbPU")
    private EntityManager em;

    public void persist(Object object) {
        em.persist(object);
    }
    
    @Override
    public Long createRoom(RoomEntity newRoomEntity){
        String typename = newRoomEntity.getRoomType();
        RoomTypeEntity roomtype = (RoomTypeEntity) em.createQuery("SELECT rt FROM RoomTypeEntity rt WHERE rt.typeName LIKE :roomtypename").setParameter("roomtypename", typename).getSingleResult();
        newRoomEntity.setRoomtype(roomtype);
        roomtype.getRooms().add(newRoomEntity);
        em.persist(newRoomEntity);
        em.flush();
        return newRoomEntity.getRoomId();
    }
    
    
    @Override
    public List<RoomEntity> viewAllRooms(){
        Query query = em.createQuery("SELECT r FROM RoomEntity r");
        return query.getResultList();
    }
    
    @Override
    public RoomEntity getRoomDetails(int roomnumber) {
        return (RoomEntity) em.createQuery("SELECT rm FROM RoomEntity rm WHERE rm.finalNumber LIKE :roomfinalnumber").setParameter("roomfinalnumber", roomnumber).getSingleResult();
    }
    @Override
        public RoomEntity getRoomByFinalNumber(int roomfinalnumber) {
        return (RoomEntity) em.createQuery("SELECT rm FROM RoomEntity rm WHERE rm.finalNumber LIKE :roomfinalnumber").setParameter("roomfinalnumber", roomfinalnumber).getSingleResult();
    }
    
    @Override
    public void updateRoomDetails (RoomEntity rm) {
        em.merge(rm);
        em.flush();
    }
    
    @Override
    public void deleteRoom(int roomnumber) {
        RoomEntity room = (RoomEntity) em.createQuery("SELECT rm FROM RoomEntity rm WHERE rm.finalNumber LIKE :roomfinalnumber").setParameter("roomfinalnumber", roomnumber).getSingleResult();
        em.remove(room);
        em.flush();
    }
    
    @Override
    public List<RoomEntity> searchRoom(GregorianCalendar dateStart, int duration) {
        List<RoomEntity> candidates = em.createQuery("SELECT rm FROM RoomEntity rm").getResultList();
        for(RoomEntity rm: candidates) {
            List<GregorianCalendar> daysBooked = rm.getDayBooked();
            for(GregorianCalendar k: daysBooked){//looking at the room's days 1 by 1
                    for(int day=0; day<duration; day++){//checking the order's required rooms
                        dateStart.add(GregorianCalendar.DAY_OF_MONTH, day);
                        if(k.compareTo(dateStart)==0){
                            candidates.remove(rm);
                        break;
                    }
                    }
            }
        }
        return(candidates);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
