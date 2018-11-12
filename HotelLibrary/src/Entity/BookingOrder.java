/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Joshua
 */
@Entity
public class BookingOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long orderId;
    @Column(nullable=false)
    private String typeName;
    @Column(nullable = false)
    private List<GregorianCalendar> dayBooked;
    @ManyToOne(optional = true)
    private EmployeeEntity employee;
    @ManyToOne(optional = true)
    private GuestEntity guest;

    public BookingOrder() {
    }

    public BookingOrder(String typeName, EmployeeEntity employee) {
        this.typeName = typeName;
        this.dayBooked = new ArrayList<GregorianCalendar>();
        this.employee = employee;
    }

    public BookingOrder(String typeName, GuestEntity guest) {
        this.typeName = typeName;
        this.dayBooked = new ArrayList<GregorianCalendar>();
        this.guest = guest;
    }


    
    

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderId != null ? orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the orderId fields are not set
        if (!(object instanceof BookingOrder)) {
            return false;
        }
        BookingOrder other = (BookingOrder) object;
        if ((this.orderId == null && other.orderId != null) || (this.orderId != null && !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.BookingOrder[ id=" + orderId + " ]";
    }



    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public GuestEntity getGuest() {
        return guest;
    }

    public void setGuest(GuestEntity guest) {
        this.guest = guest;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public List<GregorianCalendar> getDayBooked() {
        return dayBooked;
    }

    public void setDayBooked(List<GregorianCalendar> dayBooked) {
        this.dayBooked = dayBooked;
    }
    
}
