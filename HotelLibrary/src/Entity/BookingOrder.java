/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    @ManyToMany(cascade=ALL, mappedBy="booking")
    private List<dayTracker> bookedDays;
    @ManyToOne
    private EmployeeEntity employee;
    @ManyToOne
    private GuestEntity guest;

    public BookingOrder() {
    }

    public BookingOrder(List<dayTracker> bookedDays, EmployeeEntity employee) {
        this.bookedDays = bookedDays;
        this.employee = employee;
    }

    public BookingOrder(List<dayTracker> bookedDays, GuestEntity guest) {
        this.bookedDays = bookedDays;
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

    public List<dayTracker> getBookedDays() {
        return bookedDays;
    }

    public void setBookedDays(List<dayTracker> bookedDays) {
        this.bookedDays = bookedDays;
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
    
}
