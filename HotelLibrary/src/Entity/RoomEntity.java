/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Justin
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
    @Column
    private boolean roomAvailable;
    @Column(length = 32, nullable = false)
    private int bedNumber;
    @Column(length = 32, nullable = false)
    private int capacity;
    @Column
    private int[] amenities=new int[0];
    @Column(length = 32, nullable = false)
    private int roomNumberFinal;
    @ManyToOne
    private BookingOrder booking;


    public RoomEntity() {
    }

    public RoomEntity(int roomType, int floorNumber, int roomNumber, boolean roomAvailable, int bedNumber, int capacity, int[] amenities) {
        this.roomType = roomType;
        this.floorNumber = floorNumber;
        this.roomNumber = roomNumber;
        this.roomAvailable = roomAvailable;
        this.bedNumber = bedNumber;
        this.capacity = capacity;
        this.amenities = amenities;
        roomNumberFinal = (floorNumber*100) + roomNumber;
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
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public boolean isRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(boolean roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public int getBedNumber() {
        return bedNumber;
    }

    public void setBedNumber(int bedNumber) {
        this.bedNumber = bedNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int[] getAmenities() {
        return amenities;
    }

    public void setAmenities(int[] amenities) {
        this.amenities = amenities;
    }

    public int getRoomNumberFinal() {
        return roomNumberFinal;
    }

    public void setRoomNumberFinal(int roomNumberFinal) {
        this.roomNumberFinal = roomNumberFinal;
    }

    public BookingOrder getBooking() {
        return booking;
    }

    public void setBooking(BookingOrder booking) {
        this.booking = booking;
    }
        @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomId != null ? roomId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the orderId fields are not set
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


    
}
