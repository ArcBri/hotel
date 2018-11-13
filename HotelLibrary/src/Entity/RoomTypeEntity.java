/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.CascadeType.PERSIST;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Justin
 */
@Entity
public class RoomTypeEntity implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long roomTypeId;
    @Column(length = 32, nullable = false)
    private String typeName;
    @Column(length = 255, nullable = false)
    private String typeDescription;
    @Column(length = 32, nullable = false)
    private String size;
    @Column(length = 32, nullable = false)
    private int bedNumber;
    @Column(length = 32, nullable = false)
    private int capacity;
    @Column
    private int[] amenities=new int[9];
    @OneToMany(mappedBy="roomtype", cascade={PERSIST, MERGE})
    private List<RoomEntity> rooms = new ArrayList<RoomEntity>();
    @OneToMany(mappedBy="roomstype", cascade={PERSIST, MERGE})
    private List<RoomRateEntity> roomrates;
    private int disabled = 0;
    
    
   

    public RoomTypeEntity() {
    }

    public RoomTypeEntity(String typeName, String typeDescription, String size, int bedNumber, int capacity, int[] amenities) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
        this.size = size;
        this.bedNumber = bedNumber;
        this.capacity = capacity;
        this.amenities = amenities;
    }
    

    
    public RoomTypeEntity(String typeName, int bedNumber, int capacity, int[] amenities) {
        this.typeName = typeName;
        this.bedNumber = bedNumber;
        this.capacity = capacity;
        //this.amenities = amenities;
    }

    public Long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomId(Long roomId) {
        this.roomTypeId = roomId;
    }
    
        @Override
    public int hashCode() {
        int hash = 0;
        hash += (roomTypeId != null ? roomTypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the orderId fields are not set
        if (!(object instanceof RoomTypeEntity)) {
            return false;
        }
        RoomTypeEntity other = (RoomTypeEntity) object;
        if ((this.roomTypeId == null && other.roomTypeId != null) || (this.roomTypeId != null && !this.roomTypeId.equals(other.roomTypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.RoomEntity[ id=" + roomTypeId + " ]";
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getDisabled() {
        return disabled;
    }

    public void setDisabled() {
        this.disabled = 1;
    }

    public List<RoomEntity> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomEntity> rooms) {
        this.rooms = rooms;
    }

    public List<RoomRateEntity> getRoomrates() {
        return roomrates;
    }

    public void setRoomrates(List<RoomRateEntity> roomrates) {
        this.roomrates = roomrates;
    }




    
}

/*
01 = Deluxe Room
02 = Premier Room
03 = Family Room
04 = Junior Suite
05 = Grand Suite
*/
