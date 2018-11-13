/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.BookingOrder;
import Entity.EmployeeEntity;
import Entity.RoomEntity;
import java.util.List;
import javax.ejb.Remote;
import util.EmployeeNotFoundException;

/**
 *
 * @author Joshua
 */
@Remote
public interface SampleBookingControllerRemote {

    public void addTransaction(EmployeeEntity employee, BookingOrder e);

    public List<BookingOrder> retrieveBookingOrders(String username) throws EmployeeNotFoundException;

    public Long makeRoom(RoomEntity room);


    public void bookRoom(RoomEntity room, BookingOrder book);

    public void bookFirstRoom(RoomEntity room, BookingOrder book, EmployeeEntity e);
    
}
