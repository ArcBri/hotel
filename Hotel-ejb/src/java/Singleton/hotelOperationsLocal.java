/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Singleton;

import Entity.BookingOrder;
import javax.ejb.Local;
import util.RoomTypeNotFoundException;

/**
 *
 * @author Joshua
 */
@Local
public interface hotelOperationsLocal {

    public void queueBooking(BookingOrder a);

    public void generateReport() throws RoomTypeNotFoundException;
    
}
