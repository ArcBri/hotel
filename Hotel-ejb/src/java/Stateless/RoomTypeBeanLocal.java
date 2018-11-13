/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stateless;

import Entity.RoomTypeEntity;
import javax.ejb.Local;
import util.RoomTypeNotFoundException;

/**
 *
 * @author user
 */
@Local
public interface RoomTypeBeanLocal {

    public RoomTypeEntity getRoomTypeByName(String typename) throws RoomTypeNotFoundException;
    
}
