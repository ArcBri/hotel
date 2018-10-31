/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author user
 */
@Entity
public class RoomRateEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 32, nullable = false)
    private String name;
    @Column(length = 32, nullable = false)
    private int rateType;
    @Column(length = 32, nullable = false)
    private int roomType;
    @Column(length = 32, nullable = false)
    private int ratePerNight;
    @Column
    private Calendar validityPeriod;
    @ManyToOne
    private RoomTypeEntity roomstype;

    public RoomRateEntity() {
    }

    public RoomRateEntity(String name, int roomType, int rateType, int ratePerNight) {
        this.name = name;
        this.roomType = roomType;
        this.rateType = rateType;
        this.ratePerNight = ratePerNight;
    }

    public RoomRateEntity(String name, int roomType, int rateType, int ratePerNight, Calendar validityPeriod) {
        this.name = name;
        this.roomType = roomType;
        this.rateType = rateType;
        this.ratePerNight = ratePerNight;
        this.validityPeriod = validityPeriod;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoomRateEntity)) {
            return false;
        }
        RoomRateEntity other = (RoomRateEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.RoomRateEntity[ id=" + id + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRoomType() {
        return roomType;
    }

    public void setRoomType(int roomType) {
        this.roomType = roomType;
    }

    public int getRateType() {
        return rateType;
    }

    public void setRateType(int rateType) {
        this.rateType = rateType;
    }

    public int getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(int ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    public Calendar getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Calendar validityPeriod) {
        this.validityPeriod = validityPeriod;
    }

    public RoomTypeEntity getRoomsType() {
        return roomstype;
    }

    public void setRoomsType(RoomTypeEntity roomsType) {
        this.roomstype = roomsType;
    }
    
}

/*
01 = published rate
02 = normal rate
03 = peak rate
04 = promotion rate
*/