/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateful;

import Entity.RoomEntity;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Joshua
 */
@Local
public interface hotelReservationsLocal {

    public void actuallyBookTheRoom(RoomEntity roomToBook, List<GregorianCalendar> daysNeeded);
    
}
