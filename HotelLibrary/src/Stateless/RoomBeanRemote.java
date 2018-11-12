/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface RoomBeanRemote {
    
    public Long createRoom(RoomEntity newRoomEntity);
    public List<RoomEntity> viewAllRooms();
    public RoomEntity getRoomDetails(int roomnumber);
    public void updateRoomDetails(RoomEntity rm);
    public void deleteRoom(int roomnumber);
}
