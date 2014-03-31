//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.03.28 at 04:34:17 PM GMT 
//


package uk.pcn.invoice.model.external.palets;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element ref="{}paletsService" maxOccurs="unbounded"/>
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
    "paletsService"
})
@XmlRootElement(name = "platesServices")
public class PlatesServices
    implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected List<PaletsService> paletsService;

    /**
     * Gets the value of the paletsService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the paletsService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPaletsService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaletsService }
     * 
     * 
     */
    public List<PaletsService> getPaletsService() {
        if (paletsService == null) {
            paletsService = new ArrayList<PaletsService>();
        }
        return this.paletsService;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof PlatesServices)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final PlatesServices that = ((PlatesServices) object);
        {
            List<PaletsService> lhsPaletsService;
            lhsPaletsService = (((this.paletsService!= null)&&(!this.paletsService.isEmpty()))?this.getPaletsService():null);
            List<PaletsService> rhsPaletsService;
            rhsPaletsService = (((that.paletsService!= null)&&(!that.paletsService.isEmpty()))?that.getPaletsService():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "paletsService", lhsPaletsService), LocatorUtils.property(thatLocator, "paletsService", rhsPaletsService), lhsPaletsService, rhsPaletsService)) {
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
            List<PaletsService> thePaletsService;
            thePaletsService = (((this.paletsService!= null)&&(!this.paletsService.isEmpty()))?this.getPaletsService():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "paletsService", thePaletsService), currentHashCode, thePaletsService);
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
            List<PaletsService> thePaletsService;
            thePaletsService = (((this.paletsService!= null)&&(!this.paletsService.isEmpty()))?this.getPaletsService():null);
            strategy.appendField(locator, this, "paletsService", buffer, thePaletsService);
        }
        return buffer;
    }

}
