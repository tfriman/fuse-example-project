//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2019.03.08 at 07:23:44 PM EET 
//


package com.customer.app;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * "A dimensioned quantity expressing the result of measuring" - HL7 V3
 * 
 * <p>Java class for PhysicalQualtity complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PhysicalQualtity"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="unit" type="{http://www.app.customer.com}Code" minOccurs="0"/&gt;
 *         &lt;element name="value" type="{http://www.app.customer.com}Real" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PhysicalQualtity", propOrder = {
    "unit",
    "value"
})
@XmlRootElement(name = "PhysicalQualtity")
public class PhysicalQualtity
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    protected Code unit;
    protected Real value;

    /**
     * Gets the value of the unit property.
     * 
     * @return
     *     possible object is
     *     {@link Code }
     *     
     */
    public Code getUnit() {
        return unit;
    }

    /**
     * Sets the value of the unit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Code }
     *     
     */
    public void setUnit(Code value) {
        this.unit = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Real }
     *     
     */
    public Real getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Real }
     *     
     */
    public void setValue(Real value) {
        this.value = value;
    }

}
