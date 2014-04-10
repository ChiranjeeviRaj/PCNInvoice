//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.08 at 01:09:26 PM BST 
//


package uk.pcn.invoice.model.external.palets;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.jvnet.jaxb2_commons.lang.Equals;
import org.jvnet.jaxb2_commons.lang.EqualsStrategy;
import org.jvnet.jaxb2_commons.lang.HashCode;
import org.jvnet.jaxb2_commons.lang.HashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBEqualsStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBHashCodeStrategy;
import org.jvnet.jaxb2_commons.lang.JAXBToStringStrategy;
import org.jvnet.jaxb2_commons.lang.ToString;
import org.jvnet.jaxb2_commons.lang.ToStringStrategy;
import org.jvnet.jaxb2_commons.locator.ObjectLocator;
import org.jvnet.jaxb2_commons.locator.util.LocatorUtils;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}zones"/>
 *         &lt;element ref="{}platesServices"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "zones",
    "platesServices"
})
@XmlRootElement(name = "ukPaletsPrices")
public class UkPaletsPrices
    implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected Zones zones;
    @XmlElement(required = true)
    protected PlatesServices platesServices;

    /**
     * Gets the value of the zones property.
     * 
     * @return
     *     possible object is
     *     {@link Zones }
     *     
     */
    public Zones getZones() {
        return zones;
    }

    /**
     * Sets the value of the zones property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zones }
     *     
     */
    public void setZones(Zones value) {
        this.zones = value;
    }

    /**
     * Gets the value of the platesServices property.
     * 
     * @return
     *     possible object is
     *     {@link PlatesServices }
     *     
     */
    public PlatesServices getPlatesServices() {
        return platesServices;
    }

    /**
     * Sets the value of the platesServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlatesServices }
     *     
     */
    public void setPlatesServices(PlatesServices value) {
        this.platesServices = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof UkPaletsPrices)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final UkPaletsPrices that = ((UkPaletsPrices) object);
        {
            Zones lhsZones;
            lhsZones = this.getZones();
            Zones rhsZones;
            rhsZones = that.getZones();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "zones", lhsZones), LocatorUtils.property(thatLocator, "zones", rhsZones), lhsZones, rhsZones)) {
                return false;
            }
        }
        {
            PlatesServices lhsPlatesServices;
            lhsPlatesServices = this.getPlatesServices();
            PlatesServices rhsPlatesServices;
            rhsPlatesServices = that.getPlatesServices();
            if (!strategy.equals(LocatorUtils.property(thisLocator, "platesServices", lhsPlatesServices), LocatorUtils.property(thatLocator, "platesServices", rhsPlatesServices), lhsPlatesServices, rhsPlatesServices)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        final EqualsStrategy strategy = JAXBEqualsStrategy.INSTANCE;
        return equals(null, null, object, strategy);
    }

    public int hashCode(ObjectLocator locator, HashCodeStrategy strategy) {
        int currentHashCode = 1;
        {
            Zones theZones;
            theZones = this.getZones();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "zones", theZones), currentHashCode, theZones);
        }
        {
            PlatesServices thePlatesServices;
            thePlatesServices = this.getPlatesServices();
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "platesServices", thePlatesServices), currentHashCode, thePlatesServices);
        }
        return currentHashCode;
    }

    public int hashCode() {
        final HashCodeStrategy strategy = JAXBHashCodeStrategy.INSTANCE;
        return this.hashCode(null, strategy);
    }

    public String toString() {
        final ToStringStrategy strategy = JAXBToStringStrategy.INSTANCE;
        final StringBuilder buffer = new StringBuilder();
        append(null, buffer, strategy);
        return buffer.toString();
    }

    public StringBuilder append(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        strategy.appendStart(locator, this, buffer);
        appendFields(locator, buffer, strategy);
        strategy.appendEnd(locator, this, buffer);
        return buffer;
    }

    public StringBuilder appendFields(ObjectLocator locator, StringBuilder buffer, ToStringStrategy strategy) {
        {
            Zones theZones;
            theZones = this.getZones();
            strategy.appendField(locator, this, "zones", buffer, theZones);
        }
        {
            PlatesServices thePlatesServices;
            thePlatesServices = this.getPlatesServices();
            strategy.appendField(locator, this, "platesServices", buffer, thePlatesServices);
        }
        return buffer;
    }

}
