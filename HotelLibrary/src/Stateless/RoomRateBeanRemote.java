/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomRateEntity;
import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface RoomRateBeanRemote {
    public Long createRoomRate(RoomRateEntity newRoomRateEntity);
    public List <RoomRateEntity> viewAllRoomRates();
    public RoomRateEntity getDetails(String name);
    public void updateRoomRateDetails(RoomRateEntity rr);
    public void deleteRoomRate(String name);
    public RoomRateEntity getWalkInRate(String roomtype);
    public BigDecimal getOnlineRate(String roomType, GregorianCalendar startDate, int duration);
}
