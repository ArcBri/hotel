/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomRateEntity;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    
    @Override
    public BigDecimal getWalkInRate(String roomType) {
        RoomRateEntity walkrate = (RoomRateEntity) em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.rateType = \\\"Published Rate\\\" AND rr.roomType LIKE :typename").setParameter("typename", roomType).getSingleResult();
        BigDecimal price = walkrate.getRatePerNight();
        return price;
    }
    
    @Override
    public BigDecimal getOnlineRate(String roomType, GregorianCalendar startDate, int duration) {
        Query findnormal = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.roomType LIKE :roomtype AND rr.rateType LIKE :ratename");
        findnormal.setParameter("roomtype", roomType);
        findnormal.setParameter("ratename", "Normal Rate");
        Query findpromo = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.roomType LIKE :roomtype AND rr.rateType LIKE :ratename");
        findpromo.setParameter("roomtype", roomType);
        findpromo.setParameter("ratename", "Promotion Rate");
        Query findpeak = em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.roomType LIKE :roomtype AND rr.rateType LIKE :ratename");
        findpeak.setParameter("roomtype", roomType);
        findpeak.setParameter("ratename", "Peak Rate");
        
        RoomRateEntity normalrate = (RoomRateEntity) findnormal.getSingleResult();
        List <RoomRateEntity> peakrate = findpeak.getResultList();
        List <RoomRateEntity> promorate = findpromo.getResultList();
        BigDecimal overallrate = BigDecimal.ZERO;
        GregorianCalendar date = startDate;
        Boolean found;
        
        for (int i = 0; i < duration; i++) {
            found = false;
            for (RoomRateEntity pr : promorate) {
                ArrayList <GregorianCalendar> promodates = pr.getRateperiod();
                for (GregorianCalendar prgc : promodates) {
                    if (prgc.equals(date)) {
                        overallrate = overallrate.add(pr.getRatePerNight());
                        date.add(GregorianCalendar.DAY_OF_WEEK, 1);
                        found = true;
                        //break;
                    }
                    if (found == true) {
                        break;
                    }
                }
                if (found == true) {
                    break;
                }
            }
            for (RoomRateEntity pe : peakrate) {
                ArrayList <GregorianCalendar> peakdates = pe.getRateperiod();
                for (GregorianCalendar pegc : peakdates) {
                    if (pegc.equals(date)) {
                        overallrate = overallrate.add(pe.getRatePerNight());
                        date.add(GregorianCalendar.DAY_OF_WEEK, 1);
                        found = true;
                        //break;
                    }
                    if (found == true) {
                        break;
                    }
                }
                if (found == true) {
                    break;
                }
            }
            if (found == false) {
                overallrate = overallrate.add(normalrate.getRatePerNight());
                date.add(GregorianCalendar.DAY_OF_WEEK, 1);
            }
        }

        return overallrate;
        
    }
    
    @Override
    public Boolean checkDisabled(String name) {
        return false;
    }
    
    @Override
    public void disable(String name) {
        RoomRateEntity rr = (RoomRateEntity) em.createQuery("SELECT rr FROM RoomRateEntity rr WHERE rr.name LIKE :ratename").setParameter("ratename", name).getSingleResult();
        rr.setDisabled(true);
        em.merge(rr);
        em.flush();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
