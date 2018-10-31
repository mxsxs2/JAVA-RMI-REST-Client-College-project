//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2018.10.31 at 11:06:15 PM GMT 
//


package ie.gmit.sw.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for booking complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="booking"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="person" type="{http://sw.gmit.ie/model/}person"/&gt;
 *         &lt;element name="car" type="{http://sw.gmit.ie/model/}car"/&gt;
 *         &lt;element name="bookingTimeFrame" type="{http://sw.gmit.ie/model/}bookingTimeFrame"/&gt;
 *         &lt;element name="reservationTime" type="{http://www.w3.org/2001/XMLSchema}long"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "booking", propOrder = {
        "id",
        "person",
        "car",
        "bookingTimeFrame",
        "reservationTime"
})
@XmlRootElement(name = "booking")
public class Booking
        implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String id;
    @XmlElement(required = true)
    protected Person person;
    @XmlElement(required = true)
    protected Car car;
    @XmlElement(required = true)
    protected BookingTimeFrame bookingTimeFrame;
    protected long reservationTime;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        id = value;
    }

    /**
     * Gets the value of the person property.
     *
     * @return possible object is
     * {@link Person }
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Sets the value of the person property.
     *
     * @param value allowed object is
     *              {@link Person }
     */
    public void setPerson(Person value) {
        person = value;
    }

    /**
     * Gets the value of the car property.
     *
     * @return possible object is
     * {@link Car }
     */
    public Car getCar() {
        return car;
    }

    /**
     * Sets the value of the car property.
     *
     * @param value allowed object is
     *              {@link Car }
     */
    public void setCar(Car value) {
        car = value;
    }

    /**
     * Gets the value of the bookingTimeFrame property.
     *
     * @return possible object is
     * {@link BookingTimeFrame }
     */
    public BookingTimeFrame getBookingTimeFrame() {
        return bookingTimeFrame;
    }

    /**
     * Sets the value of the bookingTimeFrame property.
     *
     * @param value allowed object is
     *              {@link BookingTimeFrame }
     */
    public void setBookingTimeFrame(BookingTimeFrame value) {
        bookingTimeFrame = value;
    }

    /**
     * Gets the value of the reservationTime property.
     */
    public long getReservationTime() {
        return reservationTime;
    }

    /**
     * Sets the value of the reservationTime property.
     */
    public void setReservationTime(long value) {
        reservationTime = value;
    }

}
