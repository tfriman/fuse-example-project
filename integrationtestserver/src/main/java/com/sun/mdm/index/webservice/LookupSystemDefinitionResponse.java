
package com.sun.mdm.index.webservice;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


/**
 * <p>Java class for lookupSystemDefinitionResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lookupSystemDefinitionResponse"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="return" type="{http://webservice.index.mdm.sun.com/}systemDefinition" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lookupSystemDefinitionResponse", propOrder = {
    "_return"
})
@XmlRootElement(name = "lookupSystemDefinitionResponse")
public class LookupSystemDefinitionResponse
    implements Serializable
{

    private final static long serialVersionUID = 12343L;
    @XmlElement(name = "return")
    protected SystemDefinition _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link SystemDefinition }
     *     
     */
    public SystemDefinition getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemDefinition }
     *     
     */
    public void setReturn(SystemDefinition value) {
        this._return = value;
    }

    public boolean isSetReturn() {
        return (this._return!= null);
    }

}