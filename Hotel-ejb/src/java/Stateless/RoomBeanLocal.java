/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomEntity;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface RoomBeanLocal {

    public RoomEntity getRoomByFinalNumber(int roomfinalnumber);

    public Long createRoomLocal(RoomEntity newRoomEntity);
    
}
