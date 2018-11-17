/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateful;

import Entity.EmployeeEntity;
import Entity.GuestEntity;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Joshua
 */
@Remote
public interface hotelReservationsRemote {



    public void bookARoomEmployee(EmployeeEntity reserver, String roomType, int startYear, int startMonth, int startDay, int duration);

    public void bookARoomGuest(GuestEntity reserver, String roomType, int startYear, int startMonth, int startDay, int duration);

    public void doIt();

    public void actuallyBookTheRoomRemote(Long roomId, List<GregorianCalendar> daysNeeded);

}