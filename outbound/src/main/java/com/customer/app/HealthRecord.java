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
 * Patient's Health Record - This class is used to store a reference to the health record that is the subject of the consent rules in the Consent Directive.
 * 
 * <p>Java class for HealthRecord complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HealthRecord"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="recordid" type="{http://www.app.customer.com}ID" minOccurs="0"/&gt;
 *         &lt;element name="recordlocation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="effectivedaterange" type="{http://www.app.customer.com}TimeInterval" minOccurs="0"/&gt;
 *         &lt;element name="status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HealthRecord", propOrder = {
    "recordid",
    "recordlocation",
    "effectivedaterange",
    "status"
})
@XmlRootElement(name = "HealthRecord")
public class HealthRecord
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    protected ID recordid;
    protected String recordlocation;
    protected TimeInterval effectivedaterange;
    protected String status;

    /**
     * Gets the value of the recordid property.
     * 
     * @return
     *     possible object is
     *     {@link ID }
     *     
     */
    public ID getRecordid() {
        return recordid;
    }

    /**
     * Sets the value of the recordid property.
     * 
     * @param value
     *     allowed object is
     *     {@link ID }
     *     
     */
    public void setRecordid(ID value) {
        this.recordid = value;
    }

    /**
     * Gets the value of the recordlocation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecordlocation() {
        return recordlocation;
    }

    /**
     * Sets the value of the recordlocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecordlocation(String value) {
        this.recordlocation = value;
    }

    /**
     * Gets the value of the effectivedaterange property.
     * 
     * @return
     *     possible object is
     *     {@link TimeInterval }
     *     
     */
    public TimeInterval getEffectivedaterange() {
        return effectivedaterange;
    }

    /**
     * Sets the value of the effectivedaterange property.
     * 
     * @param value
     *     allowed object is
     *     {@link TimeInterval }
     *     
     */
    public void setEffectivedaterange(TimeInterval value) {
        this.effectivedaterange = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
