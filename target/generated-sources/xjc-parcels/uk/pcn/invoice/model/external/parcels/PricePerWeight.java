//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vhudson-jaxb-ri-2.1-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.04.08 at 01:09:25 PM BST 
//


package uk.pcn.invoice.model.external.parcels;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element ref="{}defaultMaxWeight"/>
 *         &lt;element ref="{}price"/>
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
    "defaultMaxWeight",
    "price"
})
@XmlRootElement(name = "pricePerWeight")
public class PricePerWeight
    implements Equals, HashCode, ToString
{

    protected double defaultMaxWeight;
    protected double price;

    /**
     * Gets the value of the defaultMaxWeight property.
     * 
     */
    public double getDefaultMaxWeight() {
        return defaultMaxWeight;
    }

    /**
     * Sets the value of the defaultMaxWeight property.
     * 
     */
    public void setDefaultMaxWeight(double value) {
        this.defaultMaxWeight = value;
    }

    /**
     * Gets the value of the price property.
     * 
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     * 
     */
    public void setPrice(double value) {
        this.price = value;
    }

    public boolean equals(ObjectLocator thisLocator, ObjectLocator thatLocator, Object object, EqualsStrategy strategy) {
        if (!(object instanceof PricePerWeight)) {
            return false;
        }
        if (this == object) {
            return true;
        }
        final PricePerWeight that = ((PricePerWeight) object);
        {
            double lhsDefaultMaxWeight;
            lhsDefaultMaxWeight = (true?this.getDefaultMaxWeight(): 0.0D);
            double rhsDefaultMaxWeight;
            rhsDefaultMaxWeight = (true?that.getDefaultMaxWeight(): 0.0D);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "defaultMaxWeight", lhsDefaultMaxWeight), LocatorUtils.property(thatLocator, "defaultMaxWeight", rhsDefaultMaxWeight), lhsDefaultMaxWeight, rhsDefaultMaxWeight)) {
                return false;
            }
        }
        {
            double lhsPrice;
            lhsPrice = (true?this.getPrice(): 0.0D);
            double rhsPrice;
            rhsPrice = (true?that.getPrice(): 0.0D);
            if (!strategy.equals(LocatorUtils.property(thisLocator, "price", lhsPrice), LocatorUtils.property(thatLocator, "price", rhsPrice), lhsPrice, rhsPrice)) {
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
            double theDefaultMaxWeight;
            theDefaultMaxWeight = (true?this.getDefaultMaxWeight(): 0.0D);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "defaultMaxWeight", theDefaultMaxWeight), currentHashCode, theDefaultMaxWeight);
        }
        {
            double thePrice;
            thePrice = (true?this.getPrice(): 0.0D);
            currentHashCode = strategy.hashCode(LocatorUtils.property(locator, "price", thePrice), currentHashCode, thePrice);
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
            double theDefaultMaxWeight;
            theDefaultMaxWeight = (true?this.getDefaultMaxWeight(): 0.0D);
            strategy.appendField(locator, this, "defaultMaxWeight", buffer, theDefaultMaxWeight);
        }
        {
            double thePrice;
            thePrice = (true?this.getPrice(): 0.0D);
            strategy.appendField(locator, this, "price", buffer, thePrice);
        }
        return buffer;
    }

}
