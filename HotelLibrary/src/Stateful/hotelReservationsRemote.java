/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateful;

import Entity.EmployeeEntity;
import Entity.GuestEntity;
import Entity.PartnerEntity;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Joshua
 */
@Remote
public interface hotelReservationsRemote {




    public void doIt();

    public void actuallyBookTheRoomRemote(Long roomId, List<GregorianCalendar> daysNeeded);

    public void bookARoomEmployee(EmployeeEntity reserver, String roomType, int startYear, int startMonth, int startDay, int duration, int roomnumber);

    public void bookARoomGuest(GuestEntity reserver, String roomType, int startYear, int startMonth, int startDay, int duration, int roomnumber);

    public void bookARoomPartner(PartnerEntity reserver, String roomType, int startYear, int startMonth, int startDay, int duration, int roomnumber);

}
