/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateful;

import Entity.EmployeeEntity;
import Entity.GuestEntity;
import javax.ejb.Remote;

/**
 *
 * @author Joshua
 */
@Remote
public interface hotelReservationsRemote {

    public void bookARoomEmployee(EmployeeEntity reserver, String roomType, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay);

    public void bookARoomGuest(GuestEntity reserver, String roomType, int startYear, int startMonth, int startDay, int endYear, int endMonth, int endDay);

}
