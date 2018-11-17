/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    private String rateType;
    @Column(length = 32, nullable = false)
    private String roomType;
    @Column(length = 32, nullable = false)
    private BigDecimal ratePerNight;
    @Column
    private GregorianCalendar validityStart = null;
    @Column
    private GregorianCalendar validityEnd = null;
    @Column 
    private ArrayList <GregorianCalendar> rateperiod; 
    @ManyToOne
    private RoomTypeEntity roomstype;
    private Boolean validity = false;

    public RoomRateEntity() {
    }

    public RoomRateEntity(String name, String roomType, String rateType, BigDecimal ratePerNight) {
        this.name = name;
        this.roomType = roomType;
        this.rateType = rateType;
        this.ratePerNight = ratePerNight;
        this.validityStart = new GregorianCalendar(1945, 12, 2);
        this.validityEnd = new GregorianCalendar(2100, 11, 4);
    }

    /*public RoomRateEntity(String name, String roomType, String rateType, BigDecimal ratePerNight, GregorianCalendar validityPeriod) { //should be unused
        this.name = name;
        this.roomType = roomType;
        this.rateType = rateType;
        this.ratePerNight = ratePerNight;
        this.validityStart = validityPeriod;
    }*/
    
    public Long getRoomRateId() {
        return id;
    }

    public void setRoomRateId(Long id) {
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

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRateType() {
        return rateType;
    }

    public void setRateType(String rateType) {
        this.rateType = rateType;
    }

    public BigDecimal getRatePerNight() {
        return ratePerNight;
    }

    public void setRatePerNight(BigDecimal ratePerNight) {
        this.ratePerNight = ratePerNight;
    }

    public GregorianCalendar getValidityStart() {
        return validityStart;
    }

    public void setValidityStart(GregorianCalendar validityStart) {
        this.validityStart = validityStart;
    }

    public RoomTypeEntity getRoomsType() {
        return roomstype;
    }

    public void setRoomsType(RoomTypeEntity roomsType) {
        this.roomstype = roomsType;
    }

    public GregorianCalendar getValidityEnd() {
        return validityEnd;
    }

    public void setValidityEnd(GregorianCalendar validityEnd) {
        this.validityEnd = validityEnd;
    }

    public Boolean getValidity() {
        return validity;
    }

    public void setValidity(Boolean validity) {
        this.validity = validity;
    }

    public ArrayList <GregorianCalendar> getRateperiod() {
        return rateperiod;
    }

    public void setRateperiod(ArrayList <GregorianCalendar> rateperiod) {
        this.rateperiod = rateperiod;
    }
    
}

/*
01 = published rate
02 = normal rate
03 = peak rate
04 = promotion rate
*/