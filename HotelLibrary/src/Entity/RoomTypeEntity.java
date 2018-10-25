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
    @Column(length = 32, nullable = false)
    private String typeDescription;
    @Column(length = 32, nullable = false)
    private String size;
    @Column(length = 32, nullable = false)
    private int bedNumber;
    @Column(length = 32, nullable = false)
    private int capacity;
    @Column
    private int[] amenities=new int[10];
    
   

    public RoomTypeEntity() {
    }

    public RoomTypeEntity(String typeName, String typeDescription, String size, int bedNumber, int capacity) {
        this.typeName = typeName;
        this.typeDescription = typeDescription;
        this.size = size;
        this.bedNumber = bedNumber;
        this.capacity = capacity;
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
    
    /*public String getTypeName() {
        String returnType = "";
        switch (typeName) {
            case 1:
                returnType = "Deluxe Room";
                break;
            case 2:
                returnType = "Premier Room";
                break;
            case 3:
                returnType = "Family Room";
                break;
            case 4:
                returnType = "Junior Suite";
                break;
            case 5:
                returnType = "Grand Suite";
                break;
        }
        return returnType;
    }*/

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
    
    


    
}

/*
01 = Deluxe Room
02 = Premier Room
03 = Family Room
04 = Junior Suite
05 = Grand Suite
*/
