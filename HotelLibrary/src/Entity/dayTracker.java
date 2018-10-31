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

/**
 *
 * @author Joshua
 */
@Entity
public class dayTracker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToMany(mappedBy="dayBooked", cascade=ALL)
    private List<RoomEntity> roomsBooked;
    @ManyToMany
    private List<BookingOrder> booking;

    public dayTracker() {
    }

    public dayTracker(List<RoomEntity> roomsBooked, List<BookingOrder> booking) {
        this.roomsBooked = roomsBooked;
        this.booking = booking;
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
        if (!(object instanceof dayTracker)) {
            return false;
        }
        dayTracker other = (dayTracker) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Entity.dayTracker[ id=" + id + " ]";
    }

    public List<RoomEntity> getRoomsBooked() {
        return roomsBooked;
    }

    public void setRoomsBooked(List<RoomEntity> roomsBooked) {
        this.roomsBooked = roomsBooked;
    }

    public List<BookingOrder> getBooking() {
        return booking;
    }

    public void setBooking(List<BookingOrder> booking) {
        this.booking = booking;
    }
    
}
