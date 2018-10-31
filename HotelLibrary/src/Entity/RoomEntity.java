/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author user
 */
@Entity
public class RoomEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomId;
    @Column(length = 32, nullable = false)
    private int roomType;
    @Column(length = 32, nullable = false)
    private int floorNumber;
    @Column(length = 32, nullable = false)
    private int roomNumber;
    @Column(length = 32, nullable = false)
    private int finalNumber;
    @Column
    private boolean roomAvailable;
    @ManyToMany
    private List<dayTracker> dayBooked;
    @ManyToOne
    private RoomTypeEntity roomtype;

    public RoomEntity() {
    }

    public RoomEntity(int roomType, int floorNumber, int roomNumber, boolean roomAvailable) {
        this.roomType = roomType;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.roomAvailable = roomAvailable;
        this.finalNumber =  floorNumber * 100 + roomNumber;
    }
    
    

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(int floorNumber) {
        this.floorNumber = floorNumber;
        this.finalNumber =  floorNumber * 100 + roomNumber;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
        this.finalNumber =  floorNumber * 100 + roomNumber;
    }

    public int getFinalNumber() {
        return finalNumber;
    }

    public void setFinalNumber(int finalNumber) {
        this.finalNumber = finalNumber;
    }

    public boolean isRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(boolean roomAvailable) {
        this.roomAvailable = roomAvailable;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the roomId fields are not set
        if (!(object instanceof RoomEntity)) {
            return false;
        }
        RoomEntity other = (RoomEntity) object;
        if ((this.roomId == null && other.roomId != null) || (this.roomId != null && !this.roomId.equals(other.roomId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.RoomEntity[ id=" + roomId + " ]";
    }

    public List<dayTracker> getDayBooked() {
        return dayBooked;
    }

    public void setDayBooked(List<dayTracker> dayBooked) {
        this.dayBooked = dayBooked;
    }

    public RoomTypeEntity getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(RoomTypeEntity roomtype) {
        this.roomtype = roomtype;
    }
    
}
