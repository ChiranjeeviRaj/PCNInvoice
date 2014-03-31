//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.03.28 at 04:34:16 PM GMT 
//


package uk.pcn.invoice.model.external.parcels;

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
 *         &lt;element ref="{}congestionCharge" maxOccurs="unbounded"/>
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
    "congestionCharge"
})
@XmlRootElement(name = "congestionCharges")
public class CongestionCharges
    implements Equals, HashCode, ToString
{

    @XmlElement(required = true)
    protected List<CongestionCharge> congestionCharge;

    /**
     * Gets the value of the congestionCharge property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the congestionCharge property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCongestionCharge().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CongestionCharge }
     * 
     * 
     */
    public List<CongestionCharge> getCongestionCharge() {
        if (congestionCharge == null) {
            congestionCharge = new ArrayList<CongestionCharge>();
        }
        return this.congestionCharge;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof CongestionCharges)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final CongestionCharges that = ((CongestionCharges) object);
        {
            List<CongestionCharge> lhsCongestionCharge;
            lhsCongestionCharge = (((this.congestionCharge!= null)&&(!this.congestionCharge.isEmpty()))?this.getCongestionCharge():null);
            List<CongestionCharge> rhsCongestionCharge;
            rhsCongestionCharge = (((that.congestionCharge!= null)&&(!that.congestionCharge.isEmpty()))?that.getCongestionCharge():null);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "congestionCharge", lhsCongestionCharge), LocatorUtils.property(thatLocator, "congestionCharge", rhsCongestionCharge), lhsCongestionCharge, rhsCongestionCharge)) {
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
            List<CongestionCharge> theCongestionCharge;
            theCongestionCharge = (((this.congestionCharge!= null)&&(!this.congestionCharge.isEmpty()))?this.getCongestionCharge():null);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "congestionCharge", theCongestionCharge), currentHashCode, theCongestionCharge);
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
            List<CongestionCharge> theCongestionCharge;
            theCongestionCharge = (((this.congestionCharge!= null)&&(!this.congestionCharge.isEmpty()))?this.getCongestionCharge():null);
            strategy.appendField(locator, this, "congestionCharge", buffer, theCongestionCharge);
        }
        return buffer;
    }

}
