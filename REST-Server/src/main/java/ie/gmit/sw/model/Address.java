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
 * <p>Java class for address complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="address"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="street" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="city" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="county" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "address", propOrder = {
        "street",
        "city",
        "county"
})
@XmlRootElement(name = "address")
public class Address
        implements Serializable {

    private final static long serialVersionUID = 1L;
    @XmlElement(required = true)
    protected String street;
    @XmlElement(required = true)
    protected String city;
    @XmlElement(required = true)
    protected String county;

    /**
     * Gets the value of the street property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets the value of the street property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStreet(String value) {
        street = value;
    }

    /**
     * Gets the value of the city property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the value of the city property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCity(String value) {
        city = value;
    }

    /**
     * Gets the value of the county property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCounty() {
        return county;
    }

    /**
     * Sets the value of the county property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCounty(String value) {
        county = value;
    }

}
