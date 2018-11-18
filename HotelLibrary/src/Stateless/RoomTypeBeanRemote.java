/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Remote;
import util.RoomTypeNotFoundException;

/**
 *
 * @author user
 */
@Remote
public interface RoomTypeBeanRemote {

    public List<RoomTypeEntity> viewAllRoomTypes();

    public Long createRoomType(RoomTypeEntity newRoomTypeEntity);
    
    public RoomTypeEntity getRoomTypeDetails(String typename)throws RoomTypeNotFoundException;
    
    public void updateRoomTypeDetails(RoomTypeEntity rt);
    
    public void deleteRoomType(String typename);
    
    public Boolean checkDisabled(String typename);
    
    public void disableRoomType(String typename);
}
