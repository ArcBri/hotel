/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomTypeEntity;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author user
 */
@Remote
public interface RoomTypeBeanRemote {

    public List<RoomTypeEntity> viewAllRoomTypes();

    public Long createRoomType(RoomTypeEntity newRoomTypeEntity);
    
}
